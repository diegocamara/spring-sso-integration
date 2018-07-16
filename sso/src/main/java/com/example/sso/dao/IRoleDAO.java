package com.example.sso.dao;

import com.example.sso.model.Role;

public interface IRoleDAO extends IDAO<Role, Long> {

	Role findByName(String roleName);

}
