package com.example.sso.dao;

import java.io.Serializable;
import java.util.List;

public interface IDAO<T, ID extends Serializable> {

	T save(T entity);

	T update(T entity);

	T saveOrUpdate(T entity);

	void delete(T entity);

	T findById(ID id);

	List<T> findAll();

}
