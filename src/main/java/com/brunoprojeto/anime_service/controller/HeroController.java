package com.brunoprojeto.anime_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private static final List<String> Heroes = List.of("guts","ZORO","Kakashi","Goku");



    @GetMapping

    private List<String>ListAllHeroes(){

        return Heroes;
    }
    @GetMapping("Filter")
    private List<String>ListFilterHeroes(@RequestParam(defaultValue = "") String name){

        return Heroes.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }
    @GetMapping("FilterList")
    private List<String>ListFilterHeroes(@RequestParam(defaultValue = "") List<String> names){

        return Heroes.stream().filter(names::contains).toList();
    }




}
