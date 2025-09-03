package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Producer;
import external.dependency.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor

public class ProducerHardCodedRepository {

    private final Connection connection;
    private  final ProducerData producerData;


    public  List<Producer> findAll() {
        return producerData.getProducers();
    }

    public Optional<Producer>findByid(Long id){
       return producerData.getProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst();

    }
    public  List<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer){
        producerData.getProducers().add(producer);
        return producer;
    }
    public void delete(Producer producer){
        producerData.getProducers().remove(producer);

    }
    public void update(Producer producer){
        delete(producer);
        save(producer);

    }


}
