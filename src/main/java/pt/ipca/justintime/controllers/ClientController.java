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
        ModelAndView clientform = new ModelAndView("clientform");
        if (bindingResult.hasErrors()) {
            clientform.addObject("clientform",clientForm);
            clientform.addObject("errorsmsg", "Error saving Client");
            return clientform;
        }else if ( clientService.saveClientForm(clientForm)){
            clientform.addObject("successmsg", "Success you save client");
            return clientform;
        }
        clientform.addObject("errorsmsg", "The client already exists");
        return clientform;
    }




}
