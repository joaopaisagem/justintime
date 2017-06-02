package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipca.justintime.domain.Vacation;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Created by TiagoSilva on 22/05/2017.
 */
public class EmployeeVacationEditForm {
    private EmployeeForm employee;
    private List<Vacation> vacationList;

}
