package com.brunoprojeto.anime_service.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private static final List<String> Heroes = List.of("guts", "ZORO", "Kakashi", "Goku");


    @GetMapping

    public List<String> ListAllHeroes() {

        return Heroes;
    }

    @GetMapping("Filter")
    public List<String> ListFilterHeroes(@RequestParam(defaultValue = "") String name) {

        return Heroes.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("FilterList")
    public List<String> ListFilterHeroes(@RequestParam(defaultValue = "") List<String> names) {

        return Heroes.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String FindByName(@PathVariable String name) {

        return Heroes.stream().filter(hero -> hero.equalsIgnoreCase(name)).findFirst().orElse("");
    }


}
