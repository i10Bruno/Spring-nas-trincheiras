package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.commons.AnimeUtils;
import com.brunoprojeto.anime_service.commons.ProducerUtils;
import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimeServiceTest {

    @Mock
    private AnimeHardCodedRepository repository;


    @InjectMocks
    private  AnimeService service;

    @InjectMocks
    private AnimeUtils animeUtils;

    private List<Anime> AnimesList;


    @BeforeEach
    void  init () {
        AnimesList=animeUtils.newAnimeList();
    }


    @Test
    @DisplayName("findall return a list whith all animes when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull (){
        BDDMockito.when(repository.findAll()).thenReturn(AnimesList);
        var animesFound =repository.findAll();
        org.assertj.core.api.Assertions.assertThat(animesFound).isNotNull().hasSameElementsAs(AnimesList);

    }
    @Test
    @DisplayName("findAll returns List with found object when name exists")
    @Order(2)
    void findByname_ReturnsFoundProducerInList_WhenNameIsFound (){
        var expected = AnimesList.getFirst();

        var animesExpected= Collections.singletonList(expected);
        BDDMockito.when(repository.findByName(expected.getName())).thenReturn(animesExpected);
        var animesFound =service.findAll(expected.getName());
        org.assertj.core.api.Assertions.assertThat(animesFound).containsAll(animesExpected);

    }

    @Test
    @DisplayName("findALL returns Empty list when name is not found")
    @Order(3)
    void findByname_ReturnsEmptyList_WhenNameIsNotFound(){
        var name ="not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var animesFound =service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(animesFound).isEmpty();


    }
    @Test
    @DisplayName("findByid returns a producer with given id")
    @Order(4)
    void findByid_returnsProducerById_WhenSucceful(){

        var name = AnimesList.getFirst();
        BDDMockito.when(repository.findByid(name.getId())).thenReturn(Optional.of(name));
        var animesFound =service.findById(name.getId());
        org.assertj.core.api.Assertions.assertThat(animesFound).isEqualTo(name);

    }

    @Test
    @DisplayName("findByid  throws ResponseStatusException when producer is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedAnime = AnimesList.getFirst();

        BDDMockito.when(repository.findByid(expectedAnime.getId())).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> service.findById(expectedAnime.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }
    @Test
    @DisplayName("Save creates a anime")
    @Order(6)
    void Save_CreatesProducer_WhenSuccesful(){
        var expectedAnime = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(expectedAnime)).thenReturn(expectedAnime);
        var animeSaved =service.save(expectedAnime);

        org.assertj.core.api.Assertions.assertThat(animeSaved).isEqualTo(expectedAnime).hasNoNullFieldsOrProperties();


    }
    @Test
    @DisplayName("delete remove a anime")
    @Order(7)
    void delete_RemoveAnime_whenSuccesful(){
        var expectedAnime = AnimesList.getFirst();
        BDDMockito.when(repository.findByid(expectedAnime.getId())).thenReturn(Optional.of(expectedAnime));
        var animesFound =service.findAll(expectedAnime.getName());

        BDDMockito.doNothing().when(repository).delete(expectedAnime);
        assertThatNoException().isThrownBy(()->service.delete(expectedAnime.getId()));

    }
    @Test
    @DisplayName("delete throws ResponseStatusException when Anime is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_whenAnimesIsNotFound(){
        var animeToDelete= AnimesList.getFirst();
        BDDMockito.when(repository.findByid(animeToDelete.getId())).thenReturn(Optional.empty());


        assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }
    @Test
    @DisplayName("uptate updates a  anime")
    @Order(9)
    void update_updatesAnime_WhenSuccesful(){

        var animeToupdate = AnimesList.getFirst();
        animeToupdate.setName("neymar");
        BDDMockito.when(repository.findByid(animeToupdate.getId())).thenReturn(Optional.of(animeToupdate));


        assertThatNoException()
                .isThrownBy(() -> service.update(animeToupdate));



    }
    @Test
    @DisplayName("update throws ResponseStatusException when anime is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_whenAnimeIsNotFound(){
        var animeToUP= AnimesList.getFirst();
        BDDMockito.when(repository.findByid(ArgumentMatchers.anyLong())
        ).thenReturn(Optional.empty());


        assertThatException()
                .isThrownBy(() -> service.update(animeToUP))
                .isInstanceOf(ResponseStatusException.class);

    }



}
