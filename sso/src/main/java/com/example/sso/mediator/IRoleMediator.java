package com.example.sso.mediator;

import com.example.sso.model.Role;

public interface IRoleMediator {

	Role findByName(String roleName);

}
