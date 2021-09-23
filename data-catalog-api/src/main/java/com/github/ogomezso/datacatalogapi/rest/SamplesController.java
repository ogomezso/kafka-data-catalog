package com.github.ogomezso.datacatalogapi.rest;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ogomezso.datacatalogapi.service.SearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class SamplesController {

  private final SearchService searchService;

  @GetMapping("/samples")
  @ResponseBody
  public List<String> fetchSuggestions(@RequestParam(value = "index", required = false) String index) {
    log.info("fetch documents in index: {}", index);
    List<String> documents = searchService.matchAllInIndex(index);
    log.info("documents {}",documents);
    return documents;
  }
}
