package perso.fr.globalsite.GlobalSite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import perso.fr.globalsite.Connexion.Entity.Roles;
import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.GlobalSite.Service.URLManager;

@Controller
@RequestMapping(path = "/global-site")
public class GlobalSiteController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
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
        return "Saved : " + newUser.toString();
    }

    @GetMapping("/alluser")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(URLManager.CONNECTED_URL)
    public @ResponseBody String connected() {
        return "<h1>You are connected !</h1>";
    }
}
