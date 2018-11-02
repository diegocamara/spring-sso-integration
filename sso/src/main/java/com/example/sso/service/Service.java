package com.example.sso.service;

import java.io.Serializable;

public interface Service<T, ID extends Serializable> {

	void bulkCreate(@SuppressWarnings("unchecked") T... entities);

	void bulkCreateOrUpdate(@SuppressWarnings("unchecked") T... entities);

}
