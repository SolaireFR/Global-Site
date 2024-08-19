package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Main.Entity.UserData;

/** JpaRepository de MoneyManagerUser */
public interface MoneyManagerUserRepository extends JpaRepository<MoneyManagerUser, Long> {
    /** Trouve un utilisateur par son userData.
     *
     * @param userData La liste données de l'utilisateur.
     * @return L'utilisateur.
     */
    MoneyManagerUser findOneByUserData(UserData userData);
}
