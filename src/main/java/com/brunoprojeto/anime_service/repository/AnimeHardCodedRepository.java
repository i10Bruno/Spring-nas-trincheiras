package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AnimeHardCodedRepository {

    private final AnimeData animeData;


    public List<Anime> findAll() {
        return animeData.getANIMES();
    }

    public Optional<Anime> findByid(Long id) {
        return animeData.getANIMES().stream().filter(anime -> anime.getId().equals(id)).findFirst();

    }

    public List<Anime> findByName(String name) {
        return animeData.getANIMES().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime save(Anime anime) {
        animeData.getANIMES().add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        animeData.getANIMES().remove(anime);

    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);

    }

}
