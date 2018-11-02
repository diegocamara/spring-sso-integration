package com.example.sso.service;

import com.example.sso.model.Role;

public interface RoleService {

	Role findByName(String roleName);

}
