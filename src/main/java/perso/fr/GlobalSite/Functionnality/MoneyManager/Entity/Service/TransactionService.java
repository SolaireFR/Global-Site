package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Transaction;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewTransactionDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.TransactionRepository;

/** Service de Transaction. */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    
    /** Création d'une transaction.
     *
     * @param transactionDto Transaction DTO.
     */
    public void saveTransaction(NewTransactionDto transactionDto) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.getAmount());
        newTransaction.setLabel(transactionDto.getLabel());
        newTransaction.setBankAccount(transactionDto.getBankAccount());
        newTransaction.setTime(transactionDto.getTime());
        transactionRepository.save(newTransaction);
    }
}
