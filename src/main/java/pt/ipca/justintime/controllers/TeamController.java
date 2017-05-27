package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ipca.justintime.domain.Team;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;

@Controller
public class TeamController extends WebMvcConfigurerAdapter {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/newteam", method = RequestMethod.GET)
    public String teamForm(ModelMap model) {
        model.addAttribute("team", new Team());
        model.addAttribute("teamList", teamService.getAllTeams());
        return "teamform";
    }

    @RequestMapping(value = "/newteam", method = RequestMethod.POST)
    public String checkTeamInfo(@Valid Team team, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "teamform";
        }

        teamService.saveTeam(team);
        return "redirect:/teamresult";
    }

    @RequestMapping(value = "/editteam", method = RequestMethod.POST)
    public String showTeamToEditById(Long id, ModelMap model) {
        model.addAttribute("team", teamService.getTeamById(id));

        return "editteamform";
    }

    @RequestMapping(value = "/editteam", method = RequestMethod.GET)
    public String showEditTeamForm(ModelMap model) {
        model.addAttribute("team", new Team());
        return "searchteamform";
    }

    @RequestMapping(value = "/editteam/edit", method = RequestMethod.POST)
    public String checkPersonInfo(@Valid Team team, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editteamform";
        }

        teamService.updateTeam(team);
        return "redirect:/teamresult";
    }

    @RequestMapping(value = "/deleteteam", method = RequestMethod.GET)
    public String showSearchDeleteTeamForm(ModelMap model) {
        model.addAttribute("team", new Team());
        return "deleteteamform";
    }

    @RequestMapping(value = "/deleteteam", method = RequestMethod.POST)
    public String deleteTeamById(Long id, RedirectAttributes redirectAttributes) {

        if (teamService.searchIfExists(id) == true) {
            teamService.deleteTeam(id);
            redirectAttributes.addFlashAttribute("message", "The Team was deleted");
            return "redirect:/newteam";
        }
        redirectAttributes.addFlashAttribute("message", "The team was not found");
        return "redirect:/deleteteam";
    }

    @RequestMapping(value="/seeallteams", method = RequestMethod.GET)
    public ModelAndView seeAllTeams()
    {
        ModelAndView viewteams = new ModelAndView("seeallteams","teams",teamService.getAllTeams());
        return viewteams;
    }

}


