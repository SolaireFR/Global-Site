package perso.fr.globalsite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;

@Controller
@RequestMapping(path = "/global-site/public/")
public class PublicController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
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
                return "redirect:/global-site/public/";
            } else
                return "redirect:/global-site/public/login?error=password";
        } else
            return "redirect:/global-site/public/login?error=password";
    }

    @GetMapping("/alluser")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/error")
    public ResponseEntity<String> getError(@RequestParam int status, @RequestParam String message) {
        HttpStatus statusToReturn = HttpStatus.valueOf(status);
        return ResponseEntity.status(statusToReturn).body(message);
    }
}
