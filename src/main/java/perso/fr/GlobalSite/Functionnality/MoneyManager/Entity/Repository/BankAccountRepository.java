package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.BankAccount;

/** JpaRepository de BankAccount. */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
