package com.example.sso.service;

import com.example.sso.domain.Role;

public interface RoleService {

	Role findByName(String roleName);

}
