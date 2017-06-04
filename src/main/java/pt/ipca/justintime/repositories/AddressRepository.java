package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipca.justintime.domain.Address;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
