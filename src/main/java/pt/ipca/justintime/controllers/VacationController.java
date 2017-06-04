package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
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

        vacationForm.addObject("employeeList", employeeList);

        vacationForm.addObject("availableVacations", employeeService.getAllAvailableDaysVacations(employeeList));

        vacationForm.addObject("unavailableVacations", employeeService.getAllUnavailableDaysVacations(employeeList));

        vacationForm.addObject("teams", teamService.getAllTeams());

        return vacationForm;
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.GET)
    public ModelAndView showSearchEmployeeForm() {

        ModelAndView modelAndView = new ModelAndView("searchemployeevacation", "employee", new EmployeeForm());
        return modelAndView;
    }

    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.POST)
    public ModelAndView searchEmployeeForVacationForm(Long id, EmployeeForm employeeForm, RedirectAttributes redirectAttributes) {

        ModelAndView searchModelAndView = new ModelAndView("searchemployeevacation", "employee", new EmployeeForm());
        ModelAndView addModelAndView = new ModelAndView("addemployeevacation", "employee", new EmployeeForm());

        if (id == null) {

            searchModelAndView.addObject("errormessage", "Employee cannot be null!");

            return searchModelAndView;

        } else if (employeeService.getEmployeeById(id) != null) {

            Employee employee = employeeService.getEmployeeById(id);

            int numberOfEmployeeVacations = vacationUtils.numberOfAvailableDays(employee.getVacationList());

            EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();

            if (numberOfEmployeeVacations == -1) {

                employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employee));

                employeeVacationForm.setVacation(new VacationForm());

                addModelAndView.addObject("errormessage", "The employee cannot have more vacation days");

                addModelAndView.addObject("numberOfEmployeeVacations", "0");

                addModelAndView.addObject("employeeVacation", employeeVacationForm);

                return addModelAndView;

            } else {

                employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employee));

                employeeVacationForm.setVacation(new VacationForm());

                addModelAndView.addObject("numberOfEmployeeVacations", numberOfEmployeeVacations);

                addModelAndView.addObject("employeeVacation", employeeVacationForm);

                return addModelAndView;
            }
        }
        //addModelAndView.addObject("employee", new EmployeeForm());

        searchModelAndView.addObject("errormessage", "Employee cannot be found!");

        return searchModelAndView;
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

            if (vacationUtils.numberOfAvailableDays(employee.getVacationList(), vacation) == -1) {

                model.addAttribute("employeeVacation", employeeVacationForm);

                model.addAttribute("numberOfEmployeeVacations", "You Exceede the limit");

                model.addAttribute("errormessage", "You cannot add more than 22 days of vacations");

                return "addemployeevacation";

            } else if (vacationUtils.checkIfVacationsAreInFuture(vacation)) {

                if (vacationUtils.checkIfVacationsExist(vacation, employee.getVacationList())) {

                    model.addAttribute("employeeVacation", employeeVacationForm);

                    model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                    model.addAttribute("errormessage", "The vacations you selected are already in you vacation period");

                    return "addemployeevacation";

                } else {
                    employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employeeService.saveEmployeeVacations(vacation, employee)));

                    model.addAttribute("employeeVacation", employeeVacationForm);

                    model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                    model.addAttribute("successmessage", "Success you save vacations in your employee");

                    return "addemployeevacation";

                }
            } else {
                model.addAttribute("employeeVacation", employeeVacationForm);

                model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

                model.addAttribute("errormessage", "you need to insert dates in the future");

                return "addemployeevacation";
            }

        } else {
            model.addAttribute("employeeVacation", employeeVacationForm);

            model.addAttribute("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));

            model.addAttribute("errormessage", "you need to insert a start date and end date");

            return "addemployeevacation";
        }

    }

    @RequestMapping(value = "/searchemployeetoeditvacations", method = RequestMethod.GET)
    public ModelAndView searchEmployeeToEditVacations() {

        ModelAndView modelAndView = new ModelAndView("searchemployeetoeditvacation", "employee", new EmployeeForm());
        return modelAndView;

    }

    @RequestMapping(value = "/searchemployeetoeditvacations", method = RequestMethod.POST)
    public ModelAndView editEmployeeVacations(EmployeeForm form) {

        ModelAndView searchModelAndView = new ModelAndView("searchemployeetoeditvacation", "employee", form);
        ModelAndView editModelAndView = new ModelAndView("selectemployeevacationtoeditform");
        if (form.getId() == null) {
            searchModelAndView.addObject("errormessage", "You need to insert a valid Id");
            return searchModelAndView;
        } else if (form.getId() != null) {
            if (employeeService.getEmployeeById(form.getId()) != null) {
                EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();
                employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employeeService.getEmployeeById(form.getId())));
                employeeVacationForm.setVacation(new VacationForm());
                editModelAndView.addObject("employeeVacationForm", employeeVacationForm);
                return editModelAndView;
            }
        }
        searchModelAndView.addObject("errormessage", "The employee dosen´t exist");
        return searchModelAndView;

    }

    @RequestMapping(value = "/editemployeevacation", method = RequestMethod.GET)
    public ModelAndView changeEmployeeVacations(EmployeeVacationForm employeeVacationForm) {
        ModelAndView modelAndView = new ModelAndView("selectemployeevacationtoeditform", "employeeVacationForm", employeeVacationForm);
        ModelAndView editModelAndView = new ModelAndView("editemployeevacations");
        EmployeeVacationForm vacationsToEdit = new EmployeeVacationForm();
        Employee employee = employeeService.getEmployeeById(employeeVacationForm.getEmployee().getId());
        EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
        employeeVacationForm.setEmployee(employeeForm);
        if (employeeVacationForm.getVacation().getId() == null) {
            modelAndView.addObject("errormessage", "the vacation id cannot be null !");
            return modelAndView;

        } else if (employeeVacationForm.getVacation().getId() != null) {

            if (employeeService.checkIfVacationExistOnTheEmployee(employeeVacationForm)) {


                Vacation vacation = vacationService.getVacationById(employeeVacationForm.getVacation().getId());

                VacationForm vacationForm = vacationFactory.transformVacationIntoVacationForm(vacation);

                vacationsToEdit.setEmployee(employeeForm);

                vacationsToEdit.setVacation(vacationForm);

                editModelAndView.addObject("employeeVacation", vacationsToEdit);
                return editModelAndView;
            }

        }
        modelAndView.addObject("errormessage", "the vacation dosent exist on the employee!");

        return modelAndView;

    }

    @RequestMapping(value = "/editemployeevacation", method = RequestMethod.POST)
    public ModelAndView saveEmployeeVacations(EmployeeVacationForm employeeVacationForm) {
        ModelAndView modelAndView = new ModelAndView("editemployeevacations", "employeeVacation", employeeVacationForm);

        if (vacationService.updateEmployeeVacations(employeeVacationForm)) {
            Employee employee = employeeService.getEmployeeById(employeeVacationForm.getEmployee().getId());
            EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
            employeeVacationForm.setEmployee(employeeForm);
            modelAndView.addObject("successmessage", "The Vacation was edited successfully");
            return modelAndView;
        } else {
            Employee employee = employeeService.getEmployeeById(employeeVacationForm.getEmployee().getId());
            EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
            employeeVacationForm.setEmployee(employeeForm);
            modelAndView.addObject("errormessage", "It was not possible to edit the vacations! Please try again");
            return modelAndView;

        }
    }
}

