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



/**
 * @author Utilizador
 *
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
    public String vacationForm(ModelMap model) {
		model.addAttribute("employeeList", employeeService.getAllEmployees());
		model.addAttribute("availableVacations",employeeService.getAllAvailableDaysVacations());
		model.addAttribute("unavailableVacations",employeeService.getAllUnavailableDaysVacations());
		model.addAttribute("totalVacations", vacationService.countTotalVacations());
		model.addAttribute("teams",teamService.getAllTeams());
		model.addAttribute("vacationsByteam", teamService.vacationListForAllTeams(teamService.getAllTeams()));
		return "vacation";
    }

	 @RequestMapping(value="/searchemployeevacation",method = RequestMethod.GET)
	    public String showSearchEmployeeForm(ModelMap model){
		  	model.addAttribute("employee",new Employee());
		  	return "searchemployeevacation";
	 }
	 
	 @RequestMapping(value="/addemployeevacation",method = RequestMethod.POST)
	    public ModelAndView searchEmployeeForVacationForm(Long id, Employee employee,RedirectAttributes redirectAttributes){		
	 	ModelAndView addEmployeeForm = new ModelAndView("addemployeevacation");
	 	if(id==null)
	 	{
	 		ModelAndView searchEmployeeForm = new ModelAndView("searchemployeevacation");
	 		searchEmployeeForm.addObject("employee",employee);
	 		searchEmployeeForm.addObject("message", "Employee not cannot be null!");
	 		//redirectAttributes.addFlashAttribute("message","Employee not cannot be null!" );
			return searchEmployeeForm; 
	 	}
	 	Employee employeeToAdd = employeeService.getEmployeeById(id);
	 	addEmployeeForm.addObject("employee",employeeToAdd);
	 	return addEmployeeForm;
	 } 
	 
	 
	/* @RequestMapping(value="/addemployeevacation/addvacation",method = RequestMethod.POST)
	    public  void showEmployeeToAddVacation( ModelMap model,RedirectAttributes redirectAttributes){
		 
		
	 }*/
	
}
