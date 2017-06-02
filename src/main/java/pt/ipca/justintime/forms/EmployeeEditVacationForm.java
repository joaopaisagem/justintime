package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizador on 02/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEditVacationForm {
    EmployeeForm employeeForm;
    List<VacationForm> vacationFormList = new ArrayList<>();
}
