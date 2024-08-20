package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Label;

/** JpaRepository de Label. */
public interface LabelRepository extends JpaRepository<Label, Long> {
}
