package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Accumulator;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewAccumulatorDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.AccumulatorRepository;

/** Service de Accumulator. */
@Service
public class AccumulatorService {
    @Autowired
    private AccumulatorRepository accumulatorRepository;
    
    /** Création d'un accumulateur.
     *
     * @param accumulatorDto Accumulateur DTO.
     * @param user Le compte de l'utilisateur.
     */
    public void saveAccumulator(NewAccumulatorDto accumulatorDto, MoneyManagerUser user) {
        Accumulator newAccumulator = new Accumulator();
        newAccumulator.setName(accumulatorDto.getName());
        newAccumulator.setUser(user);
        newAccumulator.setAmount(accumulatorDto.getAmount());
        newAccumulator.setPercentPerMonth(accumulatorDto.getPercentPerMonth());
        accumulatorRepository.save(newAccumulator);
    }
}
