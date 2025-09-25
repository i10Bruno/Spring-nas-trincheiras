package com.brunoprojeto.anime_service.commons;

import com.brunoprojeto.anime_service.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList() {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        return new ArrayList<>(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }

    public Anime newAnimeToSave() {
        return Anime.builder().id(1L).name("Ninja Kamui").build();
    }


}
