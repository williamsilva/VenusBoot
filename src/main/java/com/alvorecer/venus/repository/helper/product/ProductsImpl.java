package com.alvorecer.venus.repository.helper.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Product;
import com.alvorecer.venus.model.enun.Status;
import com.alvorecer.venus.repository.filter.ProductFilter;
import com.alvorecer.venus.repository.paginacao.PaginacaoUtil;

public class ProductsImpl implements ProductsQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public List<Product> porNameEAtivo(String name) {
		String jpql = "from Product where lower(name) like lower(:name) and status =:status";
		
		List<Product> products = manager.createQuery(jpql, Product.class)
				.setParameter("name", name + "%")
				.setParameter("status", Status.ATIVO)
				.getResultList();
		
		return products;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Product> filter(ProductFilter productFilter, Pageable pageable) {
	
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Product.class);
		paginacaoUtil.preparar(criteria, pageable);
		addFilter(productFilter, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(productFilter));
	}
	
	private Long total(ProductFilter productFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Product.class);
		addFilter(productFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void addFilter(ProductFilter productFilter, Criteria criteria) {		
			
		if(productFilter != null){
			if (!StringUtils.isEmpty(productFilter.getName())) {
				criteria.add(Restrictions.ilike("name", productFilter.getName(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(productFilter.getDescription())) {
				criteria.add(Restrictions.ilike("description", productFilter.getDescription(), MatchMode.ANYWHERE));
			}
			
			if (productFilter.getValue() != null) {
				criteria.add(Restrictions.ge("value", productFilter.getValue()));
			}
			
			if(productFilter.getStatus() != null){
				criteria.add(Restrictions.ge("status", productFilter.getStatus()));
			}
		}
	
	}
}
