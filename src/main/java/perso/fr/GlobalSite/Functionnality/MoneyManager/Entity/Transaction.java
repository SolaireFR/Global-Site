package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Une transaction est un déplacement d'argent. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "money_manager_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "bankAccountId", nullable = false)
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "labelId", nullable = false)
    private Label label;
}
