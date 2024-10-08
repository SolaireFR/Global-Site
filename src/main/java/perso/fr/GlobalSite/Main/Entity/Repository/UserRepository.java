package perso.fr.GlobalSite.Main.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Main.Entity.User;

/** JpaRepository de User */
public interface UserRepository extends JpaRepository<User, Long> {
    /** Trouve par Username.
     *
     * @param username Username à chercher.
     * @return L'utilisateur trouvé.
     */
    User findByUsername(String username);

    /** Supprime par Username.
     *
     * @param username Username à chercher.
     * @return Le nombre d'utilisateurs supprimés.
     */
    Integer deleteByUsername(String username);
}
