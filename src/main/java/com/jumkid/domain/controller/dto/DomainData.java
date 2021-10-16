package com.jumkid.domain.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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
