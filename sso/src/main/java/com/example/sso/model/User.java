package com.example.sso.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		this.roles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getPrivileges().getName()))
//				.collect(Collectors.toList());
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// we never lock accounts
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// credentials never expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}