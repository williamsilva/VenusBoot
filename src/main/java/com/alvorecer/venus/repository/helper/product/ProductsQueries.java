package com.alvorecer.venus.repository.helper.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.repository.filter.ProductFilter;


public interface ProductsQueries {
	
	public List<Product> porNameEAtivo(String name);
	
	public Page<Product> filter(ProductFilter filter, Pageable pageable);
}
