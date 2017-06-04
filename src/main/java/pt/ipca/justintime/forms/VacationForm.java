package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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


public class VacationForm {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationStartDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationEndDay;

}
