package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipca.justintime.domain.Address;
import pt.ipca.justintime.domain.Client;

/**
 * Created by tiagosilva on 17/05/17.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
