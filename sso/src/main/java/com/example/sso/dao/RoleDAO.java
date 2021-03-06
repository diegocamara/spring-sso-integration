package com.example.sso.dao;

import com.example.sso.domain.Role;

public interface RoleDAO extends DAO<Role, Long> {

	Role findByName(String roleName);

}
