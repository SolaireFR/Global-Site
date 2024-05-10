package perso.fr.GlobalSite.Connection.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto
{
    private Long id;
    private String displayName;
    @NotEmpty(message = "Email must not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password must not be empty")
    private String password;
}