package perso.fr.GlobalSite.Functionnality.MoneyManager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.MoneyManagerUserRepository;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Service.UserService;

/** Controlleur principal de MoneyManager. */
@Controller
@RequestMapping("MoneyManager")
public class MoneyManagerController {
    @Autowired
    private MoneyManagerUserRepository moneyManagerUserRepository;

    @Autowired
    private UserService userService;

    /** Affiche la page d'accueil.
     *
     * @param model Le model.
     * @return La page.
     */
    @GetMapping
    String mainPage(Model model) {
        String email = "not_found";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }

        UserDto mainUser = userService.findUserDtoByEmail(email);

        MoneyManagerUser user = moneyManagerUserRepository.findOneByUserData(mainUser.getUserData());
        model.addAttribute("user", user);
        return "Functionnality/MoneyManager/main";
    }
}
