package com.example.sso.mediator;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.IDAO;

public abstract class AbstractMediator<T, ID extends Serializable> implements ICRUDMediator<T, ID> {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T save(T entity) {
		return getDAO().save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T update(T entity) {
		return getDAO().update(entity);
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
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return getDAO().findAll();
	}

	protected abstract IDAO<T, ID> getDAO();

}
