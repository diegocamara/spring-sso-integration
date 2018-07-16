package com.example.sso.mediator;

import java.io.Serializable;
import java.util.List;

public interface ICRUDMediator<T, ID extends Serializable> extends IMediator {

	T save(T entity);

	T update(T entity);

	T saveOrUpdate(T entity);

	void delete(T entity);

	T findById(ID id);

	List<T> findAll();

}
