package com.example.eco_service.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Hibernate ddl-auto=update часто не снимает NOT NULL с уже существующей колонки; Envers пишет в *_AUD и
 * тоже может оставить id_class_danger NOT NULL — тогда сброс класса опасности даёт 500 от БД.
 */
@Component
@Order(0)
@Slf4j
public class MagazinTrashClassDangerNullablePatch implements ApplicationRunner {

    private final DataSource dataSource;

    public MagazinTrashClassDangerNullablePatch(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static void tryAlter(DataSource ds, String sql) {
        try (Connection c = ds.getConnection();
                Statement st = c.createStatement()) {
            st.execute(sql);
            log.debug("Schema: {}", sql);
        } catch (SQLException e) {
            log.trace("Schema skip: {} — {}", sql, e.getMessage());
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        String[] statements = {
            "ALTER TABLE magazin_trash ALTER COLUMN id_class_danger DROP NOT NULL",
            "ALTER TABLE magazin_trash_aud ALTER COLUMN id_class_danger DROP NOT NULL",
            "ALTER TABLE magazintrash ALTER COLUMN id_class_danger DROP NOT NULL",
            "ALTER TABLE magazintrash_aud ALTER COLUMN id_class_danger DROP NOT NULL",
            "ALTER TABLE \"MagazinTrash\" ALTER COLUMN id_class_danger DROP NOT NULL",
            "ALTER TABLE \"MagazinTrash_AUD\" ALTER COLUMN id_class_danger DROP NOT NULL",
        };
        for (String sql : statements) {
            tryAlter(dataSource, sql);
        }
    }
}
