package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.factorys.EmployeeFactory;
import pt.ipca.justintime.factorys.VacationFactory;
import pt.ipca.justintime.forms.EmployeeForm;
import pt.ipca.justintime.forms.EmployeeVacationForm;
import pt.ipca.justintime.forms.VacationForm;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;
import pt.ipca.justintime.services.VacationService;
import pt.ipca.justintime.utils.VacationUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Utilizador
 */

@Controller
public class VacationController extends WebMvcConfigurerAdapter {

    @Autowired
    private VacationService vacationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private VacationUtils vacationUtils;
    @Autowired
    private EmployeeFactory employeeFactory;
    @Autowired
    private VacationFactory vacationFactory;

    @RequestMapping(value = "/vacation", method = RequestMethod.GET)
    public ModelAndView vacationForm() {

        ModelAndView vacationForm = new ModelAndView("vacation");
        List<Employee> employeeList = employeeService.getAllEmployees();

        vacationForm.addObject("employeeList",employeeList );

        vacationForm.addObject("availableVacations", employeeService.getAllAvailableDaysVacations(employeeList));

        vacationForm.addObject("unavailableVacations", employeeService.getAllUnavailableDaysVacations(employeeList));

        vacationForm.addObject("totalEmployees",employeeService.getNumberOfTotalEmployees());

        vacationForm.addObject("teams", teamService.getAllTeams());

        return vacationForm;
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.GET)
    public String showSearchEmployeeForm(ModelMap model) {

        model.addAttribute("employee", new EmployeeForm());
        return "searchemployeevacation";
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.POST)
    public String searchEmployeeForVacationForm(Long id, ModelMap model , RedirectAttributes redirectAttributes) {


        if (id == null) {

            model.addAttribute("employee", new EmployeeForm());

            model.addAttribute("message", "Employee cannot be null!");

            return "searchemployeevacation";

        } else if (employeeService.getEmployeeById(id) != null) {

            Employee employee = employeeService.getEmployeeById(id);

            int numberOfEmployeeVacations = vacationUtils.numberOfAvailableDays(employee.getVacationList());

            EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();

            if (numberOfEmployeeVacations == -1) {

                employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employee));

                employeeVacationForm.setVacation(new VacationForm());

                model.addAttribute("message", "The employee cannot have more vacation days");

                model.addAttribute("numberOfEmployeeVacations", "0");

                model.addAttribute("employeeVacation", employeeVacationForm);

                return "addemployeevacation";

            } else {

                employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employee));

                employeeVacationForm.setVacation(new VacationForm());

                model.addAttribute("numberOfEmployeeVacations", numberOfEmployeeVacations);

                model.addAttribute("employeeVacation", employeeVacationForm);

                return "addemployeevacation";
            }
        }
        model.addAttribute("employee", new EmployeeForm());

        model.addAttribute("message", "Employee cannot be found!");

        return "searchemployeevacation";
    }

    @RequestMapping(value = "/addemployeevacation", method = RequestMethod.POST)
    public String showEmployeeToAddVacation(EmployeeVacationForm form, ModelMap model) {

        Employee employee = employeeService.getEmployeeById(form.getEmployee().getId());
        EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();
        EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
        Vacation vacation = vacationFactory.transformVacationFormIntoVacation(form.getVacation());
        List<Vacation> vacationList = new ArrayList<>();
        vacationList.add(vacation);

        employeeVacationForm.setEmployee(employeeForm);


        if (!vacationUtils.checkIfTheVacationIsNull(vacation)) {

            if (vacationUtils.numberOfAvailableDays(employee.getVacationList(),vacation) == -1) {

                        model.addAttribute("employeeVacation", employeeVacationForm);

                        model.addAttribute("numberOfEmployeeVacations", "You Exceede the limit");

                        model.addAttribute("errorsmsg", "You cannot add more than 22 days of vacations");

                        return "addemployeevacation";

                    } else if(vacationUtils.checkIfVacationsAreInFuture(vacation)) {

                        if (vacationUtils.checkIfVacationsExist(vacation, employee.getVacationList())) {

                            model.addAttribute("employeeVacation", employeeVacationForm);

                            model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                            model.addAttribute("errorsmsg", "The vacations you selected are already in you vacation period");

                            return "addemployeevacation";

                    } else {
                            employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employeeService.saveEmployeeVacations(vacation, employee)));

                            model.addAttribute("employeeVacation", employeeVacationForm);

                            model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                            model.addAttribute("successmsg", "Success you save vacations in your employee");

                            return "addemployeevacation";

                    }
                } else {
                    model.addAttribute("employeeVacation", employeeVacationForm);

                    model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                    model.addAttribute("errorsmsg", "you need to insert dates in the future");

                    return "addemployeevacation";
                }

            } else {
                model.addAttribute("employeeVacation", employeeVacationForm);

                model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                model.addAttribute("errorsmsg", "you need to insert a start date and end date");

                return "addemployeevacation";
            }

        }
    }

