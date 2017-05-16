package pt.ipca.justintime.domain;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
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
@Inheritance
@Entity
public class Person {
	   @Id
	   @GeneratedValue
	   private Long id;
	   @NotEmpty 
	   private String firstName;
	   @NotEmpty
	   private String lastName;
	   @NotNull @Min(value = 000000000) @Max(value=999999999)
	   private Integer nif;
	   /**
	    * ^	#start of the line
  		*[_A-Za-z0-9-\\+]+ #  must start with string in the bracket [ ], must contains one or more
  		*(# start of group #1
    	*\\.[_A-Za-z0-9-]+ # follow by a dot "." and string in the bracket [ ], must contains one or more)
  		**#   end of group #1, this group is optional (*)
  		************************************************
    	*@#   must contains a "@" symbol
     	*[A-Za-z0-9-]+ #follow by string in the bracket [ ], must contains one or more
     	*************************************************
      	*#start of group #2 - first level checking
       	*\\(.[A-Za-z0-9]+ # follow by a dot "." and string in the bracket [ ], must contains one or more)
       	*#end of group #2, this group is optional (*)
       	**************************************************
        *#start of group #3 - second level checking
       	*#\\.[A-Za-z]{2,}  #follow by a dot "." and string in the bracket [ ], with minimum length of 2)			
       	*#end of group #3
		*$#end of the line
	    */
	   @NotEmpty @Email @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", 
			   message="Please insert a valid email")
	   private String email;
	   @Enumerated(EnumType.STRING)
	   @Column(name = "gender")
	   private Gender gender;
	   @OneToOne
	   @Cascade({CascadeType.ALL})
	   private Address addressOne;
	   @OneToOne
	   @Cascade({CascadeType.ALL})
	   private Address addressTwo;
	   @Cascade({CascadeType.ALL})
	   @OneToMany
	   private List<Contact> contactList;
	
	   
	   
}
