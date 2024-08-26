package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Classe entité de compte bancaire */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "money_manager_keywords")
public class Keyword {
    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private int force = 1;

    @ManyToMany
    @JoinTable(
        name = "money_manager_label_keywords",
        joinColumns = @JoinColumn(name = "name"),
        inverseJoinColumns = @JoinColumn(name = "labelId")
    )
    private List<Label> labels = new ArrayList<>();
}
