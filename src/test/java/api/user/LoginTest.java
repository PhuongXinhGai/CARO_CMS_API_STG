package api.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest {

    private final String BASE_URL = "https://vngolf-backend.vnpaytest.vn/golf-cms/api/user/login";

    @BeforeClass
    public void setup() {
        RestAssured.useRelaxedHTTPSValidation(); // Bỏ qua SSL warning nếu có
    }

    private Response sendLoginRequest(String username, String password) {
        String requestBody = String.format("{\"user_name\":\"%s\", \"password\":\"%s\"}", username, password);

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL);
    }

    @Test(priority = 1)
    public void testLoginSuccess() {
        Response response = sendLoginRequest("phuongtt-chilinh", "4aNefZgJHLO83Qc30N5Bjg==");
        assertEquals(response.getStatusCode(), 200, "Login thành công phải trả về status 200");

        // In ra response body để kiểm tra
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Kiểm tra token trong response body
        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        assert token != null && !token.isEmpty() : "Token không được null hoặc rỗng";
    }

    @Test (priority = 2)
    public void testLoginWrongUsername() {
        Response response = sendLoginRequest("saiuser", "4aNefZgJHLO83Qc30N5Bjg==");
        assertEquals(response.getStatusCode(), 500, "Sai username phải trả về 400");
    }

    @Test (priority = 3)
    public void testLoginWrongPassword() {
        Response response = sendLoginRequest("phuongtt-chilinh", "saimatkhau");
        assertEquals(response.getStatusCode(), 400, "Sai password phải trả về 400");
    }

    @Test (priority = 4)
    public void testLoginWrongUsernameAndPassword() {
        Response response = sendLoginRequest("saiuser", "saimatkhau");
        assertEquals(response.getStatusCode(), 500, "Sai cả user và pass phải trả về 400");
    }
}
