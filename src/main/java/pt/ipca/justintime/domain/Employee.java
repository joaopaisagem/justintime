package pt.ipca.justintime.domain;


import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;
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
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee extends Person {

    private String picture;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    @OneToMany
    @Cascade({CascadeType.ALL})
    private List<WorkSkill> skillList;

    @ManyToOne
    @Cascade({CascadeType.ALL})
    private Team employeeTeamName;

    @ManyToMany(cascade = javax.persistence.CascadeType.ALL)
    @JoinTable(name = "vacations",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vacation_id", referencedColumnName = "id"))
    private List<Vacation> vacationList;
}
