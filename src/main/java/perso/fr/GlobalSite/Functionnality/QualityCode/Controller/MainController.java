package perso.fr.GlobalSite.Functionnality.QualityCode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Repository.AdviceRepository;

@Controller
@RequestMapping("/QualityCode")
public class MainController {
    @Autowired
    private AdviceRepository adviceRepository;

    @GetMapping("/list")
    public String showListOfAdvise(Model model) {
        model.addAttribute("advices", adviceRepository.findAll());
        return "Functionnality/QualityCode/adviseList.html";
    }
}