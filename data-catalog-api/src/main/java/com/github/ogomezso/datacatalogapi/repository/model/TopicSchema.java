package com.github.ogomezso.datacatalogapi.repository.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "#{@environment.getProperty('app.schemas-index')}")
@Data
public class TopicSchema {

  @Id
  private String id;
  @Field(type = FieldType.Text, name = "version")
  private String version;
  @Field(type = FieldType.Text, name = "schema")
  private String schema;
  @Field(type = FieldType.Text, name = "subject")
  private String subject;

}
