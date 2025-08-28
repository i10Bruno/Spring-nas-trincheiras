package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import com.brunoprojeto.anime_service.request.AnimePutRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class AnimeService {
    //importando  " o banco de dados " a simulação de um
    private AnimeHardCodedRepository repository;

    public AnimeService(){

        this.repository =new AnimeHardCodedRepository();

    }

    public List<Anime>findAll(String name){

       return name == null ? repository.findAll() : repository.findByName(name);
    }
    public Anime findById(Long id) {
        return repository.findByid(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " anime not found"));
    }


    public Anime save(Anime anime){

        return repository.save(anime);
    }


    public void delete (Long id){

       var animea=findById(id);
       repository.delete(animea);

    }

    public void update (Anime animetoup){

        var anime = findById(animetoup.getId());

        repository.save(anime);
    }


}
