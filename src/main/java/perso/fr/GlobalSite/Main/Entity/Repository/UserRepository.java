package perso.fr.GlobalSite.Main.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perso.fr.GlobalSite.Main.Entity.User;

/** JpaRepository de User */
public interface UserRepository extends JpaRepository<User, Long> {
    /** Trouve par Email.
     *
     * @param email Email à chercher.
     * @return L'utilisateur trouvé.
     */
    User findByEmail(String email);

    /** Supprime par Email.
     *
     * @param email Email à supprimer.
     * @return Le nombre de lignes supprimées.
     */
    Integer deleteByEmail(String email);

    /** Trouve par Token.
     *
     * @param token Token à chercher.
     * @return L'utilisateur trouvé.
     */
    User findByToken(String token);
}
