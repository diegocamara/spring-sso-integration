package com.example.sso.dao;

import java.io.Serializable;

public interface DAO<T, ID extends Serializable> {

	T save(T entity);

	T saveOrUpdate(T entity);

	void delete(T entity);

	T findById(ID id);

}
