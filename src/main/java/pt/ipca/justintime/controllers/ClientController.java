package pt.ipca.justintime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pt.ipca.justintime.forms.ClientForm;
import pt.ipca.justintime.services.ClientService;

import javax.validation.Valid;

/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */

@Controller
public class ClientController extends WebMvcConfigurerAdapter {
    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/newclient", method = RequestMethod.GET)
    public ModelAndView clientForm() {
        ModelAndView clientForm = new ModelAndView("clientform");
        clientForm.addObject("clientform", new ClientForm());
        return clientForm;
    }

    @RequestMapping(value = "/newclient", method = RequestMethod.POST)
    public ModelAndView checkNewClientInfo(@Valid @ModelAttribute("clientform") ClientForm clientForm, BindingResult bindingResult) {
        ModelAndView cliForm = new ModelAndView("clientform");
        if (bindingResult.hasErrors()) {
            cliForm.addObject("clientform", clientForm);
            cliForm.addObject("errormessage", "Error saving Client");
            return cliForm;
        } else if (clientService.saveClientForm(clientForm)) {
            cliForm.addObject("successmessage", "Success you save client");
            return cliForm;
        }
        cliForm.addObject("errormessage", "The client already exists");
        return cliForm;
    }

    @RequestMapping(value = "/showallclients", method = RequestMethod.GET)
    public ModelAndView clientsList() {

        ModelAndView showAllClients = new ModelAndView("showallclients", "clientsList", clientService.getAllClients());
        return showAllClients;
    }

    @RequestMapping(value = "/searchclienttoedit", method = RequestMethod.GET)
    public ModelAndView showSearchClientForm() {
        ModelAndView editClient = new ModelAndView("searchclienttoedit", "client", new ClientForm());
        return editClient;
    }

    @RequestMapping(value = "/editclient", method = RequestMethod.GET)
    public ModelAndView showEditClientForm(@Valid @ModelAttribute("editclientform") ClientForm clientForm, BindingResult bindingResult) {
        ModelAndView searchModelAndView = new ModelAndView("searchclienttoedit", "client", new ClientForm());
        ModelAndView editModelAndView = new ModelAndView("editclientform", "client", new ClientForm());
        if (clientForm.getId() == null) {
            searchModelAndView.addObject("errormessage", "The id cannot be null !");
            return searchModelAndView;
        } else if (clientService.getClientById(clientForm.getId()) != null) {
            editModelAndView.addObject(clientService.getClientById(clientForm.getId()));
            return editModelAndView;
        }
        searchModelAndView.addObject("errormessage", "The client could not be found!");
        return searchModelAndView;
    }

    @RequestMapping(value = "/editclient", method = RequestMethod.POST)
    public ModelAndView editClientForm(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("editclientform");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("client", clientForm);
            modelAndView.addObject("errormessage", "Please verify all the fields !");
        } else
            modelAndView.addObject("client", clientService.editClientForm(clientForm));
        modelAndView.addObject("successmessage", "The client was edited !");
        return modelAndView;

    }

    @RequestMapping(value = "/searchclienttodelete", method = RequestMethod.GET)
    public ModelAndView searchClientToDelete() {

        ModelAndView modelAndView = new ModelAndView("searchclienttodelete", "client", new ClientForm());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteclient", method = RequestMethod.POST)
    public ModelAndView deleteEmployee(ClientForm clientForm) {

        ModelAndView modelAndView = new ModelAndView("searchclienttodelete", "client", new ClientForm());
        if (clientForm.getId() == null) {
            modelAndView.addObject("errormessage", "You must insert a valid id !");
            return modelAndView;
        } else if (clientForm.getId() != null) {
            if (clientService.getClientById(clientForm.getId()) != null) {

                clientService.deleteClient(clientForm.getId());
                modelAndView.addObject("successmessage", "The Client was deleted");
                return modelAndView;
            }
        }
        modelAndView.addObject("errormessage", "The Client was not found !");
        return modelAndView;

    }


}
