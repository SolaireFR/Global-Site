package perso.fr.globalsite.GlobalSite.Service;

import java.util.Arrays;

import perso.fr.globalsite.URLManager.AdminURLs;
import perso.fr.globalsite.URLManager.PublicURLs;
import perso.fr.globalsite.URLManager.UserURLs;

public abstract class URLManager {

    public static final String SITE_URL = "/global-site";
    public static final String CONNECTED_URL = SITE_URL+"/connected";
    public static final String ALL_USER_URL = SITE_URL+"/all_user";
    public static final String ADD_USER_URL = SITE_URL+"/add_user";
    public static final String REMOVE_SESSION = SITE_URL+"/rm_session";

    public static void init() {
        PublicURLs.urls.addAll(Arrays.asList(SITE_URL, REMOVE_SESSION));
        UserURLs.urls.addAll(Arrays.asList(CONNECTED_URL));
        AdminURLs.urls.addAll(Arrays.asList(ALL_USER_URL, ADD_USER_URL));
    }
}