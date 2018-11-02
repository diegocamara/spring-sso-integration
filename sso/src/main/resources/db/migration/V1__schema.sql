--CREATE SEQUENCE USER_SEQUENCE;

CREATE TABLE USER (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	EMAIL VARCHAR(255),
	ENABLED BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (ID)
);

--CREATE SEQUENCE ROLE_SEQUENCE;

CREATE TABLE ROLE (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE USERS_ROLES (
	USER_ID BIGINT NOT NULL,
	ROLE_ID BIGINT NOT NULL,
	CONSTRAINT USERS_ROLES_PK PRIMARY KEY (USER_ID, ROLE_ID),
	CONSTRAINT USERS_ROLES_USER_FK FOREIGN KEY (USER_ID) REFERENCES USER(ID),
	CONSTRAINT USERS_ROLES_ROLE_FK FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)
);

--CREATE SEQUENCE PRIVILEGE_SEQUENCE;

CREATE TABLE PRIVILEGE (
	ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(255)
);

CREATE TABLE ROLES_PRIVILEGES (
	ROLE_ID BIGINT NOT NULL,
	PRIVILEGE_ID BIGINT NOT NULL,
	CONSTRAINT ROLES_PRIVILEGES_PK PRIMARY KEY (ROLE_ID, PRIVILEGE_ID),
	CONSTRAINT ROLES_PRIVILEGES_ROLE_FK FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID),
	CONSTRAINT ROLES_PRIVILEGES_PRIVILEGE_FK FOREIGN KEY (PRIVILEGE_ID) REFERENCES PRIVILEGE(ID)
);

--CREATE SEQUENCE VERIFICATION_TOKEN_SEQUENCE;

CREATE TABLE VERIFICATION_TOKEN (
	ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TOKEN VARCHAR(255) NULL,
	EXPIRY_DATE TIMESTAMP NULL,
	USER_ID BIGINT NOT NULL,
	CONSTRAINT TOKEN_VERIFICATION_USER_FK FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);