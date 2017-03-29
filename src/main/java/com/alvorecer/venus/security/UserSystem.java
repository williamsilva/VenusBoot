package com.alvorecer.venus.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.alvorecer.venus.model.Use;

public class UserSystem extends User {

	private static final long serialVersionUID = 1L;

	private Use use;

	public UserSystem(Use use, Collection<? extends GrantedAuthority> authorities) {
		super(use.getEmail(), use.getPassword(), authorities);
		this.use = use;
	}

	public Use getUse() {
		return use;
	}

}
