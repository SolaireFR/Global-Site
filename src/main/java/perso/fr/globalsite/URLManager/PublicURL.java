package perso.fr.globalsite.URLManager;

import java.util.ArrayList;
import java.util.List;

public abstract class PublicURL {
    public static final List<String> urls = new ArrayList<>();

    public static String PrintURLs() {
        String res = "urls [";
        for (String url : urls) {
            res+= url+", ";
        }
        return res+"]";
    }
}
