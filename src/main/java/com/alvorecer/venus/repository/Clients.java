package com.alvorecer.venus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alvorecer.venus.model.Client;

@Repository
public interface Clients extends JpaRepository<Client, Long> {

	Optional<Client> findBycpfOuCnpj(String cpfOuCnpj);

}
