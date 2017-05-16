package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipca.justintime.domain.Vacation;
@Repository
public interface VacationRepository extends JpaRepository<Vacation ,Long> {

}
