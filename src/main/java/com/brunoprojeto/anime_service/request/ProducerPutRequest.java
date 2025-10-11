package com.brunoprojeto.anime_service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProducerPutRequest {

    @NotNull(message = "The field 'id' cannot be null")
    private Long id;

    @NotBlank(message ="The field 'Name' is required")
    private String name;


}
