package com.github.ogomezso.datacatalogapi.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "topic_metadata")
@Data
public class TopicMetadata {

  @Id
  private String id;
  @Field(type = FieldType.Text, name = "version")
  private String version;
  @Field(type = FieldType.Text, name = "topic")
  private String topic;
  @Field(type = FieldType.Text, name = "domain")
  private String domain;
  @Field(type = FieldType.Text, name = "product")
  private String product;
  @Field(type = FieldType.Text, name = "owner_team")
  private String ownerTeam;
  @Field(type = FieldType.Text, name = "owner_contact")
  private String ownerContact;
}
