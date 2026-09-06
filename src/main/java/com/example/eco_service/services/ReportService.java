package com.example.eco_service.services;

import com.example.eco_service.dto.main_dto.WasteReportDto;
import com.example.eco_service.dto.main_dto.WasteRegisterObjectDto;
import com.example.eco_service.dto.main_dto.WasteRegisterRegionDto;
import com.example.eco_service.dto.main_dto.PortalRhzoRowDto;
import com.example.eco_service.dto.response.PageResponse;
import com.example.eco_service.dto.response.PhoneOnObjectResponse;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final InterfObjectPlaceTrash objectPlaceTrashRepository;
    private final InterfRegion regionRepository;
    private final InterfNameGroup nameGroupRepository;
    private final InterfCharacteristicTrash characteristicTrashRepository;
    private final InterfMagazinTrash magazinTrashRepository;
    private final InterfCities citiesRepository;
    private final EntityManager entityManager;

    /**
     * Получить полный отчет по всем объектам размещения отходов
     * с данными о регионе и группе отходов
     */
    @Transactional(readOnly = true)
    public List<WasteReportDto> getFullWasteReport() {
        log.info("Generating full waste report");

        List<ObjectPlaceTrash> objects = objectPlaceTrashRepository.findAll();
        List<WasteReportDto> report = new ArrayList<>();

        for (ObjectPlaceTrash object : objects) {
            String regionName = getRegionNameByObject(object);
            List<String> nameGroups = getNameGroupsByObject(object);

            if (nameGroups.isEmpty()) {
                report.add(createReportDto(object, regionName, "Нет данных"));
            } else {
                for (String nameGroup : nameGroups) {
                    report.add(createReportDto(object, regionName, nameGroup));
                }
            }
        }

        log.info("Generated report with {} records", report.size());
        return report;
    }

    /**
     * Получить сводный отчет по регионам с детальной информацией
     * Возвращает список объектов с полной информацией для PDF
     */
    @Transactional(readOnly = true)
    public List<WasteRegisterRegionDto> getDetailedSummaryByRegion() {
        log.info("Generating detailed summary report by region");

        List<Region> regions = regionRepository.findAll();
        List<ObjectPlaceTrash> allObjects = objectPlaceTrashRepository.findAll();
        List<WasteRegisterRegionDto> summaryData = new ArrayList<>();

        for (Region region : regions) {
            List<ObjectPlaceTrash> objectsInRegion = allObjects.stream()
                    .filter(obj -> objectBelongsToRegion(obj, region.getId_region()))
                    .filter(obj -> !Boolean.TRUE.equals(obj.getStatus()))
                    .collect(Collectors.toList());

            List<WasteRegisterObjectDto> objectsDetails = new ArrayList<>();
            long objectCount = objectsInRegion.size();
            double totalWeight = 0;
            double totalSquare = 0;

            for (ObjectPlaceTrash object : objectsInRegion) {
                List<CharacteristicTrash> characteristics =
                        characteristicTrashRepository.findAllByObjectPlaceTrashId(
                                object.getId_object_place_trash());

                for (CharacteristicTrash ct : characteristics) {
                    totalWeight += ct.getWeight_for_year();
                    totalSquare += ct.getSquare_for_year();
                }

                objectsDetails.add(buildRegisterObjectDetail(object));
            }

            // Группы мест хранения: сначала объекты с группой (по имени), затем без группы
            objectsDetails.sort((a, b) -> {
                String ga = a.getGroupPlaceName() == null ? "" : a.getGroupPlaceName();
                String gb = b.getGroupPlaceName() == null ? "" : b.getGroupPlaceName();
                int cmp = ga.compareToIgnoreCase(gb);
                if (cmp != 0) return cmp;
                String na = a.getObjectName() == null ? "" : a.getObjectName();
                String nb = b.getObjectName() == null ? "" : b.getObjectName();
                return na.compareToIgnoreCase(nb);
            });

            WasteRegisterRegionDto regionSummary = WasteRegisterRegionDto.builder()
                    .regionName(region.getName_region())
                    .objectCount(objectCount)
                    .totalWeight(totalWeight)
                    .totalSquare(totalSquare)
                    .objects(objectsDetails)
                    .build();

            summaryData.add(regionSummary);
        }

        summaryData.sort((a, b) -> Long.compare(
                b.getObjectCount(),
                a.getObjectCount()
        ));

        log.info("Generated detailed summary report with {} regions", summaryData.size());
        return summaryData;
    }

    /**
     * Плоский публичный реестр РХЗО с серверной пагинацией (portal).
     * Без характеристик отходов и без загрузки всей таблицы в память.
     */
    @Transactional(readOnly = true)
    public PageResponse<PortalRhzoRowDto> getPortalTablePaged(
            Integer page, Integer size, String q, String sort, String dir) {
        log.info("Portal RHZO table page={} size={} q={}", page, size, q);

        Specification<ObjectPlaceTrash> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // status=true в UI — «Исключен»
            predicates.add(cb.or(cb.isNull(root.get("status")), cb.isFalse(root.get("status"))));

            boolean isCount = query != null
                    && (query.getResultType() == Long.class || query.getResultType() == long.class);

            if (!isCount && query != null) {
                applyPortalSort(root, query, cb, sort, dir);
            }

            if (q != null && !q.isBlank()) {
                String like = "%" + q.trim().toLowerCase() + "%";
                var regionJoin = root.join("id_region", JoinType.LEFT);
                var groupJoin = root.join("id_group_place_save", JoinType.LEFT);
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("id_registration")), like),
                        cb.like(cb.lower(root.get("name_obj")), like),
                        cb.like(cb.lower(root.get("name_own")), like),
                        cb.like(cb.lower(root.get("place_obj")), like),
                        cb.like(cb.lower(root.get("company_located")), like),
                        cb.like(cb.lower(root.get("payer_indentification_number")), like),
                        cb.like(cb.lower(regionJoin.get("name_region")), like),
                        cb.like(cb.lower(groupJoin.get("name_group")), like)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<ObjectPlaceTrash> result = objectPlaceTrashRepository.findAll(
                spec, PageSupport.pageable(page, size, "id_object_place_trash"));

        Page<PortalRhzoRowDto> mapped = result.map(this::toPortalRow);
        return PageResponse.from(mapped);
    }

    private void applyPortalSort(
            jakarta.persistence.criteria.Root<ObjectPlaceTrash> root,
            jakarta.persistence.criteria.CriteriaQuery<?> query,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            String sort,
            String dir) {
        boolean asc = dir == null || !"desc".equalsIgnoreCase(dir.trim());
        String key = sort == null ? "" : sort.trim();
        jakarta.persistence.criteria.Order order;
        switch (key) {
            case "region" -> order = asc
                    ? cb.asc(root.get("id_region").get("name_region"))
                    : cb.desc(root.get("id_region").get("name_region"));
            case "groupPlaceName" -> order = asc
                    ? cb.asc(root.get("id_group_place_save").get("name_group"))
                    : cb.desc(root.get("id_group_place_save").get("name_group"));
            case "registrationNumber" -> order = asc
                    ? cb.asc(root.get("id_registration"))
                    : cb.desc(root.get("id_registration"));
            case "objectName" -> order = asc
                    ? cb.asc(root.get("name_obj"))
                    : cb.desc(root.get("name_obj"));
            case "objectLocation" -> order = asc
                    ? cb.asc(root.get("place_obj"))
                    : cb.desc(root.get("place_obj"));
            case "applicantName" -> order = asc
                    ? cb.asc(root.get("name_own"))
                    : cb.desc(root.get("name_own"));
            case "applicantAddress" -> order = asc
                    ? cb.asc(root.get("company_located"))
                    : cb.desc(root.get("company_located"));
            case "unp" -> order = asc
                    ? cb.asc(root.get("payer_indentification_number"))
                    : cb.desc(root.get("payer_indentification_number"));
            default -> order = cb.asc(root.get("id_registration"));
        }
        query.orderBy(order, cb.asc(root.get("id_object_place_trash")));
    }

    private PortalRhzoRowDto toPortalRow(ObjectPlaceTrash object) {
        String region = object.getId_region() != null ? object.getId_region().getName_region() : null;
        if ((region == null || region.isBlank())
                && object.getId_cities() != null
                && object.getId_cities().getId_region() != null) {
            region = object.getId_cities().getId_region().getName_region();
        }
        return PortalRhzoRowDto.builder()
                .region(region)
                .groupPlaceName(object.getId_group_place_save() != null
                        ? object.getId_group_place_save().getName_group()
                        : null)
                .registrationNumber(object.getId_registration())
                .objectName(object.getName_obj())
                .objectLocation(object.getPlace_obj())
                .objectPhone(joinPhonesByUrRole(object.getPhones(), false))
                .applicantName(object.getName_own())
                .applicantAddress(object.getCompany_located())
                .applicantPhone(joinPhonesByUrRole(object.getPhones(), true))
                .unp(object.getPayer_indentification_number())
                .build();
    }

    /**
     * Получить отчет только по активным объектам
     */
    @Transactional(readOnly = true)
    public List<WasteReportDto> getActiveWasteReport() {
        log.info("Generating active waste report");

        List<ObjectPlaceTrash> activeObjects = objectPlaceTrashRepository.findAll()
                .stream()
                .filter(obj -> obj.getStatus() != null && obj.getStatus())
                .collect(Collectors.toList());

        List<WasteReportDto> report = new ArrayList<>();

        for (ObjectPlaceTrash object : activeObjects) {
            String regionName = getRegionNameByObject(object);
            List<String> nameGroups = getNameGroupsByObject(object);

            for (String nameGroup : nameGroups) {
                report.add(createReportDto(object, regionName, nameGroup));
            }
        }

        log.info("Generated active report with {} records", report.size());
        return report;
    }

    /**
     * Получить отчет по конкретному региону
     */
    @Transactional(readOnly = true)
    public List<WasteReportDto> getReportByRegion(String regionName) {
        log.info("Generating report for region: {}", regionName);

        // Находим регион по имени
        Region region = regionRepository.findAll()
                .stream()
                .filter(r -> r.getName_region().equalsIgnoreCase(regionName))
                .findFirst()
                .orElse(null);

        if (region == null) {
            log.warn("Region not found: {}", regionName);
            return new ArrayList<>();
        }

        // Находим все объекты этой области (прямо по id_region или через город)
        List<ObjectPlaceTrash> objectsInRegion = objectPlaceTrashRepository.findAll()
                .stream()
                .filter(obj -> objectBelongsToRegion(obj, region.getId_region()))
                .collect(Collectors.toList());

        List<WasteReportDto> report = new ArrayList<>();

        for (ObjectPlaceTrash object : objectsInRegion) {
            List<String> nameGroups = getNameGroupsByObject(object);

            for (String nameGroup : nameGroups) {
                report.add(createReportDto(object, region.getName_region(), nameGroup));
            }
        }

        log.info("Generated report for region {} with {} records", regionName, report.size());
        return report;
    }

    /**
     * Получить отчет по конкретной группе отходов
     */
    @Transactional(readOnly = true)
    public List<WasteReportDto> getReportByNameGroup(String nameGroupName) {
        log.info("Generating report for name group: {}", nameGroupName);

        // Находим группу по имени
        NameGroup nameGroup = nameGroupRepository.findAll()
                .stream()
                .filter(ng -> ng.getName_group().equalsIgnoreCase(nameGroupName))
                .findFirst()
                .orElse(null);

        if (nameGroup == null) {
            log.warn("Name group not found: {}", nameGroupName);
            return new ArrayList<>();
        }

        // Находим все магазины отходов с этой группой
        List<MagazinTrash> magazinTrashes = magazinTrashRepository.findAll()
                .stream()
                .filter(mt -> mt.getId_mame_group() != null &&
                        mt.getId_mame_group().getId_mame_group().equals(nameGroup.getId_mame_group()))
                .collect(Collectors.toList());

        // Находим характеристики отходов с этими магазинами
        List<CharacteristicTrash> characteristics = characteristicTrashRepository.findAll()
                .stream()
                .filter(ct -> magazinTrashes.contains(ct.getId_magazin_trash()))
                .collect(Collectors.toList());

        // Находим объекты по характеристикам
        List<ObjectPlaceTrash> objectsWithGroup = characteristics.stream()
                .map(CharacteristicTrash::getId_object_place_trash)
                .distinct()
                .collect(Collectors.toList());

        List<WasteReportDto> report = new ArrayList<>();

        for (ObjectPlaceTrash object : objectsWithGroup) {
            String regionName = getRegionNameByObject(object);
            report.add(createReportDto(object, regionName, nameGroup.getName_group()));
        }

        log.info("Generated report for name group {} with {} records", nameGroupName, report.size());
        return report;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSummaryReportByRegion() {
        log.info("Generating summary report by region");

        // Получаем все регионы
        List<Region> regions = regionRepository.findAll();
        List<Map<String, Object>> summaryData = new ArrayList<>();

        for (Region region : regions) {
            List<ObjectPlaceTrash> objectsInRegion = objectPlaceTrashRepository.findAll()
                    .stream()
                    .filter(obj -> objectBelongsToRegion(obj, region.getId_region()))
                    .collect(Collectors.toList());

            long objectCount = objectsInRegion.size();
            double totalWeight = 0;
            double totalSquare = 0;

            // Суммируем характеристики
            for (ObjectPlaceTrash object : objectsInRegion) {
                List<CharacteristicTrash> characteristics =
                        characteristicTrashRepository.findAllByObjectPlaceTrashId(
                                object.getId_object_place_trash());

                for (CharacteristicTrash ct : characteristics) {
                    totalWeight += ct.getWeight_for_year();
                    totalSquare += ct.getSquare_for_year();
                }
            }

            Map<String, Object> regionSummary = new HashMap<>();
            regionSummary.put("regionName", region.getName_region());
            regionSummary.put("objectCount", objectCount);
            regionSummary.put("totalWeight", totalWeight);
            regionSummary.put("totalSquare", totalSquare);

            summaryData.add(regionSummary);
        }

        // Сортируем по количеству объектов
        summaryData.sort((a, b) -> Long.compare(
                (Long) b.get("objectCount"),
                (Long) a.get("objectCount")
        ));

        log.info("Generated summary report with {} regions", summaryData.size());
        return summaryData;
    }

    /**
     * Получить сводный отчет по группам отходов
     * Возвращает List<Map<String, Object>>
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSummaryReportByNameGroup() {
        log.info("Generating summary report by name group");

        List<NameGroup> nameGroups = nameGroupRepository.findAll();
        List<Map<String, Object>> summaryData = new ArrayList<>();

        for (NameGroup nameGroup : nameGroups) {
            // Находим магазины отходов с этой группой
            List<MagazinTrash> magazinTrashes = magazinTrashRepository.findAll()
                    .stream()
                    .filter(mt -> mt.getId_mame_group() != null &&
                            mt.getId_mame_group().getId_mame_group().equals(nameGroup.getId_mame_group()))
                    .collect(Collectors.toList());

            // Находим характеристики
            List<CharacteristicTrash> characteristics = characteristicTrashRepository.findAll()
                    .stream()
                    .filter(ct -> magazinTrashes.contains(ct.getId_magazin_trash()))
                    .collect(Collectors.toList());

            // Находим объекты
            long objectCount = characteristics.stream()
                    .map(CharacteristicTrash::getId_object_place_trash)
                    .distinct()
                    .count();

            double totalWeight = characteristics.stream()
                    .mapToDouble(CharacteristicTrash::getWeight_for_year)
                    .sum();

            double totalSquare = characteristics.stream()
                    .mapToDouble(CharacteristicTrash::getSquare_for_year)
                    .sum();

            Map<String, Object> groupSummary = new HashMap<>();
            groupSummary.put("nameGroup", nameGroup.getName_group());
            groupSummary.put("objectCount", objectCount);
            groupSummary.put("totalWeight", totalWeight);
            groupSummary.put("totalSquare", totalSquare);

            summaryData.add(groupSummary);
        }

        // Сортируем по количеству объектов
        summaryData.sort((a, b) -> Long.compare(
                (Long) b.get("objectCount"),
                (Long) a.get("objectCount")
        ));

        log.info("Generated summary report with {} name groups", summaryData.size());
        return summaryData;
    }

    /**
     * Получить детальный отчет с фильтрацией по региону и группе отходов
     */
    @Transactional(readOnly = true)
    public List<WasteReportDto> getDetailedReport(String regionName, String nameGroupName) {
        log.info("Generating detailed report for region: {}, name group: {}", regionName, nameGroupName);

        List<ObjectPlaceTrash> filteredObjects = objectPlaceTrashRepository.findAll()
                .stream()
                .filter(obj -> {
                    // Фильтр по региону
                    boolean regionMatches = true;
                    if (regionName != null && !regionName.isEmpty()) {
                        String objRegion = getRegionNameByObject(obj);
                        regionMatches = objRegion.equalsIgnoreCase(regionName);
                    }

                    // Фильтр по группе отходов
                    boolean groupMatches = true;
                    if (nameGroupName != null && !nameGroupName.isEmpty()) {
                        List<String> groups = getNameGroupsByObject(obj);
                        groupMatches = groups.stream()
                                .anyMatch(g -> g.equalsIgnoreCase(nameGroupName));
                    }

                    return regionMatches && groupMatches;
                })
                .collect(Collectors.toList());

        List<WasteReportDto> report = new ArrayList<>();

        for (ObjectPlaceTrash object : filteredObjects) {
            String objectRegion = getRegionNameByObject(object);
            List<String> nameGroups = getNameGroupsByObject(object);

            for (String group : nameGroups) {
                report.add(createReportDto(object, objectRegion, group));
            }
        }

        log.info("Generated detailed report with {} records", report.size());
        return report;
    }

    // Вспомогательные методы

    private String getRegionNameByObject(ObjectPlaceTrash object) {
        if (object.getId_region() != null && object.getId_region().getName_region() != null) {
            return object.getId_region().getName_region();
        }
        if (object.getId_cities() != null &&
                object.getId_cities().getId_region() != null) {
            return object.getId_cities().getId_region().getName_region();
        }
        return "Не указан";
    }

    private boolean objectBelongsToRegion(ObjectPlaceTrash object, Long regionId) {
        if (regionId == null) {
            return false;
        }
        if (object.getId_region() != null && object.getId_region().getId_region() != null) {
            return regionId.equals(object.getId_region().getId_region());
        }
        if (object.getId_cities() != null &&
                object.getId_cities().getId_region() != null &&
                object.getId_cities().getId_region().getId_region() != null) {
            return regionId.equals(object.getId_cities().getId_region().getId_region());
        }
        return false;
    }

    private List<String> getNameGroupsByObject(ObjectPlaceTrash object) {
        List<CharacteristicTrash> characteristics =
                characteristicTrashRepository.findAllByObjectPlaceTrashId(
                        object.getId_object_place_trash());

        List<String> nameGroups = new ArrayList<>();

        for (CharacteristicTrash characteristic : characteristics) {
            if (characteristic.getId_magazin_trash() != null &&
                    characteristic.getId_magazin_trash().getId_mame_group() != null) {
                nameGroups.add(characteristic.getId_magazin_trash()
                        .getId_mame_group().getName_group());
            }
        }

        return nameGroups.stream().distinct().collect(Collectors.toList());
    }

    private WasteReportDto createReportDto(ObjectPlaceTrash object, String regionName, String nameGroup) {
        return WasteReportDto.builder()
                .regionName(regionName)
                .nameGroup(nameGroup)
                .objectName(object.getName_obj())
                .objectLocation(object.getPlace_obj())
                .ownerName(object.getName_own())
                .locatedCompany(object.getCompany_located())
                .objectId(object.getId_object_place_trash())
                .registrationNumber(object.getId_registration())
                .status(object.getStatus())
                .build();
    }

    private WasteRegisterObjectDto buildRegisterObjectDetail(ObjectPlaceTrash object) {
        List<String> groups = getNameGroupsByObject(object);
        return WasteRegisterObjectDto.builder()
                .objectName(object.getName_obj())
                .objectLocation(object.getPlace_obj())
                .ownerName(object.getName_own())
                .companyLocated(object.getCompany_located())
                .phonesLegal(joinPhonesByUrRole(object.getPhones(), true))
                .phonesOwner(joinPhonesByUrRole(object.getPhones(), false))
                .phones(joinPhonesByUrRole(object.getPhones(), true))
                .groupPlaceName(object.getId_group_place_save() != null
                        ? object.getId_group_place_save().getName_group()
                        : null)
                .status(Boolean.TRUE.equals(object.getStatus()) ? "Активен" : "Неактивен")
                .registrationNumber(object.getId_registration())
                .payerIdentificationNumber(object.getPayer_indentification_number())
                .startUse(object.getStart_use())
                .square(object.getSquare())
                .wasteGroups(String.join(", ", groups))
                .build();
    }

    /** legal: юр. телефоны (0 или 3); иначе телефоны собственника (1 или 3). */
    private String joinPhonesByUrRole(List<PhoneOnObjectResponse> phones, boolean legal) {
        if (phones == null || phones.isEmpty()) {
            return "";
        }
        return phones.stream()
                .filter(p -> {
                    int u = p.getUr_ob();
                    if (legal) {
                        return u == 0 || u == 3;
                    }
                    return u == 1 || u == 3;
                })
                .map(PhoneOnObjectResponse::getNumber)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.joining("\n"));
    }
}
