package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;
import pt.ipca.justintime.services.VacationService;

import javax.validation.Valid;

/**
 * @author Utilizador
 */

@Controller
public class VacationController {

    @Autowired
    private VacationService vacationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/vacation", method = RequestMethod.GET)
    public ModelAndView vacationForm() {
        ModelAndView vacationForm = new ModelAndView("vacation");
        vacationForm.addObject("employeeList", employeeService.getAllEmployees());
        vacationForm.addObject("availableVacations", employeeService.getAllAvailableDaysVacations());
        vacationForm.addObject("unavailableVacations", employeeService.getAllUnavailableDaysVacations());
        vacationForm.addObject("totalVacations", vacationService.countTotalVacations());
        vacationForm.addObject("teams", teamService.getAllTeams());
        vacationForm.addObject("vacationsByteam", teamService.vacationListForAllTeams(teamService.getAllTeams()));

        return vacationForm;
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.GET)
    public ModelAndView showSearchEmployeeForm(ModelMap model) {
        ModelAndView searchemployeevacationForm = new ModelAndView("searchemployeevacation");
        searchemployeevacationForm.addObject("employee", new Employee());
        return searchemployeevacationForm;
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.POST)
    public ModelAndView searchEmployeeForVacationForm(Long id, Employee employee) {
        ModelAndView searchEmployeeForm = new ModelAndView("searchemployeevacation");
        if (id == null) {
            searchEmployeeForm.addObject("message", "Employee cannot be null!");
            return searchEmployeeForm;
          } else if (employeeService.getEmployeeById(id) != null) {
            ModelAndView addEmployeeForm = new ModelAndView("addemployeevacation");
            Employee emp = employeeService.getEmployeeById(id);
            addEmployeeForm.addObject(emp);
            return addEmployeeForm;
        }
        searchEmployeeForm.addObject("message", "Employee cannot be found!");
        return searchEmployeeForm;
    }

    @RequestMapping(value = "/addemployeevacation", method = RequestMethod.POST)
    public ModelAndView showEmployeeToAddVacation(Long id,Employee employee , BindingResult bindingResult ) {
        ModelAndView addemployeeForm = new ModelAndView("addemployeevacation");
        Employee employeeToAddVacation = employeeService.getEmployeeById(id);
        employeeToAddVacation.setVacationList(employee.getVacationList());
        addemployeeForm.addObject("addemployeevacation", employeeToAddVacation);
        if (bindingResult.hasErrors()) {
            addemployeeForm.addObject("errorsmsg", "Error saving employee");
            return addemployeeForm;
        }
        employeeService.updateEmployee(employeeToAddVacation);
        addemployeeForm.addObject("employee",employeeToAddVacation );
        addemployeeForm.addObject("successmsg", "Success you save employee");
        return addemployeeForm;
    }
}
