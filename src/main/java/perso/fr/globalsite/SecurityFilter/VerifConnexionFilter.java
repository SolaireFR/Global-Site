package perso.fr.globalsite.SecurityFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;

@Component
public class VerifConnexionFilter {

    @Autowired
    private UserRepository userRepository;

    protected boolean verifConnexion(HttpServletRequest req) {
        
        boolean userLoggedIn = isUserLoggedIn(req);
        return userLoggedIn;
    }

    private boolean isUserLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String email = "" + session.getAttribute("email");
            User user = userRepository.getUserByEmail(email);
            return user != null;
        }

        else
            return false;
    }
}