package com.example.sso.service.impl;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.DAO;
import com.example.sso.service.ICRUDService;
import com.example.sso.service.Service;

public abstract class AbstractService<T, ID extends Serializable> implements ICRUDService<T, ID>, Service<T, ID> {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T save(T entity) {
		return getDAO().save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T saveOrUpdate(T entity) {
		return getDAO().saveOrUpdate(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(T entity) {
		getDAO().delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public T findById(ID id) {
		return getDAO().findById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bulkCreate(@SuppressWarnings("unchecked") T... entities) {
		for (T entity : entities) {
			getDAO().save(entity);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void bulkCreateOrUpdate(@SuppressWarnings("unchecked") T... entities) {
		for (T entity : entities) {
			getDAO().saveOrUpdate(entity);
		}
	}

	protected abstract DAO<T, ID> getDAO();

}
