package com.github.ogomezso.datacatalogapi.rest.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopicMetadataResponse {

  private String version;
  private String topic;
  private String topicDescription;
  private String domain;
  private String product;
  private String ownerTeam;
  private String ownerContact;
}
