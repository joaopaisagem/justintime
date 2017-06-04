package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pt.ipca.justintime.domain.Client;
import pt.ipca.justintime.domain.SoftwareDevelopmentCicle;
import pt.ipca.justintime.domain.Team;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

public class ProjectForm {

    private Long id;
    @NotEmpty
    private String projectName;
    @NotEmpty
    private String projectDescription;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;
    @NotNull
    private Client client;
    @NotNull
    private Team assignedTeam;

    @Enumerated(EnumType.STRING)
    private SoftwareDevelopmentCicle status;


}
