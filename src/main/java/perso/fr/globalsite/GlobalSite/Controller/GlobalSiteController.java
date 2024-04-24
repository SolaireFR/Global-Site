package perso.fr.globalsite.GlobalSite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import perso.fr.globalsite.Connexion.Entity.Roles;
import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;
import perso.fr.globalsite.GlobalSite.Service.URLManager;

@Controller
public class GlobalSiteController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/**")
    public String getAllURL() {
        return "redirect:"+URLManager.SITE_URL;
    }

    @GetMapping(URLManager.SITE_URL)
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("GlobalSite/home");
        return modelAndView;
    }

    @PostMapping(URLManager.ADD_USER_URL)
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

    @GetMapping(URLManager.ALL_USER_URL)
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(URLManager.CONNECTED_URL)
    public @ResponseBody String connected() {
        return "<h1>You are connected !</h1>";
    }

    @GetMapping(URLManager.REMOVE_SESSION)
    public @ResponseBody String removeSession(HttpServletRequest req) {
        HttpSession s = req.getSession(true);
        s.invalidate();

        return "<h1>Your session was invalidate</h1>";
    }
}
