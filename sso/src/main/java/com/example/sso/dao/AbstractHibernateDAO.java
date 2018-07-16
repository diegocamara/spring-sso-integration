package com.example.sso.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateDAO<T, ID extends Serializable> {

	private Class<T> clazz;

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void postConstruct() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getCurrentSession().getCriteriaBuilder();
	}

	protected CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.createQuery(getEntityClass());
	}

	protected Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		return criteriaQuery.from(getEntityClass());
	}

	protected T getSingleResult(CriteriaQuery<T> criteriaQuery) {
		Query<T> query = getCurrentSession().createQuery(criteriaQuery);

		try {
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}

	}

	protected List<T> getResultList(CriteriaQuery<T> criteriaQuery) {
		Query<T> query = getCurrentSession().createQuery(criteriaQuery);

		return query.getResultList();
	}

	public Criteria createCriteria() {
		Criteria criteria = getCurrentSession().createCriteria(getEntityClass());
		return criteria;
	}

	public Criteria createCriteria(Class<?> criteriaClass) {
		Criteria criteria = getCurrentSession().createCriteria(criteriaClass);
		return criteria;
	}

	public T findOne(Serializable id) {
		return getCurrentSession().get(getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return createCriteria().list();
	}

	public T create(T entity) {
		getCurrentSession().persist(entity);
		return entity;
	}

	public void update(T entity) {
		getCurrentSession().merge(entity);
	}

	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	public void deleteById(Serializable id) {
		T entity = getEntityProxy(id);
		delete(entity);
	}

	public void saveOrUpdate(Object... entityObjects) {
		for (Object object : entityObjects) {
			getCurrentSession().saveOrUpdate(object);
		}
	}

	// public void save(Object... entityObjects) {
	// for (Object object : entityObjects) {
	// getCurrentSession().save(object);
	// }
	// getCurrentSession().flush();
	// }
	//
	// public void update(Object... entityObjects) {
	// for (Object object : entityObjects) {
	// getCurrentSession().update(object);
	// }
	// }
	//
	// public void delete(Object... entityObjects) {
	// for (Object object : entityObjects) {
	// getCurrentSession().delete(object);
	// }
	// }
	//
	// public void evict(Object... entityObjects) {
	// for (Object object : entityObjects) {
	// getCurrentSession().evict(object);
	// }
	// }

	protected Class<T> getEntityClass() {
		return this.clazz;
	}

	protected final Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public T getEntityProxy(Serializable id) {
		return getCurrentSession().load(getEntityClass(), id);
	}

}
