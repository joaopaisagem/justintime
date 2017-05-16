package pt.ipca.justintime.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.services.EmployeeService;
import pt.ipca.justintime.services.TeamService;
import pt.ipca.justintime.services.VacationService;



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
		vacationForm.addObject("availableVacations",employeeService.getAllAvailableDaysVacations());
		vacationForm.addObject("unavailableVacations",employeeService.getAllUnavailableDaysVacations());
		vacationForm.addObject("totalVacations", vacationService.countTotalVacations());
		vacationForm.addObject("teams",teamService.getAllTeams());
		vacationForm.addObject("vacationsByteam", teamService.vacationListForAllTeams(teamService.getAllTeams()));
		return vacationForm;
    }

	 @RequestMapping(value="/searchemployeevacation",method = RequestMethod.GET)
	    public ModelAndView showSearchEmployeeForm(){
		 	ModelAndView searchemployeeform = new ModelAndView("searchemployeevacation");
		 	searchemployeeform.addObject("employee",new Employee());
		  	return searchemployeeform;
   }
	 
	 @RequestMapping(value="/addemployeevacation",method = RequestMethod.POST)
	    public  String showEmployeeToAddVacation( Long id, ModelMap model,RedirectAttributes redirectAttributes){		 
		 if(id == null)
		 {
			 redirectAttributes.addFlashAttribute("message", "Employee id cannot be null!");
			 return "redirect:/searchemployeevacation"; 
		 }
		 if ( employeeService.getEmployeeById(id) != null) {
			 model.addAttribute("employee", employeeService.getEmployeeById(id));
		        return "addemployeevacation";

		    }
		 	redirectAttributes.addFlashAttribute("message", "Employee not found!");
	        return "redirect:/searchemployeevacation";
	    }  
	
}
