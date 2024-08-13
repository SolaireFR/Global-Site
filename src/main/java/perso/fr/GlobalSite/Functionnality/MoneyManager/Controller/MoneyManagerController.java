package perso.fr.GlobalSite.Functionnality.MoneyManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controlleur principal de MoneyManager.
 * 
 */
@Controller
@RequestMapping("MoneyManager")
public class MoneyManagerController {
    /** Affiche la page d'accueil.
     *
     * @return La page.
     */
    @GetMapping
    String mainPage() {
        return "Functionnality/MoneyManager/main";
    }
}
