package perso.fr.GlobalSite.Functionnality.QualityCode.Dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdviceCategoryDto {
    private String name;

    private List<AdviceDto> advices = new ArrayList<>();

    private AdviceCategoryDto parentCategory;

    private List<AdviceCategoryDto> subCategories;
}
