package perso.fr.SpringSecuritySite.Connection.Dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    
    private String displayName;

    private String email;

    private LocalDateTime creation;
}
