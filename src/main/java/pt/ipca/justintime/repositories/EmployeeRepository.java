package pt.ipca.justintime.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import pt.ipca.justintime.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	
}
