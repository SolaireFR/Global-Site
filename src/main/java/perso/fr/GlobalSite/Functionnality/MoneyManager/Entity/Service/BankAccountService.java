package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.BankAccount;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewBankAccountDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.BankAccountRepository;

/** Service de BankAccount. */
@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    /** Sauvegarde d'un compte bancaire.
     *
     * @param bankAccountDto Compte bancaire DTO.
     * @param user Le compte de l'utilisateur.
     */
    public void saveAccount(NewBankAccountDto bankAccountDto, MoneyManagerUser user) {
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setName(bankAccountDto.getName());
        newBankAccount.setUser(user);
        bankAccountRepository.save(newBankAccount);
    }

    /** Sauvegarde d'un compte bancaire.
     *
     * @param bankAccount Compte bancaire.
     */
    public void saveAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    /** Récupération d'un compte bancaire par son id.
     *
     * @param bankAccountId L'id du compte bancaire.
     * @return Le compte bancaire.
     */
    public BankAccount findById(Long bankAccountId) {
        return bankAccountRepository.findById(bankAccountId).get();
    }
}
