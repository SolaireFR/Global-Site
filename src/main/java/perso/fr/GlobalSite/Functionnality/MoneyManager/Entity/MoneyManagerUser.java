package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_data_id", referencedColumnName = "id")
    private UserData userData;

    /** Constructeur de MoneyManagerUser permettant l'ajout de userData.
     *
     * @param userData L'entité regroupant les fonctionnalités de l'utilisateur.
     */
    public MoneyManagerUser(UserData userData) {
        this.userData = userData;
    }
}
