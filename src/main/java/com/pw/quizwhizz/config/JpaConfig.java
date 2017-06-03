package com.pw.quizwhizz.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Klasa ustawien dla JPA Hibernate oraz polaczen z baza danych
 * @author Michał Nowiński
 */
@Configuration
@EnableTransactionManagement // wlacza zarzadzanie tranzakcjami przez springa
@EnableJpaRepositories(basePackages = "com.pw.quizwhizz")
public class JpaConfig {

    /**
     * Obiekt odpowiedzialny za polaczenie JPA i Springa. Odpowiada za kontrole cyklu życia obiektu EntityManagerFactory
     * @param adapter ustawienia providera JPA (Hibernate)
     * @param dataSource ustawienia bazy danych
     * @return LocalContainerEntityManagerFactoryBean odpowiedzialny za wstrzykiwanie EntityManagerFactory (wazne: nazwa metody tworzy beana o takiej samej nazwie)
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("db_quizwhizz");
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(adapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.setPackagesToScan("com.pw.quizwhizz");
        return entityManagerFactory;
    }

    /**
     * Ustala jakiego providera JPA uzywamy.
     * LocalContainerEntityManagerFactoryBean nie jest w stanie automatycznie okreslic providera.
     */
    @Bean
    public JpaVendorAdapter createVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);               // pokazuj w koncoli zapytania SQL
        return adapter;
    }

    /**
     * Apache Commons DBCP
     * @return ustawienia dla bazy danych
     */
    @Bean
    public DataSource createDS() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/db_quizwhizz");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(10);           // ilosc puli polaczen z baza danych
        dataSource.addConnectionProperty("uceUnicode", "yes");
        dataSource.addConnectionProperty("characterEncoding", "UTF-8");
        dataSource.addConnectionProperty("useSSL", "false");
        dataSource.addConnectionProperty("useJDBCCompliantTimezoneShift", "true");
        dataSource.addConnectionProperty("useLegacyDatetimeCode", "false");
        dataSource.addConnectionProperty("serverTimezone", "UTC");
        return dataSource;
    }

    /**
     * Manager springa do zarzadzania transakcjami
     * @param entityManagerFactory
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory);
        return txManager;
    }

    /**
     * hibernate.hbm2ddl.auto przyjmuje (validate | update | create | create-drop) - odpowiedzialny za ustawienia rozruchowe bazydanych
     * @return dodatkowe ustawenia hibernate
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");  // validate | update | create | create-drop
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.connection.CharSet", "utf-8");
        properties.setProperty("hibernate.connection.characterEncoding", "UTF-8");
        properties.setProperty("hibernate.connection.useUnicode", "true");
        return properties;
    }

    /**
     * Bean wczytujacy dane z pliku data.sql do bazy danych
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");
        resourceDatabasePopulator.addScript(new ClassPathResource("/data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(createDS());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    /**
     * Bean udostepniajacy JdbcTemplate do zapytań SQL
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}