package com.brunoprojeto.anime_service.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Anime {
    private String name;
    private Long id;

}
