package pt.ipca.justintime.domain;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.joda.time.LocalDate;
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
   /* @ManyToOne
    private Employee employee;*/
    @ManyToMany(mappedBy = "vacationList")
    private List<Employee> usersList;
}
