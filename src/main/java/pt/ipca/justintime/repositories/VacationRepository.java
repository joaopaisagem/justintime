package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipca.justintime.domain.Vacation;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

}
