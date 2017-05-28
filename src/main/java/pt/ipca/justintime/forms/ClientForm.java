package pt.ipca.justintime.forms;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pt.ipca.justintime.domain.Address;
import pt.ipca.justintime.domain.Contact;
import pt.ipca.justintime.domain.Gender;
import pt.ipca.justintime.domain.Project;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Silva on 28/05/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientForm {

    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    @Min(value = 000000000)
    @Max(value = 999999999)
    private Integer nif;

    @NotEmpty
    @Email
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Please insert a valid email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Address addressOne;

    private Address addressTwo;

    private List<Contact> contactList;

    private List<Project> projectList = new ArrayList<>();


}
