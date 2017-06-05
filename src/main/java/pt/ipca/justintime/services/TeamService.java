package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Team;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.factorys.TeamFactory;
import pt.ipca.justintime.forms.NewTeamForm;
import pt.ipca.justintime.forms.TeamForm;
import pt.ipca.justintime.repositories.TeamRepository;
import pt.ipca.justintime.utils.VacationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamFactory teamFactory;
    @Autowired
    private VacationUtils vacationUtils;


    private Team saveTeam(Team team) {

        return teamRepository.save(team);
    }

    public Team getTeamById(Long id) {

        return teamRepository.findOne(id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();

    }

    public void updateTeam(Team team) {

        teamRepository.saveAndFlush(team);

    }

    public void deleteTeam(Long id) {

        teamRepository.delete(id);
    }

    public boolean searchIfExists(Long id) {

        return teamRepository.exists(id);
    }


    private boolean checkIfTeamExists(Long id) {

        if (id == null) {

            return false;
        } else if (searchIfExists(id)) {

            return true;
        }

        return false;
    }

    private boolean checkIfTeamExistsByName(Team team) {

        List<Team> teamsList = getAllTeams();
        for (Team t : teamsList) {

            if (t.equals(team)) {

                return true;
            }
        }

        return false;
    }

    private String removeSpacesOnTheTeamName(String teamName) {

        String result = teamName.replace(" ", "");

        return result;
    }

    /**
     * Method to count the number of teams
     *
     * @return returns a count with the total of teams
     */
    public int countNumberOfTeams() {

        int count = 0;
        List<Team> teamList = teamRepository.findAll();
        count = teamList.size();

        return count;
    }


    public void updateTeamForm(TeamForm teamForm) {

        Team team = teamFactory.transformTeamFormIntoTeam(teamForm);
        team.setTeamName(removeSpacesOnTheTeamName(team.getTeamName()));
        updateTeam(team);
    }

    public boolean saveTeamForm(NewTeamForm teamForm) {

        Team team = teamFactory.transformNewTeamFormIntoTeam(teamForm);
        removeSpacesOnTheTeamName(team.getTeamName());
        team.setTeamName(removeSpacesOnTheTeamName(team.getTeamName()));

        if (checkIfTeamExistsByName(team)) {

            return false;
        } else {

            saveTeam(team);
        }

        return true;
    }

    public boolean tryToRemoveTeam(TeamForm teamForm) {

        Team team = teamFactory.transformTeamFormIntoTeam(teamForm);

        if (checkIfTeamExists(team.getId())) {

            try {

                deleteTeam(team.getId());
                return true;
            } catch (Exception e) {

                return false;
            }
        }

        return false;
    }

    public List<Team> getTeamsWithVacationsForCurrentMonth(){

        List<Team> teamList =  new ArrayList<>();

        for (Team team : getAllTeams())
        {
            int cont = 0;
            for(Employee employee : team.getEmployeeList())
            {
                List<LocalDate> localDateList = vacationUtils.getDaysOfVacationByMonth(employee.getVacationList(), LocalDate.now().getMonth().getValue());
                if (!localDateList.isEmpty()){

                    cont++;
                }
            }
            if(cont !=0)
            {
                teamList.add(team);
            }
        }

        return teamList;
    }

}
