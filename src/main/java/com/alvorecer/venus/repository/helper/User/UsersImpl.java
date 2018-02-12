package com.alvorecer.venus.repository.helper.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alvorecer.venus.model.Group;
import com.alvorecer.venus.model.Use;
import com.alvorecer.venus.model.user.UseGroup;
import com.alvorecer.venus.repository.filter.UserFilter;
import com.alvorecer.venus.repository.paginacao.PaginacaoUtil;

public class UsersImpl implements UsersQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Optional<Use> porEmailEAtivo(String email) {
		return manager.createQuery("from Use where lower(email) = lower(:email) and active = true", Use.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permission(Use user) {
		return manager.createQuery(
				"select distinct p.name from Use u inner join u.groups g inner join g.permission p where u = :user",
				String.class).setParameter("user", user).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Use> filter(UserFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Use.class);

		paginacaoUtil.preparar(criteria, pageable);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		addFilter(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	private Long total(UserFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Use.class);
		addFilter(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private boolean isGroupPresent(UserFilter userfilter){
		return userfilter.getGroups() != null && !userfilter.getGroups().isEmpty();
	}

	private void addFilter(UserFilter userfilter, Criteria criteria) {
		if (userfilter != null) {
			if (!StringUtils.isEmpty(userfilter.getName())) {
				criteria.add(Restrictions.ilike("name", userfilter.getName(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(userfilter.getEmail())) {
				criteria.add(Restrictions.ilike("email", userfilter.getEmail(), MatchMode.START));
			}
			
			criteria.createAlias("groups", "g", JoinType.LEFT_OUTER_JOIN);
			
			if (isGroupPresent(userfilter)) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long idGroups : userfilter.getGroups().stream().mapToLong(Group::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UseGroup.class);
					dc.add(Restrictions.eq("id.group.id", idGroups));
					dc.setProjection(Projections.property("id.user"));
					
					subqueries.add(Subqueries.propertyIn("id", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}

}
