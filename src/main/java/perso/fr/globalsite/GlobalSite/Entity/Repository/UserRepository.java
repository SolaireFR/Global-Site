package perso.fr.globalsite.GlobalSite.Entity.Repository;

import org.springframework.data.repository.CrudRepository;

import perso.fr.globalsite.GlobalSite.Entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User getUserByEmail(String email);
}
