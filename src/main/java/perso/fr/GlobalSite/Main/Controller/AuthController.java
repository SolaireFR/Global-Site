package perso.fr.GlobalSite.Main.Controller;

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
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Entity.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Main.Service.UserService;

/** Le contrôleur de l'authentification */
@Controller
public class AuthController {

    @Autowired
    private MessageSource messages;

    @Autowired
    private UserService userService;

    /** La page d'accueil
     *
     * @param request HttpServletRequest
     * @return String
     */
    @GetMapping("/")
    public String main(HttpServletRequest request) {
        return "Main/index";
    }

    /** La page d'enregistrement
     *
     * @param model Model
     * @return String
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserRegisterDto user = new UserRegisterDto();
        model.addAttribute("user", user);
        return "Main/register";
    }

    /** L'enregistrement de l'utilisateur
     *
     * @param userDto UserRegisterDto
     * @param result BindingResult
     * @param model Model
     * @return String
     */
    @SuppressWarnings("null")
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserRegisterDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        Locale locale = LocaleContextHolder.getLocale();
        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            result.rejectValue(
                "username", 
                null, 
                messages.getMessage("register.error.usernamealreadyexist", null, locale)
            );
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "Main/register";
        }

        userService.registerUser(userDto);
        return "redirect:/register/token?username=" + userDto.getUsername();
    }

    /** La page de tutoriel pour le token
     *
     * @return String
     */
    @GetMapping("/register/token")
    public String showTokenTuto() {
        return "Main/tutoToken";
    }

    /** Renvoyer le token
     *
     * @param username String
     * @return String
     */
    @PostMapping("/register/token")
    public String resendToken(@RequestParam("username") String username) {
        System.out.println("TOKEN RENVOYER à " + username);
        return "redirect:/register/token?error=" + username;
    }

    /** La page de connexion
     *
     * @return String
     */
    @GetMapping("/login")
    public String login() {
        return "Main/login";
    }

    /** La page de gestion de l'utilisateur
     *
     * @param model Model
     * @return String
     */
    @GetMapping("/account")
    public String account(Model model) {
        String username = "not_found";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        UserDto user = userService.findUserDtoByUsername(username);
        model.addAttribute("user", user);
        return "Main/account";
    }

    /** La page de modification de l'utilisateur
     *
     * @param session httpSession
     * @return String
     */
    @PostMapping("/account/delete")
    public String deleteAccount(HttpSession session) {
        // Récupérer l'adresse e-mail de l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // L'adresse e-mail est le nom d'utilisateur

        // Déconnecter l'utilisateur en invalidant la session
        session.invalidate();

        // Supprimer l'utilisateur de la base de données
        boolean wasRemoved = userService.deleteUserByUsername(username);
        if (wasRemoved)
            return "redirect:/?message=userRemoved";
        else
            return "redirect:/account?message=userNotRemoved";
    }
}
