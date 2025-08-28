package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();
    static {
        var maapa = Producer.builder().id(1L).name("mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producer.builder().id(2L).name("kyoto animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        PRODUCERS.addAll(List.of(maapa, kyotoAnimation, madhouse));

    }


    public static List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer>findByid(Long id){
       return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();

    }
    public  List<Producer> findByName(String name) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer){
        PRODUCERS.add(producer);
        return producer;
    }
    public void delete(Producer producer){
        PRODUCERS.remove(producer);

    }
    public void update(Producer producer){
        delete(producer);
        save(producer);

    }


}
