package com.github.ogomezso.datacatalogapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {

  private String metadataIndex;
  private String schemasIndex;
  private SearchCriteria searchCriteria;

}

