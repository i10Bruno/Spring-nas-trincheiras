package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.mapper.AnimeMapper;
import com.brunoprojeto.anime_service.request.AnimePostRequest;
import com.brunoprojeto.anime_service.request.AnimePutRequest;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import com.brunoprojeto.anime_service.response.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        var animeGetResponseList = MAPPER.toAnimeGetResponseList(animes);

        if (name == null) return ResponseEntity.ok(animeGetResponseList);

        var response = animeGetResponseList.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok(response);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        var animeGetResponse = Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst().map(MAPPER::toAnimeGetResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found"));

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {


        var animes = MAPPER.toAnimer(animePostRequest);

        Anime.getAnimes().add(animes);
        var response = MAPPER.toAnimePostResponse(animes);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("${id}")

    public  ResponseEntity<Void> deletebyid (@PathVariable Long id){

        var animeGetResponse = Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst().map(MAPPER::toAnimeGetResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found"));



        Anime.getAnimes().remove(animeGetResponse);
        return ResponseEntity.noContent().build();



    }

    @PutMapping

    public  ResponseEntity<Void> update (@PathVariable AnimePutRequest request){

        var animeToRemove = Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(request.getId()))
                .findFirst().map(MAPPER::toAnimeGetResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found"));

        var animeUpdate= MAPPER.toAnimer(request);

        Anime.getAnimes().remove(animeUpdate);
        Anime.getAnimes().add(animeUpdate);
        return ResponseEntity.noContent().build();



    }






}