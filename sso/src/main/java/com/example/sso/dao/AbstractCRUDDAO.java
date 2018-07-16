package com.example.sso.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class AbstractCRUDDAO<T, ID extends Serializable> extends AbstractHibernateDAO<T, ID>
		implements IDAO<T, ID> {

	@Override
	public T create(T entity) {
		return super.create(entity);
	}

	@Override
	public T update(ID id, T json) {
		T entityProxy = getEntityProxy(id);
		BeanUtils.copyProperties(entityProxy, json);
		super.update(entityProxy);
		return entityProxy;
	}

	@Override
	public void delete(ID id) {
		super.deleteById(id);
	}

	@Override
	public T findById(ID id) {
		return super.findOne(id);
	}

	@Override
	public List<T> findAll() {
		return super.findAll();
	}

}
