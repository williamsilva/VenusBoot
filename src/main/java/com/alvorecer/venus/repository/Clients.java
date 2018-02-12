package com.alvorecer.venus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.repository.helper.client.ClientsQueries;

@Repository
public interface Clients extends JpaRepository<Client, Long>, ClientsQueries {

	public Optional<Client> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Client> findByNameStartingWithIgnoreCase(String nome);

}
