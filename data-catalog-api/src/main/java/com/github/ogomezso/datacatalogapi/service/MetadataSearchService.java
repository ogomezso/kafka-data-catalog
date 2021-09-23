package com.github.ogomezso.datacatalogapi.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

import com.github.ogomezso.datacatalogapi.repository.model.TopicMetadata;
import com.github.ogomezso.datacatalogapi.repository.TopicMetadataRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetadataSearchService {

  private static final String TOPIC_METADATA_INDEX = "metadata_topic";

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
            .multiMatchQuery(query, "product","domain","topic")
            .fuzziness(Fuzziness.AUTO);

    Query searchQuery = new NativeSearchQueryBuilder()
        .withFilter(queryBuilder)
        .build();

    SearchHits<TopicMetadata> topicMetadataSearchHits =
        elasticsearchOperations
            .search(searchQuery, TopicMetadata.class,
                IndexCoordinates.of(TOPIC_METADATA_INDEX));

    List<TopicMetadata> topicMetadataMatches = new ArrayList<TopicMetadata>();
    topicMetadataSearchHits.forEach(srchHit -> {
      topicMetadataMatches.add(srchHit.getContent());
    });
    return topicMetadataMatches;
  }

  public List<String> fetchSuggestions(String query) {

    QueryBuilder queryBuilder = QueryBuilders
        .queryStringQuery("*"+query+"*")
        .field("product")
        .field("domain")
        .field("topic");

    Query searchQuery = new NativeSearchQueryBuilder()
        .withFilter(queryBuilder)
        .withPageable(PageRequest.of(0, 5))
        .build();

    SearchHits<TopicMetadata> searchSuggestions =
        elasticsearchOperations.search(searchQuery,
            TopicMetadata.class,
            IndexCoordinates.of(TOPIC_METADATA_INDEX));

    List<String> suggestions = new ArrayList<>();

    searchSuggestions.getSearchHits().forEach(searchHit->{
      suggestions.add(searchHit.getContent().getProduct());
    });
    return new ArrayList<>(new HashSet<>(suggestions));
  }
}
