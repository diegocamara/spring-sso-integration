package com.example.sso.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken {

	private static final int EXPIRATION_MINUTES = 60 * 24;

	public static final String COLUMN_ID = "ID";
	public static final String SEQUENCE_NAME = "VERIFICATION_TOKEN_SEQUENCE";
	public static final String SEQUENCE_GENERATOR_NAME = "VERIFICATION_TOKEN_SEQUENCE_GENERATOR";

	@Id
	@SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
	@Column(name = COLUMN_ID, nullable = false)
	private Long id;

	@Column(name = "TOKEN", columnDefinition = "varchar(255)")
	private String token;

	@Column(name = "EXPIRY_DATE")
	private LocalDateTime expiryDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = User.COLUMN_ID, nullable = false)
	private User user;

	private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
		return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
