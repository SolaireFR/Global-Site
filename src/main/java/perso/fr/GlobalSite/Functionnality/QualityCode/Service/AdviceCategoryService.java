package perso.fr.GlobalSite.Functionnality.QualityCode.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceCategoryDto;
import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.AdviceCategory;
import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Repository.AdviceCategoryRepository;

@Service
public class AdviceCategoryService implements IAdviceCategoryService {
    @Autowired
    private AdviceCategoryRepository adviceCategoryRepository;

    @Override
    public boolean saveAdviceCategory(AdviceCategoryDto adviceCategoryDto) {
        try {
            AdviceCategory newAdviceCategory = new AdviceCategory();
            newAdviceCategory.setName(adviceCategoryDto.getName());

            adviceCategoryRepository.save(newAdviceCategory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AdviceCategoryDto> findAllAdviceCategories() {
        List<AdviceCategory> adviceCategories = adviceCategoryRepository.findAll(); 
        return adviceCategories.stream()
            .map((advice) -> mapToAdviceCategoryDto(advice))
            .collect(Collectors.toList());
    }

    private AdviceCategoryDto mapToAdviceCategoryDto(AdviceCategory adviceCategory) {
        AdviceCategoryDto adviceCategoryDto = new AdviceCategoryDto();
        adviceCategoryDto.setName(adviceCategory.getName());
        return adviceCategoryDto;
    }
}
