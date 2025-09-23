package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.config.Connection;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.mapper.ProducerMapper;
import com.brunoprojeto.anime_service.request.ProducerPostRequest;
import com.brunoprojeto.anime_service.request.ProducerPutRequest;
import com.brunoprojeto.anime_service.response.ProducerGetResponse;
import com.brunoprojeto.anime_service.response.ProducerPostResponse;
import com.brunoprojeto.anime_service.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/connections")
@RequiredArgsConstructor
@Slf4j

public class ConnectionController {
    private final Connection connectionMySql;


    @GetMapping

    public  ResponseEntity<Connection> getConnections(){

        return ResponseEntity.ok(connectionMySql);


    }
}