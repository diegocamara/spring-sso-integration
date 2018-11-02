package com.example.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.DAO;
import com.example.sso.dao.RoleDAO;
import com.example.sso.model.Role;
import com.example.sso.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractService<Role, Long> implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	protected DAO<Role, Long> getDAO() {
		return this.roleDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Role findByName(String roleName) {
		return this.roleDAO.findByName(roleName);
	}

}
