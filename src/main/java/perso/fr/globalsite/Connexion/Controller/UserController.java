package perso.fr.globalsite.Connexion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.Connexion.Service.TokenManager;
import perso.fr.globalsite.Connexion.Service.URLManager;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(URLManager.LOGIN_URL)
    public String getLogin() {
        return "Connexion/login";
    }

    @PostMapping(URLManager.LOGIN_URL)
    public String postLogin(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws Exception {

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        User tmpUser = new User();
        tmpUser.setEmail(email);
        tmpUser.setEncodedPassword(password);

        User bddUser = userRepository.getUserByEmail(email);
        if (bddUser != null) {
            if (tmpUser.getEncodedPassword().equals(bddUser.getEncodedPassword())) {
                HttpSession session = req.getSession(true);
                String sessionID = RequestContextHolder.currentRequestAttributes().getSessionId();
                session.setAttribute("token", TokenManager.generateToken(email, sessionID, bddUser.getEncodedPassword()));
                // On redirige vers l'endroit où l'utilisateur souhaiter aller
                String previousURL = URLManager.getPreviousURL();
                if (previousURL == null)
                        return "redirect:"+URLManager.ERROR_URL+"?status=" + HttpStatus.NOT_FOUND.value() + "&message=" + "aucune redirection possible";
                else
                    return "redirect:" + previousURL;
            } else
                return "redirect:" + URLManager.LOGIN_URL + "?error=password";
        } else
            return "redirect:" + URLManager.LOGIN_URL + "?error=email";
    }
}