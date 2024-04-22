package perso.fr.globalsite.SecurityFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Entity.Roles;
import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;

@Component
public class VerifRoleFilter {

    @Autowired
    private UserRepository userRepository;

    protected boolean verifRoleAdmin(HttpServletRequest req) {

        boolean userIsAdmin = isUserAdmin(req);
        return userIsAdmin;
    }

    private boolean isUserAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String email = "" + session.getAttribute("email");
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
