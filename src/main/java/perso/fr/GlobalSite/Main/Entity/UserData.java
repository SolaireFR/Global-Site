package perso.fr.GlobalSite.Main.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;

/** UserData contiens l'ensemble des données applicatifs non personnel de l'utilisateur */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "main_users_data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MoneyManagerUser moneyManagerUser = new MoneyManagerUser(this);

    /** Constructeur permettant de le relier directement à un utilisateur.
     *
     * @param user L'utilisateur.
     */
    public UserData(User user) {
        this.username = user.getUsername();
    }
}
