package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import lombok.SneakyThrows;
import objects.User;

import java.nio.charset.StandardCharsets;

public class BookStoreRest {
    private static final String REGISTER_ENDPOINT = "https://demoqa.com/Account/v1/User";
    private final APIRequestContext requestContext;

    public BookStoreRest() {
        requestContext = Playwright.create().request().newContext();
    }

    public APIResponse registerUser(User user) {
        return requestContext
                .post(REGISTER_ENDPOINT, RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData("{\n" +
                                "    \"userName\": \"" + user.getUserName() + "\",\n" +
                                "    \"password\": \"" + user.getPassword() + "\"\n" +
                                "}")
                );
    }

    @SneakyThrows
    public JsonNode getRegisterBody(APIResponse response) {
        String responseAsString = new String(response.body(), StandardCharsets.UTF_8);
        return new ObjectMapper().readTree(responseAsString);
    }
}
