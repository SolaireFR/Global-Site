package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.BankAccount;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Label;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Transaction;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewTransactionDto;

/** Service de Transaction. */
@Service
public class TransactionService {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private LabelService labelService;
    
    /** Création d'une transaction.
     *
     * @param transactionDto Transaction DTO.
     */
    public void saveTransaction(NewTransactionDto transactionDto) {
        BankAccount bankAccount = bankAccountService.findById(transactionDto.getBankAccountId());
        Label label = labelService.findById(transactionDto.getLabelId());
        
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.getAmount());
        newTransaction.setTime(transactionDto.getTime());
        newTransaction.setBankAccount(bankAccount);
        newTransaction.setLabel(label);
        
        bankAccount.getTransactions().add(newTransaction);
        System.out.println(bankAccount);
        bankAccountService.saveAccount(bankAccount);
    }
}
