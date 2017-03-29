package com.alvorecer.venus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Client;

public class ClientConverter implements Converter<String, Client> {

	@Override
	public Client convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Client client = new Client();
			client.setId(Long.valueOf(id));
			return client;
		}
		return null;
	}
}