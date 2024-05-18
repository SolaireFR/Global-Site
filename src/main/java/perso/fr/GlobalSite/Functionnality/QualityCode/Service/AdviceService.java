package perso.fr.GlobalSite.Functionnality.QualityCode.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Connection.Entity.User;
import perso.fr.GlobalSite.Connection.Service.UserService;
import perso.fr.GlobalSite.Functionnality.QualityCode.Dto.AdviceDto;
import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Advice;
import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Repository.AdviceRepository;

@Service
public class AdviceService implements IAdviceService {

    @Autowired
    private AdviceRepository adviceRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean saveAdvice(AdviceDto adviceDto) {
        try {
            User user = userService.findUserByEmail("Jean.Bon@goodDocteur.fr");
            Advice newAdvice = new Advice();
            newAdvice.setContent(adviceDto.getContent());
            newAdvice.setCreator(user);

            adviceRepository.save(newAdvice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AdviceDto> findAllAdvices() {
        List<Advice> advices = adviceRepository.findAll(); 
        return advices.stream()
            .map((advice) -> mapToAdviceDto(advice))
            .collect(Collectors.toList());
    }

    private AdviceDto mapToAdviceDto(Advice advice) {
        AdviceDto adviceDto = new AdviceDto();
        adviceDto.setContent(advice.getContent());
        adviceDto.setCreator(userService.mapToUserDataDto(advice.getCreator()));
        return adviceDto;
    }
}
