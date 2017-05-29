package pt.ipca.justintime.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENT")
@PrimaryKeyJoinColumn(name ="PERSON_ID")

public class Client extends Person {

    @OneToMany(mappedBy = "client")
    private List<Project> projectList = new ArrayList<>();

}
