package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.commons.AnimeUtils;
import com.brunoprojeto.anime_service.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeHardCodedRepositoryTests {

    @InjectMocks

    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;

    @InjectMocks
    private AnimeUtils animeUtils;

    private List<Anime> AnimesList;

    @BeforeEach
    void init() {

        AnimesList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("findall return a list whith all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccesful() {

        // ensinando meu repositorio mock oq teria que retornar
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);

        var animes = repository.findAll();
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(AnimesList);

    }

    @Test
    @DisplayName("findByid return an anime with given id")
    @Order(2)
    void findByid_ReturnsAnimeById_WhenSuccesful() {

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
    void findByName_ReturnsAnimeByName_WhenSuccesful() {

        // ensinando meu repositorio mock oq teria que retornar
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        //pega o primeiro item da nossa lista;
        var expected = AnimesList.getFirst();
        //pega o id do obj dessa lista e procura no repository
        var animes = repository.findByName(expected.getName());


        org.assertj.core.api.Assertions.assertThat(animes).hasSize(1).contains(expected);

    }

    //findByName
    @Test
    @DisplayName("findByname returns empty when name is null")
    @Order(4)
    void findByname_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var animes = repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(animes).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("save creates an anime")
    @Order(5)
    void save_CreatesAnime_WhenSuccesfull() {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected = animeUtils.newAnimeToSave();


        var animes = repository.save(expected);
        Assertions.assertThat(animes).isEqualTo(expected).hasNoNullFieldsOrProperties();

        var AnimeSavedOptional = repository.findByid(expected.getId());

        Assertions.assertThat(AnimeSavedOptional).isPresent().contains(expected);

    }

    @Test
    @DisplayName("delete removes an anime")
    @Order(6)
    void delete_RemoveAnime_WhenSuccesfull() {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected = AnimesList.getFirst();


        repository.delete(expected);

        var animes = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animes).isNotEmpty().doesNotContain(expected);

    }

    @Test
    @DisplayName("update updates an anime")
    @Order(7)
    void update_UpdatesAnime_WHenSuccesfull() {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var expected = this.AnimesList.getFirst();
        expected.setName("neymar");
        repository.update(expected);

        Assertions.assertThat(this.AnimesList).contains(expected);

        var animeuptadedoptional = repository.findByid(expected.getId());

        Assertions.assertThat(animeuptadedoptional).isPresent();


        Assertions.assertThat(animeuptadedoptional.get())
                .isEqualTo(expected);


    }


}
