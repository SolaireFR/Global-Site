package perso.fr.globalsite.URLManager;

import java.util.ArrayList;
import java.util.List;

public class UserURLs extends AbstractURLs {
    public static final List<String> urls = new ArrayList<>();

    public static boolean contain(String urlToTest) {
        return listContainURL(urls, urlToTest);
    }

    public static String PrintURLs() {
        return urls_To_String(urls);
    }
}
