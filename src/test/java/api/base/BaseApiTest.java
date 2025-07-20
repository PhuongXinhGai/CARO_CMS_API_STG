package api.base;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.login.LoginResponse;
import org.testng.annotations.Test;
import utils.FileHelper;

import static org.testng.Assert.assertNotNull;
import static utils.RequestHelper.getRequestNoAuth;
import static utils.ConfigReader.get;
import java.io.IOException;


public class BaseApiTest {

    protected static final String BASE_URL = get("base_url");

    public static String getToken() throws IOException {
        String BASE_URL = get("base_url") + "/golf-cms/api/user/login";
        String requestLoginBody = FileHelper.readJsonFileAsString("src/test/resources/data/login/case1-login-success.json");

        Response response = getRequestNoAuth()
                .contentType(ContentType.JSON)
                .body(requestLoginBody)
                .post(BASE_URL);

        System.out.println("👉 Response login: " + response.asString());
        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(response.getBody().asString(), LoginResponse.class);

        // Gọi phương thức thông qua object loginResponse
        assertNotNull(loginResponse.getToken());

        String token = loginResponse.getToken();
        if (token == null) {
            throw new RuntimeException("❌ Không lấy được token! Response:\n" + response.asString());
        }
        return token;
    }

}
