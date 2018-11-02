package com.example.sso.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.example.sso.dao.DAO;

public abstract class AbstractDAO<T, ID extends Serializable> implements DAO<T, ID> {

	private Class<T> clazz;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void postConstruct() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getHibernateSession().getCriteriaBuilder();
	}

	protected CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.createQuery(getEntityClass());
	}

	protected <E> CriteriaQuery<E> getCriteriaQuery(CriteriaBuilder criteriaBuilder, Class<E> clazz) {
		return criteriaBuilder.createQuery(clazz);
	}

	protected <E> Root<E> getRoot(CriteriaQuery<?> criteriaQuery, Class<E> clazz) {
		return criteriaQuery.from(clazz);
	}

	protected Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		return criteriaQuery.from(getEntityClass());
	}

	protected <E> E getSingleResult(CriteriaQuery<E> criteriaQuery) {
		Query<E> query = getHibernateSession().createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	protected <E> List<E> getResultList(CriteriaQuery<E> criteriaQuery) {
		Query<E> query = getHibernateSession().createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public T save(T entity) {
		this.entityManager.persist(entity);
		return entity;
	}

	@Override
	public T saveOrUpdate(T entity) {
		return this.entityManager.merge(entity);
	}

	@Override
	public void delete(T entity) {
		this.entityManager.remove(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.entityManager.find(getEntityClass(), id);
	}

	protected Class<T> getEntityClass() {
		return this.clazz;
	}

	protected final Session getHibernateSession() {
		return this.entityManager.unwrap(Session.class);
	}

	public T getEntityProxy(Serializable id) {
		return getHibernateSession().load(getEntityClass(), id);
	}

}
