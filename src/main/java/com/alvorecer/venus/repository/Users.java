package com.alvorecer.venus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.repository.helper.User.UsersQueries;

public interface Users extends JpaRepository<Use, Long>, UsersQueries {

	public Optional<Use> findByEmail(String email);

}
