package pt.ipca.justintime.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.factorys.EmployeeFactory;
import pt.ipca.justintime.forms.EmployeeForm;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;
import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */

@SessionAttributes({"employeeform"})
@Controller
public class EmployeeController extends WebMvcConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private EmployeeFactory employeeFactory;

    /**
     * This method doesnt receive any arguments
     * Is used to show the employee form to the user
     * Create a ModelAndView  with the viewname:employeeform
     * Construct a new EmployeeForm()
     * Add the employee to the model
     * Add teamlist with all teams to the model
     * and return the view
     *
     * @return the view for a new employee
     */
    @RequestMapping(value = "/newemployee", method = RequestMethod.GET)
    public ModelAndView employeeForm() {

        ModelAndView employeeForm = new ModelAndView("employeeform");
        employeeForm.addObject("employeeform", new EmployeeForm());
        employeeForm.addObject("teamList", teamService.getAllTeams());

        return employeeForm;
    }

    /**
     * This method receive two arguments
     * The first one is the employee submitted by employeeForm() method
     * The second argument is BindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Create a ModelAndView  with the  viewname: employeeform
     * The employeeform submitted must pass all validation fromt the bean EmployeeForm()
     * If the bindingResult has errors return the view with bindingResult Errors , and  error message
     * If there is no errors we save the employee and return view with a success message
     *
     * @param employeeForm employee to save
     * @param bindingResult binding errors bean EmployeeForm()
     * @return view employeeform
     */
    @RequestMapping(value = "/newemployee", method = RequestMethod.POST)
    public ModelAndView checkNewEmployeeInfo(@Valid @ModelAttribute("employeeform") EmployeeForm employeeForm, BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView("employeeform");
        mav.addObject("teamList", teamService.getAllTeams());
        mav.addObject("employeeform", employeeForm);

        if (bindingResult.hasErrors()) {

            mav.addObject("errormessage", "Error saving employee");

            return mav;
        } else if (employeeService.saveEmployeeForm(employeeForm)) {

            mav.addObject("successmessage", "Success you save employee");
            return mav;
        }
        mav.addObject("errormessage", "The employee already exists");

        return mav;
    }

    /**
     * This method doesnt recieve any arguments
     * It cereates a ModelAndView with viewName: showallemployees , modelName: employeesList , getAllEmployees()
     *
     * @return a view with all employees
     */
    @RequestMapping(value = "showallemployees")
    public ModelAndView employeeList() {

        List<Employee> employeesList = employeeService.getAllEmployees();
        ModelAndView modelAndView = new ModelAndView("showallemployees", "employeesList", employeesList);

        return modelAndView;
    }

    /**
     * This method doesnt recieve any arguments
     * Create a ModelAndView with viewName: searchemployeetoedit , modelName: employee , EmployeeForm()
     *
     * @return the view to search for employee id
     */
    @RequestMapping(value = "/searchemployeetoedit", method = RequestMethod.GET)
    public ModelAndView showEditEmployeeForm() {

        ModelAndView modelAndView = new ModelAndView("searchemployeetoedit", "employee", new EmployeeForm());

        return modelAndView;
    }

    /**
     * This method received one arguments
     * The argument is the id submitted on the employeeform by the showEditEmployeeForm method
     * Creation of two ModelAndView variables
     * The first one its editemployeeform that is used to return the edit empployee form
     * The second one its searchemployeetoedit that is used to return the search form in case of errors
     * It checks if the employee ID is null , if true returns the searchform again with error message
     * if employee ID  is difrent from null , we test if the employee exists , if true
     * we get the employee by id to the form and return the form to edit the employee
     * else we return the searchemployeetoedit with error message
     *
     * @param id Long id
     * @return view
     */
    @RequestMapping(value = "/editemployee", method = RequestMethod.POST)
    public ModelAndView showEmployeeToEditById(Long id) {

        ModelAndView editModelAndView = new ModelAndView("editemployeeform");
        ModelAndView searchModelAndView = new ModelAndView("searchemployeetoedit", "employee", new EmployeeForm());

        if (id == null) {

            searchModelAndView.addObject("errormessage", "The id cannot be null !");

            return searchModelAndView;
        } else if (employeeService.getEmployeeById(id) != null) {

            editModelAndView.addObject("employee", employeeService.getEmployeeById(id));
            editModelAndView.addObject("teamList", teamService.getAllTeams());

            return editModelAndView;
        }
        searchModelAndView.addObject("errormessage", "Employee not found!");

        return searchModelAndView;
    }

    /**
     * This method receive two arguments
     * The first one is the employee submitted by showEmployeeToEditById() method
     * The second argument is BindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * Create ModelAndView  with the  viewname: editemployeeform
     * The employee submitted must pass all validation fromt the bean EmployeeForm()
     * If the bindingResult has errors return the employee with bindingResult Errors , and  error message
     * If there is no errors we update the employee and return the view with the edited client and with a success message
     *
     * @param employeeForm employee to edit
     * @param bindingResult bean validation for EmployeeForm
     * @return view
     */
    @RequestMapping(value = "/editemployee/edit", method = RequestMethod.POST)
    public ModelAndView checkEditEmployeeInfo(@Valid EmployeeForm employeeForm, BindingResult bindingResult) {

        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        ModelAndView modelAndView = new ModelAndView("editemployeeform","employee", employeeForm);
        modelAndView.addObject("teamList", teamService.getAllTeams());

        if (bindingResult.hasErrors()) {

            modelAndView.addObject("employee", employeeForm);
            modelAndView.addObject("errormessage","There was an error on your submition! Please check all the fields");

            return modelAndView;
        }

        modelAndView.addObject("employee",employeeService.updateEmployee(employee));
        modelAndView.addObject("successmessage", "You edit successfull the employee");

        return modelAndView;
    }

    /**
     * This method doesnt recieve any arguments
     * It cereates a ModelAndView with viewName: searchemployeetodelete , modelName: employee , EmployeeForm()
     *
     * @return the view to search for employee id
     */
    @RequestMapping(value = "/searchemployeetodelete", method = RequestMethod.GET)
    public ModelAndView searchEmployeeToDelete() {

        ModelAndView modelAndView = new ModelAndView("searchemployeetodelete", "employee", new EmployeeForm());

        return modelAndView;
    }

    /**
     * This Method recieve one argument
     * The argument must be one employeeform and contains only the employee id
     * Create a view with searchemployeetodelete form
     * Check if the id is null , if true returns view with errormessage
     * if false checks if the employee exists, if true delete the employee
     * if the employee dosent exist return a view with error message
     *
     * @param employeeForm
     * @return view
     */
    @RequestMapping(value = "/deleteemployee", method = RequestMethod.POST)
    public ModelAndView deleteEmployee(EmployeeForm employeeForm) {

        ModelAndView modelAndView = new ModelAndView("searchemployeetodelete", "employee", new EmployeeForm());

        if (employeeForm.getId() == null) {

            modelAndView.addObject("errormessage", "You must insert a valid id !");
            return modelAndView;

        } else if (employeeForm.getId() != null) {

            if (employeeService.getEmployeeById(employeeForm.getId()) != null) {

                employeeService.deleteEmployee(employeeForm);
                modelAndView.addObject("successmessage", "The employee was deleted");

                return modelAndView;
            }
        }
        modelAndView.addObject("errormessage", "The employee was not found !");

        return modelAndView;

    }

}
