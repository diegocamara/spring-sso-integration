package com.example.sso.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "USER")
public class User {

	public static final String COLUMN_ID = "ID";
	public static final String GENERIC_GENERATOR_NAME = "native";

	@Id
	@GenericGenerator(name = GENERIC_GENERATOR_NAME, strategy = GENERIC_GENERATOR_NAME)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = GENERIC_GENERATOR_NAME)
	@Column(name = COLUMN_ID, nullable = false, updatable = false)
	private Long id;

	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private List<Role> roles;

	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new LinkedList<>();
		List<String> privileges = getPrivileges();

		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}

		return authorities;
	}

	private List<String> getPrivileges() {
		List<String> privileges = new LinkedList<>();
		List<Privilege> collection = new LinkedList<>();

		for (Role role : this.roles) {
			collection.addAll(role.getPrivileges());
		}

		for (Privilege privilege : collection) {
			privileges.add(privilege.getName());
		}

		return privileges;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}