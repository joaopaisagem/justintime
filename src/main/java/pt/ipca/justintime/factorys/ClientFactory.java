package pt.ipca.justintime.factorys;

import org.springframework.stereotype.Component;
import pt.ipca.justintime.domain.Client;
import pt.ipca.justintime.forms.ClientForm;

/**
 * Created by Tiago Silva on 28/05/2017.
 */
@Component
public class ClientFactory {

    public Client transformClientFormToClient(ClientForm clientForm) {
        Client client = new Client();
        client.setId(clientForm.getId());
        client.setFirstName(clientForm.getFirstName());
        client.setLastName(clientForm.getLastName());
        client.setEmail(clientForm.getEmail());
        client.setNif(clientForm.getNif());
        client.setGender(clientForm.getGender());
        client.setAddressOne(clientForm.getAddressOne());
        client.setAddressTwo(clientForm.getAddressTwo());
        client.setContactList(clientForm.getContactList());
        client.setProjectList(clientForm.getProjectList());
        return client;
    }

    public ClientForm transformClientToClientForm(Client client) {
        ClientForm clientForm = new ClientForm();
        clientForm.setId(client.getId());
        clientForm.setFirstName(client.getFirstName());
        clientForm.setLastName(client.getLastName());
        clientForm.setEmail(client.getEmail());
        clientForm.setNif(client.getNif());
        clientForm.setGender(client.getGender());
        clientForm.setAddressOne(client.getAddressOne());
        clientForm.setAddressTwo(client.getAddressTwo());
        clientForm.setContactList(client.getContactList());
        clientForm.setProjectList(client.getProjectList());
        return clientForm;
    }


}
