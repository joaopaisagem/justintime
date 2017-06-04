package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Project;

import java.util.ArrayList;
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
public class NewTeamForm {

    private Long id;
    @NotEmpty
    private String teamName;

    private List<Employee> employeeList = new ArrayList<>();

    private List<Project> projectList = new ArrayList<>();
}
