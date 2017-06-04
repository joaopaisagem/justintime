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

@Controller
public class TeamController extends WebMvcConfigurerAdapter {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/newteam", method = RequestMethod.GET)
    public ModelAndView teamForm() {
        ModelAndView modelAndView = new ModelAndView("teamform", "team", new NewTeamForm());
        return modelAndView;
    }

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


    @RequestMapping(value = "/searchteam", method = RequestMethod.GET)
    public ModelAndView showEditTeamForm() {

        ModelAndView modelAndView = new ModelAndView("searchteamform", "team", new TeamForm());
        return modelAndView;
    }

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


    @RequestMapping(value = "/deleteteam", method = RequestMethod.GET)
    public ModelAndView showSearchDeleteTeamForm() {
        ModelAndView modelAndView = new ModelAndView("deleteteamform", "team", new TeamForm());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteteam", method = RequestMethod.POST)
    public ModelAndView deleteTeamById(@Valid @ModelAttribute("team") TeamForm team, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("deleteteamform", "team", team);
        if (bindingResult.hasErrors()) {

        } else if (teamService.tryToRemoveTeam(team)) {

            modelAndView.addObject("successmessage", "The Team was deleted");
            return modelAndView;
        }
        modelAndView.addObject("errormessage", "The team was not found or have employees associated to it , if there is employees associated to it you cannot delete the team");
        return modelAndView;
    }


    @RequestMapping(value = "/showallteams", method = RequestMethod.GET)
    public ModelAndView seeAllTeams() {
        ModelAndView viewteams = new ModelAndView("showallteams", "teams", teamService.getAllTeams());
        return viewteams;
    }

}


