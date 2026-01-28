package com.scrylk.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class DatabaseConfiguration {
  @Value("${spring.datasource.url}") //Pega do application.yml
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String password;
  @Value("${spring.datasource.driver-class-name}")
  String driver;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setDriverClassName(driver);
    return ds;
  }

  @Bean
  @Primary
  public DataSource hikariDataSource() {
    HikariConfig config = new HikariConfig();
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driver);
    config.setJdbcUrl(url + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true");

    config.setMaximumPoolSize(10); //maximo de conexoes liberadas
    config.setMinimumIdle(1);
    config.setPoolName("library-db-pool");
    config.setMaxLifetime(600000);
    config.setConnectionTimeout(100000); // timeout para conseguir uma conexao
    config.setIdleTimeout(300000); // 5 minutos
    config.setKeepaliveTime(30000); // mantém a conexão ativa a cada 30 segundos
    config.setConnectionTestQuery("SELECT 1");

    return new HikariDataSource(config);
  }
}
