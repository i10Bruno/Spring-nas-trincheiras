package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.mapper.AnimeMapper;
import com.brunoprojeto.anime_service.request.AnimePostRequest;
import com.brunoprojeto.anime_service.request.AnimePutRequest;
import com.brunoprojeto.anime_service.response.AnimeGetResponse;
import com.brunoprojeto.anime_service.response.AnimePostResponse;
import com.brunoprojeto.anime_service.service.AnimeService;
import com.brunoprojeto.anime_service.service.ProducerService;
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
    private AnimeService Service;
    public AnimeController (){

        this.Service = new AnimeService();
    }


    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {

        //
        var animes = Service.findAll(name);
        var animeGetResponseList = MAPPER.toAnimeGetResponseList(animes);

        return ResponseEntity.ok(animeGetResponseList);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        var anime = Service.findById(id);
        var animeGetResponse = MAPPER.toAnimeGetResponse(anime);


        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest animePostRequest) {
        var animes = MAPPER.toAnimer(animePostRequest);
        var animeSaved  =Service.save(animes);
        var response = MAPPER.toAnimePostResponse(animeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("${id}")

    public  ResponseEntity<Void> deletebyid (@PathVariable Long id){
        Service.delete(id);
        return ResponseEntity.noContent().build();



    }

    @PutMapping

    public  ResponseEntity<Void> update (@PathVariable AnimePutRequest request){

        var animeUpdate= MAPPER.toAnimer(request);
        Service.update(animeUpdate);

        return ResponseEntity.noContent().build();



    }






}