package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.forms.NewTeamForm;
import pt.ipca.justintime.forms.TeamForm;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Controller
public class TeamController extends WebMvcConfigurerAdapter {

    @Autowired
    private TeamService teamService;

    /**
     * This method dosent receive any arguments
     * Creates a ModelAndView to create a team
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/newteam", method = RequestMethod.GET)
    public ModelAndView teamForm() {

        ModelAndView modelAndView = new ModelAndView("teamform", "team", new NewTeamForm());

        return modelAndView;
    }

    /**
     * This method receive two arguments
     * 1º argument must be a Team
     * 2º is bindingresult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Creates ModelAndView for teamform
     * Check if bindingresult has errors, if true return view with error message
     * Else try to save the team, if true return view with success message
     * if false  return view with error message
     *
     * @param team team to save
     * @param bindingResult bean validation from team
     * @return view
     */
    @RequestMapping(value = "/newteam", method = RequestMethod.POST)
    public ModelAndView checkTeamInfo(@Valid @ModelAttribute("team") NewTeamForm team, BindingResult bindingResult) {

        ModelAndView newTeamForm = new ModelAndView("teamform");
        newTeamForm.addObject(team);

        if (bindingResult.hasErrors()) {

            newTeamForm.addObject("errormessage", "You have error on the name please insert a valid one !");
            return newTeamForm;

        } else if (teamService.saveTeamForm(team)) {

            newTeamForm.addObject("successmessage", "Your team was saved !");

            return newTeamForm;
        }
        newTeamForm.addObject("errormessage", "Your team already exists !");

        return newTeamForm;
    }

    /**
     * This method dosent recieve any argument
     * Creates a ModelAndView to search a team
     *
     * @return view
     */
    @RequestMapping(value = "/searchteam", method = RequestMethod.GET)
    public ModelAndView showEditTeamForm() {

        ModelAndView modelAndView = new ModelAndView("searchteamform", "team", new TeamForm());

        return modelAndView;
    }

    /**
     * This method receive two arguments
     * 1º teamForm  that is the id from the team we want to edit
     * 2º is bindingresult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Creates two ModelAndView (editModelAndView ,searchModelAndView)
     * if bindingResult has errors returns searchModelAndView with message error
     * else check if the team exists, if true return editModelAndView with team to edit
     * otherwise return searchModelAndView with error message
     *
     * @param teamForm id from team to edit
     * @param bindingResult bean validation for TeamForm
     * @return view editModelAndView , searchModelAndView
     */
    @RequestMapping(value = "/editteam", method = RequestMethod.GET)
    public ModelAndView checkTeamToEditInfo(@Valid @ModelAttribute("team") TeamForm teamForm, BindingResult bindingResult) {

        ModelAndView editModelAndView = new ModelAndView("editteamform", "team", new TeamForm());
        ModelAndView searchModelAndView = new ModelAndView("searchteamform", "team", new TeamForm());

        if (bindingResult.hasErrors()) {
            searchModelAndView.addObject("errormessage", "The Team id cannot be null !");

            return searchModelAndView;
        } else if (teamService.searchIfExists(teamForm.getId())) {

            editModelAndView.addObject("team", teamService.getTeamById(teamForm.getId()));

            return editModelAndView;
        }
        searchModelAndView.addObject("errormessage", "The team could not be found!");

        return searchModelAndView;
    }

    /**
     * This method receive two arguments
     * 1º teamForm  that is the  team we edited
     * 2º is bindingresult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Creates one ModelAndView viewName:"editteamform", modelName:"team", teamForm
     * if bindingResult has errors returns modelAndView with message error
     * else updates the team, and return view with success message
     *
     * @param teamForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/editteam", method = RequestMethod.POST)
    public ModelAndView editTeam(@Valid @ModelAttribute("team") TeamForm teamForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("editteamform", "team", teamForm);

        if (bindingResult.hasErrors()) {

            modelAndView.addObject("errormessage", "There was error on team name ");

            return modelAndView;
        }
        teamService.updateTeamForm(teamForm);
        modelAndView.addObject("successmessage", "The team name was edited!");

        return modelAndView;
    }

    /**
     * This method dosent recieve any argument
     * Creates a ModelAndView to search a team to delete
     *
     * @return view
     */
    @RequestMapping(value = "/deleteteam", method = RequestMethod.GET)
    public ModelAndView showSearchDeleteTeamForm() {

        ModelAndView modelAndView = new ModelAndView("deleteteamform", "team", new TeamForm());

        return modelAndView;
    }

    /**
     * This method receive two arguments
     * The first argument is the id from the team we want to delete
     * The second argument is bindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * if bindingResult has errors return view with error message
     * else try to remove team, if true return view with success message
     * else return error message and view
     *
     * @param team team id to delete
     * @param bindingResult bean validation from TeamForm
     * @return view
     */
    @RequestMapping(value = "/deleteteam", method = RequestMethod.POST)
    public ModelAndView deleteTeamById(@Valid @ModelAttribute("team") TeamForm team, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("deleteteamform", "team", team);
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("errormessage", "You have errors on the form please try again!");

            return modelAndView;

        } else if (teamService.tryToRemoveTeam(team)) {

            modelAndView.addObject("successmessage", "The Team was deleted");

            return modelAndView;
        }
        modelAndView.addObject("errormessage", "The team was not found or have employees associated to it , if there is employees associated to it you cannot delete the team");

        return modelAndView;
    }

    /**
     * This method dosent receive any argument
     * Creates a ModelAndView to show all teams
     *
     * @return view
     */
    @RequestMapping(value = "/showallteams", method = RequestMethod.GET)
    public ModelAndView seeAllTeams() {

        ModelAndView viewteams = new ModelAndView("showallteams", "teams", teamService.getAllTeams());

        return viewteams;
    }
}


