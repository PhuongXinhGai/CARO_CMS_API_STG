package api.base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {
    protected String token;
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxN2YyNTUxMi0xNDlhLTQ3YWEtYTliNS0yODViMDAwNzE0Y2QiLCJwYXJ0bmVyX3VpZCI6IkRFTU8iLCJjb3Vyc2VfdWlkIjoiREVNTy1ET05HVFJJRVUiLCJ1c2VyX25hbWUiOiJwaHVvbmd0dC1kb25ndHJpZXUiLCJzdGF0dXMiOiJFTkFCTEUiLCJwd2RfZXhwaXJlZF9hdCI6MTc1MjY1MzAxMiwicm9sZV91aWQiOjU1OCwicm9sZV9uYW1lIjoiRnVsbCBRdXnhu4FuIFBUVCIsImV4cCI6MTc1MzI1ODc1OH0.QOBuDTiXNAvR4KuxZIuNvjC1SC7dtMlU5mZLPRFdbeM"; // (Phương có thể load từ file config sau)

        requestSpec = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .header("Origin", "https://vngolf-portal.vnpaytest.vn")
                .header("Referer", "https://vngolf-portal.vnpaytest.vn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    public static RequestSpecification getDefaultRequestWithToken(String token) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");
    }

    public static RequestSpecification getRequestNoAuth() {
        return RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");
    }

    public static RequestSpecification getRequestWithParams(String key, String value) {
        return RestAssured
                .given()
                .queryParam(key, value)
                .header("Content-Type", "application/json");
    }
}
