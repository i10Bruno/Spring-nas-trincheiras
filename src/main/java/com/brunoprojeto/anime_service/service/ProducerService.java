package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.exception.NotFoundException;
import com.brunoprojeto.anime_service.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

//cria construtores para todos quem tenha final
@RequiredArgsConstructor


public class ProducerService {
    @Autowired
    private  ProducerRepository repository;


    public List<Producer> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);


    }

    public Producer findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("producers not found"));

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
        repository.save(producer);
    }

}
