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
 * Created by tiagosilva on 01/06/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SearchProjectForm {

    @NotNull
    private Long id;


}
