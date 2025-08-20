package com.brunoprojeto.anime_service.request;

import com.brunoprojeto.anime_service.domain.Anime;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AnimePostRequest {
    private String name;
    private static List<Anime> animes = new ArrayList<>();


}
