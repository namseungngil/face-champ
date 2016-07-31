package facechamp.configuration;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import facechamp.domain.entity.EntityAnchor;
import facechamp.reposigory.RepositoryAnchor;

/**
 * Spring Data JPA의 DB 연동에 필요한 설정을 제공한다.
 *
 * @author justburrow
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { RepositoryAnchor.class },
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableJpaAuditing
public class JpaConfiguration {
  @Value("${spring.jpa.generate-ddl}")
  private boolean     generateDdl;
  @Value("${spring.jpa.show-sql}")
  private boolean     showSql;

  @Autowired
  private Environment env;

  /**
   * Spring Data JPA가 스캔해야 할 패키지의 목록을 제공한다.
   *
   * @return
   */
  private String[] getPackagesToScan() {
    return new String[] { EntityAnchor.PACKAGE_NAME };
  }

  /**
   * JPA 구현체용 설정 정보를 제공한다.
   *
   * @return JPA 구현체용 설정.
   */
  private Map<String, Object> jpaProperties() {
    final String prefix = "spring.jpa.properties.";

    Map<String, Object> properties = asList("hibernate.cache.use_second_level_cache",
        "hibernate.cache.use_query_cache",
        "hibernate.cache.region.factory_class",
        "hibernate.generateStatistics")
            .stream()
            .filter(k -> null != this.env.getProperty(prefix + k))
            .collect(toMap(k -> k, k -> this.env.getProperty(prefix + k)));
    return properties;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(this.showSql);
    adapter.setGenerateDdl(this.generateDdl);
    adapter.setDatabase(Database.MYSQL);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(this.dataSource());
    factory.setJpaVendorAdapter(adapter);
    factory.setPackagesToScan(this.getPackagesToScan());
    factory.setJpaPropertyMap(this.jpaProperties());

    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(this.entityManagerFactory().getObject());
  }

  @Bean
  public HibernateExceptionTranslator hibernateExceptionTranslator() {
    return new HibernateExceptionTranslator();
  }
}
