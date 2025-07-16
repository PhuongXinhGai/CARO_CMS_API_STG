package api.tests.study;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class QuoteFeeTest {

    @Test
    public void testPostQuoteFee() {
        // Base URI
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";

        // Body JSON dưới dạng String
        String requestBody = """
        {
          "course_uid": "DEMO-DONGTRIEU",
          "partner_uid": "DEMO",
          "list_player": [
            {
              "booking_date": "15/07/2025",
              "guest_style": "4D_package_ptt",
              "caddie_code": "",
              "tee_type": "1",
              "tee_time": "16:04",
              "hole": 18,
              "add_ons": [],
              "player_idx": "16:0410",
              "voucher_apply": []
            },
            {
              "booking_date": "15/07/2025",
              "guest_style": "4D_package_ptt",
              "caddie_code": "",
              "tee_type": "1",
              "tee_time": "16:04",
              "hole": 18,
              "add_ons": [],
              "player_idx": "16:0411",
              "voucher_apply": []
            },
            {
              "booking_date": "15/07/2025",
              "guest_style": "4D_package_ptt",
              "caddie_code": "",
              "tee_type": "1",
              "tee_time": "16:04",
              "hole": 18,
              "add_ons": [],
              "player_idx": "16:0412",
              "voucher_apply": []
            },
            {
              "booking_date": "15/07/2025",
              "guest_style": "4D_package_ptt",
              "caddie_code": "",
              "tee_type": "1",
              "tee_time": "16:04",
              "hole": 18,
              "add_ons": [],
              "player_idx": "16:0413",
              "voucher_apply": []
            }
          ]
        }
        """;

        // Gửi request và kiểm tra status 200
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxN2YyNTUxMi0xNDlhLTQ3YWEtYTliNS0yODViMDAwNzE0Y2QiLCJwYXJ0bmVyX3VpZCI6IkRFTU8iLCJjb3Vyc2VfdWlkIjoiREVNTy1ET05HVFJJRVUiLCJ1c2VyX25hbWUiOiJwaHVvbmd0dC1kb25ndHJpZXUiLCJzdGF0dXMiOiJFTkFCTEUiLCJwd2RfZXhwaXJlZF9hdCI6MTc1MjY1MzAxMiwicm9sZV91aWQiOjU1OCwicm9sZV9uYW1lIjoiRnVsbCBRdXnhu4FuIFBUVCIsImV4cCI6MTc1MzI0MTEzMn0.NBsXrL8xJ6kYNMY3PuFI81CMMHKP_aNSXomNTfro_cc")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/golf-cms/api/booking/quote-fee")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}

