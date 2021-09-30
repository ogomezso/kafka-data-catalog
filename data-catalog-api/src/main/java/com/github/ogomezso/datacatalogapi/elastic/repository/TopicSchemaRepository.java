package com.github.ogomezso.datacatalogapi.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.github.ogomezso.datacatalogapi.elastic.model.TopicSchema;

@Repository
public interface TopicSchemaRepository extends ElasticsearchRepository<TopicSchema, String> {

  List<TopicSchema> findAllBySubject(String subject);
}
