package com.brunoprojeto.anime_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnimePostRequest {
    @NotBlank(message ="The field 'Name' is required")
    private String name;


}
