package com.example.eco_service.services;

import com.example.eco_service.dto.request.*;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CRUDServices {

    private final InterfAroundBuild aroundBuild;
    private final InterfCharacteristicTrash сharacteristicTrash;
    private final InterfObjectPlaceTrash objectPlaceTrashRepository;
    private final InterfMagazinTrash magazinTrashRepository;
    private final InterfPhysicalState physicalStateRepository;
    private final InterfCities cities;
    private final InterfRegion regionRepository;
    private final InterfClassDanger classDangerRepository;
    private final InterfCleanerBuilds cleanerBuildsRepository;
    private final InterfGroupPlaceSave groupPlaceSaveRepository;
    private final InterfGruopsDegree gruopsDegreeRepository;
    private final InterfLevelTrash levelTrashRepository;
    private final InterfTypeTrash1 typeTrash1Repository;
    private final InterfNameGroup nameGroupRepository;
    private final InterfNatualSaveBuilding natualSaveBuildingRepository;
    private final InterfNumberPhone numberPhoneRepository;
    private final InterfStorageScheme storageSchemeRepository;



    public AroundBuild createAroundBuild(AroundBuildRequest request) {
        log.info("Creating AroundBuild with name: {}", request.getName());

        AroundBuild entity = AroundBuild.builder()
                .name(request.getName())
                .build();

        return aroundBuild.save(entity);
    }


    @Transactional(readOnly = true)
    public List<AroundBuild> findAllAroundBuild() {
        log.info("Fetching all AroundBuilds");
        return aroundBuild.findAll();
    }


    @Transactional(readOnly = true)
    public AroundBuild findByIdAroundBuild(Long id) {
        log.info("Fetching AroundBuild by id: {}", id);
        return aroundBuild.findById(id)
                .orElseThrow(() -> new RuntimeException("AroundBuild not found with id: " + id));
    }


    public AroundBuild updateAroundBuild(Long id, AroundBuildRequest request) {
        log.info("Updating AroundBuild with id: {}", id);

        AroundBuild entity = aroundBuild.findById(id)
                .orElseThrow(() -> new RuntimeException("AroundBuild not found with id: " + id));

        entity.setName(request.getName());

        return aroundBuild.save(entity);
    }

    public void deleteAroundBuild(Long id) {
        log.info("Deleting AroundBuild with id: {}", id);

        if (!aroundBuild.existsById(id)) {
            throw new RuntimeException("AroundBuild not found with id: " + id);
        }

        aroundBuild.deleteById(id);
    }



    // Создание
    public CharacteristicTrash createCharacteristicTrash(CharacteristicTrashRequest request) {
        log.info("Creating CharacteristicTrash");

        ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found"));

        MagazinTrash magazinTrash = magazinTrashRepository.findById(request.getIdMagazinTrash())
                .orElseThrow(() -> new RuntimeException("MagazinTrash not found"));

        PhysicalState physicalState = physicalStateRepository.findById(request.getIdState())
                .orElseThrow(() -> new RuntimeException("PhysicalState not found"));

        CharacteristicTrash entity = CharacteristicTrash.builder()
                .id_object_place_trash(objectPlaceTrash)
                .id_magazin_trash(magazinTrash)
                .id_state(physicalState)
                .weight_for_year(request.getWeightForYear() != null ? request.getWeightForYear() : 0f)
                .square_for_year(request.getSquareForYear() != null ? request.getSquareForYear() : 0f)
                .build();

        return сharacteristicTrash.save(entity);
    }

    // Получение всех
    @Transactional(readOnly = true)
    public List<CharacteristicTrash> findAllCharacteristicTrash() {
        log.info("Fetching all CharacteristicTrash");
        return сharacteristicTrash.findAll();
    }

    // Получение по ID
    @Transactional(readOnly = true)
    public CharacteristicTrash findByIdCharacteristicTrash(Long id) {
        log.info("Fetching CharacteristicTrash by id: {}", id);
        return сharacteristicTrash.findById(id)
                .orElseThrow(() -> new RuntimeException("CharacteristicTrash not found with id: " + id));
    }

    // Обновление
    public CharacteristicTrash updateCharacteristicTrash(Long id, CharacteristicTrashRequest request) {
        log.info("Updating CharacteristicTrash with id: {}", id);

        CharacteristicTrash entity = сharacteristicTrash.findById(id)
                .orElseThrow(() -> new RuntimeException("CharacteristicTrash not found with id: " + id));

        if (request.getIdObjectPlaceTrash() != null) {
            ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                    .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found"));
            entity.setId_object_place_trash(objectPlaceTrash);
        }

        if (request.getIdMagazinTrash() != null) {
            MagazinTrash magazinTrash = magazinTrashRepository.findById(request.getIdMagazinTrash())
                    .orElseThrow(() -> new RuntimeException("MagazinTrash not found"));
            entity.setId_magazin_trash(magazinTrash);
        }

        if (request.getIdState() != null) {
            PhysicalState physicalState = physicalStateRepository.findById(request.getIdState())
                    .orElseThrow(() -> new RuntimeException("PhysicalState not found"));
            entity.setId_state(physicalState);
        }

        if (request.getWeightForYear() != null) {
            entity.setWeight_for_year(request.getWeightForYear());
        }

        if (request.getSquareForYear() != null) {
            entity.setSquare_for_year(request.getSquareForYear());
        }

        return сharacteristicTrash.save(entity);
    }

    // Удаление
    public void deleteCharacteristicTrash(Long id) {
        log.info("Deleting CharacteristicTrash with id: {}", id);

        if (!сharacteristicTrash.existsById(id)) {
            throw new RuntimeException("CharacteristicTrash not found with id: " + id);
        }

        сharacteristicTrash.deleteById(id);
    }


    public Cities createCities(CitiesRequest request) {
        log.info("Creating Cities with index: {}", request.getIndex());

        Region region = regionRepository.findById(request.getIdRegion())
                .orElseThrow(() -> new RuntimeException("Region not found with id: " + request.getIdRegion()));

        Cities entity = Cities.builder()
                .id_region(region)
                .index(request.getIndex())
                .district(request.getDistrict())
                .name_cities(request.getName_cities())
                .build();

        return cities.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Cities> findAllCities() {
        log.info("Fetching all Cities");
        return cities.findAll();
    }

    @Transactional(readOnly = true)
    public Cities findByIdCities(Long id) {
        log.info("Fetching Cities by id: {}", id);
        return cities.findById(id)
                .orElseThrow(() -> new RuntimeException("Cities not found with id: " + id));
    }

    public Cities updateCities(Long id, CitiesRequest request) {
        log.info("Updating Cities with id: {}", id);

        Cities entity = cities.findById(id)
                .orElseThrow(() -> new RuntimeException("Cities not found with id: " + id));

        if (request.getIdRegion() != null) {
            Region region = regionRepository.findById(request.getIdRegion())
                    .orElseThrow(() -> new RuntimeException("Region not found with id: " + request.getIdRegion()));
            entity.setId_region(region);
        }

        if (request.getIndex() != null) {
            entity.setIndex(request.getIndex());
        }

        if (request.getDistrict() != null) {
            entity.setDistrict(request.getDistrict());
        }

        return cities.save(entity);
    }

    public void deleteCities(Long id) {
        log.info("Deleting Cities with id: {}", id);

        if (!cities.existsById(id)) {
            throw new RuntimeException("Cities not found with id: " + id);
        }

        cities.deleteById(id);
    }




    public ClassDanger createClassDanger(ClassDangerRequest request) {
        log.info("Creating ClassDanger with class: {}", request.getClassDanger());

        ClassDanger entity = ClassDanger.builder()
                .class_danger(request.getClassDanger())
                .build();

        return classDangerRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<ClassDanger> findAllClassDanger() {
        log.info("Fetching all ClassDanger");
        return classDangerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ClassDanger findByIdClassDanger(Long id) {
        log.info("Fetching ClassDanger by id: {}", id);
        return classDangerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassDanger not found with id: " + id));
    }

    public ClassDanger updateClassDanger(Long id, ClassDangerRequest request) {
        log.info("Updating ClassDanger with id: {}", id);

        ClassDanger entity = classDangerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassDanger not found with id: " + id));

        if (request.getClassDanger() != null) {
            entity.setClass_danger(request.getClassDanger());
        }

        return classDangerRepository.save(entity);
    }

    public void deleteClassDanger(Long id) {
        log.info("Deleting ClassDanger with id: {}", id);

        if (!classDangerRepository.existsById(id)) {
            throw new RuntimeException("ClassDanger not found with id: " + id);
        }

        classDangerRepository.deleteById(id);
    }




    public CleanerBuilds createCleanerBuilds(CleanerBuildsRequest request) {
        log.info("Creating CleanerBuilds with registrNumber: {}", request.getRegistrNumber());

        ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + request.getIdObjectPlaceTrash()));

        CleanerBuilds entity = CleanerBuilds.builder()
                .registr_number(request.getRegistrNumber())
                .id_object_place_trash(objectPlaceTrash)
                .name_object(request.getNameObject())
                .start_use(request.getStartUse() != null ? request.getStartUse() : 0)
                .all_square(request.getAllSquare() != null ? request.getAllSquare() : 0f)
                .trash_count(request.getTrashCount() != null ? request.getTrashCount() : 0f)
                .build();

        return cleanerBuildsRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<CleanerBuilds> findAllCleanerBuilds() {
        log.info("Fetching all CleanerBuilds");
        return cleanerBuildsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CleanerBuilds findByIdCleanerBuilds(Long id) {
        log.info("Fetching CleanerBuilds by id: {}", id);
        return cleanerBuildsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CleanerBuilds not found with id: " + id));
    }

    public CleanerBuilds updateCleanerBuilds(Long id, CleanerBuildsRequest request) {
        log.info("Updating CleanerBuilds with id: {}", id);

        CleanerBuilds entity = cleanerBuildsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CleanerBuilds not found with id: " + id));

        if (request.getRegistrNumber() != null) {
            entity.setRegistr_number(request.getRegistrNumber());
        }

        if (request.getIdObjectPlaceTrash() != null) {
            ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                    .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + request.getIdObjectPlaceTrash()));
            entity.setId_object_place_trash(objectPlaceTrash);
        }

        if (request.getNameObject() != null) {
            entity.setName_object(request.getNameObject());
        }

        if (request.getStartUse() != null) {
            entity.setStart_use(request.getStartUse());
        }

        if (request.getAllSquare() != null) {
            entity.setAll_square(request.getAllSquare());
        }

        if (request.getTrashCount() != null) {
            entity.setTrash_count(request.getTrashCount());
        }

        return cleanerBuildsRepository.save(entity);
    }

    public void deleteCleanerBuilds(Long id) {
        log.info("Deleting CleanerBuilds with id: {}", id);

        if (!cleanerBuildsRepository.existsById(id)) {
            throw new RuntimeException("CleanerBuilds not found with id: " + id);
        }

        cleanerBuildsRepository.deleteById(id);
    }



    public GroupPlaceSave createGroupPlaceSave(GroupPlaceSaveRequest request) {
        log.info("Creating GroupPlaceSave with name: {}", request.getNameGroup());

        GroupPlaceSave entity = GroupPlaceSave.builder()
                .name_group(request.getNameGroup())
                .build();

        return groupPlaceSaveRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<GroupPlaceSave> findAllGroupPlaceSave() {
        log.info("Fetching all GroupPlaceSave");
        return groupPlaceSaveRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GroupPlaceSave findByIdGroupPlaceSave(Long id) {
        log.info("Fetching GroupPlaceSave by id: {}", id);
        return groupPlaceSaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupPlaceSave not found with id: " + id));
    }

    public GroupPlaceSave updateGroupPlaceSave(Long id, GroupPlaceSaveRequest request) {
        log.info("Updating GroupPlaceSave with id: {}", id);

        GroupPlaceSave entity = groupPlaceSaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupPlaceSave not found with id: " + id));

        if (request.getNameGroup() != null) {
            entity.setName_group(request.getNameGroup());
        }

        return groupPlaceSaveRepository.save(entity);
    }

    public void deleteGroupPlaceSave(Long id) {
        log.info("Deleting GroupPlaceSave with id: {}", id);

        if (!groupPlaceSaveRepository.existsById(id)) {
            throw new RuntimeException("GroupPlaceSave not found with id: " + id);
        }

        groupPlaceSaveRepository.deleteById(id);
    }



    public GruopsDegree createGruopsDegree(GruopsDegreeRequest request) {
        log.info("Creating GruopsDegree with number: {}", request.getNamberGruop());

        GruopsDegree entity = GruopsDegree.builder()
                .namber_gruop(request.getNamberGruop())
                .build();

        return gruopsDegreeRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<GruopsDegree> findAllGruopsDegree() {
        log.info("Fetching all GruopsDegree");
        return gruopsDegreeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GruopsDegree findByIdGruopsDegree(Long id) {
        log.info("Fetching GruopsDegree by id: {}", id);
        return gruopsDegreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GruopsDegree not found with id: " + id));
    }

    public GruopsDegree updateGruopsDegree(Long id, GruopsDegreeRequest request) {
        log.info("Updating GruopsDegree with id: {}", id);

        GruopsDegree entity = gruopsDegreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GruopsDegree not found with id: " + id));

        if (request.getNamberGruop() != null) {
            entity.setNamber_gruop(request.getNamberGruop());
        }

        return gruopsDegreeRepository.save(entity);
    }

    public void deleteGruopsDegree(Long id) {
        log.info("Deleting GruopsDegree with id: {}", id);

        if (!gruopsDegreeRepository.existsById(id)) {
            throw new RuntimeException("GruopsDegree not found with id: " + id);
        }

        gruopsDegreeRepository.deleteById(id);
    }


    public LevelTrash createLevelTrash(LevelTrashRequest request) {
        log.info("Creating LevelTrash with name: {}", request.getNameLevelTrash());

        LevelTrash entity = LevelTrash.builder()
                .name_level_trash(request.getNameLevelTrash())
                .build();

        return levelTrashRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<LevelTrash> findAllLevelTrash() {
        log.info("Fetching all LevelTrash");
        return levelTrashRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LevelTrash findByIdLevelTrash(Long id) {
        log.info("Fetching LevelTrash by id: {}", id);
        return levelTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LevelTrash not found with id: " + id));
    }

    public LevelTrash updateLevelTrash(Long id, LevelTrashRequest request) {
        log.info("Updating LevelTrash with id: {}", id);

        LevelTrash entity = levelTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LevelTrash not found with id: " + id));

        if (request.getNameLevelTrash() != null) {
            entity.setName_level_trash(request.getNameLevelTrash());
        }

        return levelTrashRepository.save(entity);
    }

    public void deleteLevelTrash(Long id) {
        log.info("Deleting LevelTrash with id: {}", id);

        if (!levelTrashRepository.existsById(id)) {
            throw new RuntimeException("LevelTrash not found with id: " + id);
        }

        levelTrashRepository.deleteById(id);
    }


    public MagazinTrash createMagazinTrash(MagazinTrashRequest request) {
        log.info("Creating MagazinTrash with code: {}", request.getCodeTrash());

        ClassDanger classDanger = null;
        if (request.getIdClassDanger() != null && request.getIdClassDanger() > 0) {
            classDanger = classDangerRepository.findById(request.getIdClassDanger())
                    .orElseThrow(() -> new RuntimeException("ClassDanger not found with id: " + request.getIdClassDanger()));
        }

        TypeTrash1 typeTrash = typeTrash1Repository.findById(request.getIdTypeTrash())
                .orElseThrow(() -> new RuntimeException("TypeTrash1 not found with id: " + request.getIdTypeTrash()));

        LevelTrash levelTrash = levelTrashRepository.findById(request.getIdLevelTrash())
                .orElseThrow(() -> new RuntimeException("LevelTrash not found with id: " + request.getIdLevelTrash()));

        NameGroup nameGroup = nameGroupRepository.findById(request.getIdMameGroup())
                .orElseThrow(() -> new RuntimeException("NameGroup not found with id: " + request.getIdMameGroup()));

        MagazinTrash entity = MagazinTrash.builder()
                .id_class_danger(classDanger)
                .id_type_trash(typeTrash)
                .id_level_trash(levelTrash)
                .id_mame_group(nameGroup)
                .code_trash(request.getCodeTrash())
                .name_trash(request.getNameTrash())
                .block1(request.getBlock1())
                .group2(request.getGroup2())
                .group3(request.getGroup3())
                .build();

        return magazinTrashRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<MagazinTrash> findAllMagazinTrash() {
        log.info("Fetching all MagazinTrash");
        return magazinTrashRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MagazinTrash findByIdMagazinTrash(Long id) {
        log.info("Fetching MagazinTrash by id: {}", id);
        return magazinTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MagazinTrash not found with id: " + id));
    }

    public MagazinTrash updateMagazinTrash(Long id, MagazinTrashRequest request) {
        log.info("Updating MagazinTrash with id: {}", id);

        MagazinTrash entity = magazinTrashRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MagazinTrash not found with id: " + id));

        if (request.getIdClassDanger() != null && request.getIdClassDanger() > 0) {
            ClassDanger classDanger = classDangerRepository.findById(request.getIdClassDanger())
                    .orElseThrow(() -> new RuntimeException("ClassDanger not found with id: " + request.getIdClassDanger()));
            entity.setId_class_danger(classDanger);
        } else {
            entity.setId_class_danger(null);
        }

        if (request.getIdTypeTrash() != null) {
            TypeTrash1 typeTrash = typeTrash1Repository.findById(request.getIdTypeTrash())
                    .orElseThrow(() -> new RuntimeException("TypeTrash1 not found with id: " + request.getIdTypeTrash()));
            entity.setId_type_trash(typeTrash);
        }

        if (request.getIdLevelTrash() != null) {
            LevelTrash levelTrash = levelTrashRepository.findById(request.getIdLevelTrash())
                    .orElseThrow(() -> new RuntimeException("LevelTrash not found with id: " + request.getIdLevelTrash()));
            entity.setId_level_trash(levelTrash);
        }

        if (request.getIdMameGroup() != null) {
            NameGroup nameGroup = nameGroupRepository.findById(request.getIdMameGroup())
                    .orElseThrow(() -> new RuntimeException("NameGroup not found with id: " + request.getIdMameGroup()));
            entity.setId_mame_group(nameGroup);
        }

        if (request.getCodeTrash() != null) {
            entity.setCode_trash(request.getCodeTrash());
        }

        if (request.getNameTrash() != null) {
            entity.setName_trash(request.getNameTrash());
        }

        if (request.getBlock1() != null) {
            entity.setBlock1(request.getBlock1());
        }

        if (request.getGroup2() != null) {
            entity.setGroup2(request.getGroup2());
        }

        if (request.getGroup3() != null) {
            entity.setGroup3(request.getGroup3());
        }



        return magazinTrashRepository.save(entity);
    }

    public void deleteMagazinTrash(Long id) {
        log.info("Deleting MagazinTrash with id: {}", id);

        if (!magazinTrashRepository.existsById(id)) {
            throw new RuntimeException("MagazinTrash not found with id: " + id);
        }

        magazinTrashRepository.deleteById(id);
    }




    public NameGroup createNameGroup(NameGroupRequest request) {
        log.info("Creating NameGroup with name: {}", request.getNameGroup());

        NameGroup entity = NameGroup.builder()
                .name_group(request.getNameGroup())
                .build();

        return nameGroupRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<NameGroup> findAllNameGroup() {
        log.info("Fetching all NameGroup");
        return nameGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NameGroup findByIdNameGroup(Long id) {
        log.info("Fetching NameGroup by id: {}", id);
        return nameGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NameGroup not found with id: " + id));
    }

    public NameGroup updateNameGroup(Long id, NameGroupRequest request) {
        log.info("Updating NameGroup with id: {}", id);

        NameGroup entity = nameGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NameGroup not found with id: " + id));

        if (request.getNameGroup() != null) {
            entity.setName_group(request.getNameGroup());
        }

        return nameGroupRepository.save(entity);
    }

    public void deleteNameGroup(Long id) {
        log.info("Deleting NameGroup with id: {}", id);

        if (!nameGroupRepository.existsById(id)) {
            throw new RuntimeException("NameGroup not found with id: " + id);
        }

        nameGroupRepository.deleteById(id);
    }

    public NatualSaveBuilding createNatualSaveBuilding(NatualSaveBuildingRequest request) {
        log.info("Creating NatualSaveBuilding with name: {}", request.getName());

        NatualSaveBuilding entity = NatualSaveBuilding.builder()
                .name(request.getName())
                .build();

        return natualSaveBuildingRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<NatualSaveBuilding> findAllNatualSaveBuilding() {
        log.info("Fetching all NatualSaveBuilding");
        return natualSaveBuildingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NatualSaveBuilding findByIdNatualSaveBuilding(Long id) {
        log.info("Fetching NatualSaveBuilding by id: {}", id);
        return natualSaveBuildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NatualSaveBuilding not found with id: " + id));
    }

    public NatualSaveBuilding updateNatualSaveBuilding(Long id, NatualSaveBuildingRequest request) {
        log.info("Updating NatualSaveBuilding with id: {}", id);

        NatualSaveBuilding entity = natualSaveBuildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NatualSaveBuilding not found with id: " + id));

        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        return natualSaveBuildingRepository.save(entity);
    }

    public void deleteNatualSaveBuilding(Long id) {
        log.info("Deleting NatualSaveBuilding with id: {}", id);

        if (!natualSaveBuildingRepository.existsById(id)) {
            throw new RuntimeException("NatualSaveBuilding not found with id: " + id);
        }

        natualSaveBuildingRepository.deleteById(id);
    }



    public NumberPhone createNumberPhone(NumberPhoneRequest request) {
        log.info("Creating NumberPhone with number: {}", request.getNumber());

        // Находим объект ObjectPlaceTrash
        ObjectPlaceTrash objectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + request.getIdObjectPlaceTrash()));

        // Создаем номер телефона
        NumberPhone entity = NumberPhone.builder()
                .objectPlaceTrash(objectPlaceTrash)  // изменено с id_object_place_trash на objectPlaceTrash
                .number(request.getNumber())
                .build();

        // Сохраняем номер
        NumberPhone savedPhone = numberPhoneRepository.save(entity);

        // Добавляем номер в коллекцию объекта (для поддержки двунаправленной связи)
        if (objectPlaceTrash.getPhones() == null) {
            objectPlaceTrash.setPhones(new ArrayList<>());
        }
        objectPlaceTrash.getPhones().add(savedPhone);

        log.info("Created NumberPhone with id: {}", savedPhone.getId_phone_number());
        return savedPhone;
    }

    // ==================== READ (все) ====================
    @Transactional(readOnly = true)
    public List<NumberPhone> findAllNumberPhone() {
        log.info("Fetching all NumberPhone");
        return numberPhoneRepository.findAll();
    }

    // ==================== READ by ID ====================
    @Transactional(readOnly = true)
    public NumberPhone findByIdNumberPhone(Long id) {
        log.info("Fetching NumberPhone by id: {}", id);
        return numberPhoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NumberPhone not found with id: " + id));
    }

    // ==================== UPDATE ====================
    public NumberPhone updateNumberPhone(Long id, NumberPhoneRequest request) {
        log.info("Updating NumberPhone with id: {}", id);

        NumberPhone entity = numberPhoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NumberPhone not found with id: " + id));

        // Обновляем связь с ObjectPlaceTrash если передан новый ID
        if (request.getIdObjectPlaceTrash() != null) {
            // Удаляем номер из старого объекта
            if (entity.getObjectPlaceTrash() != null && entity.getObjectPlaceTrash().getPhones() != null) {
                entity.getObjectPlaceTrash().getPhones().remove(entity);
            }

            // Находим новый объект
            ObjectPlaceTrash newObjectPlaceTrash = objectPlaceTrashRepository.findById(request.getIdObjectPlaceTrash())
                    .orElseThrow(() -> new RuntimeException("ObjectPlaceTrash not found with id: " + request.getIdObjectPlaceTrash()));

            // Обновляем связь
            entity.setObjectPlaceTrash(newObjectPlaceTrash);  // изменено с id_object_place_trash на objectPlaceTrash

            // Добавляем номер в коллекцию нового объекта
            if (newObjectPlaceTrash.getPhones() == null) {
                newObjectPlaceTrash.setPhones(new ArrayList<>());
            }
            newObjectPlaceTrash.getPhones().add(entity);
        }

        // Обновляем номер телефона
        if (request.getNumber() != null) {
            entity.setNumber(request.getNumber());
        }

        return numberPhoneRepository.save(entity);
    }

    // ==================== DELETE ====================
    public void deleteNumberPhone(Long id) {
        log.info("Deleting NumberPhone with id: {}", id);

        NumberPhone entity = numberPhoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NumberPhone not found with id: " + id));

        // Удаляем номер из коллекции связанного объекта
        if (entity.getObjectPlaceTrash() != null && entity.getObjectPlaceTrash().getPhones() != null) {
            entity.getObjectPlaceTrash().getPhones().remove(entity);
        }

        numberPhoneRepository.deleteById(id);
        log.info("Deleted NumberPhone with id: {}", id);
    }
    /*
    // ==================== Дополнительные методы ====================

    // Поиск номеров по объекту
    @Transactional(readOnly = true)
    public List<NumberPhone> findNumberPhonesByObjectPlaceTrashId(Long objectId) {
        log.info("Fetching NumberPhones for ObjectPlaceTrash with id: {}", objectId);
        return numberPhoneRepository.findByObjectPlaceTrash_Id_object_place_trash(objectId);
    }
    */


    public PhysicalState createPhysicalState(PhysicalStateRequest request) {
        log.info("Creating PhysicalState with state: {}", request.getState());

        PhysicalState entity = PhysicalState.builder()
                .state(request.getState())
                .build();

        return physicalStateRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<PhysicalState> findAllPhysicalState() {
        log.info("Fetching all PhysicalState");
        return physicalStateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PhysicalState findByIdPhysicalState(Long id) {
        log.info("Fetching PhysicalState by id: {}", id);
        return physicalStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PhysicalState not found with id: " + id));
    }

    public PhysicalState updatePhysicalState(Long id, PhysicalStateRequest request) {
        log.info("Updating PhysicalState with id: {}", id);

        PhysicalState entity = physicalStateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PhysicalState not found with id: " + id));

        if (request.getState() != null) {
            entity.setState(request.getState());
        }

        return physicalStateRepository.save(entity);
    }

    public void deletePhysicalState(Long id) {
        log.info("Deleting PhysicalState with id: {}", id);

        if (!physicalStateRepository.existsById(id)) {
            throw new RuntimeException("PhysicalState not found with id: " + id);
        }

        physicalStateRepository.deleteById(id);
    }



    public Region createRegion(RegionRequest request) {
        log.info("Creating Region with name: {}", request.getNameRegion());

        Region entity = Region.builder()
                .name_region(request.getNameRegion())
                .build();

        return regionRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Region> findAllRegion() {
        log.info("Fetching all Region");
        return regionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Region findByIdRegion(Long id) {
        log.info("Fetching Region by id: {}", id);
        return regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found with id: " + id));
    }

    public Region updateRegion(Long id, RegionRequest request) {
        log.info("Updating Region with id: {}", id);

        Region entity = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region not found with id: " + id));

        if (request.getNameRegion() != null) {
            entity.setName_region(request.getNameRegion());
        }

        return regionRepository.save(entity);
    }

    public void deleteRegion(Long id) {
        log.info("Deleting Region with id: {}", id);

        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Region not found with id: " + id);
        }

        regionRepository.deleteById(id);
    }



    public StorageScheme createStorageScheme(StorageSchemeRequest request) {
        log.info("Creating StorageScheme with name: {}", request.getNameStorageScheme());

        StorageScheme entity = StorageScheme.builder()
                .name_storage_scheme(request.getNameStorageScheme())
                .build();

        return storageSchemeRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<StorageScheme> findAllStorageScheme() {
        log.info("Fetching all StorageScheme");
        return storageSchemeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public StorageScheme findByIdStorageScheme(Long id) {
        log.info("Fetching StorageScheme by id: {}", id);
        return storageSchemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StorageScheme not found with id: " + id));
    }

    public StorageScheme updateStorageScheme(Long id, StorageSchemeRequest request) {
        log.info("Updating StorageScheme with id: {}", id);

        StorageScheme entity = storageSchemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StorageScheme not found with id: " + id));

        if (request.getNameStorageScheme() != null) {
            entity.setName_storage_scheme(request.getNameStorageScheme());
        }

        return storageSchemeRepository.save(entity);
    }

    public void deleteStorageScheme(Long id) {
        log.info("Deleting StorageScheme with id: {}", id);

        if (!storageSchemeRepository.existsById(id)) {
            throw new RuntimeException("StorageScheme not found with id: " + id);
        }

        storageSchemeRepository.deleteById(id);
    }



    public TypeTrash1 createTypeTrash1(TypeTrash1Request request) {
        log.info("Creating TypeTrash1 with name: {}", request.getNameTypeTrash1());

        TypeTrash1 entity = TypeTrash1.builder()
                .name_type_trash1(request.getNameTypeTrash1())
                .build();

        return typeTrash1Repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<TypeTrash1> findAllTypeTrash1() {
        log.info("Fetching all TypeTrash1");
        return typeTrash1Repository.findAll();
    }

    @Transactional(readOnly = true)
    public TypeTrash1 findByIdTypeTrash1(Long id) {
        log.info("Fetching TypeTrash1 by id: {}", id);
        return typeTrash1Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeTrash1 not found with id: " + id));
    }

    public TypeTrash1 updateTypeTrash1(Long id, TypeTrash1Request request) {
        log.info("Updating TypeTrash1 with id: {}", id);

        TypeTrash1 entity = typeTrash1Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeTrash1 not found with id: " + id));

        if (request.getNameTypeTrash1() != null) {
            entity.setName_type_trash1(request.getNameTypeTrash1());
        }

        return typeTrash1Repository.save(entity);
    }

    public void deleteTypeTrash1(Long id) {
        log.info("Deleting TypeTrash1 with id: {}", id);

        if (!typeTrash1Repository.existsById(id)) {
            throw new RuntimeException("TypeTrash1 not found with id: " + id);
        }

        typeTrash1Repository.deleteById(id);
    }

}
