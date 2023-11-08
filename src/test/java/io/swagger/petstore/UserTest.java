package io.swagger.petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.swagger.petstore.model.user.User;
import io.swagger.petstore.model.user.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Epic("User API Test")
public class UserTest extends BaseTest {
    private final String USER_NAME = "TestUserName";
    private final String UPDATE_USER_NAME = "UpdateTestUserName";
    private final String EMAIL = "example@example.org";
    private final String PHONE = "+0-000-000-00-00";

    @Test(description = "Create user")
    public void shouldCreateUser() throws JsonProcessingException {
        User user = createUser(USER_NAME, EMAIL, PHONE);
        Response response = createUser(user);
        deleteUser(USER_NAME);
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertNotNull(userResponse.getMessage());
    }

    @Test(description = "Update user")
    public void shouldUpdateUser() throws JsonProcessingException {
        User user = createUser(USER_NAME, EMAIL, PHONE);
        createUser(user);
        user.setUsername(UPDATE_USER_NAME);
        Response response = RestAssured.given(specification)
                .body(mapper.writeValueAsString(user))
                .put(String.format("/user/%s",USER_NAME));
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertNotNull(userResponse.getMessage());
    }

    @Test(description = "Delete user")
    public void shouldDeleteUser() throws JsonProcessingException {
        User user = createUser(USER_NAME, EMAIL, PHONE);
        createUser(user);
        Response response = deleteUser(USER_NAME);
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertNotNull(userResponse.getMessage());
    }

    @Test(description = "Get user by user name")
    public void shouldReturnUserByUsername() throws JsonProcessingException {
        User user = createUser(USER_NAME, EMAIL, PHONE);
        createUser(user);
        Response response = RestAssured.given(specification)
                .get(String.format("/user/%s",USER_NAME));
        User returned = response.as(User.class);
        Assert.assertEquals(returned, user);
    }

    @Test(description = "Creates list of users with given input array")
    public void shouldCreateUsersByArray() throws JsonProcessingException {
        List<User> userList = createUserList(USER_NAME, EMAIL, PHONE, 5);
        Response response = RestAssured.given(specification)
                .body(mapper.writeValueAsString(userList))
                .post("/user/createWithArray");
        deleteUserList(userList);
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertEquals(userResponse.getMessage(), "ok");
    }

    @Test(description = "Creates list of users with given input array")
    public void shouldCreateUsersByList() throws JsonProcessingException {
        List<User> userList = createUserList(USER_NAME, EMAIL, PHONE, 5);
        Response response = RestAssured.given(specification)
                .body(mapper.writeValueAsString(userList))
                .post("/user/createWithList");
        deleteUserList(userList);
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertEquals(userResponse.getMessage(), "ok");
    }

    @Test(description = "Logs user into system")
    public void shouldLogin() throws JsonProcessingException {
        User user = createUser(USER_NAME, EMAIL, PHONE);
        createUser(user);
        Response response = RestAssured.given(specification)
                .get(String.format("/user/login?username=%s&password=%s",USER_NAME, USER_NAME));
        deleteUser(USER_NAME);
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertTrue(userResponse.getMessage().contains("logged"));
    }

    @Test(description = "Logs out current logged in user session")
    public void shouldLogout() throws JsonProcessingException {
        Response response = RestAssured.given(specification)
                .get("/user/logout");
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertEquals(userResponse.getCode(), 200);
        Assert.assertEquals(userResponse.getMessage(), "ok");
    }

    @Step("Запрос на создание пользователя")
    public Response createUser(User user) throws JsonProcessingException {
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
    public User createUser(String userName, String email, String phone) {
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
    public List<User> createUserList(String userName, String email, String phone, int listSize) {
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
