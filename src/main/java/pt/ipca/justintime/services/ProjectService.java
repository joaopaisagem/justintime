package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Project;
import pt.ipca.justintime.repositories.ProjectRepository;

import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * This method receive one argument
     * The argument must be a Project
     * Save a project into database
     *
     * @param project to save
     * @return saved project
     */
    public Project saveProject(Project project) {

        return projectRepository.save(project);
    }

    /**
     * This method receive one argument
     * The argument must be a Long id
     * Gets from database a project by id
     *
     * @param id project id
     * @return project
     */
    public Project getProjectById(Long id) {

        return projectRepository.findOne(id);
    }

    /**
     * This method dosent receive arguments
     * Gets all projects from database
     *
     * @return List<Project>
     */
    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }

    /**
     * This method receive one argument
     * The argument must be a project
     * updates the project
     * dosent return any type
     *
     * @param project project to update
     */
    public void updateProject(Project project) {

        projectRepository.saveAndFlush(project);
    }

    /**
     * This method receive one argument
     * The argument must be a project
     * Checks if the project exists
     *
     * @param project to check
     * @return TRUE , FALSE
     */
    public boolean checkIfProjectExists(Project project) {

        List<Project> projectList = projectRepository.findAll();
        for (Project proj : projectList) {

            if (proj.equals(project)) {

                return true;
            }
        }

        return false;
    }

    /**
     * This method receive one argument
     * The argument must be a project
     * Checks if the project exists
     * if returns false means the project exists so it dosent save
     * if return true save the project into database
     *
     * @param projectForm project to save
     * @return TRUE, FALSE
     */
    public boolean saveProjectForm(Project projectForm) {

        if (checkIfProjectExists(projectForm)) {

            return false;
        }
        saveProject(projectForm);

        return true;
    }

}
