package perso.fr.globalsite.Connexion.Service;

import java.util.Arrays;

import perso.fr.globalsite.URLManager.PublicURL;

public abstract class URLManager {

    public static final String LOGIN_URL = "/login";
    public static final String CREATE_ACCOUNT_URL = "/create_account";
    public static final String ERROR_URL = "/error";

    public static void init() {
        PublicURL.urls.addAll(Arrays.asList(LOGIN_URL, CREATE_ACCOUNT_URL, ERROR_URL));
        System.out.println("\n\nADD-ALL\n\n");
    }
}