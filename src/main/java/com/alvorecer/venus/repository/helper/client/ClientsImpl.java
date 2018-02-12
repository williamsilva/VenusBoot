package com.alvorecer.venus.repository.helper.client;

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

import com.alvorecer.venus.dto.ClientDTO;
import com.alvorecer.venus.model.Client;
import com.alvorecer.venus.repository.filter.ClientFilter;
import com.alvorecer.venus.repository.paginacao.PaginacaoUtil;

public class ClientsImpl implements ClientsQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Client> filter(ClientFilter clientFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Client.class);

		paginacaoUtil.preparar(criteria, pageable);

		addFilter(clientFilter, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(clientFilter));
	}

	private Long total(ClientFilter clientFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Client.class);
		addFilter(clientFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void addFilter(ClientFilter clientFilter, Criteria criteria) {		
		criteria.createAlias("city", "c");
		
		if (clientFilter != null) {
			if (!StringUtils.isEmpty(clientFilter.getName())) {
				criteria.add(Restrictions.ilike("name", clientFilter.getName(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(clientFilter.getEmail())) {
				criteria.add(Restrictions.ilike("email", clientFilter.getEmail(), MatchMode.START));
			}

			if (!StringUtils.isEmpty(clientFilter.getCpfOuCnpj())) {
				criteria.add(Restrictions.ilike("cpfOuCnpj", clientFilter.getCpfOuCnpj(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(clientFilter.getTypeClient())) {
				criteria.add(Restrictions.eq("typeClient", clientFilter.getTypeClient()));
			}
			
			if (!StringUtils.isEmpty(clientFilter.getCity())) {
				criteria.add(Restrictions.eq("c.id", clientFilter.getCity()));
			}
			
			if (!StringUtils.isEmpty(clientFilter.getState())) {
				criteria.add(Restrictions.eq("c.state.id", clientFilter.getState()));
			}
			
		}
	}

	@Override
	public List<ClientDTO> porNameOuCpfCnpj(String nameOuCpfCnpj) {
		String jpql = "select new com.alvorecer.venus.dto.ClientDTO(name,cpfOuCnpj,phoneNumber,cellPhone,email) "
				+ "from Client where lower(name) like lower(:nameOuCpfCnpj) or lower(cpfOuCnpj) like lower(:nameOuCpfCnpj)";
		
		List<ClientDTO> clientsFiltrados = manager.createQuery(jpql, ClientDTO.class)
				.setParameter("nameOuCpfCnpj", nameOuCpfCnpj +"%")
				.getResultList();
		
		return clientsFiltrados;
	}
}
