package com.alvorecer.venus.repository.helper.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.repository.filter.UserFilter;

public interface UsersQueries {
	
	public Optional<Use> porEmailEAtivo(String email);
	
	public List<String> permission(Use user);
	
	public Page<Use> filter(UserFilter userFilter, Pageable pageable);
}
