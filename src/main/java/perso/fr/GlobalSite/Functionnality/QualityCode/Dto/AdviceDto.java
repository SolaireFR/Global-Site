package perso.fr.GlobalSite.Functionnality.QualityCode.Dto;

import lombok.Getter;
import lombok.Setter;
import perso.fr.GlobalSite.Connection.Dto.UserDataDto;

@Getter
@Setter
public class AdviceDto {
    private String content;

    private UserDataDto creator;
}
