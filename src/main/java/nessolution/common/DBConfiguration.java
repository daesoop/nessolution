package nessolution.common;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("org.mariadb.jdbc.Driver")
                .url("jdbc:mariadb://localhost:3307/nessol?characterEncoding=UTF-8&serverTimezone=UTC")
                .username("root")
                .password("nessol")
                .build();
    }
}
