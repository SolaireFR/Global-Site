package perso.fr.globalsite.Connexion.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.Connexion.Service.TokenManager;

@Component
public class VerifConnexionFilter {
    @Autowired
    private UserRepository userRepository;

    protected boolean verifConnexion(HttpServletRequest req) throws Exception {
        
        boolean userLoggedIn = isUserLoggedIn(req);
        return userLoggedIn;
    }

    private boolean isUserLoggedIn(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String token = "" + session.getAttribute("token");
            return TokenManager.isTokenValid(token, userRepository);
        }

        else
            return false;
    }
}