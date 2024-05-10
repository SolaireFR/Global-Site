package perso.fr.GlobalSite.Connection.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.GlobalSite.Connection.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
