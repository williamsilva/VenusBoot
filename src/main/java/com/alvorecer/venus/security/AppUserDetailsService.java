package com.alvorecer.venus.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.repository.Users;

@Service
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	private Users users;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Use> userOptional = users.porEmailEAtivo(email);		
		Use use = userOptional.orElseThrow(()-> new UsernameNotFoundException("Usuário e/ou senha Invaliddo"));
		
		return new UserSystem(use, getPermission(use));
	}

	private Collection<? extends GrantedAuthority> getPermission(Use use) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		//Lista de permissoes do usuario logado
		List<String> permission = users.permission(use);
		permission.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return authorities;
	}

}
