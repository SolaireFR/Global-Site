package perso.fr.GlobalSite.Functionnality.QualityCode.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perso.fr.GlobalSite.Connection.Entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(
        name = "advice_category_mapping",
        joinColumns = @JoinColumn(name = "advice_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<AdviceCategory> categories = new ArrayList<>();
}
