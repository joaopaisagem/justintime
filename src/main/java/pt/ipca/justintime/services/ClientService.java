package pt.ipca.justintime.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ipca.justintime.domain.Client;
import pt.ipca.justintime.repositories.ClientRepository;

@Service
public class ClientService {
@Autowired	
private ClientRepository clientRepository; 
	
	public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
	
	public Client getClientById(Long id){	
		return clientRepository.findOne(id);
	}
	
	public List<Client> getAllClients(){
		return clientRepository.findAll();
		
	}
	
	public void updateCliente(Client client)
	{
		clientRepository.save(client);
	}

}
