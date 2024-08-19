package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Classe entité de compte bancaire */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "money_manager_bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MoneyManagerUser user;

    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MonthlyTransaction> monthlyTransactions = new ArrayList<>();
}
