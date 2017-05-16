package pt.ipca.justintime.domain;


import java.util.Date;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Builder
public class Employee extends Person {
	
	private String picture;
	@Past @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthdayDate;
    @OneToMany
    @Cascade({CascadeType.ALL}) 
    private List<WorkSkill> skillList;
  /*  @OneToMany
    @Cascade({CascadeType.ALL}) 
    private List<Vacation> vacationList;*/
    @ManyToOne
    @Cascade({CascadeType.ALL})
    private Team employeeTeamName;
    @ManyToMany
    @JoinTable( name = "vacations", 
    joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "vacation_id", referencedColumnName = "id")) 
    private List<Vacation> vacationList; 
    
   
    
   

    
}
