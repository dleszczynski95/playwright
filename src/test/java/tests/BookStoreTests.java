package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import configuration.BaseTest;
import objects.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.BookStoreRest;

import java.util.UUID;

public class BookStoreTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BookStoreTests.class);
    private final BookStoreRest rest;

    public BookStoreTests() {
        super(TestType.BACKEND);
        rest = new BookStoreRest();
    }

    @Test
    public void createUserThatExists() {
        String userName = RandomStringUtils.randomAlphabetic(10);
        User user = new User(userName, "Haslo12345!@", "email@email.com");
        APIResponse apiResponse = rest.registerUser(user);
        AssertJUnit.assertEquals(apiResponse.status(), 201);
        JsonNode body = rest.getRegisterBody(apiResponse);
        AssertJUnit.assertEquals(body.get("username").asText(), userName);
        AssertJUnit.assertEquals(UUID.fromString(body.get("userID").asText()).toString(), body.get("userID").asText());

        apiResponse = rest.registerUser(user);
        body = rest.getRegisterBody(apiResponse);
        AssertJUnit.assertEquals(apiResponse.status(), 406);
        AssertJUnit.assertEquals(body.get("message").asText(), "User exists!");
        AssertJUnit.assertEquals(body.get("code").asInt(), 1204);
    }

    @Test
    public void createUserWithoutUserName() {
        User user = new User("", "Haslo12345!@", "email@email.com");
        APIResponse apiResponse = rest.registerUser(user);
        JsonNode body = rest.getRegisterBody(apiResponse);

        AssertJUnit.assertEquals(apiResponse.status(), 400);
        AssertJUnit.assertEquals(body.get("message").asText(), "UserName and Password required.");
        AssertJUnit.assertEquals(body.get("code").asInt(), 1200);
    }

    @Test
    public void createUserWithoutPassword() {
        User user = new User(RandomStringUtils.randomAlphabetic(10), "", "email@email.com");
        APIResponse apiResponse = rest.registerUser(user);
        JsonNode body = rest.getRegisterBody(apiResponse);

        AssertJUnit.assertEquals(apiResponse.status(), 400);
        AssertJUnit.assertEquals(body.get("message").asText(), "UserName and Password required.");
        AssertJUnit.assertEquals(body.get("code").asInt(), 1200);
    }

    @Test(dataProvider = "passwords")
    public void checkCreatingWithDifferentPassword(Boolean isProperly, String password) {
        User user = new User(RandomStringUtils.randomAlphabetic(10), password, "email@email.com");
        APIResponse apiResponse = rest.registerUser(user);
        JsonNode body = rest.getRegisterBody(apiResponse);

        AssertJUnit.assertEquals(apiResponse.status(), isProperly ? 201 : 400);
        if (!isProperly) {
            AssertJUnit.assertEquals(body.get("message").asText(), config.getWrongPasswordError());
            AssertJUnit.assertEquals(body.get("code").asInt(), 1300);
        } else {
            AssertJUnit.assertEquals(UUID.fromString(body.get("userID").asText()).toString(), body.get("userID").asText());
        }
    }

    @DataProvider(name = "passwords")
    public Object[][] passwords() {
        return new Object[][]{
                {false, RandomStringUtils.randomAlphabetic(10)},
                {false, RandomStringUtils.randomNumeric(10)},
                {false, "Has12!"},
                {false, "Haslo123"},
                {true, "Haslo123!"},
                {false, "haslo123!"}

        };
    }
}