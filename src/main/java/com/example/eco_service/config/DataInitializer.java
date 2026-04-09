package com.example.eco_service.config;

import com.example.eco_service.entities.TestUser;
import com.example.eco_service.repositories.TestUserRep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TestUserRep userRepository;
    private final InterfRegion regionRepository;
    private final InterfCities citiesRepository;
    private final InterfClassDanger classDangerRepository;
    private final InterfTypeTrash1 typeTrash1Repository;
    private final InterfLevelTrash levelTrashRepository;
    private final InterfNameGroup nameGroupRepository;
    private final InterfPhysicalState physicalStateRepository;
    private final InterfStorageScheme storageSchemeRepository;
    private final InterfGroupPlaceSave groupPlaceSaveRepository;
    private final InterfGruopsDegree gruopsDegreeRepository;
    private final InterfCommentsOfPlace commentsOfPlaceRepository;
    private final InterfAroundBuild aroundBuildRepository;
    private final InterfNatualSaveBuilding natualSaveBuildingRepository;
    private final InterfMagazinTrash magazinTrashRepository;
    private final InterfObjectPlaceTrash objectPlaceTrashRepository;
    private final InterfAroundBuildCount aroundBuildCountRepository;
    private final InterfNatualSaveBuildCount natualSaveBuildCountRepository;
    private final InterfNumberPhone numberPhoneRepository;
    private final InterfCleanerBuilds cleanerBuildsRepository;
    private final InterfCharacteristicTrash characteristicTrashRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Checking if database needs initialization...");

        // Проверяем, пустая ли БД (по основной таблице)
        if (objectPlaceTrashRepository.count() > 0) {
            log.info("Database already contains data. Skipping initialization.");
            return;
        }

        log.info("Initializing test data...");

        // ==================== 1. СПРАВОЧНИКИ (без внешних зависимостей) ====================

        // 1.1 Регионы
        List<Region> regions = Arrays.asList(
                Region.builder().name_region("Киевская область").build(),
                Region.builder().name_region("Львовская область").build(),
                Region.builder().name_region("Одесская область").build(),
                Region.builder().name_region("Днепропетровская область").build(),
                Region.builder().name_region("Харьковская область").build()
        );
        regions = regionRepository.saveAll(regions);
        log.info("Created {} regions", regions.size());

        // 1.2 Города
        List<Cities> cities = Arrays.asList(
                Cities.builder().id_region(regions.get(0)).index("01001").district("Киевский").name_cities("Киев").build(),
                Cities.builder().id_region(regions.get(0)).index("08132").district("Вишневский").name_cities("Вишня").build(),
                Cities.builder().id_region(regions.get(1)).index("79000").district("Львовский").name_cities("Львов").build(),
                Cities.builder().id_region(regions.get(2)).index("65000").district("Одесский").name_cities("Одесса").build(),
                Cities.builder().id_region(regions.get(3)).index("49000").district("Днепрский").name_cities("Днепр").build(),
                Cities.builder().id_region(regions.get(4)).index("61000").district("Харьковский").name_cities("Харьков").build()
        );
        cities = citiesRepository.saveAll(cities);
        log.info("Created {} cities", cities.size());

        // 1.3 Классы опасности
        List<ClassDanger> classDangers = Arrays.asList(
                ClassDanger.builder().class_danger(1).build(),
                ClassDanger.builder().class_danger(2).build(),
                ClassDanger.builder().class_danger(3).build(),
                ClassDanger.builder().class_danger(4).build(),
                ClassDanger.builder().class_danger(5).build()
        );
        classDangers = classDangerRepository.saveAll(classDangers);
        log.info("Created {} danger classes", classDangers.size());

        // 1.4 Типы отходов (уровень 1)
        List<TypeTrash1> typeTrash1s = Arrays.asList(
                TypeTrash1.builder().name_type_trash1("Промышленные отходы").build(),
                TypeTrash1.builder().name_type_trash1("Бытовые отходы").build(),
                TypeTrash1.builder().name_type_trash1("Опасные отходы").build(),
                TypeTrash1.builder().name_type_trash1("Строительные отходы").build()
        );
        typeTrash1s = typeTrash1Repository.saveAll(typeTrash1s);
        log.info("Created {} trash types", typeTrash1s.size());

        // 1.5 Уровни отходов
        List<LevelTrash> levelTrashes = Arrays.asList(
                LevelTrash.builder().name_level_trash("I уровень опасности").build(),
                LevelTrash.builder().name_level_trash("II уровень опасности").build(),
                LevelTrash.builder().name_level_trash("III уровень опасности").build(),
                LevelTrash.builder().name_level_trash("IV уровень опасности").build(),
                LevelTrash.builder().name_level_trash("V уровень опасности").build()
        );
        levelTrashes = levelTrashRepository.saveAll(levelTrashes);
        log.info("Created {} trash levels", levelTrashes.size());

        // 1.6 Группы названий
        List<NameGroup> nameGroups = Arrays.asList(
                NameGroup.builder().name_group("Органические").build(),
                NameGroup.builder().name_group("Неорганические").build(),
                NameGroup.builder().name_group("Пластмассы").build(),
                NameGroup.builder().name_group("Металлы").build(),
                NameGroup.builder().name_group("Стекло").build()
        );
        nameGroups = nameGroupRepository.saveAll(nameGroups);
        log.info("Created {} name groups", nameGroups.size());

        // 1.7 Физические состояния
        List<PhysicalState> physicalStates = Arrays.asList(
                PhysicalState.builder().state("твердое").build(),
                PhysicalState.builder().state("жидкое").build(),
                PhysicalState.builder().state("пастообразное").build(),
                PhysicalState.builder().state("сыпучее").build()
        );
        physicalStates = physicalStateRepository.saveAll(physicalStates);
        log.info("Created {} physical states", physicalStates.size());

        // 1.8 Схемы хранения
        List<StorageScheme> storageSchemes = Arrays.asList(
                StorageScheme.builder().name_storage_scheme("Открытый полигон").build(),
                StorageScheme.builder().name_storage_scheme("Закрытый полигон").build(),
                StorageScheme.builder().name_storage_scheme("Подземное хранение").build(),
                StorageScheme.builder().name_storage_scheme("Сортировочная станция").build()
        );
        storageSchemes = storageSchemeRepository.saveAll(storageSchemes);
        log.info("Created {} storage schemes", storageSchemes.size());

        // 1.9 Группы мест сохранения
        List<GroupPlaceSave> groupPlaceSaves = Arrays.asList(
                GroupPlaceSave.builder().name_region("Центральный").build(),
                GroupPlaceSave.builder().name_region("Северный").build(),
                GroupPlaceSave.builder().name_region("Южный").build(),
                GroupPlaceSave.builder().name_region("Западный").build(),
                GroupPlaceSave.builder().name_region("Восточный").build()
        );
        groupPlaceSaves = groupPlaceSaveRepository.saveAll(groupPlaceSaves);
        log.info("Created {} place groups", groupPlaceSaves.size());

        // 1.10 Группы степеней
        List<GruopsDegree> gruopsDegrees = Arrays.asList(
                GruopsDegree.builder().namber_gruop(1).build(),
                GruopsDegree.builder().namber_gruop(2).build(),
                GruopsDegree.builder().namber_gruop(3).build()
        );
        gruopsDegrees = gruopsDegreeRepository.saveAll(gruopsDegrees);
        log.info("Created {} degree groups", gruopsDegrees.size());

        // 1.11 Комментарии (создаем отдельно для каждого объекта, так как отношение @OneToOne)
        // Сохраняем их сначала, чтобы потом использовать ID

        // 1.12 Окружающие постройки
        List<AroundBuild> aroundBuilds = Arrays.asList(
                AroundBuild.builder().name("Жилой комплекс").build(),
                AroundBuild.builder().name("Промышленная зона").build(),
                AroundBuild.builder().name("Лесопарковая зона").build(),
                AroundBuild.builder().name("Сельскохозяйственные угодья").build(),
                AroundBuild.builder().name("Водоем").build()
        );
        aroundBuilds = aroundBuildRepository.saveAll(aroundBuilds);
        log.info("Created {} around builds", aroundBuilds.size());

        // 1.13 Природоохранные сооружения
        List<NatualSaveBuilding> natualSaveBuildings = Arrays.asList(
                NatualSaveBuilding.builder().name("Защитная дамба").build(),
                NatualSaveBuilding.builder().name("Дренажная система").build(),
                NatualSaveBuilding.builder().name("Биоплато").build(),
                NatualSaveBuilding.builder().name("Ветрозащитная стена").build()
        );
        natualSaveBuildings = natualSaveBuildingRepository.saveAll(natualSaveBuildings);
        log.info("Created {} natural save buildings", natualSaveBuildings.size());

        // ==================== 2. ОСНОВНЫЕ СУЩНОСТИ ====================

        // 2.1 Магазин отходов (связи со справочниками)
        List<MagazinTrash> magazinTrashes = Arrays.asList(
                MagazinTrash.builder()
                        .id_class_danger(classDangers.get(0))
                        .id_type_trash(typeTrash1s.get(0))
                        .id_level_trash(levelTrashes.get(0))
                        .id_mame_group(nameGroups.get(0))
                        .code_trash("010101")
                        .name_trash("Органические отходы")
                        .level1(1)
                        .group2(1)
                        .level3(1)
                        .level4("A")
                        .build(),
                MagazinTrash.builder()
                        .id_class_danger(classDangers.get(2))
                        .id_type_trash(typeTrash1s.get(2))
                        .id_level_trash(levelTrashes.get(2))
                        .id_mame_group(nameGroups.get(2))
                        .code_trash("020202")
                        .name_trash("Пластиковые отходы")
                        .level1(2)
                        .group2(2)
                        .level3(3)
                        .level4("B")
                        .build(),
                MagazinTrash.builder()
                        .id_class_danger(classDangers.get(4))
                        .id_type_trash(typeTrash1s.get(1))
                        .id_level_trash(levelTrashes.get(4))
                        .id_mame_group(nameGroups.get(3))
                        .code_trash("030303")
                        .name_trash("Металлолом")
                        .level1(3)
                        .group2(3)
                        .level3(5)
                        .level4("C")
                        .build()
        );
        magazinTrashes = magazinTrashRepository.saveAll(magazinTrashes);
        log.info("Created {} trash magazines", magazinTrashes.size());

        // 2.2 Пользователи
        TestUser user1 = TestUser.builder()
                .username("eco_inspector")
                .email("inspector@eco.gov.ua")
                .passwordHash("$2a$10$encrypted_hash_here")
                .fullName("Петренко Иван Васильевич")
                .age(42)
                .phoneNumber("+380501234567")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        TestUser user2 = TestUser.builder()
                .username("plant_director")
                .email("director@ecoplant.com")
                .passwordHash("$2a$10$encrypted_hash_here")
                .fullName("Коваленко Сергей Николаевич")
                .age(55)
                .phoneNumber("+380671234567")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.saveAll(Arrays.asList(user1, user2));
        log.info("Created test users");

        // 2.3 Комментарии - создаем отдельно для каждого объекта (чтобы не нарушать уникальность)
        CommentsOfPlace comment1 = CommentsOfPlace.builder()
                .comments("Объект работает в штатном режиме, соответствует экологическим нормам")
                .build();

        CommentsOfPlace comment2 = CommentsOfPlace.builder()
                .comments("Станция сортировки функционирует стабильно, требуется модернизация оборудования")
                .build();

        CommentsOfPlace comment3 = CommentsOfPlace.builder()
                .comments("Полигон закрыт, ведутся работы по рекультивации территории")
                .build();

        comment1 = commentsOfPlaceRepository.save(comment1);
        comment2 = commentsOfPlaceRepository.save(comment2);
        comment3 = commentsOfPlaceRepository.save(comment3);
        log.info("Created {} comments for objects", 3);

        // 2.4 Основные объекты размещения отходов (3 объекта для разнообразия)
        List<ObjectPlaceTrash> objectPlaceTrashes = Arrays.asList(
                // Объект 1: Киевский полигон (активный)
                ObjectPlaceTrash.builder()
                        .id_registration("REG001")
                        .register(1001)
                        .date_register(LocalDate.of(2015, 3, 15))
                        .id_cities(cities.get(0))
                        .id_group_place_save(groupPlaceSaves.get(0))
                        .id_storage_scheme(storageSchemes.get(0))
                        .id_gruops_degree(gruopsDegrees.get(0))
                        .id_comments_of_place(comment1)
                        .name_obj("Киевский полигон ТБО")
                        .name_own("Киевская городская администрация")
                        .start_use(2015)
                        .servise_life("25 лет")
                        .company_located("ООО Эко-Сервис")
                        .place_obj("Киевская обл., с. Пидгорцы")
                        .project("Проект №123/2014")
                        .state_expertize(true)
                        .eco_pasport("Пас-001")
                        .prava_place("Аренда на 49 лет")
                        .confirmation_use(true)
                        .square(125000.5f)
                        .use_square(85000.0f)
                        .trash_square(40000.0f)
                        .project_power("500000 тонн/год")
                        .facticheskay_power("350000 тонн/год")
                        .accomulated_trash("2500000 тонн")
                        .type_grounds("Суглинки")
                        .ander_water("5 метров")
                        .observation_hole("Скважина №1")
                        .date_axclute(null)
                        .reson_axclute(null)
                        .status(true)
                        .build(),

                // Объект 2: Львовская сортировочная станция (активная)
                ObjectPlaceTrash.builder()
                        .id_registration("REG002")
                        .register(1002)
                        .date_register(LocalDate.of(2018, 7, 20))
                        .id_cities(cities.get(2))
                        .id_group_place_save(groupPlaceSaves.get(3))
                        .id_storage_scheme(storageSchemes.get(3))
                        .id_gruops_degree(gruopsDegrees.get(1))
                        .id_comments_of_place(comment2)
                        .name_obj("Львовская сортировочная станция")
                        .name_own("Львовский горсовет")
                        .start_use(2018)
                        .servise_life("30 лет")
                        .company_located("ТОВ Запад-Эко")
                        .place_obj("г. Львов, ул. Промышленная")
                        .project("Проект №456/2017")
                        .state_expertize(true)
                        .eco_pasport("Пас-002")
                        .prava_place("Собственность")
                        .confirmation_use(true)
                        .square(45000.0f)
                        .use_square(32000.0f)
                        .trash_square(18000.0f)
                        .project_power("200000 тонн/год")
                        .facticheskay_power("185000 тонн/год")
                        .accomulated_trash("980000 тонн")
                        .type_grounds("Глина")
                        .ander_water("8 метров")
                        .observation_hole("Скважина №2,3")
                        .date_axclute(null)
                        .reson_axclute(null)
                        .status(true)
                        .build(),

                // Объект 3: Одесский полигон (закрытый/неактивный)
                ObjectPlaceTrash.builder()
                        .id_registration("REG003")
                        .register(1003)
                        .date_register(LocalDate.of(2005, 5, 10))
                        .id_cities(cities.get(3))
                        .id_group_place_save(groupPlaceSaves.get(2))
                        .id_storage_scheme(storageSchemes.get(1))
                        .id_gruops_degree(gruopsDegrees.get(2))
                        .id_comments_of_place(comment3)
                        .name_obj("Одесский полигон ТБО")
                        .name_own("Одесская областная администрация")
                        .start_use(2005)
                        .servise_life("20 лет")
                        .company_located("ТОВ Чистое море")
                        .place_obj("Одесская обл., с. Усатово")
                        .project("Проект №789/2004")
                        .state_expertize(true)
                        .eco_pasport("Пас-003")
                        .prava_place("Постоянное пользование")
                        .confirmation_use(false)
                        .square(85000.0f)
                        .use_square(85000.0f)
                        .trash_square(82000.0f)
                        .project_power("300000 тонн/год")
                        .facticheskay_power("280000 тонн/год")
                        .accomulated_trash("4500000 тонн")
                        .type_grounds("Известняк")
                        .ander_water("3 метра")
                        .observation_hole("Скважина №4,5,6")
                        .date_axclute(LocalDate.of(2025, 12, 31))
                        .reson_axclute("Исчерпание мощности, закрытие по экологическим нормам")
                        .status(false)
                        .build()
        );
        objectPlaceTrashes = objectPlaceTrashRepository.saveAll(objectPlaceTrashes);
        log.info("Created {} trash objects", objectPlaceTrashes.size());

        // 2.5 Связи объектов с окружающими постройками (многие ко многим через таблицу AroundBuildCount)
        List<AroundBuildCount> aroundBuildCounts = Arrays.asList(
                AroundBuildCount.builder()
                        .id_around_build(aroundBuilds.get(0))
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .build(),
                AroundBuildCount.builder()
                        .id_around_build(aroundBuilds.get(1))
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .build(),
                AroundBuildCount.builder()
                        .id_around_build(aroundBuilds.get(2))
                        .id_object_place_trash(objectPlaceTrashes.get(1))
                        .build(),
                AroundBuildCount.builder()
                        .id_around_build(aroundBuilds.get(3))
                        .id_object_place_trash(objectPlaceTrashes.get(2))
                        .build(),
                AroundBuildCount.builder()
                        .id_around_build(aroundBuilds.get(4))
                        .id_object_place_trash(objectPlaceTrashes.get(2))
                        .build()
        );
        aroundBuildCountRepository.saveAll(aroundBuildCounts);
        log.info("Created {} around build connections", aroundBuildCounts.size());

        // 2.6 Связи с природоохранными сооружениями
        List<NatualSaveBuildCount> natualSaveBuildCounts = Arrays.asList(
                NatualSaveBuildCount.builder()
                        .id_natual_save_build(natualSaveBuildings.get(0))
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .build(),
                NatualSaveBuildCount.builder()
                        .id_natual_save_build(natualSaveBuildings.get(1))
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .build(),
                NatualSaveBuildCount.builder()
                        .id_natual_save_build(natualSaveBuildings.get(2))
                        .id_object_place_trash(objectPlaceTrashes.get(1))
                        .build(),
                NatualSaveBuildCount.builder()
                        .id_natual_save_build(natualSaveBuildings.get(3))
                        .id_object_place_trash(objectPlaceTrashes.get(2))
                        .build()
        );
        natualSaveBuildCountRepository.saveAll(natualSaveBuildCounts);
        log.info("Created {} natural save connections", natualSaveBuildCounts.size());

        // 2.7 Номера телефонов объектов
        List<NumberPhone> numberPhones = Arrays.asList(
                NumberPhone.builder()
                        .objectPlaceTrash(objectPlaceTrashes.get(0))
                        .number("+380442345678")
                        .build(),
                NumberPhone.builder()
                        .objectPlaceTrash(objectPlaceTrashes.get(0))
                        .number("+380442345679")
                        .build(),
                NumberPhone.builder()
                        .objectPlaceTrash(objectPlaceTrashes.get(1))
                        .number("+380322345678")
                        .build(),
                NumberPhone.builder()
                        .objectPlaceTrash(objectPlaceTrashes.get(2))
                        .number("+380482345678")
                        .build()
        );
        numberPhoneRepository.saveAll(numberPhones);
        log.info("Created {} phone numbers", numberPhones.size());

        // 2.8 Очистные сооружения
        List<CleanerBuilds> cleanerBuilds = Arrays.asList(
                CleanerBuilds.builder()
                        .registr_number("CLN001")
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .name_object("Фильтрационная станция №1")
                        .start_use(2016)
                        .all_square(2500.5f)
                        .trash_count(15000.0f)
                        .build(),
                CleanerBuilds.builder()
                        .registr_number("CLN002")
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .name_object("Биологическая очистка")
                        .start_use(2017)
                        .all_square(1800.0f)
                        .trash_count(12000.0f)
                        .build(),
                CleanerBuilds.builder()
                        .registr_number("CLN003")
                        .id_object_place_trash(objectPlaceTrashes.get(1))
                        .name_object("Сортировочная линия")
                        .start_use(2019)
                        .all_square(3500.0f)
                        .trash_count(25000.0f)
                        .build(),
                CleanerBuilds.builder()
                        .registr_number("CLN004")
                        .id_object_place_trash(objectPlaceTrashes.get(2))
                        .name_object("Система сбора фильтрата")
                        .start_use(2006)
                        .all_square(1200.0f)
                        .trash_count(8500.0f)
                        .build()
        );
        cleanerBuildsRepository.saveAll(cleanerBuilds);
        log.info("Created {} cleaner buildings", cleanerBuilds.size());

        // 2.9 Характеристики отходов (ОДНА характеристика на объект)
        List<CharacteristicTrash> characteristicTrashes = Arrays.asList(
                // Характеристика для Киевского полигона
                CharacteristicTrash.builder()
                        .id_object_place_trash(objectPlaceTrashes.get(0))
                        .id_magazin_trash(magazinTrashes.get(0))
                        .id_state(physicalStates.get(0))
                        .weight_for_year(350000.5f)
                        .square_for_year(12500.0f)
                        .build(),

                // Характеристика для Львовской сортировочной станции
                CharacteristicTrash.builder()
                        .id_object_place_trash(objectPlaceTrashes.get(1))
                        .id_magazin_trash(magazinTrashes.get(1))
                        .id_state(physicalStates.get(0))
                        .weight_for_year(185000.3f)
                        .square_for_year(8500.0f)
                        .build(),

                // Характеристика для Одесского полигона
                CharacteristicTrash.builder()
                        .id_object_place_trash(objectPlaceTrashes.get(2))
                        .id_magazin_trash(magazinTrashes.get(2))
                        .id_state(physicalStates.get(3))
                        .weight_for_year(280000.7f)
                        .square_for_year(15000.0f)
                        .build()
        );
        characteristicTrashRepository.saveAll(characteristicTrashes);
        log.info("Created {} trash characteristics (one per object)", characteristicTrashes.size());

        log.info("========================================");
        log.info("Database initialization completed successfully!");
        log.info("Created:");
        log.info("  - {} regions", regions.size());
        log.info("  - {} cities", cities.size());
        log.info("  - {} trash objects", objectPlaceTrashes.size());
        log.info("  - {} users", 2);
        log.info("  - {} comments", 3);
        log.info("  - {} trash characteristics (1 per object)", characteristicTrashes.size());
        log.info("  - {} cleaner buildings", cleanerBuilds.size());
        log.info("  - {} phone numbers", numberPhones.size());
        log.info("========================================");
    }
}