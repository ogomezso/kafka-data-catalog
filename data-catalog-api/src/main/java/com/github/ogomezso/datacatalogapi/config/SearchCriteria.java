package com.github.ogomezso.datacatalogapi.config;

import java.util.List;

import lombok.Data;

@Data
public class SearchCriteria {

  private List<String> fields;
  private List<Float> weights;
}
