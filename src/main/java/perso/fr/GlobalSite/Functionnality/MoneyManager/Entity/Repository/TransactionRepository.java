package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Transaction;

/** JpaRepository de Transaction. */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
