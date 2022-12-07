package com.springboot.rest.api.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class JsonUtil {

  private ObjectMapper objectMapper;

  public String mapToJson(Map elements) {

//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JavaTimeModule());

    try {
      return objectMapper.writeValueAsString(elements);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
