package pt.ipca.justintime.domain;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email  a7809@alunos.ipca.pt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {
		@Id
		@GeneratedValue
	 	private Long id;
	    private String teamName;
	    @OneToMany(mappedBy="employeeTeamName")
	    private List<Employee> employeeList = new ArrayList<Employee>();
	    @OneToMany(mappedBy="assignedTeam")
	    private List<Project> projectList = new ArrayList<Project>();

}
