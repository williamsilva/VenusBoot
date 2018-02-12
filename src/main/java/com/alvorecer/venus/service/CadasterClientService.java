package com.alvorecer.venus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.repository.Clients;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;
import com.alvorecer.venus.service.excepition.RegistroObrigatorioException;
import com.google.common.base.Strings;

@Service
public class CadasterClientService {

	@Autowired
	private Clients clients;

	@Transactional
	public Client save(Client client) {
		Optional<Client> clientOptional = clients.findByCpfOuCnpj(client.getCpfOuCnpj());
		
		if(Strings.isNullOrEmpty(client.getCity().getName())){
			throw new RegistroObrigatorioException("Informe uma Cidade");
		}
		
		if (clientOptional.isPresent() && !clientOptional.get().equals(client)) {
			throw new ClientJaCadastradoExcepition("Cliente já cadastrado");
		}

		return clients.saveAndFlush(client);
	}

}