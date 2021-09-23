package com.github.ogomezso.datacatalogapi.rest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.ogomezso.datacatalogapi.repository.model.TopicMetadata;
import com.github.ogomezso.datacatalogapi.service.MetadataSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UIController {

  private final MetadataSearchService searchService;


  @GetMapping("/search")
  public String home(Model model) {
    List<TopicMetadata> metadata = searchService.fetchByProduct("Hornby");

    List<String> products = metadata.stream().flatMap(meta -> Stream.of(meta.getProduct()))
        .collect(Collectors.toList());
    log.info("product names {}", products);
    model.addAttribute("names", products);
    return "search";
  }

}
