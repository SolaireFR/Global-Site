package perso.fr.globalsite.Controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import perso.fr.globalsite.Entity.User;
import perso.fr.globalsite.Entity.Repository.UserRepository;
import perso.fr.globalsite.Entity.Roles;

@Controller
@RequestMapping(path = "/global-site")
public class MainController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/")
  public String index() {
    return "forward:/index.html";
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
    return "Saved";
  }

  @GetMapping("/alluser")
  public @ResponseBody Iterable<User> getAllVoitures() {
    return userRepository.findAll();
  }
}
