package com.brunoprojeto.anime_service.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Anime {
    private  String name;
    private Long id;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        var ninjaKamui = new Anime( 1L,"Ninja Kamui");
        var kaijuu = new Anime( 2L, "Kaijuu-8gou");
        var kimetsuNoYaiba = new Anime(3L,  "Kimetsu No Yaiba");

        return List.of(ninjaKamui, kaijuu, kimetsuNoYaiba);
    }


    public List<String> retorno(List<String> animes){

        return animes;
    }

}
