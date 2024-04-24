package perso.fr.globalsite.URLManager;

import java.util.List;

public abstract class AbstractURLs {

    protected static boolean listContainURL(List<String> urls, String urlToTest) {
        urlToTest = urlToTest.replaceAll("/", "");
        for (String url : urls) {
            if (urlToTest.equals(url.replaceAll("/", "")))
                return true;
        }
        return false;
    }

    protected static String urls_To_String(List<String> urls) {
        String res = "urls [";
        for (String url : urls) {
            res+= url+", ";
        }
        return res+"]";
    }
}
