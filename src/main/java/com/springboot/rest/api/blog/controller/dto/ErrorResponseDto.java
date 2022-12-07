package com.springboot.rest.api.blog.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Class that represents RFC7807 and RFC7231 error description
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

  @Schema(description = " A short, human-readable summary of the problem " +
    " type. It SHOULD NOT change from occurrence to occurrence of the " +
    " problem, except for purposes of localization (e.g., using " +
    " proactive content negotiation; see [RFC7231], Section 3.4). "
    , example = "Error")
  String title;

  @Schema(description = " A human-readable description of the specific error."
    , example = "Post not found with id = 1")
  Object message;

  @Schema(description = " A Json Object of the validation errors messages by field."
    , example = "{}")
  Object validationMessage;

  @Schema(description = "The HTTP status code ([RFC7231], Section 6) " +
    "      generated by the origin server for this occurrence of the problem.",
    example = "404")
  private Integer status;

  private Long timestamp;

}