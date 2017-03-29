package com.alvorecer.venus.repository.helper.User;

import java.util.List;
import java.util.Optional;

import com.alvorecer.venus.model.Use;

public interface UsersQueries {
	
	public Optional<Use> porEmailEAtivo(String email);
	
	public List<String> permission(Use user);
}
