package com.alvorecer.venus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.repository.Products;
import com.alvorecer.venus.service.excepition.ClientJaCadastradoExcepition;

@Service
public class CadasterProductsService {
	
	@Autowired
	private Products products;
	
	@Transactional
	public Product save (Product product){
		Optional<Product> productOptinonal = products.findByName(product.getName());
		
		if (productOptinonal.isPresent() && !productOptinonal.get().equals(product)) {
			throw new ClientJaCadastradoExcepition("Produto já cadastrado");
		}

		return products.save(product);
	}
}
