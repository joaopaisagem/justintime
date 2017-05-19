package pt.ipca.justintime.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@Table(name = "PERSON")
@Inheritance(strategy=InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue
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
    @Column(name = "GENDER")
    private Gender gender;

    @OneToOne
    private Address addressOne;

    @OneToOne
    private Address addressTwo;

    @OneToMany
    private List<Contact> contactList;
}
