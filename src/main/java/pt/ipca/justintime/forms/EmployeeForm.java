package pt.ipca.justintime.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pt.ipca.justintime.domain.*;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Tiago Silva on 28/05/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeForm {

    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NotNull
    @Min(value = 000000000)
    @Max(value = 999999999)
    private Integer nif;
    /**
     * ^	#start of the line
     * [_A-Za-z0-9-\\+]+ #  must start with string in the bracket [ ], must contains one or more
     * (# start of group #1
     * \\.[_A-Za-z0-9-]+ # follow by a dot "." and string in the bracket [ ], must contains one or more)
     * *#   end of group #1, this group is optional (*)
     * ***********************************************
     *
     * @# must contains a "@" symbol
     * [A-Za-z0-9-]+ #follow by string in the bracket [ ], must contains one or more
     * ************************************************
     * #start of group #2 - first level checking
     * \\(.[A-Za-z0-9]+ # follow by a dot "." and string in the bracket [ ], must contains one or more)
     * #end of group #2, this group is optional (*)
     * *************************************************
     * #start of group #3 - second level checking
     * #\\.[A-Za-z]{2,}  #follow by a dot "." and string in the bracket [ ], with minimum length of 2)
     * #end of group #3
     * $#end of the line
     */
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

    private String picture;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;

    private List<WorkSkill> skillList;

    private Team employeeTeamName;

    private List<Vacation> vacationList;

}
