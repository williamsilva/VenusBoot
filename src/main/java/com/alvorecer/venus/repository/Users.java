package com.alvorecer.venus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.repository.helper.User.UsersQueries;

@Repository
public interface Users extends JpaRepository<Use, Long>, UsersQueries {

	public Optional<Use> findByEmail(String email);
	
	public List<Use> findByIdIn(Long[] id);

}
