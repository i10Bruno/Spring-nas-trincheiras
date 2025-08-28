package com.brunoprojeto.anime_service.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Anime {
    @EqualsAndHashCode.Include
    private String name;
    private Long id;

}
