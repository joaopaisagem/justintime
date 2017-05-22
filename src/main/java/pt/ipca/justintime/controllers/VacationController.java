package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.forms.EmployeeVacationForm;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;
import pt.ipca.justintime.services.VacationService;
import pt.ipca.justintime.utils.VacationUtils;

import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    private VacationUtils vacationUtils;

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
    public String showSearchEmployeeForm(ModelMap model) {

        model.addAttribute("employee", new Employee());
        return "searchemployeevacation";
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.POST )
    public String searchEmployeeForVacationForm(Long id ,ModelMap model) {
        if (id == null) {
            model.addAttribute("employee",new Employee());
            model.addAttribute("message", "Employee cannot be null!");
            return "searchemployeevacation";
        } else if ( employeeService.getEmployeeById(id) != null) {
            EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();
            employeeVacationForm.setEmployee(employeeService.getEmployeeById(id));
            employeeVacationForm.setVacation(new Vacation());
            model.addAttribute( "employeeVacation",employeeVacationForm);
            return "addemployeevacation";
        }
        model.addAttribute("employee",new Employee());
        model.addAttribute("message", "Employee cannot be found!");
        return "searchemployeevacation";
    }

    @RequestMapping(value = "/addemployeevacation", method = RequestMethod.POST)
    public String showEmployeeToAddVacation( EmployeeVacationForm form, ModelMap model) {
        Employee employee = employeeService.getEmployeeById(form.getEmployee().getId());
        EmployeeVacationForm employeeForm = new EmployeeVacationForm();
        employeeForm.setEmployee(employee);

       if( !vacationUtils.checkIfTheVacationIsNull(form.getVacation()))
       {
           if (vacationUtils.checkIfVacationsAreInFuture(form.getVacation())) {
               if(vacationUtils.checkIfVacationsExist(form.getVacation(),employee.getVacationList())) {

                   model.addAttribute("employeeVacation",employeeForm);
                   model.addAttribute("errorsmsg", "The vacations you selected are already in you vacation period");
                   return "addemployeevacation";
               }else {
                   employeeForm.setEmployee( employeeService.saveEmployeeVacations(form.getVacation(), employee));
                   model.addAttribute("employeeVacation",employeeForm);
                   model.addAttribute("successmsg", "Success you save vacations in your employee");
                   return "addemployeevacation";
               }
           }else
           {
               model.addAttribute("employeeVacation",employeeForm);
               model.addAttribute("errorsmsg", "you need to insert dates in the future");
               return "addemployeevacation";
           }

       }else{
           model.addAttribute("employeeVacation",employeeForm);
           model.addAttribute("errorsmsg", "you need to insert a start date and end date");
           return "addemployeevacation";
       }
    }





}
