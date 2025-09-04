package apiTests.apiActions;
import apiTests.apiHelper.ApiHelper;
import apiTests.data.OnlineUser;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.util.List;
import static apiTests.apiHelper.AdditionalHelper.assertContainsKeresettNev;
import static apiTests.endPoints.EndPointsClass.BASE_URI;
import Reports.ExtentTestNGListener;

@Listeners(ExtentTestNGListener.class)
public class ApiActionsTest {
    ApiHelper apiHelper = new ApiHelper();

    @Test(
            priority = 1,
            description = "Ez a teszt meghivja a baseURI-t és megnézi, hogy a kérés 200-al jön-e vissza."
    )
    public void getBaseURI() {
        Response response = apiHelper.sendGetRequest(
                BASE_URI,
                apiHelper.getDefaultHeaders()
        );
        response.then().statusCode(200);
    }

    @Test(
            priority = 2,
            description = "Ez a teszt meghivja a baseURI-t és a response-ból kiszedi, hogy hány felhasználó van online. Megnézi hogy bizonyos nevű felhasználókból van-e és hány db, majd kilistázza azokat."
    )
    public void getSpecificOnlineUser() {
        Response response = apiHelper.sendGetRequest(
                BASE_URI,
                apiHelper.getDefaultHeaders()
        );
        List<OnlineUser> users = OnlineUser.fromResponse(response);
        assertContainsKeresettNev(users);
    }
}

