package pt.ipca.justintime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ipca.justintime.domain.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>{

}
