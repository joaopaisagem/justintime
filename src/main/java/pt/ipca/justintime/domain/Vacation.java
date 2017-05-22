package pt.ipca.justintime.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vacation {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationStartDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationEndDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacation)) return false;

        Vacation vacation = (Vacation) o;

        if (!vacationStartDay.equals(vacation.vacationStartDay)) return false;
        return vacationEndDay.equals(vacation.vacationEndDay);
    }

    @Override
    public int hashCode() {
        int result = vacationStartDay.hashCode();
        result = 31 * result + vacationEndDay.hashCode();
        return result;
    }
}
