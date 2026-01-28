package com.scrylk.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;


@Configuration
public class DatabaseConfiguration {
  @Value("${spring.datasource.url") //Pega do application.yml
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

  // @Bean
  // public DataSource hikariDataSource() {
  //   HikariConfig config = new HikariConfig();
  //   config.setUsername(username);
  //   config.setPassword(password);
  //   return config;
  // }
}
