package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.response.ProducerGetResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producer")
public class ProducerController {


    @GetMapping

    public  List<Producer> ListAllHeroes(@RequestParam(required = false) String name){
         var producers = Producer.getProducers();

         if(name == null) return producers;

         return producers.stream().filter(producer->producer.getName().equalsIgnoreCase(name)).toList();

    }
    @GetMapping("{id}")

    public Producer findByid (@PathVariable Long id){;
        return Producer.getProducers().stream().filter
                (producer -> producer.getId().equals(id)).findFirst().orElse(null);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes =MediaType.APPLICATION_JSON_VALUE,headers = "x-api-key")
    public  ResponseEntity<ProducerGetResponse> add (@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers){
       var producer= Producer.builder().id(ThreadLocalRandom.current().nextLong(100_000))
                .name(producerPostRequest.getName())
                .createdAt(LocalDateTime.now()).build();

         Producer.getProducers().add(producer);
        var response = ProducerGetResponse.builder().name(producer.getName()).id(producer.getId()).createdAt(producer.getCreatedAt()).build();

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
