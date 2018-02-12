package com.alvorecer.venus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.repository.helper.product.ProductsQueries;

@Repository
public interface Products extends JpaRepository<Product, Long>, ProductsQueries{

	Optional<Product> findByName(String name);

}
