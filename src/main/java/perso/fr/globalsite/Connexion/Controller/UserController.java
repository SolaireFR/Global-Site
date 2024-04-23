package perso.fr.globalsite.Connexion.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.Connexion.Service.URLManager;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping(URLManager.LOGIN_URL)
    public String getLogin() {
        return "login";
    }

    @PostMapping(URLManager.LOGIN_URL)
    public String postLogin(
            HttpServletRequest req,
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws NoSuchAlgorithmException {

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        User tmpUser = new User();
        tmpUser.setEmail(email);
        tmpUser.setEncodedPassword(password);

        User bddUser = userRepository.getUserByEmail(email);
        if (bddUser != null) {
            if (tmpUser.getEncodedPassword().equals(bddUser.getEncodedPassword())) {
                HttpSession session = req.getSession(true);
                session.setAttribute("email", email);
                return "redirect:/I-DONT-KNOW/";
            } else
                return "redirect:"+URLManager.LOGIN_URL+"?error=password";
        } else
            return "redirect:"+URLManager.LOGIN_URL+"?error=email";
    }

    @GetMapping(URLManager.ERROR_URL)
    public ResponseEntity<String> getError(@RequestParam int status, @RequestParam String message) {
        HttpStatus statusToReturn = HttpStatus.valueOf(status);
        return ResponseEntity.status(statusToReturn).body(message);
    }
}