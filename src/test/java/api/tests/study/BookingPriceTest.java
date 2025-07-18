package api.tests.study;
import api.base.BaseApiTest;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import java.util.List;  // 👈 để dùng List<>
import java.util.stream.Collectors;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;  // 👈 để dùng assertEquals(...)
import static org.hamcrest.Matchers.*;
import java.util.Map;

import models.booking_price.BookingPriceResponse;
import models.booking_price.Booking;




import static org.hamcrest.Matchers.equalTo;

public class BookingPriceTest extends BaseApiTest {

    @Test (priority = 1)
    public void testGetBookingPriceStatus200() {
        // Gửi GET request và kiểm tra status 200
                requestSpec
                    .queryParam("partner_uid", "DEMO")
                    .queryParam("course_uid", "DEMO-DONGTRIEU")
                    .queryParam("booking_date", "15/07/2025")
                .when()
                    .get("/golf-cms/api/booking/booking-price")
                .then()
                    .log().all()
                    .statusCode(200) // Kiểm tra status code
                    .extract().response();
    }

    @Test (priority = 2)
    public void testResponseStatusAndTime() {
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        Response response = RestAssured
                .given()
                    .header("Accept", "application/json")
                    .header("Authorization", token)
                    .queryParam("partner_uid", "DEMO")
                    .queryParam("course_uid", "DEMO-DONGTRIEU")
                    .queryParam("booking_date", "15/07/2025")
                .when()
                    .get("/golf-cms/api/booking/booking-price")
                .then()
                    .log().all()
                    .statusCode(200)   // Kiểm tra status code
                    .extract().response();

        // Lấy thời gian phản hồi (milliseconds)
        long responseTime = response.getTime();
        System.out.println("Response time: " + responseTime + "ms");

        // Assert thời gian phản hồi dưới 2000ms
        assertTrue(responseTime < 2000, "API quá chậm!");
    }

    @Test (priority = 3)
    public void testBookingPriceBodyContent() {
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        RestAssured
                .given()
                    .header("Accept", "application/json")
                    .header("Authorization", token)
                    .queryParam("partner_uid", "DEMO")
                    .queryParam("course_uid", "DEMO-DONGTRIEU")
                    .queryParam("booking_date", "15/07/2025")
                .when()
                    .get("/golf-cms/api/booking/booking-price")
                .then()
                    .log().body()
                    .statusCode(200)

                    // ✅ Kiểm tra tổng tiền lớn hơn 0
                    .body("total_amount", greaterThan(0))

                    // ✅ Kiểm tra mảng data có ít nhất 1 booking
                    .body("data", not(empty()))

                    // ✅ Kiểm tra field cụ thể ở phần tử đầu tiên
                    .body("data[0].status", equalTo("ENABLE"))
                    .body("data[0].partner_uid", equalTo("DEMO"))
                    .body("data[0].course_uid", equalTo("DEMO-DONGTRIEU"))
                    .body("data[0].total_amount", greaterThan(0))
                    //✅ 1. Kiểm tra đúng total_amount của từng phần tử cụ thể
                    .body("data.find { it.id == 22018 }.total_amount", equalTo(3030000))
                    //✅ 2. Kiểm tra số lượng phần tử trong data
                    .body("data.size()", equalTo(5));
    }

    @Test (priority = 4)
    public void testExtractValuesWithJsonPath() {
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        Response response = RestAssured
                .given()
                .header("Authorization", token)
                .queryParam("partner_uid", "DEMO")
                .queryParam("course_uid", "DEMO-DONGTRIEU")
                .queryParam("booking_date", "15/07/2025")
                .when()
                .get("/golf-cms/api/booking/booking-price")
                .then()
                .statusCode(200)
                .extract().response();

        // ✅ Parse response về JsonPath
        JsonPath jsonPath = response.jsonPath();

        // ✅ Lấy booking_code đầu tiên
        String firstBookingCode = jsonPath.getString("data[0].booking_code");
        System.out.println("First booking code: " + firstBookingCode);

        // ✅ Lấy tổng số lượng booking
        int count = jsonPath.getList("data").size();
        System.out.println("Tổng số booking: " + count);

        // ✅ Lấy mảng total_amount
        List<Integer> allAmounts = jsonPath.getList("data.total_amount", Integer.class);
        System.out.println("Tổng tiền từng booking: " + allAmounts);

        // ✅ Tính tổng lại từ list và so sánh với field "total_amount"
        int actualTotal = allAmounts.stream().mapToInt(Integer::intValue).sum();
        int expectedTotal = jsonPath.getInt("total_amount");

        System.out.println("Tổng tính lại: " + actualTotal);
        System.out.println("Tổng trong response: " + expectedTotal);

        assertEquals(actualTotal, expectedTotal, "Tổng tiền không khớp!");
    }

    @Test (priority = 5)
    public void testExtractValuesWithGson() {
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        Response response = RestAssured
                .given()
                    .header("Authorization", token)
                    .queryParam("partner_uid", "DEMO")
                    .queryParam("course_uid", "DEMO-DONGTRIEU")
                    .queryParam("booking_date", "15/07/2025")
                .when()
                    .get("/golf-cms/api/booking/booking-price")
                .then()
                    .statusCode(200)
                    .extract().response();

        // Convert JSON -> Java object
        Gson gson = new Gson();
        BookingPriceResponse bookingResponse = gson.fromJson(response.asString(), BookingPriceResponse.class);

        // In thử tổng tiền
        System.out.println("Tổng tiền: " + bookingResponse.getTotal_amount());

        // Lặp qua danh sách booking
        for (Booking b : bookingResponse.getData()) {
            System.out.println("Booking: " + b.getBooking_code() + ", Tiền: " + b.getTotal_amount());
            assertTrue(b.getTotal_amount() > 0, "Booking có total_amount <= 0");
        }
    }
    @Test(priority = 6)
    public void testBookingCodesGroupedCorrectly() {
        Response response = RestAssured
                .given()
                .header("Authorization", token)
                .queryParam("partner_uid", "DEMO")
                .queryParam("course_uid", "DEMO-DONGTRIEU")
                .queryParam("booking_date", "15/07/2025")
                .when()
                .get("/golf-cms/api/booking/booking-price")
                .then()
                .statusCode(200)
                .extract().response();

        // Convert response
        Gson gson = new Gson();
        BookingPriceResponse bookingResponse = gson.fromJson(response.asString(), BookingPriceResponse.class);

        // Tạo Map để group theo booking_code
        Map<String, List<Booking>> groupedByCode = bookingResponse.getData().stream()
                .collect(Collectors.groupingBy(Booking::getBooking_code));

        // Kiểm tra mỗi nhóm booking_code có ít nhất 1 booking
        for (Map.Entry<String, List<Booking>> entry : groupedByCode.entrySet()) {
            String bookingCode = entry.getKey();
            List<Booking> group = entry.getValue();

            System.out.println("Booking code: " + bookingCode + " - Số lượng: " + group.size());
            assertNotNull(bookingCode, "Có booking code bị null");
            assertFalse(bookingCode.trim().isEmpty(), "Có booking code bị rỗng");
            assertTrue(group.size() > 0, "Không có booking nào trong nhóm " + bookingCode);
        }
    }


}
