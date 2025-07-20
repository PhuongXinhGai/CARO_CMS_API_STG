package api.tests.booking;
import api.base.BaseApiTest;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import java.util.List;  // 👈 để dùng List<>
import java.util.stream.Collectors;
import static utils.Constants.*;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;  // 👈 để dùng assertEquals(...)

import java.util.Map;

import models.booking_price.BookingPriceResponse;
import models.booking_price.Booking;
import utils.RequestHelper;

import java.io.IOException;

public class BookingPriceTest extends BaseApiTest {
    // ✅ Hàm gửi request dùng chung
    private Response getBookingPriceResponse(String bookingDate) throws IOException {
        String token = getToken();

        return RequestHelper.getDefaultRequestWithToken(token)
                .queryParam("partner_uid", "DEMO")
                .queryParam("course_uid", "DEMO-DONGTRIEU")
                .queryParam("booking_date", bookingDate)
                .when()
                .get(BASE_URL + BOOKING_PRICE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 1)
    public void testGetBookingPriceStatus200() throws IOException {
        Response response = getBookingPriceResponse("20/07/2025");
        assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    public void testResponseStatusAndTime() throws IOException {
        Response response = getBookingPriceResponse("15/07/2025");
        long responseTime = response.getTime();
        System.out.println("⏱ Response time: " + responseTime + "ms");
        assertTrue(responseTime < 2000, "API quá chậm!");
    }

    @Test(priority = 3)
    public void testBookingPriceBodyContent() throws IOException {
        Response response = getBookingPriceResponse("15/07/2025");
        System.out.println("📦 Response body:");
        response.prettyPrint();
    }

    @Test(priority = 4)
    public void testExtractValuesWithJsonPath() throws IOException {
        Response response = getBookingPriceResponse("15/07/2025");
        JsonPath jsonPath = response.jsonPath();

        String firstCode = jsonPath.getString("data[0].booking_code");
        System.out.println("🔢 Booking code đầu: " + firstCode);

        List<Integer> allAmounts = jsonPath.getList("data.total_amount", Integer.class);
        int sum = allAmounts.stream().mapToInt(Integer::intValue).sum();
        int expected = jsonPath.getInt("total_amount");

        System.out.println("✅ Tổng tính lại: " + sum);
        System.out.println("📦 Tổng trong response: " + expected);
        assertEquals(sum, expected, "Tổng tiền không khớp!");
    }

    @Test(priority = 5)
    public void testExtractValuesWithGson() throws IOException {
        Response response = getBookingPriceResponse("15/07/2025");
        BookingPriceResponse data = new Gson().fromJson(response.asString(), BookingPriceResponse.class);

        System.out.println("📦 Tổng tiền: " + data.getTotal_amount());
        for (Booking b : data.getData()) {
            System.out.println("🧾 " + b.getBooking_code() + ": " + b.getTotal_amount());
            assertTrue(b.getTotal_amount() > 0, "Booking có total_amount <= 0");
        }
    }

    @Test(priority = 6)
    public void testBookingCodesGroupedCorrectly() throws IOException {
        Response response = getBookingPriceResponse("15/07/2025");
        BookingPriceResponse data = new Gson().fromJson(response.asString(), BookingPriceResponse.class);

        Map<String, List<Booking>> grouped = data.getData().stream()
                .collect(Collectors.groupingBy(Booking::getBooking_code));

        for (Map.Entry<String, List<Booking>> entry : grouped.entrySet()) {
            String code = entry.getKey();
            List<Booking> list = entry.getValue();

            System.out.println("📦 " + code + ": " + list.size());
            assertNotNull(code, "booking_code bị null");
            assertFalse(code.trim().isEmpty(), "booking_code bị rỗng");
            assertTrue(list.size() > 0, "Không có booking nào trong nhóm " + code);
        }
    }


}
