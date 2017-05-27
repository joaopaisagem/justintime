package pt.ipca.justintime.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ipca.justintime.domain.Project;
import pt.ipca.justintime.services.ClientService;
import pt.ipca.justintime.services.ProjectService;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;

@Controller
public class ProjectController extends WebMvcConfigurerAdapter {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TeamService teamService;


    /*******************************************************************************************************/
    /***************************************** SAVE AN EMPLOYEE*********************************************/
    /*******************************************************************************************************/
    /*
     * METHOD TO MAP AND SHOW EMPLOYEE FORM
	 */
    @RequestMapping(value = "/newproject", method = RequestMethod.GET)
    public String newProjectForm(ModelMap model) {
        model.addAttribute("project", new Project());
        model.addAttribute("teamList", teamService.getAllTeams());
        model.addAttribute("clientList", clientService.getAllClients());
        return "projectform";
    }

    @RequestMapping(value = "/newproject", method = RequestMethod.POST)
    public String saveProject(Project project, ModelMap model, RedirectAttributes redirectAttributes) {
        if( projectService.saveProjectForm(project))
        {
             redirectAttributes.addFlashAttribute(project);
             redirectAttributes.addFlashAttribute("successmessage","You saved the project successfully");
             return "redirect:/newproject";
        }else {
            redirectAttributes.addFlashAttribute(project);
            redirectAttributes.addFlashAttribute("errormessage","The project already exists");
            return "redirect:/newproject";
        }
    }

    @GetMapping(value = "/showproject")
    public String showProjectForm(ModelMap model) {
        model.addAttribute("project", new Project());
        return "showproject";
    }

    @RequestMapping(value = "/showproject", method = RequestMethod.POST)
    public String showProjectById(Long id, ModelMap model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "showproject";
    }

    @GetMapping(value = "/editproject")
    public String showEditProjectForm(ModelMap model) {
        model.addAttribute("project", new Project());
        return "searcheditproject";
    }

    @RequestMapping(value = "/editproject", method = RequestMethod.POST)
    public String showProjectToEditById(Long id, ModelMap model) {
        model.addAttribute("project", projectService.getProjectById(id));
        model.addAttribute("teamList", teamService.getAllTeams());
        model.addAttribute("clientList", clientService.getAllClients());

        return "editprojectform";
    }

    @PostMapping("/editproject/edit")
    public String checkPersonInfo(@Valid Project project, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editprojectform";
        }

        projectService.updateProject(project);
        return "redirect:/projectresult";
    }


}
