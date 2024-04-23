package perso.fr.globalsite.Connexion.Entity.Repository;

import org.springframework.data.repository.CrudRepository;

import perso.fr.globalsite.Connexion.Entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User getUserByEmail(String email);
}
