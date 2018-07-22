package com.example.sso.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role {

	public static final String COLUMN_ID = "ID";
	public static final String SEQUENCE_NAME = "ROLE_SEQUENCE";
	public static final String SEQUENCE_GENERATOR_NAME = "ROLE_SEQUENCE_GENERATOR";

	@Id
	@SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
	@Column(name = COLUMN_ID, nullable = false)
	private Long id;

	@Column(name = "NAME", columnDefinition = "VARCHAR(255)")
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLES_PRIVILEGES", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
	private List<Privilege> privileges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
