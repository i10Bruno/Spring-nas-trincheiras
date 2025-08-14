package com.brunoprojeto.anime_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
public class Producer {

    @JsonProperty("full_name")
    private  String name;
    private Long id;

    private static  List<Producer> producers = new ArrayList<>();

    public Producer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        var maapa = new Producer(1L, "maapa");
        var kyotoAnimation = new Producer(2L, "kyoto animation");
        var madhouse = new Producer(3L, "Madhouse");
        producers.addAll(List.of(maapa, kyotoAnimation, madhouse));

    }


    public static List<Producer> getProducers() {
        return producers;
    }


}
