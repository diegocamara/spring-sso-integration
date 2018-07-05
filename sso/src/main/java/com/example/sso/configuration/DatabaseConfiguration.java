package com.example.sso.configuration;

import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.sso.annotation.ExcludeFromTests;

@Configuration
@EnableTransactionManagement
@ExcludeFromTests
public class DatabaseConfiguration {

	private static final String SCHEMA_SPLIT_REGEX = ",";

	@Value("${db.driver}")
	private String DB_DRIVER;

	@Value("${db.password}")
	private String DB_PASSWORD;

	@Value("${db.url}")
	private String DB_URL;

	@Value("${db.username}")
	private String DB_USERNAME;

	@Value("${hibernate.dialect}")
	private String HIBERNATE_DIALECT;

	@Value("${hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.format_sql}")
	private String HIBERNATE_FORMAT_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${hibernate.default.schema}")
	private String HIBERNATE_DEFAULT_SCHEMA;

	@Value("${entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;

	// Flyway configuration

	@Value("${migration.clean_database}")
	private boolean MIGRATION_CLEAN_DATABASE;

	@Value("${migration.enabled}")
	private boolean MIGRATION_ENABLED;

	@Value("${migration.try_repair_on_failure}")
	private boolean MIGRATION_TRY_REPAIR_ON_FAILURE;

	@Value("${migration.schemas}")
	private String MIGRATION_SCHEMAS;

	@Value("${migration.locations}")
	private String MIGRATION_LOCATIONS;

	private static final Logger LOGGER = LogManager.getLogger(DatabaseConfiguration.class);

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}

	@Bean
	@DependsOn("flyway")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		hibernateProperties.put("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
		hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
		hibernateProperties.put("hibernate.default_schema", HIBERNATE_DEFAULT_SCHEMA);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);

		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {

		if (MIGRATION_ENABLED) {

			final Flyway flyway = new Flyway();
			flyway.setDataSource(this.dataSource());
			flyway.setLocations(MIGRATION_LOCATIONS);

			String[] schemas = (String[]) Arrays.stream(MIGRATION_SCHEMAS.split(SCHEMA_SPLIT_REGEX))
					.map(schema -> schema.trim()).toArray(String[]::new);

			flyway.setSchemas(schemas);

			LOGGER.info("Initializing Flyway...");

			if (MIGRATION_CLEAN_DATABASE) {
				LOGGER.info("Clean database...");
				flyway.clean();
			}

			try {
				LOGGER.info("Starting database migration");
				flyway.migrate();
			} catch (FlywayException e) {
				LOGGER.info("The migration failed: " + e.getMessage());
				e.printStackTrace();
				if (MIGRATION_TRY_REPAIR_ON_FAILURE) {
					LOGGER.info("Trying repair");
					flyway.clean();
					flyway.migrate();
				}
			}
			LOGGER.info("Done.");
			return flyway;
		}
		return null;
	}

}
