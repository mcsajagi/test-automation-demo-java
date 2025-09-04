package apiTests.data;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OnlineUser {
    private String nev;

    public OnlineUser(String nev) {
        this.nev = nev;
    }

    public String getNev() {
        return nev;
    }

    @Override
    public String toString() {
        return "OnlineUser{nev='" + nev + "'}";
    }

    public static List<OnlineUser> fromResponse(Response response) {
        List<Map<String, Object>> onlineUsers = response.jsonPath().getList("online_users");

        return onlineUsers.stream()
                .map(user -> new OnlineUser((String) user.get("wu_nev")))
                .collect(Collectors.toList());
    }
}

