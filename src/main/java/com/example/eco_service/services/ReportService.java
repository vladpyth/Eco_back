package com.example.eco_service.services;

import com.example.eco_service.dto.main_dto.WasteReportDto;
import com.example.eco_service.dto.response.PhoneOnObjectResponse;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

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
    public List<Map<String, Object>> getDetailedSummaryByRegion() {
        log.info("Generating detailed summary report by region");

        List<Region> regions = regionRepository.findAll();
        List<Map<String, Object>> summaryData = new ArrayList<>();

        for (Region region : regions) {
            // Находим города в регионе
            List<Cities> citiesInRegion = citiesRepository.findAll()
                    .stream()
                    .filter(city -> city.getId_region() != null &&
                            city.getId_region().getId_region().equals(region.getId_region()))
                    .collect(Collectors.toList());

            // Находим объекты в этих городах (поле status в UI — «Исключен»: true = не включать в отчёт)
            List<ObjectPlaceTrash> objectsInRegion = objectPlaceTrashRepository.findAll()
                    .stream()
                    .filter(obj -> obj.getId_cities() != null &&
                            citiesInRegion.contains(obj.getId_cities()))
                    .filter(obj -> !Boolean.TRUE.equals(obj.getStatus()))
                    .collect(Collectors.toList());

            // Собираем детальную информацию по объектам
            List<Map<String, Object>> objectsDetails = new ArrayList<>();
            long objectCount = objectsInRegion.size();
            double totalWeight = 0;
            double totalSquare = 0;

            for (ObjectPlaceTrash object : objectsInRegion) {
                List<CharacteristicTrash> characteristics = characteristicTrashRepository.findAll()
                        .stream()
                        .filter(ct -> ct.getId_object_place_trash() != null &&
                                ct.getId_object_place_trash().getId_object_place_trash()
                                        .equals(object.getId_object_place_trash()))
                        .collect(Collectors.toList());

                for (CharacteristicTrash ct : characteristics) {
                    totalWeight += ct.getWeight_for_year();
                    totalSquare += ct.getSquare_for_year();
                }

                // Детальная информация по объекту
                Map<String, Object> objectDetail = new HashMap<>();
                objectDetail.put("objectName", object.getName_obj());
                objectDetail.put("objectLocation", object.getPlace_obj());
                objectDetail.put("ownerName", object.getName_own());
                objectDetail.put("companyLocated", object.getCompany_located());
                objectDetail.put("phonesLegal", joinPhonesByUrRole(object.getPhones(), true));
                objectDetail.put("phonesOwner", joinPhonesByUrRole(object.getPhones(), false));
                objectDetail.put(
                        "phones",
                        joinPhonesByUrRole(object.getPhones(), true)
                );
                objectDetail.put(
                        "groupPlaceName",
                        object.getId_group_place_save() != null
                                ? object.getId_group_place_save().getName_group()
                                : null
                );
                objectDetail.put("status", object.getStatus() ? "Активен" : "Неактивен");
                objectDetail.put("registrationNumber", object.getId_registration());
                objectDetail.put("payerIdentificationNumber", object.getPayer_indentification_number());
                objectDetail.put("startUse", object.getStart_use());
                objectDetail.put("square", object.getSquare());

                // Получаем группы отходов для объекта
                List<String> groups = getNameGroupsByObject(object);
                objectDetail.put("wasteGroups", String.join(", ", groups));

                objectsDetails.add(objectDetail);
            }

            Map<String, Object> regionSummary = new LinkedHashMap<>();
            regionSummary.put("regionName", region.getName_region());
            regionSummary.put("objectCount", objectCount);
            regionSummary.put("totalWeight", totalWeight);
            regionSummary.put("totalSquare", totalSquare);
            regionSummary.put("objects", objectsDetails);

            summaryData.add(regionSummary);
        }

        // Сортируем по количеству объектов
        summaryData.sort((a, b) -> Long.compare(
                (Long) b.get("objectCount"),
                (Long) a.get("objectCount")
        ));

        log.info("Generated detailed summary report with {} regions", summaryData.size());
        return summaryData;
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

        // Находим все объекты в городах этого региона
        List<ObjectPlaceTrash> objectsInRegion = objectPlaceTrashRepository.findAll()
                .stream()
                .filter(obj -> obj.getId_cities() != null &&
                        obj.getId_cities().getId_region() != null &&
                        obj.getId_cities().getId_region().getId_region().equals(region.getId_region()))
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
            // Находим города в регионе
            List<Cities> citiesInRegion = citiesRepository.findAll()
                    .stream()
                    .filter(city -> city.getId_region() != null &&
                            city.getId_region().getId_region().equals(region.getId_region()))
                    .collect(Collectors.toList());

            // Находим объекты в этих городах
            List<ObjectPlaceTrash> objectsInRegion = objectPlaceTrashRepository.findAll()
                    .stream()
                    .filter(obj -> obj.getId_cities() != null &&
                            citiesInRegion.contains(obj.getId_cities()))
                    .collect(Collectors.toList());

            long objectCount = objectsInRegion.size();
            double totalWeight = 0;
            double totalSquare = 0;

            // Суммируем характеристики
            for (ObjectPlaceTrash object : objectsInRegion) {
                List<CharacteristicTrash> characteristics = characteristicTrashRepository.findAll()
                        .stream()
                        .filter(ct -> ct.getId_object_place_trash() != null &&
                                ct.getId_object_place_trash().getId_object_place_trash()
                                        .equals(object.getId_object_place_trash()))
                        .collect(Collectors.toList());

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
        if (object.getId_cities() != null &&
                object.getId_cities().getId_region() != null) {
            return object.getId_cities().getId_region().getName_region();
        }
        return "Не указан";
    }

    private List<String> getNameGroupsByObject(ObjectPlaceTrash object) {
        List<CharacteristicTrash> characteristics = characteristicTrashRepository.findAll()
                .stream()
                .filter(ct -> ct.getId_object_place_trash() != null &&
                        ct.getId_object_place_trash().getId_object_place_trash()
                                .equals(object.getId_object_place_trash()))
                .collect(Collectors.toList());

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
