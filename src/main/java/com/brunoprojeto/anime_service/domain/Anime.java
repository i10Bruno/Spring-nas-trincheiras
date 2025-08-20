package com.brunoprojeto.anime_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
@Setter
public class Anime {
    private  String name;
    private Long id;

    private static  List<Anime> animes = new ArrayList<>();

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static {
        var ninjaKamui = new Anime( 1L,"Ninja Kamui");
        var kaijuu = new Anime( 2L, "Kaijuu-8gou");
        var kimetsuNoYaiba = new Anime(3L,  "Kimetsu No Yaiba");
        animes.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));

    }



    public List<String> retorno(List<String> animes){

        return animes;
    }

    public static List<Anime> getAnimes() {
        return animes;
    }


}
