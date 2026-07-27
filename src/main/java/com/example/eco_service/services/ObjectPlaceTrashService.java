package com.example.eco_service.services;

import com.example.eco_service.dto.request.ObjectAroundBuildLinkRequest;
import com.example.eco_service.dto.request.ObjectNatualSaveBuildLinkRequest;
import com.example.eco_service.dto.request.ObjectPlaceTrashRequest;
import com.example.eco_service.dto.response.PageResponse;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ObjectPlaceTrashService {

    private final InterfObjectPlaceTrash objectPlaceTrashRepository;

    // Репозитории для OneToOne связей
    private final InterfCities citiesRepository;
    private final InterfRegion regionRepository;
    private final InterfGroupPlaceSave groupPlaceSaveRepository;
    private final InterfStorageScheme storageSchemeRepository;
    private final InterfGruopsDegree gruopsDegreeRepository;
    private final InterfAroundBuild aroundBuildRepository;
    private final InterfNatualSaveBuilding natualSaveBuildingRepository;
    private final InterfAroundBuildCount aroundBuildCountRepository;
    private final InterfNatualSaveBuildCount natualSaveBuildCountRepository;

    // ==================== CREATE ====================
    public ObjectPlaceTrash createObjectPlaceTrash(ObjectPlaceTrashRequest request) {
        log.info("Creating ObjectPlaceTrash with registration: {}", request.getIdRegistration());

        if (request.getIdRegistration() != null
                && objectPlaceTrashRepository.existsByRegistrationNumber(request.getIdRegistration())) {
            throw new RuntimeException("Регистрационный номер уже существует у другой записи");
        }

        ObjectPlaceTrash entity = mapToEntity(request, new ObjectPlaceTrash());

        return objectPlaceTrashRepository.save(entity);
    }

    // ==================== READ (с пагинацией и фильтрацией) ====================
    @Transactional(readOnly = true)
    public Page<ObjectPlaceTrash> findAllObjectPlaceTrashWithPagination(Pageable pageable, ObjectPlaceTrashFilter filter) {
        log.info("Fetching all ObjectPlaceTrash with pagination and filter");
        Specification<ObjectPlaceTrash> spec = buildFilterSpecification(filter);
        return objectPlaceTrashRepository.findAll(spec, pageable);
    }

    // ==================== READ (без пагинации) ====================
    @Transactional(readOnly = true)
    public List<ObjectPlaceTrash> findAllObjectPlaceTrash() {
        log.info("Fetching all ObjectPlaceTrash without pagination");
        return objectPlaceTrashRepository.findAll();
    }

    /** page+size+q+sort+dir — как в РОИО MagasinFactory.
     * includeExcluded=true — показывать исключённые (status=true); иначе скрывать их. */
    @Transactional(readOnly = true)
    public PageResponse<ObjectPlaceTrash> findAllObjectPlaceTrashPaged(
            Integer page, Integer size, String q, String sort, String dir, Boolean includeExcluded) {
        log.info("Fetching ObjectPlaceTrash paged page={} size={} q={} includeExcluded={}",
                page, size, q, includeExcluded);
        Specification<ObjectPlaceTrash> spec =
                PageSupport.textSearch(q, "id_object_place_trash", sort, dir, "id_registration", true);
        if (!Boolean.TRUE.equals(includeExcluded)) {
            spec = spec.and((root, query, cb) ->
                    cb.or(cb.isNull(root.get("status")), cb.isFalse(root.get("status"))));
        }
        return PageResponse.from(objectPlaceTrashRepository.findAll(
                spec,
                PageSupport.pageable(page, size, "id_object_place_trash")));
    }

    // ==================== READ by ID ====================
    @Transactional(readOnly = true)
    public ObjectPlaceTrash findByIdObjectPlaceTrash(Long id) {
        log.info("Fetching ObjectPlaceTrash by id: {}", id);
        return objectPlaceTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AroundBuild> findAroundBuildsByObjectPlaceTrash(Long objectId) {
        objectPlaceTrashRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + objectId));
        return aroundBuildCountRepository.findAllByObjectPlaceId(objectId).stream()
                .map(AroundBuildCount::getId_around_build)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NatualSaveBuilding> findNatualSaveBuildsByObjectPlaceTrash(Long objectId) {
        objectPlaceTrashRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + objectId));
        return natualSaveBuildCountRepository.findAllByObjectPlaceId(objectId).stream()
                .map(NatualSaveBuildCount::getId_natual_save_build)
                .toList();
    }

    public AroundBuild addAroundBuildToObjectPlaceTrash(Long objectId, ObjectAroundBuildLinkRequest request) {
        ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + objectId));
        AroundBuild aroundBuild;
        if (request.getAroundBuildId() != null) {
            aroundBuild = aroundBuildRepository.findById(request.getAroundBuildId())
                    .orElseThrow(() -> new RuntimeException("AroundBuild not found with id: " + request.getAroundBuildId()));
        } else {
            String name = request.getName() == null ? "" : request.getName().trim();
            if (name.isEmpty()) throw new RuntimeException("name is required");
            aroundBuild = aroundBuildRepository.save(AroundBuild.builder().name(name).build());
        }
        boolean exists = aroundBuildCountRepository
                .existsLink(
                        objectId,
                        aroundBuild.getId_around_build()
                );
        if (!exists) {
            aroundBuildCountRepository.save(AroundBuildCount.builder()
                    .id_object_place_trash(objectPlaceTrash)
                    .id_around_build(aroundBuild)
                    .build());
        }
        return aroundBuild;
    }

    public NatualSaveBuilding addNatualSaveBuildToObjectPlaceTrash(Long objectId, ObjectNatualSaveBuildLinkRequest request) {
        ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + objectId));
        NatualSaveBuilding natualSaveBuilding;
        if (request.getNatualSaveBuildId() != null) {
            natualSaveBuilding = natualSaveBuildingRepository.findById(request.getNatualSaveBuildId())
                    .orElseThrow(() -> new RuntimeException("NatualSaveBuilding not found with id: " + request.getNatualSaveBuildId()));
        } else {
            String name = request.getName() == null ? "" : request.getName().trim();
            if (name.isEmpty()) throw new RuntimeException("name is required");
            natualSaveBuilding = natualSaveBuildingRepository.save(NatualSaveBuilding.builder().name(name).build());
        }
        boolean exists = natualSaveBuildCountRepository
                .existsLink(
                        objectId,
                        natualSaveBuilding.getId_natual_save_build()
                );
        if (!exists) {
            natualSaveBuildCountRepository.save(NatualSaveBuildCount.builder()
                    .id_object_place_trash(objectPlaceTrash)
                    .id_natual_save_build(natualSaveBuilding)
                    .build());
        }
        return natualSaveBuilding;
    }

    public void deleteAroundBuildFromObjectPlaceTrash(Long objectId, Long aroundBuildId) {
        aroundBuildCountRepository.deleteLink(
                objectId,
                aroundBuildId
        );
    }

    public void deleteNatualSaveBuildFromObjectPlaceTrash(Long objectId, Long natualSaveBuildId) {
        natualSaveBuildCountRepository
                .deleteLink(
                        objectId,
                        natualSaveBuildId
                );
    }

    // ==================== UPDATE (только основные поля) ====================
    public ObjectPlaceTrash updateObjectPlaceTrash(Long id, ObjectPlaceTrashRequest request) {
        log.info("Updating ObjectPlaceTrash with id: {}", id);

        ObjectPlaceTrash entity = objectPlaceTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + id));

        // Обновляем только основные поля с проверкой существования связанных записей
        if (request.getIdRegistration() != null) {
            String reg = request.getIdRegistration();
            if (!reg.equals(entity.getId_registration())
                    && objectPlaceTrashRepository.existsByRegistrationNumberAndIdNot(reg, id)) {
                throw new RuntimeException("Регистрационный номер уже существует у другой записи");
            }
            entity.setId_registration(reg);
        }

        if (request.getRegister() != null) {
            entity.setRegister(request.getRegister());
        }

        if (request.getDateRegister() != null) {
            entity.setDate_register(request.getDateRegister());
        }

        // Связи: фронт шлёт -1 для сброса (Long null в JSON не отличить от «поле не передано»)
        if (request.getCitiesId() != null) {
            if (request.getCitiesId() < 0) {
                entity.setId_cities(null);
            } else {
                Cities cities = citiesRepository.findById(request.getCitiesId())
                        .orElseThrow(() -> new RuntimeException("Cities not found with id: " + request.getCitiesId()));
                entity.setId_cities(cities);
            }
        }

        if (request.getRegionId() != null) {
            if (request.getRegionId() < 0) {
                entity.setId_region(null);
            } else {
                Region region = regionRepository.findById(request.getRegionId())
                        .orElseThrow(() -> new RuntimeException("Region not found with id: " + request.getRegionId()));
                entity.setId_region(region);
            }
        }

        if (request.getGroupPlaceSaveId() != null) {
            if (request.getGroupPlaceSaveId() < 0) {
                entity.setId_group_place_save(null);
            } else {
                GroupPlaceSave groupPlaceSave = groupPlaceSaveRepository.findById(request.getGroupPlaceSaveId())
                        .orElseThrow(() -> new RuntimeException("GroupPlaceSave not found with id: " + request.getGroupPlaceSaveId()));
                entity.setId_group_place_save(groupPlaceSave);
            }
        }

        if (request.getStorageSchemeId() != null) {
            if (request.getStorageSchemeId() < 0) {
                entity.setId_storage_scheme(null);
            } else {
                StorageScheme storageScheme = storageSchemeRepository.findById(request.getStorageSchemeId())
                        .orElseThrow(() -> new RuntimeException("StorageScheme not found with id: " + request.getStorageSchemeId()));
                entity.setId_storage_scheme(storageScheme);
            }
        }

        if (request.getGruopsDegreeId() != null) {
            if (request.getGruopsDegreeId() < 0) {
                entity.setId_gruops_degree(null);
            } else {
                GruopsDegree gruopsDegree = gruopsDegreeRepository.findById(request.getGruopsDegreeId())
                        .orElseThrow(() -> new RuntimeException("GruopsDegree not found with id: " + request.getGruopsDegreeId()));
                entity.setId_gruops_degree(gruopsDegree);
            }
        }



        // Обновляем остальные поля
        if (request.getNameObj() != null) {
            entity.setName_obj(request.getNameObj());
        }

        if (request.getPayer_indentification_number() != null) {
            entity.setPayer_indentification_number(request.getPayer_indentification_number());
        }

        if (request.getNameOwn() != null) {
            entity.setName_own(request.getNameOwn());
        }

        if (request.getStartUse() != null) {
            entity.setStart_use(request.getStartUse());
        }

        if (request.getServiseLife() != null) {
            entity.setServise_life(request.getServiseLife());
        }

        if (request.getCompanyLocated() != null) {
            entity.setCompany_located(request.getCompanyLocated());
        }

        if (request.getPlaceObj() != null) {
            entity.setPlace_obj(request.getPlaceObj());
        }

        if (request.getProject() != null) {
            entity.setProject(request.getProject());
        }

        if (request.getStateExpertize() != null) {
            entity.setState_expertize(request.getStateExpertize());
        }

        if (request.getEcoPasport() != null) {
            entity.setEco_pasport(request.getEcoPasport());
        }

        if (request.getPravaPlace() != null) {
            entity.setPrava_place(request.getPravaPlace());
        }

        if (request.getConfirmationUse() != null) {
            entity.setConfirmation_use(request.getConfirmationUse());
        }

        if (request.getSquare() != null) {
            entity.setSquare(request.getSquare());
        }

        if (request.getUseSquare() != null) {
            entity.setUse_square(request.getUseSquare());
        }

        if (request.getTrashSquare() != null) {
            entity.setTrash_square(request.getTrashSquare());
        }

        if (request.getProjectPower() != null) {
            entity.setProject_power(request.getProjectPower());
        }

        if (request.getFacticheskayPower() != null) {
            entity.setFacticheskay_power(request.getFacticheskayPower());
        }

        if (request.getAccomulatedTrash() != null) {
            entity.setAccomulated_trash(request.getAccomulatedTrash());
        }

        if (request.getTypeGrounds() != null) {
            entity.setType_grounds(request.getTypeGrounds());
        }

        if (request.getAnderWater() != null) {
            entity.setAnder_water(request.getAnderWater());
        }

        if (request.getObservationHole() != null) {
            entity.setObservation_hole(request.getObservationHole());
        }

        if (request.getDateAxclute() != null) {
            entity.setDate_axclute(request.getDateAxclute());
        }

        if (request.getResonAxclute() != null) {
            entity.setReson_axclute(request.getResonAxclute());
        }

        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }

        return objectPlaceTrashRepository.save(entity);
    }

    // ==================== DELETE ====================
    public void deleteObjectPlaceTrash(Long id) {
        log.info("Deleting ObjectPlaceTrash with id: {}", id);

        if (!objectPlaceTrashRepository.existsById(id)) {
            throw new RuntimeException("ObjectPlaceTrash not found with id: " + id);
        }

        objectPlaceTrashRepository.deleteById(id);
    }

    // ==================== PRIVATE METHODS ====================

    private ObjectPlaceTrash mapToEntity(ObjectPlaceTrashRequest req, ObjectPlaceTrash entity) {
        entity.setId_registration(req.getIdRegistration());
        entity.setRegister(req.getRegister());
        entity.setDate_register(req.getDateRegister());

        if (req.getCitiesId() != null && req.getCitiesId() > 0) {
            Cities cities = citiesRepository.findById(req.getCitiesId())
                    .orElseThrow(() -> new RuntimeException("Cities not found with id: " + req.getCitiesId()));
            entity.setId_cities(cities);
        }

        if (req.getRegionId() != null && req.getRegionId() > 0) {
            Region region = regionRepository.findById(req.getRegionId())
                    .orElseThrow(() -> new RuntimeException("Region not found with id: " + req.getRegionId()));
            entity.setId_region(region);
        }

        if (req.getGroupPlaceSaveId() != null) {
            GroupPlaceSave groupPlaceSave = groupPlaceSaveRepository.findById(req.getGroupPlaceSaveId())
                    .orElseThrow(() -> new RuntimeException("GroupPlaceSave not found with id: " + req.getGroupPlaceSaveId()));
            entity.setId_group_place_save(groupPlaceSave);
        }

        if (req.getStorageSchemeId() != null) {
            StorageScheme storageScheme = storageSchemeRepository.findById(req.getStorageSchemeId())
                    .orElseThrow(() -> new RuntimeException("StorageScheme not found with id: " + req.getStorageSchemeId()));
            entity.setId_storage_scheme(storageScheme);
        }

        if (req.getGruopsDegreeId() != null) {
            GruopsDegree gruopsDegree = gruopsDegreeRepository.findById(req.getGruopsDegreeId())
                    .orElseThrow(() -> new RuntimeException("GruopsDegree not found with id: " + req.getGruopsDegreeId()));
            entity.setId_gruops_degree(gruopsDegree);
        }



        entity.setName_obj(req.getNameObj());
        entity.setName_own(req.getNameOwn());
        entity.setStart_use(req.getStartUse());
        entity.setServise_life(req.getServiseLife());
        entity.setCompany_located(req.getCompanyLocated());
        entity.setPlace_obj(req.getPlaceObj());
        entity.setProject(req.getProject());
        entity.setState_expertize(req.getStateExpertize());
        entity.setEco_pasport(req.getEcoPasport());
        entity.setPrava_place(req.getPravaPlace());
        entity.setConfirmation_use(req.getConfirmationUse());
        entity.setSquare(req.getSquare());
        entity.setUse_square(req.getUseSquare());
        entity.setTrash_square(req.getTrashSquare());
        entity.setProject_power(req.getProjectPower());
        entity.setFacticheskay_power(req.getFacticheskayPower());
        entity.setAccomulated_trash(req.getAccomulatedTrash());
        entity.setType_grounds(req.getTypeGrounds());
        entity.setAnder_water(req.getAnderWater());
        entity.setObservation_hole(req.getObservationHole());
        entity.setDate_axclute(req.getDateAxclute());
        entity.setReson_axclute(req.getResonAxclute());
        entity.setStatus(req.getStatus());
        entity.setPayer_indentification_number(req.getPayer_indentification_number());
        return entity;
    }

    private Specification<ObjectPlaceTrash> buildFilterSpecification(ObjectPlaceTrashFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdRegistration() != null && !filter.getIdRegistration().isEmpty()) {
                predicates.add(cb.like(root.get("id_registration"), "%" + filter.getIdRegistration() + "%"));
            }
            if (filter.getRegister() != null) {
                predicates.add(cb.equal(root.get("register"), filter.getRegister()));
            }
            if (filter.getDateRegisterFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date_register"), filter.getDateRegisterFrom()));
            }
            if (filter.getDateRegisterTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date_register"), filter.getDateRegisterTo()));
            }
            if (filter.getCitiesId() != null) {
                predicates.add(cb.equal(root.get("id_cities").get("id_cities"), filter.getCitiesId()));
            }
            if (filter.getNameObj() != null && !filter.getNameObj().isEmpty()) {
                predicates.add(cb.like(root.get("name_obj"), "%" + filter.getNameObj() + "%"));
            }
            if (filter.getNameOwn() != null && !filter.getNameOwn().isEmpty()) {
                predicates.add(cb.like(root.get("name_own"), "%" + filter.getNameOwn() + "%"));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // ==================== FILTER CLASS ====================
    public static class ObjectPlaceTrashFilter {
        private String idRegistration;
        private Integer register;
        private LocalDate dateRegisterFrom;
        private LocalDate dateRegisterTo;
        private Long citiesId;
        private String nameObj;
        private String nameOwn;
        private Boolean status;

        // Геттеры и сеттеры
        public String getIdRegistration() { return idRegistration; }
        public void setIdRegistration(String idRegistration) { this.idRegistration = idRegistration; }
        public Integer getRegister() { return register; }
        public void setRegister(Integer register) { this.register = register; }
        public LocalDate getDateRegisterFrom() { return dateRegisterFrom; }
        public void setDateRegisterFrom(LocalDate dateRegisterFrom) { this.dateRegisterFrom = dateRegisterFrom; }
        public LocalDate getDateRegisterTo() { return dateRegisterTo; }
        public void setDateRegisterTo(LocalDate dateRegisterTo) { this.dateRegisterTo = dateRegisterTo; }
        public Long getCitiesId() { return citiesId; }
        public void setCitiesId(Long citiesId) { this.citiesId = citiesId; }
        public String getNameObj() { return nameObj; }
        public void setNameObj(String nameObj) { this.nameObj = nameObj; }
        public String getNameOwn() { return nameOwn; }
        public void setNameOwn(String nameOwn) { this.nameOwn = nameOwn; }
        public Boolean getStatus() { return status; }
        public void setStatus(Boolean status) { this.status = status; }
    }
}