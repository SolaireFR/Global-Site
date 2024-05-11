package perso.fr.GlobalSite.Connection.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import perso.fr.GlobalSite.Connection.Dto.UserDataDto;
import perso.fr.GlobalSite.Connection.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Connection.Entity.User;
import perso.fr.GlobalSite.Connection.Service.IUserService;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = { "/index", "/index/" })
    public String main() {
        return "Connection/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserRegisterDto user = new UserRegisterDto();
        model.addAttribute("user", user);
        return "Connection/register";
    }

    @SuppressWarnings("null")
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserRegisterDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "Connection/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDataDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "Connection/users";
    }

    @GetMapping("/login")
    public String login() {
        return "Connection/login";
    }

    @GetMapping("/account")
    public String account(Model model) {
        String email = "not_found";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }

        UserDataDto user = userService.findUserDataByEmail(email);
        model.addAttribute("user", user);
        return "Connection/account";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(HttpSession session) {
        // Récupérer l'adresse e-mail de l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // L'adresse e-mail est le nom d'utilisateur

        // Déconnecter l'utilisateur en invalidant la session
        session.invalidate();

        // Supprimer l'utilisateur de la base de données
        boolean wasRemoved = userService.deleteUserByEmail(email);
        if (wasRemoved)
            return "redirect:/index?message=userRemoved";
        else
            return "redirect:/account?message=userNotRemoved";
    }
}