package perso.fr.GlobalSite.Functionnality.MoneyManager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("MoneyManager")
public class MoneyManagerController {
    @GetMapping()
    String mainPage() {
        return "Functionnality/MoneyManager/main";
    }
}
