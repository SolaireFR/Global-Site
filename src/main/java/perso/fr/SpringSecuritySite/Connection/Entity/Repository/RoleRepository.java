package perso.fr.SpringSecuritySite.Connection.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.SpringSecuritySite.Connection.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
