package com.github.ogomezso.datacatalogapi.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.github.ogomezso.datacatalogapi.elastic.model.TopicMetadata;

@Repository
public interface TopicMetadataRepository extends ElasticsearchRepository<TopicMetadata,String> {


  List<TopicMetadata> findByProductContaining(String product);
}
