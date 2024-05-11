package perso.fr.GlobalSite.Functionnality.QualityCode.Service;

import java.util.List;

import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceDto;

public interface IAdviceService {
    boolean saveAdvice(AdviceDto adviceDto);

    List<AdviceDto> findAllAdvices();
}
