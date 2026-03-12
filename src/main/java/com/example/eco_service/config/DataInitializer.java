package com.example.eco_service.config;

import com.example.eco_service.entities.TestUser;
import com.example.eco_service.repositories.TestUserRep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TestUserRep userRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking if database needs initialization...");

        // Создаем тестовые данные только если таблицы пустые
        if (userRepository.count() == 0) {
            log.info("Initializing test data...");

            // Создаем пользователя
            TestUser user = TestUser.builder()
                    .username("john_doe")
                    .email("john@example.com")
                    .passwordHash("hashed_password_123")
                    .fullName("John Doe")
                    .age(30)
                    .phoneNumber("+1234567890")
                    .build();

            user = userRepository.save(user);
            log.info("Created user: {}", user.getUsername());

        }
    }
}