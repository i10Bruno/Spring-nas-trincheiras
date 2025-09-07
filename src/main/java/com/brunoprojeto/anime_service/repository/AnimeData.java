package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {
    private final List<Anime> ANIMES = new ArrayList<>();


    {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        ANIMES.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }


    public List<Anime> getANIMES() {
        return ANIMES;
    }
}