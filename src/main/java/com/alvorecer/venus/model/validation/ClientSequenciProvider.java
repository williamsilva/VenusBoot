package com.alvorecer.venus.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.alvorecer.venus.model.Client;

public class ClientSequenciProvider implements DefaultGroupSequenceProvider<Client> {

	@Override
	public List<Class<?>> getValidationGroups(Client client) {
		List<Class<?>> groups = new ArrayList<>();
		groups.add(Client.class);
		
		if(isClientSelect(client)){
			groups.add(client.getTypeClient().getGrupo());
		}
		return groups;
	}

	private boolean isClientSelect(Client client) {
		return client != null && client.getTypeClient() != null;
	}

}
