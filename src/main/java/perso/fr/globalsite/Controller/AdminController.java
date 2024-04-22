package perso.fr.globalsite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;
import perso.fr.globalsite.Entity.Roles;

@Controller
@RequestMapping(path = "/global-site")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

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
}
