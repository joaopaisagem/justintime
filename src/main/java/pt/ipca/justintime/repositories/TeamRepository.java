package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipca.justintime.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
