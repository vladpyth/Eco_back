package com.example.eco_service.routers;

import com.example.eco_service.dto.request.UpdateDataTestUserRequest;
import com.example.eco_service.entities.*;
import com.example.eco_service.repositories.*;
import com.example.eco_service.dto.request.EchoRequest;
import com.example.eco_service.dto.response.EchoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Тестовый контроллер", description = "API для проверки работы сервера")
public class TestController {


    private final TestUserRep testUserRep;

    public TestController(TestUserRep testUserRep) {
        this.testUserRep = testUserRep;
    }


    @PostMapping("/echo")
    @Operation(summary = "Эхо-сервис", description = "Возвращает отправленные данные")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    public EchoResponse echo(@Valid @RequestBody EchoRequest request) {


        return EchoResponse.builder()
                .received(true)
                .timestamp(LocalDateTime.now())
                .name(request.getName())
                .age(request.getAge())
                .build();
    }

    @GetMapping("/users")
    @Operation(summary = "тест юзер", description = "Возвращает юзеров")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    public List<TestUser> getAllUsers() {
        return testUserRep.findAll();
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Обновить пользователя", description = "Обновляет данные существующего пользователя по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    public ResponseEntity<TestUser> updateUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id,

            @Parameter(description = "Данные для обновления", required = true)
            @Valid @RequestBody UpdateDataTestUserRequest request) {



        // Ищем существующего пользователя
        TestUser existingUser = testUserRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь с ID " + id + " не найден"));

        // Проверяем уникальность если меняется username
        if (request.getUsername() != null && !request.getUsername().equals(existingUser.getUsername())) {
            if (testUserRep.existsByUsername(request.getUsername())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username уже занят");
            }
            existingUser.setUsername(request.getUsername());
        }

        // Проверяем уникальность если меняется email
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (testUserRep.existsByEmail(request.getEmail())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Email уже используется");
            }
            existingUser.setEmail(request.getEmail());
        }

        // Обновляем остальные поля
        if (request.getFullName() != null) {
            existingUser.setFullName(request.getFullName());
        }
        if (request.getAge() != null) {
            existingUser.setAge(request.getAge());
        }
        if (request.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getPasswordHash() != null) {
            existingUser.setPasswordHash(request.getPasswordHash());
        }

        // Сохраняем обновленного пользователя
        TestUser updatedUser = testUserRep.save(existingUser);


        return ResponseEntity.ok(updatedUser);
    }

}