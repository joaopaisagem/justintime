package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Created by TiagoSilva on 22/05/2017.
 */
public class EmployeeVacationForm {
  private Employee employee;
  private Vacation vacation;

}
