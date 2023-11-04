package io.swagger.petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.swagger.petstore.model.user.User;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Epic("User API Test")
public class UserTest extends BaseTest {

    @BeforeTest
    public void init() {

    }

    @AfterTest
    public void finit() {

    }

    @Test(description = "Create user")
    public void shouldCreateUser() throws JsonProcessingException {

    }

    @Test(description = "Update user")
    public void shouldUpdateUser() throws JsonProcessingException {

    }

    @Test(description = "Delete user")
    public void shouldDeleteUser() throws JsonProcessingException {

    }

    @Test(description = "Get user by user name")
    public void shouldReturnUserByUsername() throws JsonProcessingException {

    }

    @Test(description = "Creates list of users with given input array")
    public void shouldCreateUsersByArray() throws JsonProcessingException {

    }

    @Test(description = "Creates list of users with given input array")
    public void shouldCreateUsersByList() throws JsonProcessingException {

    }

    @Test(description = "Logs user into system")
    public void shouldLogin() throws JsonProcessingException {

    }

    @Test(description = "Logs out current logged in user session")
    public void shouldLogout() throws JsonProcessingException {

    }

    @Step("Запрос на создание пользователя")
    public Response createUser() throws JsonProcessingException {
        User user = getUser("test", "example@example.org", "+0-000-000-00-00");
        Response response = RestAssured.given(specification)
                .body(mapper.writeValueAsString(user))
                .post("/user");
        return response;
    }

    @Step("Запрос на удаление пользователя")
    public Response deleteUser(String userName) throws JsonProcessingException {
        Response response = RestAssured.given(specification)
                .delete(String.format("/user/%s", userName));
        return response;
    }

    @Step("Запрос на удаление пользователя")
    public void deleteUserList(List<User> userList) throws JsonProcessingException {
        for(User u : userList) {
            Response response = RestAssured.given(specification)
                    .delete(String.format("/user/%s", u.getUsername()));
        }
    }

    @Step("Создание тестового пользователя")
    public User getUser(String userName, String email, String phone) {
        return User.builder()
                .id(0L)
                .username(userName)
                .firstName(userName)
                .lastName(userName)
                .email(email)
                .password(userName)
                .phone(phone)
                .userStatus(0)
                .build();
    }

    @Step("Создание списка тестовых пользователей")
    public List<User> getUserList(String userName, String email, String phone, int listSize) {
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            userList.add(User.builder()
                    .id((long) i)
                    .username(userName)
                    .firstName(userName)
                    .lastName(userName)
                    .email(email)
                    .password(userName)
                    .phone(phone)
                    .userStatus(i)
                    .build());
        }
        return userList;
    }
}
