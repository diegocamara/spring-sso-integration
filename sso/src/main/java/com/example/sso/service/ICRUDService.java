package com.example.sso.service;

import java.io.Serializable;

import com.example.sso.dao.DAO;

public interface ICRUDService<T, ID extends Serializable> extends DAO<T, ID> {

}
