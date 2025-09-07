package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Anime;
import org.assertj.core.api.Assertions;
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
    @Test
    @DisplayName("findall a Anime by id")
    @Order(2)
    void findByid_ReturnsAnimeById_WhenSuccesful (){

        // ensinando meu repositorio mock oq teria que retornar
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        //pega o primeiro item da nossa lista;
        var expected = AnimesList.getFirst();
        //pega o id do obj dessa lista e procura no repository
        var animes = repository.findByid(expected.getId());


        // verifique no anime se tem algo presente e contem o obj experado
        org.assertj.core.api.Assertions.assertThat(animes).isPresent().contains(expected);

    }
    @Test
    @DisplayName("findall a Anime by Name")
    @Order(3)
    void findByName_ReturnsAnimeByName_WhenSuccesful (){

        // ensinando meu repositorio mock oq teria que retornar
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        //pega o primeiro item da nossa lista;
        var expected = AnimesList.getFirst();
        //pega o id do obj dessa lista e procura no repository
        var animes = repository.findByName(expected.getName());


        org.assertj.core.api.Assertions.assertThat(animes).contains(expected);

    }
    //findByName
    @Test
    @DisplayName("findByname returns empty when name is null")
    @Order(4)
    void findByname_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var animes= repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEmpty();

    }
    @Test
    @DisplayName("save")
    @Order(5)
    void save(){
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected=Anime.builder().id(1L).name("Ninja Kamui").build();


        var animes= repository.save(expected);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEqualTo(expected);

    }
    @Test
    @DisplayName("delete")
    @Order(6)

    void delete(){
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected=AnimesList.getFirst();
        repository.delete(expected);
        var animes = expected.getName();
        org.assertj.core.api.Assertions.assertThat(this.AnimesList).doesNotContain(expected);

    }
    @Test
    @DisplayName("updated")
    @Order(7)



    void updated(){
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected=this.AnimesList.getFirst();
        expected.setName("neymar");
        repository.update(expected);

        Assertions.assertThat(this.AnimesList).contains(expected);

        var animeuptadedoptional = repository.findByid(expected.getId());

        Assertions.assertThat(animeuptadedoptional).isPresent();


        Assertions.assertThat(animeuptadedoptional.get())
                .isEqualTo(expected);


    }








}
