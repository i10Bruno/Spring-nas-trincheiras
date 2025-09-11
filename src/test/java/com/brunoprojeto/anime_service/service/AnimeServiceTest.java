package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeServiceTest {

    @Mock
    private AnimeHardCodedRepository repository;


    @InjectMocks
    private  AnimeService service;

    private List<Anime> AnimesList;


    @BeforeEach
    void  init () {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        AnimesList= new ArrayList<>(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }


    @Test
    @DisplayName("findall return a list whith all animes when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull (){
        BDDMockito.when(repository.findAll()).thenReturn(AnimesList);
        var animes =repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().hasSameElementsAs(AnimesList);

    }
    @Test
    @DisplayName("findAll returns List with found object when name exists")
    @Order(2)
    void findByname_ReturnsFoundProducerInList_WhenNameIsFound (){
        var expected = AnimesList.getFirst();

        var animesExpected= Collections.singletonList(expected);
        BDDMockito.when(repository.findByName(expected.getName())).thenReturn(animesExpected);
        var animes =service.findAll(expected.getName());
        org.assertj.core.api.Assertions.assertThat(animes).containsAll(animesExpected);

    }





}
