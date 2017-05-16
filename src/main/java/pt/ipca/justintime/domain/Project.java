package pt.ipca.justintime.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
public class Project {
	@Id
	@GeneratedValue
	private Long id;
    private String projectName;
    private String projectDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectEndDate;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Team assignedTeam;
    @Enumerated(EnumType.STRING)
 	@Column(name = "status")
    private SoftwareDevelopmentCicle status; 
  
}
