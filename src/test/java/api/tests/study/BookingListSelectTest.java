package api.tests.study;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BookingListSelectTest {

    @Test
    public void testGetBookingListWithQueryParams() {
        // Base URI
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxN2YyNTUxMi0xNDlhLTQ3YWEtYTliNS0yODViMDAwNzE0Y2QiLCJwYXJ0bmVyX3VpZCI6IkRFTU8iLCJjb3Vyc2VfdWlkIjoiREVNTy1ET05HVFJJRVUiLCJ1c2VyX25hbWUiOiJwaHVvbmd0dC1kb25ndHJpZXUiLCJzdGF0dXMiOiJFTkFCTEUiLCJwd2RfZXhwaXJlZF9hdCI6MTc1MjY1MzAxMiwicm9sZV91aWQiOjU1OCwicm9sZV9uYW1lIjoiRnVsbCBRdXnhu4FuIFBUVCIsImV4cCI6MTc1MzIwMjg4NX0.knq77ljKIoFMQJEBWaz4XEwc-jJuAI5BOzrAC9NZ2mI")
                .header("Origin", "https://vngolf-portal.vnpaytest.vn")
                .header("Referer", "https://vngolf-portal.vnpaytest.vn/")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .queryParam("sort_by", "created_at")
                .queryParam("sort_dir", "desc")
                .queryParam("partner_uid", "DEMO")
                .queryParam("course_uid", "DEMO-DONGTRIEU")
                .queryParam("booking_date", "15/07/2025")
                .queryParam("is_single_book", "true")
                .queryParam("is_ignore_tournament_booking", "true")
                .when()
                .get("/golf-cms/api/booking/list/select")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}
