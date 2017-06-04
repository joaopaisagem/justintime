package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Client;
import pt.ipca.justintime.factorys.ClientFactory;
import pt.ipca.justintime.forms.ClientForm;
import pt.ipca.justintime.repositories.AddressRepository;
import pt.ipca.justintime.repositories.ClientRepository;
import pt.ipca.justintime.repositories.ContactRepository;

import java.util.List;

@Service
public class ClientService {
    //////////////////////////////////////////////////////////
    //               CRUD METHOD`S                         //
    ////////////////////////////////////////////////////////
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ClientFactory clientFactory;

    public Client saveClient(Client client) {

        addressRepository.save(client.getAddressOne());

        addressRepository.save(client.getAddressTwo());

        contactRepository.save(client.getContactList());

        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {


        return clientRepository.findOne(id);
    }

    public List<Client> getAllClients() {

        return clientRepository.findAll();

    }

    public Client updateClient(Client client) {

        addressRepository.save(client.getAddressOne());


        addressRepository.save(client.getAddressTwo());


        contactRepository.save(client.getContactList());

        return clientRepository.saveAndFlush(client);
    }

    public void deleteClient(Long id) {
        clientRepository.delete(id);
    }

    //////////////////////////////////////////////////////////
    //               Client METHOD`S                         //
    ////////////////////////////////////////////////////////


    private boolean checkIfClientExists(Client client) {

        List<Client> clientsList = clientRepository.findAll();
        for (Client client1 : clientsList) {
            if (client.equals(client1)) {
                return true;
            }
        }
        return false;
    }

    public boolean saveClientForm(ClientForm clientForm) {

        Client client = clientFactory.transformClientFormToClient(clientForm);
        if (checkIfClientExists(client)) {
            return false;
        }
        saveClient(client);
        return true;
    }

    public ClientForm editClientForm(ClientForm clientForm) {

        Client client = clientFactory.transformClientFormToClient(clientForm);

        ClientForm form = clientFactory.transformClientToClientForm(updateClient(client));

        return form;
    }


}
