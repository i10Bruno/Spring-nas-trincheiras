package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.exception.DefaultErrorMessage;
import com.brunoprojeto.anime_service.exception.NotFoundException;
import com.brunoprojeto.anime_service.mapper.AnimeMapper;
import com.brunoprojeto.anime_service.request.AnimePostRequest;
import com.brunoprojeto.anime_service.request.AnimePutRequest;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import com.brunoprojeto.anime_service.response.AnimePostResponse;
import com.brunoprojeto.anime_service.service.AnimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper mapper;
    private final AnimeService Service;


    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        //
        var animes = Service.findAll(name);
        var animeGetResponseList = mapper.toAnimeGetResponseList(animes);

        return ResponseEntity.ok(animeGetResponseList);

    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AnimeGetResponse>>findAllPaginated(Pageable pageable) {
        //
        var animes = Service.findAllPaginated(pageable).map(mapper::toAnimeGetResponse);


        return ResponseEntity.ok(animes);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        var anime = Service.findById(id);
        var animeGetResponse = mapper.toAnimeGetResponse(anime);
        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody @Valid AnimePostRequest animePostRequest) {
        var animes = mapper.toAnimer(animePostRequest);
        var animeSaved = Service.save(animes);
        var response = mapper.toAnimePostResponse(animeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deletebyid(@PathVariable Long id) {
        Service.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping()

    public ResponseEntity<Void> update(@RequestBody  @Valid AnimePutRequest request) {

        var animeUpdate = mapper.toAnimer(request);
        Service.update(animeUpdate);

        return ResponseEntity.noContent().build();


    }









}