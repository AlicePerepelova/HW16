package tests;

import io.restassured.RestAssured;
import models.requests.CreateUserModel;
import models.requests.SuccessRegRequestUserModel;
import models.responses.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specifications.Specification.*;

public class ApiStart {
  @BeforeAll
  static void setUp() {
    RestAssured.baseURI = "https://reqres.in";
    RestAssured.basePath = "/api";
  }

  @Test
  @DisplayName("Успешная проверка пользователя по id и email")
  void checkUser() {
    UserSingleResponseModel response =
      step("Response user id and email", () ->
        given(defaultRequestSpec)
          .when()
          .get("/users/2")
          .then()
          .spec(createResponseSpec200)
          .extract().as(UserSingleResponseModel.class)
      );

    step("Check that user exist", () -> {
      assertThat(response.getData().getId()).isEqualTo("2");
      assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
    });
  }

  @Test
  @DisplayName("Проверка, что в списке 6 пользователей")
  void checkListUserSize() {
    ListUsersBodyModel response = step("Check request list of users", () ->
      given(defaultRequestSpec)

        .when()
        .queryParam("page", "2")
        .get("/users")
        .then()
        .spec(createResponseSpec200)
        .extract().as(ListUsersBodyModel.class)

    );
    System.out.println("Response: " + response);
    step("Check response that list of users have size 6", () ->
      assertThat(response.getData()).hasSize(6));
  }

  @Test
  @DisplayName("Успешное создание пользователя")
  void successfulcheckCreateUser() {
    CreateUserModel newUser = new CreateUserModel();
    newUser.setName("morpheus");
    newUser.setJob("leader");

    CreateUserResponseModel response = step("Make request", () ->
      given(defaultRequestSpec)

        .body(newUser)
        .when()
        .post("/users")
        .then()
        .spec(createResponseSpec201)
        .extract().as(CreateUserResponseModel.class));

    step("Check response", () -> {
      assertThat(response.getName()).isEqualTo("morpheus");
      assertThat(response.getJob()).isEqualTo("leader");
      assertThat(response.getId()).isNotNull();
      assertThat(response.getCreatedAt()).isNotNull();
    });
  }

  @Test
  @DisplayName("Неуспешное создание пользователя с некорректными данными")
  void unsuccessfulcheckCreateUser() {
    CreateUserModel newUser = new CreateUserModel();
    newUser.setName("");
    newUser.setJob("");

    ErrorCreateUserModel response = step("Make request", () ->
      given(defaultRequestSpec)
        .body(newUser)
        .when()
        .post("/users")
        .then()
        .spec(createResponseSpec400)
        .extract().as(ErrorCreateUserModel.class));
    step("Check response", () ->
      assertEquals("Missing password", response.getError()));

  }

  @Test
  @DisplayName("Успешная регистрация пользователя")
  void successfulRegister() {
    SuccessRegRequestUserModel user = new SuccessRegRequestUserModel();
    user.setEmail("eve.holt@reqres.in");
    user.setPassword("pistol");

    RegistrationUserModel response = step("Make request", () ->
      given(defaultRequestSpec)
        .body(user)
        .when()
        .post("/register")
        .then()
        .spec(createResponseSpec200)
        .extract().as(RegistrationUserModel.class));
    step("Check response", () -> {
      assertThat(response.getId()).isNotNull();
      assertThat(response.getToken()).isNotNull();
    });
  }

  @Test
  @DisplayName("Удаление пользователя")
  void checkDeleteUser() {
    step("Delete user", () ->
      given(defaultRequestSpec)
        .when()
        .delete("/users/{id}", 2)
        .then()
        .assertThat()
        .log().all()
        .spec(createResponseSpec204));
  }
}

