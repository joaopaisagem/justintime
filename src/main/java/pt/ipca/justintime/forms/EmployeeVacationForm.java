package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Created by TiagoSilva on 22/05/2017.
 */
public class EmployeeVacationForm {
    private EmployeeForm employee;
    private VacationForm vacation;

}
