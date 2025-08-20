package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.mapper.AnimeMapper;
import com.brunoprojeto.anime_service.mapper.ProducerMapper;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper MAPPER =  AnimeMapper.INSTANCE;
    @GetMapping
    public List<Anime> listAll(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        if (name == null) return animes;

        return animes.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        var animeGetResponse= Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst().map(MAPPER::toAnimeGetResponse).orElse(null);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public Anime save(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(100_000));
        Anime.getAnimes().add(anime);
        return anime;
    }
}