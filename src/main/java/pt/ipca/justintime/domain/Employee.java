package pt.ipca.justintime.domain;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Table;

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
@Table(name="EMPLOYEE")
@PrimaryKeyJoinColumn(name="PERSON_ID")
public class Employee extends Person {

    @Column(name="PICTURE")
    private String picture;

    @Column(name="BIRTHDAYDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    @OneToMany
    private List<WorkSkill> skillList;

    @ManyToOne
    private Team employeeTeamName;

    @OneToMany
    private List<Vacation> vacationList;
}
