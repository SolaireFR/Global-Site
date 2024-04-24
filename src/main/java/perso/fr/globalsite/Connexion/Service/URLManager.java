package perso.fr.globalsite.Connexion.Service;

import java.util.Arrays;

import perso.fr.globalsite.URLManager.PublicURLs;

public abstract class URLManager {

    public static final String LOGIN_URL = "/login";
    public static final String CREATE_ACCOUNT_URL = "/create_account";
    public static final String ERROR_URL = "/error";

    public static void init() {
        PublicURLs.urls.addAll(Arrays.asList(LOGIN_URL, CREATE_ACCOUNT_URL, ERROR_URL));
    }

    private static String previousURL = null;

    public static String getPreviousURL() {
        return previousURL;
    }

    public static boolean setPreviousURL(String url) {
        boolean noFile = !stringContainFile(url);
        if (noFile)
            URLManager.previousURL = url;

        return noFile;
    }

    public static boolean stringContainFile(String url) {
        return url.contains(".");
    }
}