package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.domain.Client;
import pt.ipca.justintime.forms.ClientForm;
import pt.ipca.justintime.services.ClientService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Shapeshifter on 28/05/2017.
 */
@Controller
public class ClientController extends WebMvcConfigurerAdapter {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/newclient", method = RequestMethod.GET)
    public ModelAndView clientForm(ModelMap model) {
        ModelAndView clientForm = new ModelAndView("clientform");
        clientForm.addObject("clientform", new ClientForm());
        return clientForm;
    }

    @RequestMapping(value = "/newclient", method = RequestMethod.POST)
    public ModelAndView checkNewClientInfo(@Valid @ModelAttribute("clientform") ClientForm clientForm, BindingResult bindingResult) {
        ModelAndView cliForm = new ModelAndView("clientform");
        if (bindingResult.hasErrors()) {
            cliForm.addObject("clientform",clientForm);
            cliForm.addObject("errorsmsg", "Error saving Client");
            return cliForm;
        }else if ( clientService.saveClientForm(clientForm)){
            cliForm.addObject("successmsg", "Success you save client");
            return cliForm;
        }
        cliForm.addObject("errorsmsg", "The client already exists");
        return cliForm;
    }

    @RequestMapping(value = "/showallclients", method = RequestMethod.GET)
    public ModelAndView clientsList() {

       ModelAndView showAllClients = new ModelAndView("showallclients","clientsList", clientService.getAllClients());
       return showAllClients;
    }

    @RequestMapping(value = "/editclient", method = RequestMethod.GET)
    public ModelAndView showEditEmployeeForm() {
        ModelAndView editClient = new ModelAndView("editclient","clientForm",new ClientForm());
        return editClient;
    }


}
