package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Team;
import pt.ipca.justintime.repositories.TeamRepository;
import pt.ipca.justintime.utils.VacationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private VacationUtils vacationUtils;

    //////////////////////////////////////////////////////////
    //               CRUD METHOD`S                         //
    ////////////////////////////////////////////////////////
    public Team saveTeam(Team team) {
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
    //////////////////////////////////////////////////////////
    //                    Team method´s                    //
    ////////////////////////////////////////////////////////

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

    /**
     * Returns a local date list with the days of vacations for the chosen Team.
     * The arguments must specify a team id .
     * Argument name are specifier that is relative to id.
     * This method always returns a List of days for chosen team
     * We use a for each loop to go through the employee list and get the vacation list for each employee of the team
     * then for each date we check if the date is for current year if yes we add to a list
     *
     * @param id of one team
     * @return list of days for that team
     */
    public List<LocalDate> vacationListForTeam(Long id) {
        List<LocalDate> employeeVacation = new ArrayList<>();
        List<LocalDate> listToReturn = new ArrayList<>();
        Team teamToSearch = getTeamById(id);
        List<Employee> employeeList = teamToSearch.getEmployeeList();
        for (Employee employee : employeeList) {
            employeeVacation = vacationUtils.getDaysBetweenDates(employee.getVacationList());
            for (LocalDate date : employeeVacation) {
                if (vacationUtils.getDatesForCurrentYear(date) == true) {
                    listToReturn.add(date);
                }
            }
        }
        return listToReturn;
    }

    /**
     *
     */
    public List<LocalDate> vacationListForAllTeams(List<Team> teamList) {
        List<LocalDate> employeeVacation = new ArrayList<>();
        List<LocalDate> listToReturn = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        for (Team team : teamList) {
            employeeList = team.getEmployeeList();
            for (Employee employee : employeeList) {
                employeeVacation = vacationUtils.getDaysBetweenDates(employee.getVacationList());
                for (LocalDate date : employeeVacation) {
                    if (vacationUtils.getDatesForCurrentYear(date) == true) {
                        listToReturn.add(date);
                    }
                }
            }

        }

        return listToReturn;
    }
}
