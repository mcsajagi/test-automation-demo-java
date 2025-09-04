package apiTests;
import apiTests.apiHelper.ApiHelper;
import apiTests.endPoints.EndPointsClass;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class ApiBase {
    protected ApiHelper apiHelper;

    @BeforeClass
    public void setup() {
        try {
            RestAssured.baseURI = EndPointsClass.BASE_URI;
            apiHelper = new ApiHelper();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

