package perso.fr.GlobalSite.Main.Entity.Dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import perso.fr.GlobalSite.Main.Entity.UserData;

/** DTO de User. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private String username;

    private LocalDateTime creation;

    private UserData userData;
}
