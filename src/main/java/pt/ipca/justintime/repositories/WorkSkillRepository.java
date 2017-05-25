package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipca.justintime.domain.WorkSkill;

/**
 * Created by tiagosilva on 17/05/17.
 */
public interface WorkSkillRepository extends JpaRepository<WorkSkill, Long> {

}
