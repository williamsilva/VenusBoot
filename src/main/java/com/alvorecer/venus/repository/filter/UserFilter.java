package com.alvorecer.venus.repository.filter;

import java.util.List;

import com.alvorecer.venus.model.Group;

public class UserFilter {

	private String name;
	private String email;
	private List<Group> groups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
