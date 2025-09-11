package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;


    @Mock
    private  AnimeService service;

    private List<Anime> AnimesList;


    @BeforeEach
    void  init () {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        AnimesList= new ArrayList<>(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }





}
