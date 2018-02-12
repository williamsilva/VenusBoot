package com.alvorecer.venus.repository.helper.attendance;

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

import com.alvorecer.venus.model.Attendance;
import com.alvorecer.venus.repository.filter.AttendanceFilter;
import com.alvorecer.venus.repository.paginacao.PaginacaoUtil;

public class AttendancesImpl implements AttendancesQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Attendance> filter(AttendanceFilter filter, Pageable pageable) {

		Criteria criteria = manager.unwrap(Session.class).createCriteria(Attendance.class);

		paginacaoUtil.preparar(criteria, pageable);

		addFilter(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	private Long total(AttendanceFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Attendance.class);
		addFilter(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private boolean isClientPresent(AttendanceFilter filter) {
		return filter.getClient() != null && filter.getClient().getId() != null;
	}

	private void addFilter(AttendanceFilter filter, Criteria criteria) {
		criteria.createAlias("client", "c");
		
		if (filter != null) {

			if (!StringUtils.isEmpty(filter.getDateRegisterInicial())) {
				criteria.add(Restrictions.ge("dateRegister", filter.getDateRegisterInicial()));
			}

			if (!StringUtils.isEmpty(filter.getDateRegisterFinal())) {
				criteria.add(Restrictions.le("dateRegister", filter.getDateRegisterFinal()));
			}

			if (!StringUtils.isEmpty(filter.getChannel())) {
				criteria.add(Restrictions.eq("channel", filter.getChannel()));
			}
			
			if (!StringUtils.isEmpty(filter.getTypeClient())) {
				criteria.add(Restrictions.eq("c.typeClient", filter.getTypeClient()));
			}

			if (!StringUtils.isEmpty(filter.getProtocol())) {
				criteria.add(Restrictions.ilike("protocol", filter.getProtocol(), MatchMode.ANYWHERE));
			}

			if (isClientPresent(filter)) {
				criteria.add(Restrictions.eq("client", filter.getClient()));
			}

			if (!StringUtils.isEmpty(filter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", filter.getCpfOuCnpj()));
			}
			
			if (!StringUtils.isEmpty(filter.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.name", filter.getNomeCliente(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filter.getAsPark())) {
				criteria.add(Restrictions.eq("asPark", filter.getAsPark()));
			}

			if (!StringUtils.isEmpty(filter.getSubject())) {
				criteria.add(Restrictions.eq("subject", filter.getSubject()));
			}
			
			if (!StringUtils.isEmpty(filter.getEmail())) {
				criteria.add(Restrictions.ilike("c.email", filter.getEmail(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filter.getKnowsPark())) {
				criteria.add(Restrictions.eq("knowsPark", filter.getKnowsPark()));
			}

			if (!StringUtils.isEmpty(filter.getClosed())) {
				criteria.add(Restrictions.eq("closed", filter.getClosed()));
			}				

			if (!StringUtils.isEmpty(filter.getReturnContact())) {
				criteria.add(Restrictions.eq("returnContact", filter.getReturnContact()));
			}

		}
	}

}
