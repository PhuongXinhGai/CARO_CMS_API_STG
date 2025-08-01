package api.tests.user;

import api.base.BaseApiTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.FileHelper;
import static utils.Constants.*;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import models.login.LoginResponse;
import com.google.gson.Gson;

public class LoginTest extends BaseApiTest {
    @BeforeClass
    public void setup() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    private Response sendLoginRequestFromJson(String filePath) throws IOException {
        String requestBody = FileHelper.readJsonFileAsString(filePath);
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(BASE_URL + LOGIN_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    @Test(priority = 1)
    public void testLoginSuccess() throws IOException {
        Response response = sendLoginRequestFromJson("src/test/resources/data/login/case1-login-success.json");
        assertEquals(response.getStatusCode(), 200);

        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        assert token != null && !token.isEmpty();

        // Assert các thông tin cần thiết
        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(response.getBody().asString(), LoginResponse.class);

        // Gọi phương thức thông qua object loginResponse
        assertNotNull(loginResponse.getToken());
        assertEquals(loginResponse.getData().getFull_name(), "Trần Phương");
        assertEquals(loginResponse.getData().getCourse_info().getName(), "Sân Đông Triều");
        assertTrue(loginResponse.getData().getPermissions().contains("BOOK_TEE_TIME_VIEW"));


    }

    @Test(priority = 2)
    public void testLoginWrongUsername() throws IOException {
        Response response = sendLoginRequestFromJson("src/test/resources/data/login/case2-login-wrong-username.json");
        assertEquals(response.getStatusCode(), 500,"Expected status code 500 for wrong username, but got: " + response.getStatusCode());
    }

    @Test(priority = 3)
    public void testLoginWrongPassword() throws IOException {
        Response response = sendLoginRequestFromJson("src/test/resources/data/login/case3-login-wrong-password.json");
        assertEquals(response.getStatusCode(), 400, "Expected status code 400 for wrong password, but got: " + response.getStatusCode());
    }

    @Test(priority = 4)
    public void testLoginWrongUsernameAndPassword() throws IOException {
        Response response = sendLoginRequestFromJson("src/test/resources/data/login/case4-login-wrong-userpass.json");
        assertEquals(response.getStatusCode(), 500, "Expected status code 500 for wrong username and password, but got: " + response.getStatusCode());
    }
}
