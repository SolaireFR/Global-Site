package perso.fr.GlobalSite.Functionnality.MoneyManager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewAccumulatorDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewBankAccountDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewLabelDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.MoneyManagerUserRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service.BankAccountService;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Service.UserService;

/** Controlleur principal de MoneyManager. */
@Controller
@RequestMapping("MoneyManager")
public class MoneyManagerController {
    @Autowired
    private MoneyManagerUserRepository moneyManagerUserRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    /** Affiche la page d'accueil.
     *
     * @param model Le model.
     * @return La page.
     */
    @GetMapping
    String mainPage(Model model) {
        model.addAttribute("newBankAccountDto", new NewBankAccountDto());
        model.addAttribute("newAccumulatorDto", new NewAccumulatorDto());
        model.addAttribute("newLabelDto", new NewLabelDto());
        
        model.addAttribute("user", this.getMoneyManagerUser());
        return "Functionnality/MoneyManager/main";
    }
    
    /** Création de compte bancaire.
     *
     * @param bankAccount Le compte bancaire.
     * @return La page.
     */
    @PostMapping("/bankAccount/create")
    public String createBankAccount(@ModelAttribute NewBankAccountDto bankAccount) {
        bankAccountService.saveAccount(bankAccount, getMoneyManagerUser());
        return "redirect:/MoneyManager"; // Redirige vers la page de détails utilisateur
    }

    /** Création d'un accumulateur.
     *
     * @param accumulator L'accumulateur'.
     * @return La page.
     */
    @PostMapping("/accumulator/create")
    public String createAccumulator(@ModelAttribute NewAccumulatorDto accumulator) {
        // Logique pour sauvegarder l'Accumulator
        return "redirect:/MoneyManager";
    }

    /** Création d'un label.
     *
     * @param label Le label.
     * @return La page.
     */
    @PostMapping("/label/create")
    public String createLabel(@ModelAttribute NewLabelDto label) {
        // Logique pour sauvegarder le Label
        return "redirect:/MoneyManager";
    }

    /** Renvoie le MoneyManagerUser de l'utilisateur.
     *
     * @return Le compte utilisateur.
     */
    private MoneyManagerUser getMoneyManagerUser() {
        String email = "not_found";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }

        UserDto mainUser = userService.findUserDtoByEmail(email);

        return moneyManagerUserRepository.findOneByUserData(mainUser.getUserData());
    }
}
