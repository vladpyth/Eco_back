package com.example.eco_service.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление пользователя")

public class TestUpdateDataTestUserRequest {
    @Schema(description = "Имя пользователя", example = "john_updated")
    @Size(min = 3, max = 50, message = "Имя должно быть от 3 до 50 символов")
    private String username;

    @Schema(description = "Email", example = "john.updated@example.com")
    @Email(message = "Некорректный email")
    private String email;

    @Schema(description = "Полное имя", example = "John Updated Doe")
    @Size(max = 100, message = "Максимум 100 символов")
    private String fullName;

    @Schema(description = "Возраст", example = "26")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private Integer age;

    @Schema(description = "Номер телефона", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Новый пароль", example = "newpassword123")
    @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
    private String passwordHash;

}
