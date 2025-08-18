package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Producer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

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
    public  ResponseEntity<Producer> add (@RequestBody Producer producer , @RequestHeader HttpHeaders headers){

        producer.setId(ThreadLocalRandom.current().nextLong(100_000));
         Producer.getProducers().add(producer);
         return ResponseEntity.status(HttpStatus.CREATED).body(producer);
    }


}
