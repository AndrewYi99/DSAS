package com.dsas;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.Properties;


@SpringBootApplication
@MapperScan("com.dsas.model.dao")
@ImportAutoConfiguration(PageHelperAutoConfiguration.class)
@EnableCaching
@ServletComponentScan
public class DsasApplication {

  @Bean
  public PageHelper pageHelper() {
    PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    properties.setProperty("offsetAsPageNum", "true");
    properties.setProperty("rowBoundsWithCount", "true");
    properties.setProperty("reasonable", "true");
    properties.setProperty("dialect", "mysql");
    pageHelper.setProperties(properties);
    return pageHelper;
  }

  public static void main(String[] args) {
    SpringApplication.run(DsasApplication.class, args);
  }


}
