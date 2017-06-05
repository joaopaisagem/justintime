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
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ClientFactory clientFactory;

    /**
     * This method receive one argument
     * The argument must be a Client
     * Saves the client into database
     *
     * @param client
     * @return client saved
     */
    public Client saveClient(Client client) {

        addressRepository.save(client.getAddressOne());
        addressRepository.save(client.getAddressTwo());
        contactRepository.save(client.getContactList());

        return clientRepository.save(client);
    }

    /**
     * This method receive one argument
     * The argument must be a Long id
     * Search the client by id in database
     *
     * @param id client id
     * @return client
     */
    public Client getClientById(Long id) {

        return clientRepository.findOne(id);
    }

    /**
     * This method dosent receive any argumet
     * Search for all clients in database and return a list of clients
     *
     * @return List<Client>
     */
    public List<Client> getAllClients() {

        return clientRepository.findAll();
    }

    /**
     * This method receive one argument
     * The argument must be a client
     * Updates the client in database
     *
     * @param client to update
     * @return client updated
     */
    public Client updateClient(Client client) {

        addressRepository.save(client.getAddressOne());
        addressRepository.save(client.getAddressTwo());
        contactRepository.save(client.getContactList());
        return clientRepository.saveAndFlush(client);
    }

    /**
     * This method receive one argument
     * The argument must be a Long id
     * The id must be from a client
     * deletes the client by id from database
     * dosent have a return type
     *
     * @param id client id
     */
    public void deleteClient(Long id) {

        clientRepository.delete(id);
    }

    /**
     * This method receive one argument
     * The argument must be related to a Client
     * Uses a for each to check if the client exists
     *
     * @param client to check
     * @return TRUE, FALSE
     */
    private boolean checkIfClientExists(Client client) {

        List<Client> clientsList = clientRepository.findAll();

        for (Client client1 : clientsList) {

            if (client.equals(client1)) {

                return true;
            }
        }

        return false;
    }

    /**
     * This method receive one argument
     * The argument must be related to ClientForm
     * Try to save the client form into database
     *
     * @param clientForm to save
     * @return TRUE , FALSE
     */
    public boolean saveClientForm(ClientForm clientForm) {

        Client client = clientFactory.transformClientFormToClient(clientForm);

        if (checkIfClientExists(client)) {
            return false;
        }
        saveClient(client);
        return true;
    }

    /**
     * This method receive one argument
     * The argument must be related to a clientForm
     * Edit a client from database
     *
     * @param clientForm client to edit
     * @return ClientForm edited client
     */
    public ClientForm editClientForm(ClientForm clientForm) {

        Client client = clientFactory.transformClientFormToClient(clientForm);
        ClientForm form = clientFactory.transformClientToClientForm(updateClient(client));

        return form;
    }

}
