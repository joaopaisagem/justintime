package pt.ipca.justintime.factorys;

import org.springframework.stereotype.Component;
import pt.ipca.justintime.domain.Team;
import pt.ipca.justintime.forms.NewTeamForm;
import pt.ipca.justintime.forms.TeamForm;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Component
public class TeamFactory {

    public Team transformTeamFormIntoTeam(TeamForm teamForm) {

        Team team = new Team();
        team.setId(teamForm.getId());
        team.setTeamName(teamForm.getTeamName());
        team.setEmployeeList(teamForm.getEmployeeList());
        team.setProjectList(teamForm.getProjectList());

        return team;
    }

    public Team transformNewTeamFormIntoTeam(NewTeamForm teamForm) {

        Team team = new Team();
        team.setId(teamForm.getId());
        team.setTeamName(teamForm.getTeamName());
        team.setEmployeeList(teamForm.getEmployeeList());
        team.setProjectList(teamForm.getProjectList());

        return team;
    }


    public TeamForm transformTeamIntoTeamForm(Team team) {
        TeamForm teamForm = new TeamForm();

        teamForm.setId(team.getId());
        teamForm.setTeamName(team.getTeamName());
        teamForm.setEmployeeList(team.getEmployeeList());
        teamForm.setProjectList(team.getProjectList());

        return teamForm;
    }


}
