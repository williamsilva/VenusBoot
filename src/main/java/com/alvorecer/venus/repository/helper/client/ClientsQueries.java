package com.alvorecer.venus.repository.helper.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alvorecer.venus.dto.ClientDTO;
import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.repository.filter.ClientFilter;

public interface ClientsQueries {
	
	public Page<Client> filter(ClientFilter clientFilter, Pageable pageable);
	
	public List<ClientDTO> porNameOuCpfCnpj(String nameOuCpfCnpj);
}
