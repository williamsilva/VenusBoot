package com.alvorecer.venus.repository.helper.User;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alvorecer.venus.model.Use;

public class UsersImpl implements UsersQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Optional<Use> porEmailEAtivo(String email) {
		return manager
				.createQuery("from Use where lower(email) = lower(:email) and active = true", Use.class)
				.setParameter("email", email)
				.getResultList().stream().findFirst();
	}

	@Override
	public List<String> permission(Use user) {
		return manager.createQuery(
				"select distinct p.name from Use u inner join u.groups g inner join g.permission p where u = :user", 
						String.class)
				.setParameter("user", user)
				.getResultList();
	}
}
