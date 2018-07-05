package com.example.sso.mediator;

import java.io.Serializable;
import java.util.List;

public interface ICRUDMediator<T, ID extends Serializable> extends IMediator {

	T create(T json);

	T update(ID id, T json);

	void delete(ID id);

	T findOne(ID id);

	List<T> listAll();

}
