package com.brunoprojeto.anime_service.controller;


import com.brunoprojeto.anime_service.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    @GetMapping

    public static List<Anime> ListAllHeroes(){

        return Anime.getAnimes();
    }
    @GetMapping

    public static List<Anime> ListAllHeroes(@RequestParam(required = false) String name){
         var animes = Anime.getAnimes();

         if(name == null) return animes;

         return animes.stream().filter(anime->anime.getName().equalsIgnoreCase(name)).toList();

    }
    @GetMapping("{id}")

    public static Anime findByid (@PathVariable Long id){;
        return Anime.getAnimes().stream().filter
                (anime -> anime.getId().equals(id)).findFirst().orElse(null);

    }


}
