package com.example.sso.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration {

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

	@Value("${hibernate.jdbc.batch_size}")
	private int HIBERNATE_JDBC_BATCH_SIZE;

	@Value("${hibernate.order_inserts}")
	private boolean HIBERNATE_ORDER_INSERTS;

	@Value("${hibernate.order_updates}")
	private boolean HIBERNATE_ORDER_UPDATES;

	@Value("${hibernate.generate_statistics}")
	private boolean HIBERNATE_GENERATE_STATISTICS;

	@Value("${entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DB_DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);
		return dataSource;
	}

	@Bean
	@DependsOn("flyway")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactoryBean.setJpaProperties(hibernateProperties());
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}

	private Properties hibernateProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.put(Environment.DIALECT, HIBERNATE_DIALECT);
		hibernateProperties.put(Environment.SHOW_SQL, HIBERNATE_SHOW_SQL);
		hibernateProperties.put(Environment.FORMAT_SQL, HIBERNATE_FORMAT_SQL);
		hibernateProperties.put(Environment.HBM2DDL_AUTO, HIBERNATE_HBM2DDL_AUTO);
		hibernateProperties.put(Environment.DEFAULT_SCHEMA, HIBERNATE_DEFAULT_SCHEMA);
		hibernateProperties.put(Environment.STATEMENT_BATCH_SIZE, HIBERNATE_JDBC_BATCH_SIZE);
		hibernateProperties.put(Environment.ORDER_INSERTS, HIBERNATE_ORDER_INSERTS);
		hibernateProperties.put(Environment.ORDER_UPDATES, HIBERNATE_ORDER_UPDATES);
		hibernateProperties.put(Environment.GENERATE_STATISTICS, HIBERNATE_GENERATE_STATISTICS);
		return hibernateProperties;

	}

}
