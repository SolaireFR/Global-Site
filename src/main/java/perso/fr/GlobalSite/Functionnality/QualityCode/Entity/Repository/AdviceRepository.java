package perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.fr.GlobalSite.Connection.Entity.User;
import perso.fr.GlobalSite.Functionnality.QualityCode.Entity.Advice;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
    Advice findByCreator(User creator);
}
