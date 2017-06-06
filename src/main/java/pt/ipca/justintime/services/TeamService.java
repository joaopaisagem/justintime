package pt.ipca.justintime.services;

import lombok.experimental.PackagePrivate;
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

    /**
     * This method receive one argument
     * The argument must be a Team
     * Saves the team into database
     * Return the saved team
     *
     * @param team to save
     * @return team
     */
    private Team saveTeam(Team team) {

        return teamRepository.save(team);
    }

    /**
     * This method receive one argument
     * Teh argument must be related to a team id - Long id
     * Finds the team trough the id
     *
     * @param id team id
     * @return team
     */
    public Team getTeamById(Long id) {

        return teamRepository.findOne(id);
    }

    /**
     * This method dosent receive any parameter
     * Get all teams from database
     *
     * @return List<Team>
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();

    }

    /**
     * This method receive one argument
     * The argument must be a team
     * Updates the team
     * Dosent return any type
     *
     * @param team to update
     */
    public void updateTeam(Team team) {

        teamRepository.saveAndFlush(team);
    }

    /**
     * This method receive one argument
     * The argument must be related to a team id -Long id
     * Deletes the team from the database
     * Dosent return any type
     *
     * @param id team id
     */
    public void deleteTeam(Long id) {

        teamRepository.delete(id);
    }

    /**
     * This method receive one argument
     * The argument must be related to a team id -Long id
     * Search if the team exists
     *
     * @param id Long
     * @return TRUE,FALSE
     */
    public boolean searchIfExists(Long id) {

        return teamRepository.exists(id);
    }


    /**
     * This method receive one argument
     * The argument must be related to a team id -Long id
     * checkIfTeamExists
     *
     * @param id team id
     * @return TRUE,FALSE
     */
    private boolean checkIfTeamExists(Long id) {

        if (id == null) {

            return false;
        } else if (searchIfExists(id)) {

            return true;
        }

        return false;
    }

    /**
     * This method receive one argument
     * The argument must be related to a team
     * check if Team Exists By Name
     *
     * @param team team to search
     * @return TRUE,FALSE
     */
    private boolean checkIfTeamExistsByName(Team team) {

        List<Team> teamsList = getAllTeams();
        for (Team t : teamsList) {

            if (t.equals(team)) {

                return true;
            }
        }

        return false;
    }

    /**
     * This method receive one argument
     * Teh argument must be a Team name ( String teamName)
     * Removes the spaces from the team name ex:( Team One )
     * The final result will be ( TeamOne )
     *
     * @param teamName String
     * @return String teamName
     */
    private String removeSpacesOnTheTeamName(String teamName) {

        String result = teamName.replace(" ", "");

        return result;
    }

    /**
     * This method dosent receive any argument
     * Method to count the number of teams in database
     *
     * @return returns a int with the total of teams
     */
    public int countNumberOfTeams() {

        int count = 0;
        List<Team> teamList = teamRepository.findAll();
        count = teamList.size();

        return count;
    }

    /**
     * This method receive one argument
     * The argument must be a TeamForm from the team controller
     * Updates the team
     * Dosent return any type
     *
     * @param teamForm
     */
    public void updateTeamForm(TeamForm teamForm) {

        Team team = teamFactory.transformTeamFormIntoTeam(teamForm);
        team.setTeamName(removeSpacesOnTheTeamName(team.getTeamName()));
        updateTeam(team);
    }

    /**
     * This method receive one argument
     * The argument must be a NewTeamForm
     * Saves the team into database
     *
     * @param teamForm a team to save
     * @return TRUE,FALSE
     */
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

    /**
     * Method that receive one argument
     * The argument must be one TeamForm
     * Try to remove a team from database
     *
     * @param teamForm team to remove
     * @return TRUE,FALSE
     */
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

    /**
     * Method that dosent receive any argument
     * Gets all teams with vacations in the current month "LocalDate.Now()"
     *
     * @return List<Team> with teams with vacations
     */
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

    /**
     * Method that dosent receive any argument
     * Gets all teams without vacations in the current month "LocalDate.Now()"
     *
     * @return List<Team>
     */
    public List<Team> getTeamsWithoutVacationsThisMonth(){

        List<Team> teamList =  new ArrayList<>();

        for (Team team : getAllTeams()) {

            int cont = 0;
            for(Employee employee : team.getEmployeeList())
            {
                List<LocalDate> localDateList = vacationUtils.getDaysOfVacationByMonth(employee.getVacationList(), LocalDate.now().getMonth().getValue());
                if (localDateList.isEmpty()){

                    cont++;
                }
            }
            if(cont == team.getEmployeeList().size() )
            {
                teamList.add(team);
            }
        }

        return teamList;
    }

}
