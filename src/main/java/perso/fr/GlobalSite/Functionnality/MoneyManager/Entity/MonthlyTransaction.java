package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Une charge correspond aux sorties fixes d'argent pour des objectifs précis. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "money_manager_monthly_transaction")
public class MonthlyTransaction {
    public static final int MIN_DAY = 1;
    public static final int MAX_DAY = 28;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monthlyTransactionId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(nullable = false)
    private float ammount = 0.0f;

    @Column(nullable = false)
    @Min(MIN_DAY)
    @Max(MAX_DAY)
    private int dayOfMonth = 1;
}
