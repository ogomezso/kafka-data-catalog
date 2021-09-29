package com.github.ogomezso.datacatalogapi.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.ogomezso.datacatalogapi.config.AppConfig;
import com.github.ogomezso.datacatalogapi.repository.TopicMetadataRepository;
import com.github.ogomezso.datacatalogapi.repository.model.TopicMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetadataSearchService {

  private final AppConfig config;
  private final TopicMetadataRepository topicMetadataRepository;
  private final ElasticsearchOperations elasticsearchOperations;

  public List<TopicMetadata> fetchByProduct(final String product) {

    return topicMetadataRepository.findByProductContaining(
        product);
  }

  public List<TopicMetadata> processSearch(final String query) {
    log.info("Search with query {}", query);

    QueryBuilder queryBuilder =
        QueryBuilders
            .multiMatchQuery(query, String.valueOf(config.getSearchCriteria().getFields()))
            .fuzziness(Fuzziness.AUTO);

    Query searchQuery = new NativeSearchQueryBuilder()
        .withFilter(queryBuilder)
        .build();

    SearchHits<TopicMetadata> topicMetadataSearchHits =
        elasticsearchOperations
            .search(searchQuery, TopicMetadata.class,
                IndexCoordinates.of(config.getMetadataIndex()));

    List<TopicMetadata> topicMetadataMatches = new ArrayList<TopicMetadata>();
    topicMetadataSearchHits.forEach(srchHit -> {
      topicMetadataMatches.add(srchHit.getContent());
    });
    return topicMetadataMatches;
  }

  public List<String> fetchSuggestions(String query) {

    Map<String, Float> weightedFields = IntStream.range(0,
            config.getSearchCriteria().getFields().size())
        .boxed()
        .collect(Collectors.toMap(config.getSearchCriteria().getFields()::get,
            config.getSearchCriteria().getWeights()::get));

    QueryBuilder queryBuilder = QueryBuilders
        .queryStringQuery("*" + query + "*")
        .fields(weightedFields);

    Query searchQuery = new NativeSearchQueryBuilder()
        .withFilter(queryBuilder)
        .withPageable(PageRequest.of(0, 5))
        .build();

    SearchHits<TopicMetadata> searchSuggestions =
        elasticsearchOperations.search(searchQuery,
            TopicMetadata.class,
            IndexCoordinates.of(config.getMetadataIndex()));

    List<String> suggestions = new ArrayList<>();

    searchSuggestions.getSearchHits().forEach(searchHit -> {
      suggestions.add(searchHit.getContent().getTopic());
    });
    return new ArrayList<>(new HashSet<>(suggestions));
  }
}
