package perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** DTO de création d'un accumulateur. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewAccumulatorDto {
    private String name;

    private float amount;

    private int percentPerMonth;
}
