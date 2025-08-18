package com.brunoprojeto.anime_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class Producer {

    @JsonProperty("full_name")
    private Long id;
    private  String name;
    private LocalDateTime createdAt;


    private static  List<Producer> producers = new ArrayList<>();


    static {
        var maapa = new Producer(1L, "maapa",LocalDateTime.now());
        var kyotoAnimation = new Producer(2L, "kyoto animation",LocalDateTime.now());
        var madhouse = new Producer(3L, "Madhouse",LocalDateTime.now());
        producers.addAll(List.of(maapa, kyotoAnimation, madhouse));

    }


    public static List<Producer> getProducers() {
        return producers;
    }
}
