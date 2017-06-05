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

    /**
     * This method dosent recieve any arguments
     * Creates a ModelAndView to show vacations
     * Creates a list of employees
     * Add 4 objects to ModelAndview
     * employeeList. availablevacations , unavailablevacations , teams

     * @return view
     */
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

    /**
     * This method dosent receive any arguments
     * Creates a ModelAndView to search employee
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.GET)
    public ModelAndView showSearchEmployeeForm() {

        ModelAndView modelAndView = new ModelAndView("searchemployeevacation", "employee", new EmployeeForm());

        return modelAndView;
    }

    /**
     * This method receive one argument
     * The argument must be one employee id
     * Creates two ModelAndView (searchModelAndView ,addModelAndView)
     * checks if the id is null, if true return error message with searchModelAndView
     * if id != null checks if employee exists if true checks the number of vacations the employee have
     * if numberOfEmployeeVacations == -1  return addModelAndView with error message , this employee exceed the limit of vacations
     * else save the vacations in the employee and return addModelAndView
     * if employee dosent exists return searchModelAndView with error message
     *
     * @param id employee id to add vacations
     * @return searchModelAndView, addModelAndView
     */
    @RequestMapping(value = "/searchemployeevacation", method = RequestMethod.POST)
    public ModelAndView searchEmployeeForVacationForm(Long id) {

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
        searchModelAndView.addObject("errormessage", "Employee cannot be found!");

        return searchModelAndView;
    }

    /**
     * This method receive one argument
     * The argument must be one employeeVacationForm
     * Creates a ModelAndView viewName:addemployeevacation
     * getEmployeeById , creates new employeeVacationForm , transform employee to employeeform
     * Transform vacationForm into vacation
     * Creates a list of vacations for checks
     * Sets the employeeForm to employeevacationForm.setEmployee
     * Calls a method checkIfTheVacationIsNull( vacation )
     * if false calls method numberOfAvailableDays ( vacationlist , vacation ) to get the number of available days for the employee
     * if numberOfAvailableDays == -1 the employee cannot have more vacation days ans returns modelAndView
     * checks if the dates insert by user are in future atleaste 1 day, from localdate.now() - checkIfVacationsAreInFuture(vacation)
     * if true checkIfVacationsExist( vacation , vacationlist)
     * if checkIfVacationsExist gives true returns modelAndView with error message
     * if false saves vacation into the employee
     *
     * @param form EmployeeVacationForm
     * @return modelAndview
     */
    @RequestMapping(value = "/addemployeevacation", method = RequestMethod.POST)
    public ModelAndView showEmployeeToAddVacation(EmployeeVacationForm form) {

        ModelAndView modelAndView = new ModelAndView("addemployeevacation");
        Employee employee = employeeService.getEmployeeById(form.getEmployee().getId());
        EmployeeVacationForm employeeVacationForm = new EmployeeVacationForm();
        EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
        Vacation vacation = vacationFactory.transformVacationFormIntoVacation(form.getVacation());
        List<Vacation> vacationList = new ArrayList<>();
        vacationList.add(vacation);
        employeeVacationForm.setEmployee(employeeForm);


        if (!vacationUtils.checkIfTheVacationIsNull(vacation)) {

            if (vacationUtils.numberOfAvailableDays(employee.getVacationList(), vacation) == -1) {

                modelAndView.addObject("employeeVacation", employeeVacationForm);
                modelAndView.addObject("numberOfEmployeeVacations", "You Exceede the limit");
                modelAndView.addObject("errormessage", "You cannot add more than 22 days of vacations");

                return modelAndView;

            } else if (vacationUtils.checkIfVacationsAreInFuture(vacation)) {

                if (vacationUtils.checkIfVacationsExist(vacation, employee.getVacationList())) {

                    modelAndView.addObject("employeeVacation", employeeVacationForm);
                    modelAndView.addObject("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));
                    modelAndView.addObject("errormessage", "The vacations you selected are already in you vacation period");

                    return modelAndView;

                } else {
                    employeeVacationForm.setEmployee(employeeFactory.transformEmployeeIntoEmployeeForm(employeeService.saveEmployeeVacations(vacation, employee)));

                    modelAndView.addObject("employeeVacation", employeeVacationForm);
                    modelAndView.addObject("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));
                    modelAndView.addObject("successmessage", "Success you save vacations in your employee");

                    return modelAndView;
                }
            } else {

                modelAndView.addObject("employeeVacation", employeeVacationForm);
                modelAndView.addObject("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));
                modelAndView.addObject("errormessage", "you need to insert dates in the future");

                return modelAndView;
            }

        } else {

            modelAndView.addObject("employeeVacation", employeeVacationForm);
            modelAndView.addObject("numberOfEmployeeVacations", vacationUtils.numberOfAvailableDays(employee.getVacationList()));
            modelAndView.addObject("errormessage", "you need to insert a start date and end date");

            return modelAndView;
        }
    }

    /**
     * This method dosent receive any argument
     * Creates a ModelAndView with viewname:searchemployeetoeditvacation and model EmployeeForm
     *
     * @return modelAndView to search employee
     */
    @RequestMapping(value = "/searchemployeetoeditvacations", method = RequestMethod.GET)
    public ModelAndView searchEmployeeToEditVacations() {

        ModelAndView modelAndView = new ModelAndView("searchemployeetoeditvacation", "employee", new EmployeeForm());

        return modelAndView;
    }

    /**
     * This method recieve one argument
     * The argument must be EmployeeForm that contains the id passed in the method searchEmployeeToEditVacations
     * Creates two ModelAndView searchModelAndView viewName:searchemployeetoeditvacation and editModelAndView viewname:selectemployeevacationtoeditform
     * checks if the id passed on the argument is null
     * if true returns searchModelAndView with error message
     * if false  will check if the employee exists
     * if true  return editModelAndView else returns searchModelAndView with errormessage
     *
     * @param form EmployeeForm with id
     * @return searchModelAndView , editModelAndView
     */
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

    /**
     * This method receives an argument
     * The argument must be EmployeeVacationForm
     * Creates two ModelAndView editModelAndView viewName: editemployeevacations and modelAndView viewname:selectemployeevacationtoeditform
     * checks if the vacation id passed on the argument is null
     * if true returns modelAndview with error message
     * if false check if vacation exists
     * if true return modelAndView with error message
     * if false returns editModelAndView with the vacation to edit
     *
     * @param employeeVacationForm brings an employeeForm  and a vacationForm
     * @return modelAndView , editModelAndView
     */
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

    /**
     * This method receive the EmployeevacationForm edited
     * Creates ModelAndView viewName:"editemployeevacations"
     * try to update the employeevacations if true returns
     * if true return modelAndView with success message
     * if false returns modelAndView wirh error message
     *
     * @param employeeVacationForm employe with vacation edited
     * @return modelAndView with success message or error message
     */
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

