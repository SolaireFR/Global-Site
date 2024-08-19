package perso.fr.GlobalSite.Functionnality.QualityCode.Dto;

import lombok.Getter;
import lombok.Setter;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;

@Getter
@Setter
public class AdviceDto {
    private String content;

    private UserDto creator;
}
