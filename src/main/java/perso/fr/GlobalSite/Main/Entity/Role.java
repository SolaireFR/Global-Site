package perso.fr.GlobalSite.Main.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Le role d'un utilisateur permettant de lui limiter les accès. */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role
{
    private String name;
}
