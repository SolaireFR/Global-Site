package perso.fr.GlobalSite.Main.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.GlobalSite.Main.Entity.UserData;

/** JpaRepository de UserData */
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    /** Trouve par Username.
     *
     * @param username Username à chercher.
     * @return L'utilisateur trouvé.
     */
    UserData findByUsername(String username);

    /** Supprime par Username.
     *
     * @param username Username à chercher.
     * @return Le nombre d'utilisateurs supprimés.
     */
    Integer deleteByUsername(String username);
}
