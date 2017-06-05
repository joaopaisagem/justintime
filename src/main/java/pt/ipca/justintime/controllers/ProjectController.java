package pt.ipca.justintime.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.domain.Project;
import pt.ipca.justintime.forms.ProjectForm;
import pt.ipca.justintime.forms.SearchProjectForm;
import pt.ipca.justintime.services.ClientService;
import pt.ipca.justintime.services.ProjectService;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Controller
public class ProjectController extends WebMvcConfigurerAdapter {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TeamService teamService;

    /**
     * This method dosent receive any arguments
     * Creates a ModelAndView with viewName: projectform
     * Its composed by 3 objects
     * new Project(), teamList , clientList
     *
     * @return  new project form view
     */
    @RequestMapping(value = "/newproject", method = RequestMethod.GET)
    public ModelAndView newProjectForm() {

        ModelAndView projectForm = new ModelAndView("projectform");
        projectForm.addObject("project", new Project());
        projectForm.addObject("teamList", teamService.getAllTeams());
        projectForm.addObject("clientList", clientService.getAllClients());

        return projectForm;
    }

    /**
     * This method receive two arguments
     * The first one is the project submitted by newProjectForm() method
     * The second argument is BindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Create a ModelAndView  with the  viewname: employeeform
     * The Project submitted must pass all validation fromt the bean Project()
     * If the bindingResult has errors return the view with bindingResult Errors , and  error message
     * If there is no errors , try to save  the project and checks if the project exists
     * if false save the project and return view with a success message
     * else return a error message and the view
     *
     * @param project project to save
     * @param bindingResult bean validation from project
     * @return view
     */
    @RequestMapping(value = "/newproject", method = RequestMethod.POST)
    public ModelAndView checkNewProjectInfo(@Valid @ModelAttribute("project") Project project, BindingResult bindingResult) {

        ModelAndView projectForm = new ModelAndView("projectform");
        projectForm.addObject("project", project);
        projectForm.addObject("teamList", teamService.getAllTeams());
        projectForm.addObject("clientList", clientService.getAllClients());

        if (bindingResult.hasErrors()) {
            projectForm.addObject("errormessage", "The project have errors please verify all the fields");

            return projectForm;
        } else if (projectService.saveProjectForm(project)) {

            projectForm.addObject("successmessage", "You saved the project successfully");

            return projectForm;
        } else {
            projectForm.addObject("errormessage", "The project already exists");

            return projectForm;
        }
    }

    /**
     * This method dosent receive any argument
     * Creates a ModelAndView  viewName:"searchprojecttoshow",model:"project", new SearchProjectForm()
     *
     * @return view to chose a project to show
     */
    @RequestMapping(value = "/showproject", method = RequestMethod.GET)
    public ModelAndView showProjectForm() {

        ModelAndView modelAndView = new ModelAndView("searchprojecttoshow", "project", new SearchProjectForm());

        return modelAndView;
    }

    /**
     * This method receive two arguments
     * 1º searchProjectForm with id of the project to search
     * 2º bindingResult with project validation
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Creates two ModelAndView ( searchprojecttoshow , showproject)
     * if bindingResult has error return searchprojecttoshow with errormessage
     * Search the project by id
     * if id is null return searchprojecttoshow with errormessage
     * if != null return showModelAndView with project
     *
     * @param searchProjectForm id to search
     * @param bindingResult bean validation from SearchProjectForm
     * @return view searchprojecttoshow,showproject
     */
    @RequestMapping(value = "/showproject", method = RequestMethod.POST)
    public ModelAndView showProjectById(@Valid @ModelAttribute("searchProjectForm") SearchProjectForm searchProjectForm, BindingResult bindingResult) {

        ModelAndView searchModelAndView = new ModelAndView("searchprojecttoshow", "project", searchProjectForm);
        ModelAndView showModelAndView = new ModelAndView("showproject", "project", new ProjectForm());

        if (bindingResult.hasErrors()) {

            searchModelAndView.addObject("errormessage", "You need to insert a Id to search");

            return searchModelAndView;
        } else if (searchProjectForm.getId() != null) {

            Project project = projectService.getProjectById(searchProjectForm.getId());

            if (project != null) {

                showModelAndView.addObject("project", project);

                return showModelAndView;
            }

        } else
            searchModelAndView.addObject("errormessage", "There is no project with that id!");

        return searchModelAndView;
    }

    /**
     * This method dosent receive any argument
     * Creates a ModelAndView viewName:"searcheditproject","project", new Project()
     *
     * @return view to search id to edit project
     */
    @RequestMapping(value = "/editproject", method = RequestMethod.GET)
    public ModelAndView showEditProjectForm() {

        ModelAndView modelAndView = new ModelAndView("searcheditproject","project", new Project());

        return modelAndView;
    }

    /**
     * This method recieve one argument
     * The argument must be a valid project id
     * Creates two views (editModelAndView,searchModelAndView)
     * Check if the argument id is null, if true return searchModelAndView and error message
     * If false checks if the project exists, if true return the editModelAndView with the project
     * otherwise return searchModelAndView with error message
     *
     * @param id project id
     * @return view (editModelAndView,searchModelAndView)
     */
    @RequestMapping(value = "/editproject", method = RequestMethod.POST)
    public ModelAndView showProjectToEditById(Long id) {

        ModelAndView editModelAndView = new ModelAndView("editprojectform");
        ModelAndView searchModelAndView = new ModelAndView("searcheditproject","project", new Project());

        if(id == null)
        {
         searchModelAndView.addObject("errormessage","You need to insert a valid id");
         return searchModelAndView;

        }else if(id!=null)
        {
            if(projectService.getProjectById(id)!=null)
            {
                editModelAndView.addObject("project", projectService.getProjectById(id));
                editModelAndView.addObject("teamList", teamService.getAllTeams());
                editModelAndView.addObject("clientList", clientService.getAllClients());

                return editModelAndView;
            }
        }
        searchModelAndView.addObject("errormessage","The project could not be found!");

        return searchModelAndView;
    }

    /**
     * This method receive two arguments
     * 1º argument is the project that we edited
     * 2º bindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Checks if bindingResult has errors if true return view with project to edit and error messages
     * if false updates the project and return view with the project updated
     *
     * @param project project to edit
     * @param bindingResult bean project validation
     * @return view
     */
    @RequestMapping(value = "/editproject/edit", method = RequestMethod.POST)
    public ModelAndView checkEditProjectInfo(@Valid Project project, BindingResult bindingResult) {

        ModelAndView editModelAndView = new ModelAndView("editprojectform");

        if (bindingResult.hasErrors()) {
            editModelAndView.addObject("project", project);
            editModelAndView.addObject("teamList", teamService.getAllTeams());
            editModelAndView.addObject("clientList", clientService.getAllClients());
            return editModelAndView;
        }

        projectService.updateProject(project);
        editModelAndView.addObject("project", projectService.getProjectById(project.getId()));
        editModelAndView.addObject("teamList", teamService.getAllTeams());
        editModelAndView.addObject("clientList", clientService.getAllClients());

        return editModelAndView;
    }

    /**
     * This method dosent receive any arguments
     * Creates a ModelAndView to show all projects
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/showallprojects", method = RequestMethod.GET)
    public ModelAndView showAllProjects() {

        ModelAndView modelAndView = new ModelAndView("showallprojects");
        modelAndView.addObject("projectsList", projectService.getAllProjects());

        return modelAndView;
    }

}
