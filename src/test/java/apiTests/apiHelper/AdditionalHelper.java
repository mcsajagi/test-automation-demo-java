package apiTests.apiHelper;
import apiTests.data.OnlineUser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.testng.Assert.assertTrue;

public class AdditionalHelper {
    private static final List<String> keresettNevek = Arrays.asList("gabor", "attila", "tamas");

    public static void printNameTable(List<OnlineUser> users) {
        Map<String, Integer> counts = users.stream()
                .map(OnlineUser::getNev)
                .map(AdditionalHelper::removeAccents)
                .map(String::toLowerCase)
                .flatMap(nev -> keresettNevek.stream()
                        .filter(knev -> Pattern.compile(
                                        "(^|[\\s_\\-.])" + Pattern.quote(knev) + "([\\s_\\-.]|$)", Pattern.CASE_INSENSITIVE)
                                .matcher(nev)
                                .find()
                        )
                )
                .collect(Collectors.groupingBy(név -> név, Collectors.summingInt(n -> 1)));

        System.out.println("+--------+-------+");
        System.out.println("| Név    | Darab |");
        System.out.println("+--------+-------+");

        keresettNevek.forEach(name -> {
            int count = counts.getOrDefault(name, 0);
            System.out.printf("| %-6s | %-5d |\n", capitalize(name), count);
        });

        System.out.println("+--------+-------+");
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String removeAccents(String text) {
        if (text == null) return null;
        String normalized = java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static void assertContainsKeresettNev(List<OnlineUser> users) {
        boolean found = users.stream().anyMatch(user -> {
            String nev = user.getNev();
            String[] parts = nev.split(" ");
            String keresztnév = parts.length > 1 ? parts[1] : parts[0];
            keresztnév = removeAccents(keresztnév).toLowerCase();
            return keresettNevek.contains(keresztnév);
        });

        assertTrue(found, "Nem található keresett nevű felhasználó a listában.");
        printNameTable(users);
    }
}