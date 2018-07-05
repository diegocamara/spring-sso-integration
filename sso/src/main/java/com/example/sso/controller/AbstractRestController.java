package com.example.sso.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.sso.mediator.ICRUDMediator;

public abstract class AbstractRestController<T, ID extends Serializable> implements IBasicRestController<T, ID> {

	@Override
	public T create(@RequestBody T json) {
		return getCRUDMediator().create(json);
	}

	@Override
	public T update(@PathVariable ID id, @RequestBody T json) {
		return getCRUDMediator().update(id, json);
	}

	@Override
	public void delete(@PathVariable ID id) {
		getCRUDMediator().delete(id);
	}

	@Override
	public T findOne(@PathVariable ID id) {
		return getCRUDMediator().findOne(id);
	}

	@Override
	public List<T> listAll() {
		return getCRUDMediator().listAll();
	}

	public abstract ICRUDMediator<T, ID> getCRUDMediator();

}
