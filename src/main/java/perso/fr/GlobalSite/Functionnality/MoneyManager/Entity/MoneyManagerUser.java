package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perso.fr.GlobalSite.Main.Entity.UserData;

/** Classe Entity User de MoneyManagerUser.*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "money_manager_users")
public class MoneyManagerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "userDataId", referencedColumnName = "id")
    private UserData userData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Label> labels = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Accumulator> accumulators = new ArrayList<>();

    /** Constructeur de MoneyManagerUser permettant l'ajout de userData.
     *
     * @param userData L'entité regroupant les fonctionnalités de l'utilisateur.
     */
    public MoneyManagerUser(UserData userData) {
        this.userData = userData;
    }
}
