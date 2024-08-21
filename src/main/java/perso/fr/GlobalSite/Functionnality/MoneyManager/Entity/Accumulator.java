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
import lombok.ToString;

/** Une charge correspond aux sorties fixes d'argent pour des objectifs précis. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "money_manager_accumulators")
public class Accumulator {
    public static final int MIN_PERCENT = 0;
    public static final int MAX_PERCENT = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargesId;

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "userId")
    private MoneyManagerUser user;

    @Column(nullable = false)
    private float amount = 0.0f;

    @Column(nullable = false)
    @Min(MIN_PERCENT)
    @Max(MAX_PERCENT)
    private int percentPerMonth = 0;
}
