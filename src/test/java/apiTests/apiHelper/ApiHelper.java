package apiTests.apiHelper;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.Arrays;
import static io.restassured.RestAssured.given;

public class ApiHelper {

    public Headers getDefaultHeaders() {
        return new Headers(
                Arrays.asList(
                        new Header("accept", "*/*"),
                        new Header("accept-encoding", "gzip, deflate, br, zstd"),
                        new Header("accept-language", "hu-HU,hu;q=0.9,en-US;q=0.8,en;q=0.7"),
                        new Header("cookie", "gsfanatic_com=8d7af5885427d04611baf9db44b9e3dd; _ga=GA1.1.1501790901.1750160869; FCCDCF=...; abc=true"),
                        new Header("priority", "u=1, i"),
                        new Header("referer", "https://gsfanatic.com/"),
                        new Header("sec-ch-ua", "Not;A=Brand;v=99, Google Chrome;v=139, Chromium;v=139"),
                        new Header("sec-ch-ua-mobile", "?0"),
                        new Header("sec-ch-ua-platform", "macOS"),
                        new Header("sec-fetch-dest", "empty"),
                        new Header("sec-fetch-mode", "cors"),
                        new Header("sec-fetch-site", "same-origin"),
                        new Header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36")
                )
        );
    }

    public Response sendPostRequest(String url, String body, Headers headers) {
        return given()
                .headers(headers)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response sendGetRequest(String url, Headers headers) {
        return given()
                .headers(headers)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response sendPutRequest(String url, String body, Headers headers) {
        return given()
                .headers(headers)
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }
}
