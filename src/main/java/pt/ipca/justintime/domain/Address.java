package pt.ipca.justintime.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class Address {
		@Id
		@GeneratedValue
	  	private Long id;
	    private String addressStreet;
	    private String city;
	    private String country;
	    private String postalCode;
	
}
