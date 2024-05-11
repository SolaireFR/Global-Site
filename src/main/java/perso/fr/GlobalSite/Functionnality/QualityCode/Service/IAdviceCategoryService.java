package perso.fr.GlobalSite.Functionnality.QualityCode.Service;

import java.util.List;

import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceCategoryDto;

public interface IAdviceCategoryService {
    boolean saveAdviceCategory(AdviceCategoryDto adviceCategoryDto);

    List<AdviceCategoryDto> findAllAdviceCategories();
}
