package com.github.ogomezso.datacatalogapi.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.ogomezso.datacatalogapi.elastic.repository.TopicSchemaRepository;
import com.github.ogomezso.datacatalogapi.elastic.model.TopicSchema;
import com.github.ogomezso.datacatalogapi.rest.model.TopicSchemaResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SchemaController {

  private final TopicSchemaRepository repository;

  @GetMapping("/schemas")
  @ResponseBody
  public List<TopicSchemaResponse> fetchSuggestions(@RequestParam(value = "subject", required = false) String subject) {
    log.info("fetch schemas for subject: {}", subject);
    List<TopicSchema> schemas = repository.findAllBySubject(subject);
    log.info("fetch schemas for subject: {}", schemas);
    return schemas.stream().map(
        schema -> TopicSchemaResponse.builder()
            .version(schema.getVersion())
            .schema(schema.getSchema())
            .build()
    ).collect(Collectors.toList());
  }

}
