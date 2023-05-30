package com.jumkid.domain.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class DomainData {

    @Min(0L)
    private Long id;

    @NotBlank(message = "industry is required")
    private String industry;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "value is required")
    private String value;

}
