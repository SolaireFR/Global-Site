package perso.fr.globalsite.GlobalSite.Service;

import java.util.Arrays;

import perso.fr.globalsite.URLManager.AdminURL;

public abstract class URLManager {

    public static final String CONNECTED_URL = "/connected";
    public static final String ALL_USER_URL = "/all_user";
    public static final String ADD_USER_URL = "/add_user";

    static {
        AdminURL.urls.addAll(Arrays.asList(ALL_USER_URL, ADD_USER_URL));
    }
}