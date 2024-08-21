package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** DTO de création Transaction. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewTransactionDto {
    private float amount;

    private String secondParticipant;

    private LocalDate transactionDate;

    private Long bankAccountId;

    private Long labelId;
}
