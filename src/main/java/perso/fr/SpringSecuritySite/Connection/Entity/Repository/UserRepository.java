package perso.fr.SpringSecuritySite.Connection.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.SpringSecuritySite.Connection.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Integer deleteByEmail(String email);
}
