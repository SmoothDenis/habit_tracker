package com.example.habitstracker.dsl;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import com.example.habitstracker.CleanerService;
import com.example.habitstracker.constants.ApiConstants;
import com.example.habitstracker.mappers.UserMapper;
import com.example.habitstracker.models.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

/**
 * Инструменты для взаимодействия с механизмом авторизации
 */
public class AuthDSL {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Зарегистрировать нового пользователя
     *
     * @param user Пользователь
     */
    public static void register(UserEntity user) throws JsonProcessingException {
        var dto = UserMapper.toDTO(user);

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.writeValueAsString(dto))
            .when()
                .post("/auth/registration")
            .then()
                .statusCode(200);
        // @formatter:on

        CleanerService.addTask(() -> {
            if (TokenHolder.token == null) {
                try {
                    login(user);
                } catch (JsonProcessingException e) {
                    Assertions.fail("Can't login. Casued by: " + e.getMessage());
                }
            }

            UserDSL.deleteUser(user);
        });
    }

    /**
     * Залогинится в системе
     *
     * @param user Пользователь, под котором логинимся
     */
    public static void login(UserEntity user) throws JsonProcessingException {
        var values = new HashMap<String, String>();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        var json = OBJECT_MAPPER.writeValueAsString(values);

        // @formatter:off
        var response = given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(ApiConstants.LOGIN)
                .then()
                .statusCode(200);
        // @formatter:on
        TokenHolder.token = response.extract().header("Authorization").split(" ")[1];
    }
}
