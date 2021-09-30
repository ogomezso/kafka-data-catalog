package com.github.ogomezso.datacatalogapi.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.ogomezso.datacatalogapi.config.AppConfig;
import com.github.ogomezso.datacatalogapi.elastic.model.TopicMetadata;
import com.github.ogomezso.datacatalogapi.elastic.service.MetadataSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UIController {

  private final MetadataSearchService searchService;
  private final AppConfig appConfig;


  @GetMapping("/search")
  public String home(Model model) {
    List<TopicMetadata> metadata = searchService.fetchByProduct("Hornby");

    List<String> topicMetadata = metadata.stream().flatMap(meta -> Stream.of(meta.getProduct()))
        .collect(Collectors.toList());
    List<String> searchData = appConfig.getSearchData().getFields();
    log.info("TopicMetadata {}", topicMetadata);
    model.addAttribute("topicMetadata", topicMetadata);
    model.addAttribute("searchData", searchData);
    return "search";
  }

}
