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


    /**
     * This method dosen´t receive any arguments
     * Is used to show the client form to the user
     * It created a ModelAndView  with the viewname:clienform
     * We construct a new ClientForm() and return the view
     *
     * @return the view for a new client
     */
    @RequestMapping(value = "/newclient", method = RequestMethod.GET)
    public ModelAndView clientForm() {

        ModelAndView clientForm = new ModelAndView("clientform");
        clientForm.addObject("clientform", new ClientForm());

        return clientForm;
    }

    /**
     * This method received two arguments
     * The first one is the client submited on the clientform method
     * The second argument is BindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * It created a ModelAndView  with the  viewname: clientform
     * The client submitted must pass all validation fromt the bean ClientForm()
     * If the bindingResult has errors return the clientform with bindingResult Errors , and an error message
     * If there is no errors we try to save the client , if case of success we return the clientform with the client saved and with a success message
     * in case of this client exists we return the clientform with error message
     *
     * @param clientForm the new client to be saved
     * @param bindingResult represents binding results from the model ClientForm()
     * @return view with clientform
     */
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

    /**
     * This method dosen´t recieve any arguments
     * It cereates a ModelAndView with viewName:showallclients , modelName: clientsList , getAllClients()
     *
     * @return a view with all clients
     */
    @RequestMapping(value = "/showallclients", method = RequestMethod.GET)
    public ModelAndView clientsList() {

        ModelAndView showAllClients = new ModelAndView("showallclients", "clientsList", clientService.getAllClients());

        return showAllClients;
    }

    /**
     * This method dosen´t recieve any arguments
     * It cereates a ModelAndView with viewName: searchclienttoedit , modelName: client , ClientForm()
     *
     * @return the view to search for a client id
     */
    @RequestMapping(value = "/searchclienttoedit", method = RequestMethod.GET)
    public ModelAndView showSearchClientForm() {

        ModelAndView editClient = new ModelAndView("searchclienttoedit", "client", new ClientForm());

        return editClient;
    }

    /**This method received one arguments
     * The argument is the id submited on the clientform by the showSearchClientForm method
     * Its created two ModelAndView variables
     * The first one its searchModelAndView that is used to return the searchform again in case of errors
     * The second one its editModelAndView that is used to return the client to edit
     * It checks if the clientform ID is null , if true returns the searchform again with error message
     * if clientform ID  is difrent from null , we test if the client exists , if true
     * we get the client by id to the form and return the form to edit the client
     * else we return the searchclientform with error message
     *
     * @param clientForm it brings only the id to search the client
     * @return searchModelAndView , editModelAndView
     */
    @RequestMapping(value = "/editclient", method = RequestMethod.GET)
    public ModelAndView showEditClientForm(@Valid @ModelAttribute("editclientform") ClientForm clientForm) {

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

    /**
     * This method receive two arguments
     * The first one is the client submited by showEditClientForm() method
     * The second argument is BindingResult
     * The BindingResult is a general interface that represents binding results.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * Serves as result holder for a DataBinder, obtained via the DataBinder.getBindingResult() method.
     * BindingResult implementations can also be used directly, for example to invoke a Validator on it (e.g. as part of a unit test).
     * It creats a ModelAndView  with the  viewname: editclientform
     * The client submitted must pass all validation fromt the bean ClientForm()
     * If the bindingResult has errors return the clientform with bindingResult Errors , and  error message
     * If there is no errors we update the client and return the clientform with the client updated and with a success message
     *
     * @param clientForm the edited client
     * @param bindingResult the validation from bean ClientForm()
     * @return modelAndView: editclientform
     */
    @RequestMapping(value = "/editclient", method = RequestMethod.POST)
    public ModelAndView editClientForm(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("editclientform");
        if (bindingResult.hasErrors()) {

            modelAndView.addObject("client", clientForm);
            modelAndView.addObject("errormessage", "Please verify all the fields !");

            return modelAndView;
        } else
            modelAndView.addObject("client", clientService.editClientForm(clientForm));
            modelAndView.addObject("successmessage", "The client was edited !");

            return modelAndView;
    }

    /**
     * This method dosen´t recieve any arguments
     * It cereates a ModelAndView with viewName: searchclienttodelete , modelName: client , ClientForm()
     *
     * @return modelAndView to search the client by id
     */
    @RequestMapping(value = "/searchclienttodelete", method = RequestMethod.GET)
    public ModelAndView searchClientToDelete() {

        ModelAndView modelAndView = new ModelAndView("searchclienttodelete", "client", new ClientForm());

        return modelAndView;
    }

    /**
     * This method receive one argument
     * The argument is a clientForm and brings only the id of the client to delete
     * it creates a ModelandView "searchclienttodelete"
     * if the id is null we return a error message with the view to searchclienttodelete
     * otherwise it checks if the client exists , if true  delete the client and return success message
     * if false return a error message and the view to searchclienttodelete
     *
     * @param clientForm id from the client to delete
     * @return modelAndView searchclienttodelete
     */
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
