package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.exception.NotFoundException;
import com.brunoprojeto.anime_service.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class AnimeService {
    //importando  " o banco de dados " a simulação de um
    @Autowired
    private AnimeRepository repository;

    public List<Anime> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }
    public Page<Anime> findAllPaginated(Pageable pageable) {

        return repository.findAll(pageable);
    }


    public Anime findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("anime not found"));
    }


    public Anime save(Anime anime) {

        return repository.save(anime);
    }


    public void delete(Long id) {

        var animea = findById(id);
        repository.delete(animea);

    }

    public void update(Anime animetoup) {

        var anime = findById(animetoup.getId());

        repository.save(anime);
    }


}
