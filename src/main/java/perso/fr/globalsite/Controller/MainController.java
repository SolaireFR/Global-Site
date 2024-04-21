package perso.fr.globalsite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
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
import perso.fr.globalsite.Entity.Roles;

@Controller
@RequestMapping(path = "/global-site")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home"); // "home" correspond au nom du fichier HTML dans le répertoire "templates"
        // Vous pouvez ajouter des objets à transmettre au modèle ici si nécessaire
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
                return "redirect:/global-site/";
            } else
                return "redirect:/global-site/login?error=password";
        } else
            return "redirect:/global-site/login?error=password";
    }

    @PostMapping("/adduser")
    public @ResponseBody String addNewUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Roles role)
            throws NoSuchAlgorithmException {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setEncodedPassword(password);
        newUser.setRole(role);
        System.out.println("------\n" + newUser);
        userRepository.save(newUser);
        return "Saved : "+newUser.toString();
    }

    @GetMapping("/alluser")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
