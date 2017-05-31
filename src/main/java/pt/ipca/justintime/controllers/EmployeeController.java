package pt.ipca.justintime.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.factorys.EmployeeFactory;
import pt.ipca.justintime.forms.EmployeeForm;
import pt.ipca.justintime.repositories.AddressRepository;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes(
        {"employeeform"})
@Controller
public class EmployeeController extends WebMvcConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private EmployeeFactory employeeFactory;
    /*
     * METHOD TO MAP AND SHOW EMPLOYEE FORM
     */
    @RequestMapping(value = "/newemployee", method = RequestMethod.GET)
    public ModelAndView employeeForm(ModelMap model) {
        ModelAndView employeeForm = new ModelAndView("employeeform");
        employeeForm.addObject("employeeform", new EmployeeForm());
        employeeForm.addObject("teamList", teamService.getAllTeams());
        return employeeForm;
    }

    @RequestMapping(value = "/newemployee", method = RequestMethod.POST)
    public ModelAndView checkNewEmployeeInfo(@Valid @ModelAttribute("employeeform") EmployeeForm employeeForm, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("employeeform");
        mav.addObject("teamList", teamService.getAllTeams());
        mav.addObject("employeeform", employeeForm);
        if (bindingResult.hasErrors()) {
            mav.addObject("errorssmsg", "Error saving employee");
            return mav;
        }
        else if( employeeService.saveEmployeeForm(employeeForm))
        {
            mav.addObject("successmsg", "Success you save employee");
            return mav;
        }
        mav.addObject("errorssmsg", "The employee already exists");
        return mav;
    }

    @RequestMapping(value = "showallemployees")
    public ModelAndView employeeList() {

        List<Employee> employeesList = employeeService.getAllEmployees();

        ModelAndView modelAndView = new ModelAndView("showallemployees","employeesList",employeesList);
        return modelAndView;
    }


    @RequestMapping(value = "/searchemployeetoedit", method = RequestMethod.GET)
    public ModelAndView showEditEmployeeForm() {
        ModelAndView modelAndView = new ModelAndView("searchemployeetoedit","employee",new EmployeeForm());
        return modelAndView;
    }

    @RequestMapping(value = "/editemployee", method = RequestMethod.POST)
    public ModelAndView showEmployeeToEditById(Long id, EmployeeForm employeeForm) {
        ModelAndView editModelAndView = new ModelAndView("editemployeeform" ,"employee", new EmployeeForm());
        ModelAndView searchModelAndView = new ModelAndView("searchemployeetoedit","employee",new EmployeeForm());
        if(id ==null)
        {
            searchModelAndView.addObject("errormessage","The id cannot be null !");
            return searchModelAndView;
        }else
        if (employeeService.getEmployeeById(id) != null) {
            editModelAndView.addObject("employee", employeeService.getEmployeeById(id));
            editModelAndView.addObject("teamList", teamService.getAllTeams());
            return editModelAndView;
        }
        searchModelAndView.addObject("errormessage", "Employee not found!");
        return searchModelAndView;
    }

    @RequestMapping(value = "/editemployee/edit",method = RequestMethod.POST)
    public String checkEditEmployeeInfo(@Valid Employee employee, BindingResult bindingResult,ModelMap model) {

        if (bindingResult.hasErrors()) {

            return "editemployeeform";
        }

        employeeService.updateEmployee(employee);
        model.addAttribute(employee);
        model.addAttribute("successmsg", "You edit successfull the employee");
        return "editemployeeform";
    }

    @RequestMapping(value = "/searchemployeetodelete" , method = RequestMethod.GET)
    public ModelAndView searchEmployeeToDelete (){

        ModelAndView modelAndView = new ModelAndView("searchemployeetodelete","employee", new EmployeeForm());
        return modelAndView;
    }

    @RequestMapping(value="/deleteemployee", method = RequestMethod.POST)
    public ModelAndView deleteEmployee (EmployeeForm employeeForm){

        ModelAndView modelAndView = new ModelAndView("searchemployeetodelete", "employee", new EmployeeForm());
        if(employeeForm.getId() == null)
        {
            modelAndView.addObject("errormessage","You must insert a valid id !");
            return modelAndView;
        }else if (employeeForm.getId()!= null)
        {
            if(employeeService.getEmployeeById(employeeForm.getId())!= null ){

                       employeeService.deleteEmployee(employeeForm);
                       modelAndView.addObject("successmessage","The employee was deleted");
                       return modelAndView;
            }
        }
           modelAndView.addObject("errormessage","The employee was not found !");
           return modelAndView;

    }

    
}
