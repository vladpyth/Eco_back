package com.example.eco_service.routers;

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
import org.springframework.web.bind.annotation.*;
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
        this.testUserRep = testUserRep;  // присваиваем параметр полю
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


}