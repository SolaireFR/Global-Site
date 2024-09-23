package perso.fr.GlobalSite.Main.Entity.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Les données de l'utilisateur pour l'enregistrement. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto
{
    private Long id;

    private String username;
    
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
