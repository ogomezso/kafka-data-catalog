package com.github.ogomezso.datacatalogapi.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ogomezso.datacatalogapi.elastic.model.TopicMetadata;
import com.github.ogomezso.datacatalogapi.elastic.service.MetadataSearchService;
import com.github.ogomezso.datacatalogapi.rest.model.TopicMetadataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class TopicMetadataController {

  private final MetadataSearchService metadataSearchService;

  @GetMapping("/metadata")
  @ResponseBody
  public List<TopicMetadataResponse> fetchBySearchCriteria(
      @RequestParam(value = "q", required = false) String query) {
    log.info("searching by product {}", query);
    List<TopicMetadata> metadataByProduct = metadataSearchService.processSearch(query);
    log.info("products {}", metadataByProduct);

    return metadataByProduct.stream().map(
        topicMetadata -> TopicMetadataResponse.builder()
            .domain(topicMetadata.getDomain())
            .ownerContact(topicMetadata.getOwnerContact())
            .ownerTeam(topicMetadata.getOwnerTeam())
            .product(topicMetadata.getProduct())
            .topic(topicMetadata.getTopic())
            .topicDescription(topicMetadata.getTopicDescription())
            .version(topicMetadata.getVersion())
            .build()
    ).collect(Collectors.toList());
  }

  @GetMapping("/suggestions")
  @ResponseBody
  public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query,
      @RequestParam(value = "f", required = false) String field) {
    log.info("fetch suggests {}", query);
    List<String> suggests = metadataSearchService.fetchSuggestions(query, field);
    log.info("suggests {}", suggests);
    return suggests;
  }
}
