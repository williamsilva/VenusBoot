package com.alvorecer.venus.repository.helper.reserve;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Reserve;
import com.alvorecer.venus.repository.filter.ReserveFilter;
import com.alvorecer.venus.repository.paginacao.PaginacaoUtil;

public class ReservesImpl implements ReservesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Reserve> filter(ReserveFilter reserveFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Reserve.class);

		paginacaoUtil.preparar(criteria, pageable);

		addFilter(reserveFilter, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(reserveFilter));
	}

	@Transactional(readOnly = true)
	@Override
	public Reserve buscarComItens(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Reserve.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Reserve) criteria.uniqueResult();
	}

	private Long total(ReserveFilter reserveFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Reserve.class);
		addFilter(reserveFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void addFilter(ReserveFilter reserveFilter, Criteria criteria) {
		criteria.createAlias("client", "c");

		if (reserveFilter != null) {
			if (!StringUtils.isEmpty(reserveFilter.getVoucher())) {
				criteria.add(Restrictions.ilike("voucher", reserveFilter.getVoucher(), MatchMode.ANYWHERE));
			}

			if (reserveFilter.getStatus() != null) {
				criteria.add(Restrictions.eq("statusVouchers", reserveFilter.getStatus()));
			}

			if (reserveFilter.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(reserveFilter.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("creationDate", desde));
			}

			if (reserveFilter.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(reserveFilter.getAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("creationDate", ate));
			}

			if (reserveFilter.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valor", reserveFilter.getValorMinimo()));
			}

			if (reserveFilter.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valor", reserveFilter.getValorMaximo()));
			}

			if (!StringUtils.isEmpty(reserveFilter.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.name", reserveFilter.getNomeCliente(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(reserveFilter.getTypeClient())) {
				criteria.add(Restrictions.eq("c.typeClient", reserveFilter.getTypeClient()));
			}

			if (!StringUtils.isEmpty(reserveFilter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", reserveFilter.getCpfOuCnpj()));
			}
		}
	}
}
