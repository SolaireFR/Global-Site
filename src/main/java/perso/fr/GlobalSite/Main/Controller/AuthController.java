package perso.fr.GlobalSite.Main.Controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import perso.fr.GlobalSite.Main.Entity.User;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDataDto;
import perso.fr.GlobalSite.Main.Entity.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Main.Service.IUserService;
import perso.fr.GlobalSite.Main.Service.MailService;

@Controller
public class AuthController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String main(HttpServletRequest request) {
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
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserRegisterDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        Locale locale = LocaleContextHolder.getLocale();
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, messages.getMessage("register.error.emailalreadyexist", null, locale));
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "Connection/register";
        }

        userService.registerUser(userDto);
        return "redirect:/register/token?email="+userDto.getEmail();
    }

    @GetMapping("/register/token")
    public String showTokenTuto() {
        return "Connection/tutoToken";
    }

    @PostMapping("/register/token")
    public String resendToken(@RequestParam("email") String email) {
        System.out.println("TOKEN RENVOYER à "+email);
        return "redirect:/register/token?error="+email;
    }

    @GetMapping("/userVerification")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
    
        User user = userService.findUserWithToken(token);
        if (user == null) {
            return "redirect:/register/token?error=Le_token_est_invalide";
        }
        
        Calendar cal = Calendar.getInstance();
        if ((user.getTokenExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "redirect:/register/token?error=Le_token_est_expire";
        }
        
        userService.enableUser(user);
        return "redirect:/register/token?success=userVerified"; 
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
            return "redirect:/?message=userRemoved";
        else
            return "redirect:/account?message=userNotRemoved";
    }

    @GetMapping("/mail")
    public String getMail() {
        return "mail";
    }

    @Autowired
    private MailService mailService;

    @PostMapping("/send-mail")
    public String sendEmail(@RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body) {
        mailService.sendEmail(to, subject, body);
        return "redirect:/mail?EmailSend=true";
    }
}
