package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipca.justintime.domain.Contact;

/**
 * Created by tiagosilva on 17/05/17.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
