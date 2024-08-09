package perso.fr.GlobalSite.Main.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.GlobalSite.Main.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Integer deleteByEmail(String email);

    User findByToken(String token);
}
