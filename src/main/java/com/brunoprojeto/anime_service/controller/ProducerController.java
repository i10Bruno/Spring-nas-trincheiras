package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.mapper.ProducerMapper;
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

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;


    @GetMapping

    public  ResponseEntity<List<ProducerGetResponse>>ListAllHeroes(@RequestParam(required = false) String name){
         var producers = Producer.getProducers();
         var response = MAPPER.toProducerGetResponse(producers);

         if(name == null) return ResponseEntity.ok(response);

         return ResponseEntity.ok(response.stream().filter(producer->producer.getName().equalsIgnoreCase(name)).toList());

    }
    @GetMapping("{id}")

    public ResponseEntity<ProducerGetResponse> findByid (@PathVariable Long id){;
        var producers =Producer.getProducers().stream().filter
                (producer -> producer.getId().equals(id)).findFirst().orElse(null);

        var response= MAPPER.toProducerGetResponse(producers);


        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes =MediaType.APPLICATION_JSON_VALUE,headers = "x-api-key")
    public  ResponseEntity<ProducerGetResponse> add (@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers){
       var producer= MAPPER.toProducer(producerPostRequest);
        var response= MAPPER.toProducerGetResponse(producer);

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
