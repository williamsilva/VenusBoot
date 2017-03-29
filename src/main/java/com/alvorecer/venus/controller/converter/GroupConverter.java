package com.alvorecer.venus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Group;

public class GroupConverter implements Converter<String, Group> {

	@Override
	public Group convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			Group group = new Group();
			group.setId(Long.valueOf(id));
			return group;
		}
		return null;
	}

}
