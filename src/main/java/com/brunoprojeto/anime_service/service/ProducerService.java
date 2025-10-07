package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service

//cria construtores para todos quem tenha final
@RequiredArgsConstructor


public class ProducerService {

    private final ProducerHardCodedRepository repository;


    public List<Producer> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);

    }

    public Producer findById(Long id) {

        return repository.findByid(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "producers not found"));

    }


    public Producer save(Producer producer) {

        return repository.save(producer);
    }

    public void delete(Long id) {
        var producer = findById(id);
        repository.delete(producer);

    }


    public void update(Producer producerToUpdate) {

        var producer = findById(producerToUpdate.getId());

        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        repository.save(producer);
    }

}
