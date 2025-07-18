package api.tests.study;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileHelper;
import java.io.IOException;


public class QuoteFeeTest {

    @Test
    public void testPostQuoteFee() throws IOException {
        // Base URI
        RestAssured.baseURI = "https://vngolf-backend.vnpaytest.vn";
        String requestBody = FileHelper.readJsonFileAsString("src/test/resources/data/quote-fee/quote-fee-body-case1.json");

        // Gửi request và kiểm tra status 200
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxN2YyNTUxMi0xNDlhLTQ3YWEtYTliNS0yODViMDAwNzE0Y2QiLCJwYXJ0bmVyX3VpZCI6IkRFTU8iLCJjb3Vyc2VfdWlkIjoiREVNTy1ET05HVFJJRVUiLCJ1c2VyX25hbWUiOiJwaHVvbmd0dC1kb25ndHJpZXUiLCJzdGF0dXMiOiJFTkFCTEUiLCJwd2RfZXhwaXJlZF9hdCI6MTc1MjY1MzAxMiwicm9sZV91aWQiOjU1OCwicm9sZV9uYW1lIjoiRnVsbCBRdXnhu4FuIFBUVCIsImV4cCI6MTc1MzQyODYxNH0.AL33wcdv8dVFp9STSgaX1XNZyxOWGejCmcQQ4Zk-7mU")
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

