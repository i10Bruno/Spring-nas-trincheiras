package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.mapper.ProducerMapper;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.request.ProducerPutRequest;
import com.brunoprojeto.anime_service.response.ProducerGetResponse;
import com.brunoprojeto.anime_service.response.ProducerPostResponse;
import com.brunoprojeto.anime_service.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/producer")
@RequiredArgsConstructor

public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    private final ProducerService producerService;


    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> ListAll(@RequestParam(required = false) String name) {
        var producers = producerService.findAll(name);
        var ProducerGetresponse = MAPPER.toProducerGetResponselist(producers);

        return ResponseEntity.ok(ProducerGetresponse);

    }

    @GetMapping("{id}")

    public ResponseEntity<ProducerGetResponse> findByid(@PathVariable Long id) {

       Producer producer= producerService.findById(id);
       var producers = MAPPER.toProducerGetResponse(producer);
        return ResponseEntity.ok(producers);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        var producer = MAPPER.toProducer(producerPostRequest);

        var producerSaved = producerService.save(producer);

        var response = MAPPER.producerPostResponse(producerSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("{$id}")

    public ResponseEntity<Void> DeleteById(@PathVariable Long id) {
        producerService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("{$id}")

    public ResponseEntity<Void> updated(@PathVariable ProducerPutRequest request) {

        var requested= MAPPER.toProducerPutRequest(request);

        producerService.update(requested);

        return ResponseEntity.noContent().build();
    }


}
