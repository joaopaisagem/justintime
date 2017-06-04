package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
