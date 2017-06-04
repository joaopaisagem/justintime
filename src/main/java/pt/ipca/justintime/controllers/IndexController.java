package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.services.EmployeeService;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Controller
public class IndexController extends WebMvcConfigurerAdapter {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView projectForm() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("totalEmployees", employeeService.getNumberOfTotalEmployees());
        view.addObject("vacationToCurrentMonth",employeeService.getPercentageOfEmployeesWithVacationInCurrentMonth());
        return view;
    }
}
