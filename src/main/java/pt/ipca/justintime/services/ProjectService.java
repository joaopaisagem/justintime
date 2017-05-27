package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Project;
import pt.ipca.justintime.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    //////////////////////////////////////////////////////////
    //               CRUD METHOD`S                         //
    ////////////////////////////////////////////////////////
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findOne(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();

    }

    public void updateProject(Project project) {
        projectRepository.saveAndFlush(project);

    }

    public void deleteProject(Long id) {
        projectRepository.delete(id);
    }

    //////////////////////////////////////////////////////////
    //              Project METHOD`S                       //
    ////////////////////////////////////////////////////////

    public boolean checkIfProjectExists(Project project){
        List<Project> projectList = projectRepository.findAll();
        for(Project proj : projectList)
        {
            if (proj.equals(project))
            {
                return true;
            }
        }
        return false;
    }
    public boolean saveProjectForm(Project projectForm)
    {
       if(checkIfProjectExists(projectForm))
       {
           return false;
       }
        saveProject(projectForm);
        return true;
    }

}
