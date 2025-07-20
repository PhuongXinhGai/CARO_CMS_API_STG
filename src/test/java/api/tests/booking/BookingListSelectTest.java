package api.tests.booking;

import api.base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.RequestHelper;
import static utils.Constants.*;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class BookingListSelectTest extends BaseApiTest {

    /**
     * Hàm gửi request tới API /booking/list/select với các query mặc định
     */
    private Response sendBookingListSelectRequest() throws IOException {
        String token = getToken();

        return RequestHelper.getDefaultRequestWithToken(token)
                .queryParam("sort_by", "created_at")
                .queryParam("sort_dir", "desc")
                .queryParam("partner_uid", "DEMO")
                .queryParam("course_uid", "DEMO-DONGTRIEU")
                .queryParam("booking_date", "15/07/2025")
                .queryParam("is_single_book", "true")
                .queryParam("is_ignore_tournament_booking", "true")
                .get(BASE_URL + BOOKING_LIST_SELECT_ENDPOINT);
    }

    @Test
    public void testGetBookingListStatusCode200() throws IOException {
        Response response = sendBookingListSelectRequest();
        assertEquals(response.getStatusCode(), 200, "❌ API booking list select không trả về 200!");
    }

}
