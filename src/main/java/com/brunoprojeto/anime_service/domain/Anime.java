package com.brunoprojeto.anime_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
//comparar obj pelos atributos  e não pela memoria
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Anime {
    @EqualsAndHashCode.Include
    @JsonProperty("full_name")
    private String name;
    private Long id;

}
