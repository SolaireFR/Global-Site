package perso.fr.globalsite.Entity.Repository;

import org.springframework.data.repository.CrudRepository;

import perso.fr.globalsite.Entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
