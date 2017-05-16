package pt.ipca.justintime.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import pt.ipca.justintime.domain.Client;

public interface ClientRepository extends JpaRepository<Client,Long>{
	

}
