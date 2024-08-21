package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.BankAccount;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Label;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Transaction;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewTransactionDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.TransactionRepository;

/** Service de Transaction. */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

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

        System.out.println("transactionDto = " + transactionDto);
        System.out.println("bankAccount = " + bankAccount);
        System.out.println("label = " + label);

        Transaction newTransaction = new Transaction();
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTime(transactionDto.getTime());
        transaction.setBankAccount(bankAccount);
        transaction.setLabel(label);
        
        System.out.println("NewTransaction = " + transaction);
        System.out.println("bankAccountId = " + bankAccount.getBankAccountId());
        System.out.println("labelId = " + label.getLabelId());
        transactionRepository.save(newTransaction);
    }
}
