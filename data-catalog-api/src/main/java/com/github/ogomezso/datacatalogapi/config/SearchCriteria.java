package com.github.ogomezso.datacatalogapi.config;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchCriteria {

  private final AppConfig config;

  public Map<String,Float> getSearchCriteria(){

    return IntStream.range(0,
            config.getSearchData().getFields().size())
        .boxed()
        .collect(Collectors.toMap(config.getSearchData().getFields()::get,
            config.getSearchData().getWeights()::get));
  }
}
