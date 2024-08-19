package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** DTO de création d'un compte bancaire. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewBankAccountDto {
    private String name;
}
