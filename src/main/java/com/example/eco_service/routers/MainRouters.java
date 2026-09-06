package com.example.eco_service.routers;
import com.example.eco_service.dto.request.*;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import com.example.eco_service.dto.response.EchoResponse;
import com.example.eco_service.dto.response.ObjectPlaceTrashListResponse;
import com.example.eco_service.services.CRUDServices;
import com.example.eco_service.services.ObjectPlaceTrashService;
import com.example.eco_service.services.PageSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Главный контроллер", description = "API для web")
public class MainRouters {

    private final CRUDServices aroundBuildService, characteristicTrashService, citiesService, classDangerService,
                                cleanerBuildsService, commentsOfPlaceService, groupPlaceSaveService, gruopsDegreeService,
                            levelTrashService, magazinTrashService, nameGroupService, natualSaveBuildingService,
                        numberPhoneService, physicalStateService, regionService,storageSchemeService, typeTrash1Service,
                        districtService;
    private final ObjectPlaceTrashService objectPlaceTrashService;

    @PostMapping("district")
    public ResponseEntity<District> createDistrict(@Valid @RequestBody DistrictRequest request) {
        District entity = districtService.createDistrict(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("district")
    public ResponseEntity<?> findAllDistricts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(districtService.findAllDistrictsPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(districtService.findAllDistricts());
    }

    @GetMapping("district/{id}")
    public ResponseEntity<District> findByIdDistrict(@PathVariable Long id) {
        District entity = districtService.findByIdDistrict(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("district/{id}")
    public ResponseEntity<District> updateDistrict(
            @PathVariable Long id,
            @Valid @RequestBody DistrictRequest request) {
        District entity = districtService.updateDistrict(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("district/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("around-build")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<AroundBuild> createAroundBuild(@Valid @RequestBody AroundBuildRequest request) {
        AroundBuild entity = aroundBuildService.createAroundBuild(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("around-build")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllAroundBuild(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(aroundBuildService.findAllAroundBuildPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(aroundBuildService.findAllAroundBuild());
    }

    @GetMapping("around-build/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<AroundBuild> findByIdAroundBuild(@PathVariable Long id) {
        AroundBuild entity = aroundBuildService.findByIdAroundBuild(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("around-build/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<AroundBuild> updateAroundBuild(
            @PathVariable Long id,
            @Valid @RequestBody AroundBuildRequest request) {
        AroundBuild entity = aroundBuildService.updateAroundBuild(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("around-build/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteAroundBuild(@PathVariable Long id) {
        aroundBuildService.deleteAroundBuild(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("characteristic-trash")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<CharacteristicTrash> createCharacteristicTrash(@Valid @RequestBody CharacteristicTrashRequest request) {
        CharacteristicTrash entity = characteristicTrashService.createCharacteristicTrash(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("characteristic-trash")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllCharacteristicTrash(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(characteristicTrashService.findAllCharacteristicTrashPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(characteristicTrashService.findAllCharacteristicTrash());
    }

    @GetMapping("characteristic-trash/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<CharacteristicTrash> findByIdCharacteristicTrash(@PathVariable Long id) {
        CharacteristicTrash entity = characteristicTrashService.findByIdCharacteristicTrash(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("characteristic-trash/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<CharacteristicTrash> updateCharacteristicTrash(
            @PathVariable Long id,
            @Valid @RequestBody CharacteristicTrashRequest request) {
        CharacteristicTrash entity = characteristicTrashService.updateCharacteristicTrash(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("characteristic-trash/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteCharacteristicTrash(@PathVariable Long id) {
        characteristicTrashService.deleteCharacteristicTrash(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("cities")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<Cities> createCities(@Valid @RequestBody CitiesRequest request) {
        Cities entity = citiesService.createCities(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("cities")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllCities(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(citiesService.findAllCitiesPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(citiesService.findAllCities());
    }

    @GetMapping("cities/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<Cities> findByIdCities(@PathVariable Long id) {
        Cities entity = citiesService.findByIdCities(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("cities/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<Cities> updateCities(
            @PathVariable Long id,
            @Valid @RequestBody CitiesRequest request) {
        Cities entity = citiesService.updateCities(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("cities/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteCities(@PathVariable Long id) {
        citiesService.deleteCities(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("classDanger")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<ClassDanger> createClassDanger(@Valid @RequestBody ClassDangerRequest request) {
        ClassDanger entity = classDangerService.createClassDanger(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("classDanger")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllClassDanger(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(classDangerService.findAllClassDangerPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(classDangerService.findAllClassDanger());
    }

    @GetMapping("classDanger/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<ClassDanger> findByIdClassDanger(@PathVariable Long id) {
        ClassDanger entity = classDangerService.findByIdClassDanger(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("classDanger/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<ClassDanger> updateClassDanger(
            @PathVariable Long id,
            @Valid @RequestBody ClassDangerRequest request) {
        ClassDanger entity = classDangerService.updateClassDanger(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("classDanger/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteClassDanger(@PathVariable Long id) {
        classDangerService.deleteClassDanger(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("cleaner-builds")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<CleanerBuilds> createCleanerBuilds(@Valid @RequestBody CleanerBuildsRequest request) {
        CleanerBuilds entity = cleanerBuildsService.createCleanerBuilds(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("cleaner-builds")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllCleanerBuilds(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(cleanerBuildsService.findAllCleanerBuildsPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(cleanerBuildsService.findAllCleanerBuilds());
    }

    @GetMapping("cleaner-builds/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<CleanerBuilds> findByIdCleanerBuilds(@PathVariable Long id) {
        CleanerBuilds entity = cleanerBuildsService.findByIdCleanerBuilds(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("cleaner-builds/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<CleanerBuilds> updateCleanerBuilds(
            @PathVariable Long id,
            @Valid @RequestBody CleanerBuildsRequest request) {
        CleanerBuilds entity = cleanerBuildsService.updateCleanerBuilds(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("cleaner-builds/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteCleanerBuilds(@PathVariable Long id) {
        cleanerBuildsService.deleteCleanerBuilds(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("group-place-save")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<GroupPlaceSave> createGroupPlaceSave(@Valid @RequestBody GroupPlaceSaveRequest request) {
        GroupPlaceSave entity = groupPlaceSaveService.createGroupPlaceSave(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("group-place-save")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllGroupPlaceSave(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(groupPlaceSaveService.findAllGroupPlaceSavePaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(groupPlaceSaveService.findAllGroupPlaceSave());
    }

    @GetMapping("group-place-save/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<GroupPlaceSave> findByIdGroupPlaceSave(@PathVariable Long id) {
        GroupPlaceSave entity = groupPlaceSaveService.findByIdGroupPlaceSave(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("group-place-save/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<GroupPlaceSave> updateGroupPlaceSave(
            @PathVariable Long id,
            @Valid @RequestBody GroupPlaceSaveRequest request) {
        GroupPlaceSave entity = groupPlaceSaveService.updateGroupPlaceSave(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("group-place-save/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteGroupPlaceSave(@PathVariable Long id) {
        groupPlaceSaveService.deleteGroupPlaceSave(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("gruops-degree")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<GruopsDegree> createGruopsDegree(@Valid @RequestBody GruopsDegreeRequest request) {
        GruopsDegree entity = gruopsDegreeService.createGruopsDegree(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("gruops-degree")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllGruopsDegree(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(gruopsDegreeService.findAllGruopsDegreePaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(gruopsDegreeService.findAllGruopsDegree());
    }

    @GetMapping("gruops-degree/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<GruopsDegree> findByIdGruopsDegree(@PathVariable Long id) {
        GruopsDegree entity = gruopsDegreeService.findByIdGruopsDegree(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("gruops-degree/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<GruopsDegree> updateGruopsDegree(
            @PathVariable Long id,
            @Valid @RequestBody GruopsDegreeRequest request) {
        GruopsDegree entity = gruopsDegreeService.updateGruopsDegree(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("gruops-degree/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteGruopsDegree(@PathVariable Long id) {
        gruopsDegreeService.deleteGruopsDegree(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("level-trash")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<LevelTrash> createLevelTrash(@Valid @RequestBody LevelTrashRequest request) {
        LevelTrash entity = levelTrashService.createLevelTrash(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("level-trash")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllLevelTrash(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(levelTrashService.findAllLevelTrashPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(levelTrashService.findAllLevelTrash());
    }

    @GetMapping("level-trash/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<LevelTrash> findByIdLevelTrash(@PathVariable Long id) {
        LevelTrash entity = levelTrashService.findByIdLevelTrash(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("level-trash/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<LevelTrash> updateLevelTrash(
            @PathVariable Long id,
            @Valid @RequestBody LevelTrashRequest request) {
        LevelTrash entity = levelTrashService.updateLevelTrash(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("level-trash/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteLevelTrash(@PathVariable Long id) {
        levelTrashService.deleteLevelTrash(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("magazin-trash")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<MagazinTrash> createMagazinTrash(@Valid @RequestBody MagazinTrashRequest request) {
        MagazinTrash entity = magazinTrashService.createMagazinTrash(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("magazin-trash")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllMagazinTrash(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(magazinTrashService.findAllMagazinTrashPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(magazinTrashService.findAllMagazinTrash());
    }

    @GetMapping("magazin-trash/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<MagazinTrash> findByIdMagazinTrash(@PathVariable Long id) {
        MagazinTrash entity = magazinTrashService.findByIdMagazinTrash(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("magazin-trash/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<MagazinTrash> updateMagazinTrash(
            @PathVariable Long id,
            @Valid @RequestBody MagazinTrashRequest request) {
        MagazinTrash entity = magazinTrashService.updateMagazinTrash(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("magazin-trash/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteMagazinTrash(@PathVariable Long id) {
        magazinTrashService.deleteMagazinTrash(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("name-group")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<NameGroup> createNameGroup(@Valid @RequestBody NameGroupRequest request) {
        NameGroup entity = nameGroupService.createNameGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("name-group")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllNameGroup(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(nameGroupService.findAllNameGroupPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(nameGroupService.findAllNameGroup());
    }

    @GetMapping("name-group/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<NameGroup> findByIdNameGroup(@PathVariable Long id) {
        NameGroup entity = nameGroupService.findByIdNameGroup(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("name-group/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<NameGroup> updateNameGroup(
            @PathVariable Long id,
            @Valid @RequestBody NameGroupRequest request) {
        NameGroup entity = nameGroupService.updateNameGroup(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("name-group/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteNameGroup(@PathVariable Long id) {
        nameGroupService.deleteNameGroup(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("natual-save-building")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<NatualSaveBuilding> createNatualSaveBuilding(@Valid @RequestBody NatualSaveBuildingRequest request) {
        NatualSaveBuilding entity = natualSaveBuildingService.createNatualSaveBuilding(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("natual-save-building")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllNatualSaveBuilding(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(natualSaveBuildingService.findAllNatualSaveBuildingPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(natualSaveBuildingService.findAllNatualSaveBuilding());
    }

    @GetMapping("natual-save-building/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<NatualSaveBuilding> findByIdNatualSaveBuilding(@PathVariable Long id) {
        NatualSaveBuilding entity = natualSaveBuildingService.findByIdNatualSaveBuilding(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("natual-save-building/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<NatualSaveBuilding> updateNatualSaveBuilding(
            @PathVariable Long id,
            @Valid @RequestBody NatualSaveBuildingRequest request) {
        NatualSaveBuilding entity = natualSaveBuildingService.updateNatualSaveBuilding(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("natual-save-building/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteNatualSaveBuilding(@PathVariable Long id) {
        natualSaveBuildingService.deleteNatualSaveBuilding(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("number-phone")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<NumberPhone> createNumberPhone(@Valid @RequestBody NumberPhoneRequest request) {
        NumberPhone entity = numberPhoneService.createNumberPhone(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("number-phone")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllNumberPhone(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(numberPhoneService.findAllNumberPhonePaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(numberPhoneService.findAllNumberPhone());
    }

    @GetMapping("number-phone/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<NumberPhone> findByIdNumberPhone(@PathVariable Long id) {
        NumberPhone entity = numberPhoneService.findByIdNumberPhone(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("number-phone/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<NumberPhone> updateNumberPhone(
            @PathVariable Long id,
            @Valid @RequestBody NumberPhoneRequest request) {
        NumberPhone entity = numberPhoneService.updateNumberPhone(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("number-phone/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteNumberPhone(@PathVariable Long id) {
        numberPhoneService.deleteNumberPhone(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("physical-state")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<PhysicalState> createPhysicalState(@Valid @RequestBody PhysicalStateRequest request) {
        PhysicalState entity = physicalStateService.createPhysicalState(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("physical-state")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllPhysicalState(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(physicalStateService.findAllPhysicalStatePaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(physicalStateService.findAllPhysicalState());
    }

    @GetMapping("physical-state/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<PhysicalState> findByIdPhysicalState(@PathVariable Long id) {
        PhysicalState entity = physicalStateService.findByIdPhysicalState(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("physical-state/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<PhysicalState> updatePhysicalState(
            @PathVariable Long id,
            @Valid @RequestBody PhysicalStateRequest request) {
        PhysicalState entity = physicalStateService.updatePhysicalState(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("physical-state/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deletePhysicalState(@PathVariable Long id) {
        physicalStateService.deletePhysicalState(id);
        return ResponseEntity.noContent().build();
    }





    @PostMapping("region")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<Region> createRegion(@Valid @RequestBody RegionRequest request) {
        Region entity = regionService.createRegion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("region")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllRegion(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(regionService.findAllRegionPaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(regionService.findAllRegion());
    }

    @GetMapping("region/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<Region> findByIdRegion(@PathVariable Long id) {
        Region entity = regionService.findByIdRegion(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("region/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<Region> updateRegion(
            @PathVariable Long id,
            @Valid @RequestBody RegionRequest request) {
        Region entity = regionService.updateRegion(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("region/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("storage-scheme")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<StorageScheme> createStorageScheme(@Valid @RequestBody StorageSchemeRequest request) {
        StorageScheme entity = storageSchemeService.createStorageScheme(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("storage-scheme")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllStorageScheme(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(storageSchemeService.findAllStorageSchemePaged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(storageSchemeService.findAllStorageScheme());
    }

    @GetMapping("storage-scheme/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<StorageScheme> findByIdStorageScheme(@PathVariable Long id) {
        StorageScheme entity = storageSchemeService.findByIdStorageScheme(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("storage-scheme/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<StorageScheme> updateStorageScheme(
            @PathVariable Long id,
            @Valid @RequestBody StorageSchemeRequest request) {
        StorageScheme entity = storageSchemeService.updateStorageScheme(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("storage-scheme/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteStorageScheme(@PathVariable Long id) {
        storageSchemeService.deleteStorageScheme(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("type-trash1")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<TypeTrash1> createTypeTrash1(@Valid @RequestBody TypeTrash1Request request) {
        TypeTrash1 entity = typeTrash1Service.createTypeTrash1(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("type-trash1")
    @Operation(summary = "Список (page+size — страница; без них — весь список)")
    public ResponseEntity<?> findAllTypeTrash1(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir) {
        if (PageSupport.wantsPage(page, size)) {
            return ResponseEntity.ok(typeTrash1Service.findAllTypeTrash1Paged(page, size, q, sort, dir));
        }
        return ResponseEntity.ok(typeTrash1Service.findAllTypeTrash1());
    }

    @GetMapping("type-trash1/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<TypeTrash1> findByIdTypeTrash1(@PathVariable Long id) {
        TypeTrash1 entity = typeTrash1Service.findByIdTypeTrash1(id);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("type-trash1/{id}")
    @Operation(summary = "Обновить запись")
    public ResponseEntity<TypeTrash1> updateTypeTrash1(
            @PathVariable Long id,
            @Valid @RequestBody TypeTrash1Request request) {
        TypeTrash1 entity = typeTrash1Service.updateTypeTrash1(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("type-trash1/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteTypeTrash1(@PathVariable Long id) {
        typeTrash1Service.deleteTypeTrash1(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("object-place-trash")
    @Operation(summary = "Создать новую запись")
    public ResponseEntity<ObjectPlaceTrash> createObjectPlaceTrash(@Valid @RequestBody ObjectPlaceTrashRequest request) {
        ObjectPlaceTrash entity = objectPlaceTrashService.createObjectPlaceTrash(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("object-place-trash/with-pagination")
    @Operation(summary = "Получить все записи с пагинацией и фильтрацией")
    public ResponseEntity<Page<ObjectPlaceTrashListResponse>> findAllObjectPlaceTrashWithPagination(
            @PageableDefault(size = 50, sort = "id_registration", direction = Sort.Direction.ASC) Pageable pageable,
            ObjectPlaceTrashService.ObjectPlaceTrashFilter filter) {
        Page<ObjectPlaceTrashListResponse> page =
                objectPlaceTrashService.findAllObjectPlaceTrashWithPagination(pageable, filter);
        return ResponseEntity.ok(page);
    }

    @GetMapping("object-place-trash")
    @Operation(summary = "Список объектов с пагинацией (по умолчанию page=0, size=50)")
    public ResponseEntity<?> findAllObjectPlaceTrash(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String dir,
            @RequestParam(required = false) Boolean includeExcluded,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String wasteCode) {
        return ResponseEntity.ok(objectPlaceTrashService.findAllObjectPlaceTrashPaged(
                page, size, q, sort, dir, includeExcluded, location, wasteCode));
    }

    @GetMapping("object-place-trash/{id}")
    @Operation(summary = "Получить запись по ID")
    public ResponseEntity<ObjectPlaceTrash> findByIdObjectPlaceTrash(@PathVariable Long id) {
        ObjectPlaceTrash entity = objectPlaceTrashService.findByIdObjectPlaceTrash(id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("object-place-trash/{id}/characteristic-trash")
    @Operation(summary = "Получить характеристики отходов объекта")
    public ResponseEntity<List<CharacteristicTrash>> findCharacteristicTrashByObjectPlaceTrash(@PathVariable Long id) {
        List<CharacteristicTrash> entities = characteristicTrashService.findCharacteristicTrashByObjectPlaceTrash(id);
        return ResponseEntity.ok(entities);
    }

    @GetMapping("object-place-trash/{id}/around-builds")
    @Operation(summary = "Получить привязанные окружающие здания (AroundBuild)")
    public ResponseEntity<List<AroundBuild>> findAroundBuildsByObjectPlaceTrash(@PathVariable Long id) {
        List<AroundBuild> entities = objectPlaceTrashService.findAroundBuildsByObjectPlaceTrash(id);
        return ResponseEntity.ok(entities);
    }

    @PostMapping("object-place-trash/{id}/around-builds")
    @Operation(summary = "Добавить/привязать окружающее здание к объекту")
    public ResponseEntity<AroundBuild> addAroundBuildToObjectPlaceTrash(
            @PathVariable Long id,
            @Valid @RequestBody ObjectAroundBuildLinkRequest request) {
        AroundBuild entity = objectPlaceTrashService.addAroundBuildToObjectPlaceTrash(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @DeleteMapping("object-place-trash/{id}/around-builds/{aroundBuildId}")
    @Operation(summary = "Удалить привязку окружающего здания от объекта")
    public ResponseEntity<Void> deleteAroundBuildFromObjectPlaceTrash(
            @PathVariable Long id,
            @PathVariable Long aroundBuildId) {
        objectPlaceTrashService.deleteAroundBuildFromObjectPlaceTrash(id, aroundBuildId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("object-place-trash/{id}/natual-save-buildings")
    @Operation(summary = "Получить привязанные природоохранные здания (NatualSaveBuilding)")
    public ResponseEntity<List<NatualSaveBuilding>> findNatualSaveBuildsByObjectPlaceTrash(@PathVariable Long id) {
        List<NatualSaveBuilding> entities = objectPlaceTrashService.findNatualSaveBuildsByObjectPlaceTrash(id);
        return ResponseEntity.ok(entities);
    }

    @PostMapping("object-place-trash/{id}/natual-save-buildings")
    @Operation(summary = "Добавить/привязать природоохранное здание к объекту")
    public ResponseEntity<NatualSaveBuilding> addNatualSaveBuildToObjectPlaceTrash(
            @PathVariable Long id,
            @Valid @RequestBody ObjectNatualSaveBuildLinkRequest request) {
        NatualSaveBuilding entity = objectPlaceTrashService.addNatualSaveBuildToObjectPlaceTrash(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @DeleteMapping("object-place-trash/{id}/natual-save-buildings/{natualSaveBuildId}")
    @Operation(summary = "Удалить привязку природоохранного здания от объекта")
    public ResponseEntity<Void> deleteNatualSaveBuildFromObjectPlaceTrash(
            @PathVariable Long id,
            @PathVariable Long natualSaveBuildId) {
        objectPlaceTrashService.deleteNatualSaveBuildFromObjectPlaceTrash(id, natualSaveBuildId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("object-place-trash/{objectId}/number-phone/{phoneId}")
    @Operation(summary = "Отвязать телефон от объекта (номер в справочнике не удаляется)")
    public ResponseEntity<Void> unlinkNumberPhoneFromObject(
            @PathVariable Long objectId,
            @PathVariable Long phoneId) {
        numberPhoneService.unlinkNumberPhoneFromObject(objectId, phoneId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("object-place-trash/{id}")
    @Operation(summary = "Обновить запись (только основные поля)")
    public ResponseEntity<ObjectPlaceTrash> updateObjectPlaceTrash(
            @PathVariable Long id,
            @Valid @RequestBody ObjectPlaceTrashRequest request) {
        ObjectPlaceTrash entity = objectPlaceTrashService.updateObjectPlaceTrash(id, request);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("object-place-trash/{id}")
    @Operation(summary = "Удалить запись")
    public ResponseEntity<Void> deleteObjectPlaceTrash(@PathVariable Long id) {
        objectPlaceTrashService.deleteObjectPlaceTrash(id);
        return ResponseEntity.noContent().build();
    }
}
