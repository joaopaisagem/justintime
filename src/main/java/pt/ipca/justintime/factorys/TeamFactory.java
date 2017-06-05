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

    /**
     * This method receive one argument
     * The argument must be TeamForm
     * Transform one TeamForm into a form
     *
     * @param teamForm to transform into team
     * @return team
     */
    public Team transformTeamFormIntoTeam(TeamForm teamForm) {

        Team team = new Team();
        team.setId(teamForm.getId());
        team.setTeamName(teamForm.getTeamName());
        team.setEmployeeList(teamForm.getEmployeeList());
        team.setProjectList(teamForm.getProjectList());

        return team;
    }

    /**
     * This method receive one argument
     * The argument must be NewTeamForm
     * Transform one NewTeamForm into a form
     *
     * @param teamForm to transform into team
     * @return team
     */
    public Team transformNewTeamFormIntoTeam(NewTeamForm teamForm) {

        Team team = new Team();
        team.setId(teamForm.getId());
        team.setTeamName(teamForm.getTeamName());
        team.setEmployeeList(teamForm.getEmployeeList());
        team.setProjectList(teamForm.getProjectList());

        return team;
    }


}
