package com.brunoprojeto.anime_service.response;

import com.brunoprojeto.anime_service.domain.Anime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class AnimeGetResponse {

    private Long id;
    private String name;



}
