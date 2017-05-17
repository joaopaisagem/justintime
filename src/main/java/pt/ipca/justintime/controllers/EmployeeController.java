package pt.ipca.justintime.controllers;



import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;

@SessionAttributes(
{ "employeeform" })
@Controller
public class EmployeeController extends WebMvcConfigurerAdapter {
	   
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TeamService teamService;
	
	/*
	 * METHOD TO MAP AND SHOW EMPLOYEE FORM
	 */
	@RequestMapping(value = "/newemployee", method = RequestMethod.GET)
    public ModelAndView employeeForm(ModelMap model) {
		ModelAndView employeeForm = new ModelAndView("employeeform");
		employeeForm.addObject("employee", new Employee());
		employeeForm.addObject("teamList",teamService.getAllTeams());
        return employeeForm;
    }

	@RequestMapping(value="/newemployee",method = RequestMethod.POST)
	public ModelAndView checkNewEmployeeInfo(@Valid @ModelAttribute("employee")Employee employee,BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView("employeeform");
		mav.addObject("teamList", teamService.getAllTeams());
		mav.addObject("employeeform",employee);
		if (bindingResult.hasErrors()) {
			mav.addObject("errorsmsg","Error saving employee");
			return mav;
		}
		employeeService.saveEmployee(employee);
		mav.addObject("successmsg","Success you save employee");
		return mav;
	}
	
	 @RequestMapping(value = "showallemployees")
	    public String employeeList(ModelMap model){
	       List<Employee> employeeList =  employeeService.getAllEmployees();
		 	model.put("employee", employeeList);
	        return "showallemployees";
	    } 
	 
	 
	 @RequestMapping(value="/editemployee",method = RequestMethod.GET)
	    public String showEditEmployeeForm(ModelMap model){
		  	model.addAttribute("employee",new Employee());
		  	return "edit";
    }
	 
	 @RequestMapping(value="/editemployee",method = RequestMethod.POST)
	    public  String showEmployeeToEditById( Long id, ModelMap model,RedirectAttributes redirectAttributes){		 	 
		 if ( employeeService.getEmployeeById(id) != null) {
			 model.addAttribute("employee", employeeService.getEmployeeById(id));
		     model.addAttribute("teamList", teamService.getAllTeams());
		        return "editemployeeform";

		    }
		 	redirectAttributes.addFlashAttribute("message", "Employee not found!");
	        return "redirect:/editemployee";
	    } 
	 
	 @PostMapping("/editemployee/edit")
		public String checkEditEmployeeInfo(@Valid Employee employee, BindingResult bindingResult) {

			if (bindingResult.hasErrors()) {
				return "editemployeeform";
			}
			
			employeeService.updateEmployee(employee);
			return "redirect:/results";
		}
	 @RequestMapping(value="/searchemployeetoaddvacation",method = RequestMethod.GET)
	    public String addEmployeeVacation(ModelMap model){
		  	model.addAttribute("employee",new Employee());
		  	return "addemployeevacation";
 }
	  
	  
}
