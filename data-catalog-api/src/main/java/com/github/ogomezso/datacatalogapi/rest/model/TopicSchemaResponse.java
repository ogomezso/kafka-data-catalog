package com.github.ogomezso.datacatalogapi.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopicSchemaResponse {

  private String version;
  private String schema;
}
