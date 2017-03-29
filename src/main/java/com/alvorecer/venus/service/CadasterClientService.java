package com.alvorecer.venus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.repository.Clients;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;

@Service
public class CadasterClientService {

	@Autowired
	private Clients clients;

	@Transactional
	public Client save(Client client) {
		Optional<Client> clientOptional = clients.findBycpfOuCnpj(client.getCpfOuCnpj());
		if (clientOptional.isPresent()) {
			throw new ClientJaCadastradoExcepition("Cliente já cadastrado");
		}

		return clients.saveAndFlush(client);
	}

	public Client getClient(Client client) {
		Optional<Client> clientOptional = clients.findBycpfOuCnpj(client.getCpfOuCnpj());

		if (clientOptional.isPresent()) {
			return clientOptional.get();
		}
		return client;
	}
}
