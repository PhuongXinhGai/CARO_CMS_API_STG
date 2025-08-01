package api.tests.booking;

import api.base.BaseApiTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import utils.FileHelper;
import utils.RequestHelper;
import static utils.Constants.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

public class QuoteFeeTest extends BaseApiTest {

    /**
     * Hàm dùng chung để gửi request quote-fee
     * @param jsonPath: đường dẫn tới file JSON body request
     * @return Response object
     */
    private Response sendQuoteFeeRequest(String jsonPath) throws IOException {
        String token = getToken();  // lấy token
        String body = FileHelper.readJsonFileAsString(jsonPath);  // đọc file body

        return RequestHelper.getDefaultRequestWithToken(token)
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE_URL + QUOTE_FEE_ENDPOINT);
    }

    @Test(priority = 1)
    public void testQuoteFee_TotalAmount_Case1() throws IOException {
        Response response = sendQuoteFeeRequest("src/test/resources/data/quote-fee/quote-fee-body-case1.json");
        assertEquals(response.getStatusCode(), 200);

        JsonPath jsonPath = response.jsonPath();
        int totalAmount = jsonPath.getInt("total_amount");
        System.out.println("Total Amount: " + totalAmount);
        // assertEquals(totalAmount, 22840000, "❌ Tổng tiền không đúng!");
    }

//    @Test(priority = 2)
//    public void testQuoteFee_Status200_Case2() throws IOException {
//        Response response = sendQuoteFeeRequest("src/test/resources/data/quote-fee/quote-fee-body-case2.json");
//        assertEquals(response.getStatusCode(), 200);
//    }
//
//    @Test(priority = 3)
//    public void testQuoteFee_EmptyVoucher() throws IOException {
//        Response response = sendQuoteFeeRequest("src/test/resources/data/quote-fee/quote-fee-body-empty-voucher.json");
//        assertEquals(response.getStatusCode(), 200);
//
//        int amount = response.jsonPath().getInt("total_amount");
//        System.out.println("Tổng khi không có voucher: " + amount);
//        // Có thể assert thêm nếu cần
//    }
//
//    @Test(priority = 4)
//    public void testQuoteFee_InvalidHole() throws IOException {
//        Response response = sendQuoteFeeRequest("src/test/resources/data/quote-fee/quote-fee-invalid-hole.json");
//        assertEquals(response.getStatusCode(), 400, "❌ Trường hợp hole không hợp lệ phải trả về 400");
//    }
}
