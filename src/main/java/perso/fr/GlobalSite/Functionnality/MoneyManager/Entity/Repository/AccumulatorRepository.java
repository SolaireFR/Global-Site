package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Accumulator;

/** JpaRepository de Accumulator. */
public interface AccumulatorRepository extends JpaRepository<Accumulator, Long> {
}
