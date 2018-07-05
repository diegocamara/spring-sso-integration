package com.example.sso.dao;

import java.io.Serializable;
import java.util.List;

public interface ICRUDDAO<T, ID extends Serializable> {

	T create(T json);

	T update(ID id, T json);

	void delete(ID id);

	T findById(ID id);

	List<T> findAll();

}
