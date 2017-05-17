package pt.ipca.justintime.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkSkill {

    @Id
    @GeneratedValue
    private Long id;

    private String nameOfSkill;
}
