package perso.fr.GlobalSite.Functionnality.QualityCode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceCategoryDto;
import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceDto;
import perso.fr.GlobalSite.Functionnality.QualityCode.Service.AdviceCategoryService;
import perso.fr.GlobalSite.Functionnality.QualityCode.Service.AdviceService;

@Controller
@RequestMapping("/QualityCode")
public class MainController {
    @Autowired
    private AdviceService adviceService;

    @Autowired
    private AdviceCategoryService adviceCategoryService;

    @GetMapping("/list")
    public String showListOfAdvise(Model model) {
        model.addAttribute("advices", adviceService.findAllAdvices());
        model.addAttribute("categories", adviceCategoryService.findAllAdviceCategories());
        return "Functionnality/QualityCode/adviseList";
    }

    @GetMapping("/advices/new")
    public String showFormToCreateAdvice(Model model) {
        model.addAttribute("advice", new AdviceDto());
        return "Functionnality/QualityCode/createAdvice";
    }

    @PostMapping("/advices/new")
    public String createAdvice(@Valid @ModelAttribute("advice") AdviceDto adviceDto) {
        if (adviceService.saveAdvice(adviceDto))
            return "redirect:/QualityCode/list";
        else
            return "redirect:/QualityCode/advices/new?error=notSaved";
    }

    @GetMapping("/adviceCategories/new")
    public String showFormToCreateAdviceCategory(Model model) {
        model.addAttribute("adviceCategory", new AdviceCategoryDto());
        return "Functionnality/QualityCode/createAdviceCategory";
    }

    @PostMapping("/adviceCategories/new")
    public String createAdviceCategory(@Valid @ModelAttribute("adviceCategory") AdviceCategoryDto adviceCategoryDto) {
        if (adviceCategoryService.saveAdviceCategory(adviceCategoryDto))
            return "redirect:/QualityCode/list";
        else
            return "redirect:/QualityCode/adviceCategories/new?error=notSaved";
    }
}