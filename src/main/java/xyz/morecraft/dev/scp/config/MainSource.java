package xyz.morecraft.dev.scp.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("xyz.morecraft.dev.scp.dao.repository")
public class MainSource {

    @Bean(name = "dataSource-scp")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

}
