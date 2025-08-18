package com.brunoprojeto.anime_service.controller;


import com.brunoprojeto.anime_service.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    @GetMapping
    public  List<Anime> ListAllHeroes(){

        return Anime.getAnimes();
    }
    @GetMapping("nomes")
    public  List<Anime> ListHeroes(@RequestParam(required = false) String name){
         var animes = Anime.getAnimes();

         if(name == null) return animes;

         return animes.stream().filter(anime->anime.getName().equalsIgnoreCase(name)).toList();

    }
    @GetMapping("{id}")

    public  Anime findByid (@PathVariable Long id){;
        return Anime.getAnimes().stream().filter
                (anime -> anime.getId().equals(id)).findFirst().orElse(null);

    }

    @PostMapping
    public Anime add (@RequestBody Anime animes){

        animes.setId(ThreadLocalRandom.current().nextLong(100_000));
         Anime.getAnimes().add(animes);
         return animes;
    }


}
