package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimeHardCodedRepository {
    private static final List<Anime> ANIMES = new ArrayList<>();
    static {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        ANIMES.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }

    public static List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findByid(Long id){
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();

    }
    public  List<Anime> findByName(String name) {
        return ANIMES.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime save (Anime anime){
        ANIMES.add(anime);
        return anime;
    }
    public void delete(Anime anime){
        ANIMES.remove(anime);

    }
    public void update(Anime anime){
        delete(anime);
        save(anime);

    }

}
