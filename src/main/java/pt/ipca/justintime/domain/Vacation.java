package pt.ipca.justintime.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
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
@Builder
@Entity
public class Vacation {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationStartDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationEndDay;

    @ManyToMany(mappedBy = "vacationList",cascade = javax.persistence.CascadeType.ALL)
    private List<Employee> usersList;
}
