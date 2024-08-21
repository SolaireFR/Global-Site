package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Label;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewLabelDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.LabelRepository;

/** Service de Label. */
@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;
    
    /** Création d'un label.
     *
     * @param labelDto Label DTO.
     * @param user Le compte de l'utilisateur.
     */
    public void saveLabel(NewLabelDto labelDto, MoneyManagerUser user) {
        Label newLabel = new Label();
        newLabel.setName(labelDto.getName());
        newLabel.setUser(user);
        labelRepository.save(newLabel);
    }

    /** Récupération d'un label par son id.
     *
     * @param labelId L'id du label.
     * @return Le label.
     */
    public Label findById(Long labelId) {
        return labelRepository.findById(labelId).get();
    }
}
