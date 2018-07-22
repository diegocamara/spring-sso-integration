package com.example.sso.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRIVILEGE")
public class Privilege {

	public static final String COLUMN_ID = "ID";
	public static final String SEQUENCE_NAME = "PRIVILEGE_SEQUENCE";
	public static final String SEQUENCE_GENERATOR_NAME = "PRIVILEGE_SEQUENCE_GENERATOR";

	@Id
	@SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
	@Column(name = COLUMN_ID, nullable = false)
	private Long id;

	@Column(name = "NAME", nullable = true, unique = true)
	private String name;

	@ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
