package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeHardCodedRepositoryTests {

    @InjectMocks

    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;

    private  final List<Anime> AnimesList = new ArrayList<>();

    @BeforeEach
   void  init () {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        AnimesList.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }
    @Test
    @DisplayName("findall return a list whith all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccesful (){

        // ensinando meu repositorio mock oq teria que retornar
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);

        var animes = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().hasSameElementsAs(AnimesList);


    }






}
