package com.github.ogomezso.datacatalogapi.elastic.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SearchService {

  private final ElasticsearchOperations elasticsearchOperations;

  public List<String> matchAllInIndex(String index) {

    Query query = new NativeSearchQueryBuilder()
        .withFilter(QueryBuilders.matchAllQuery())
        .withMaxResults(10)
        .build();

    SearchHits<Object> searchHits = elasticsearchOperations.search(query, Object.class,
        IndexCoordinates.of(index));
    List<String> result = new ArrayList<>();
    searchHits.forEach(stringSearchHit -> result.add(stringSearchHit.getContent().toString()));
    return result;
  }

}
