package com.example.sso.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sso.dao.IDAO;
import com.example.sso.dao.IRoleDAO;
import com.example.sso.model.Role;

@Service
public class RoleMediator extends AbstractMediator<Role, Long> implements IRoleMediator {

	@Autowired
	private IRoleDAO roleDAO;

	@Override
	protected IDAO<Role, Long> getDAO() {
		return this.roleDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Role findByName(String roleName) {
		return this.roleDAO.findByName(roleName);
	}

}
