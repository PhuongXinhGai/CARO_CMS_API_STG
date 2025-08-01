package utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class RequestHelper {
    public static RequestSpecification getDefaultRequestWithToken(String token) {
        return RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .header("Origin", "https://vngolf-portal.vnpaytest.vn")
                .header("Referer", "https://vngolf-portal.vnpaytest.vn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    public static RequestSpecification getRequestNoAuth() {
        return RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Connection", "keep-alive")
                .header("Origin", "https://vngolf-portal.vnpaytest.vn")
                .header("Referer", "https://vngolf-portal.vnpaytest.vn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
    }

    public static RequestSpecification getRequestWithParams(String key, String value) {
        return RestAssured
                .given()
                .queryParam(key, value)
                .header("Content-Type", "application/json");
    }

    public static RequestSpecification getDefaultRequestWithTokenParams(String token, String key, String value) {
        return RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", token)
                .header("Connection", "keep-alive")
                .header("Origin", "https://vngolf-portal.vnpaytest.vn")
                .header("Referer", "https://vngolf-portal.vnpaytest.vn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .queryParam(key, value);
    }
}
