package perso.fr.globalsite.Connexion.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Connexion.Entity.Roles;
import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.Connexion.Service.TokenManager;

@Component
public class VerifRoleFilter {

    @Autowired
    private UserRepository userRepository;

    protected boolean verifRoleAdmin(HttpServletRequest req) throws Exception {

        boolean userIsAdmin = isUserAdmin(req);
        return userIsAdmin;
    }

    private boolean isUserAdmin(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String token = "" + session.getAttribute("token");
            String email = TokenManager.getTokenEmail(token);
            User user = userRepository.getUserByEmail(email);
            if (user != null) {
                boolean isAdmin = user.getRole().equals(Roles.ADMIN);
                return isAdmin;
            } else
                return false;
        }

        else
            return false;
    }
}
