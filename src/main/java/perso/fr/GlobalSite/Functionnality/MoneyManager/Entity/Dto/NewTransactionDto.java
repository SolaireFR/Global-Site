package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.BankAccount;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Label;

/** DTO de création Transaction. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewTransactionDto {
    private float amount;

    private LocalDateTime time;

    private BankAccount bankAccount;

    private Label label;
}
