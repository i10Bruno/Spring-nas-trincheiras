package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producerList; // 1 usage

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();

        producerList= new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }



    @Test
    @DisplayName("findall return a list whith all producers when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull(){
        BDDMockito.when(repository.findAll()).thenReturn(producerList);
        var producers= repository.findAll();
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);

    }
    @Test
    @DisplayName("findAll returns List with found object when name exists")
    @Order(2)
    void findByname_ReturnsFoundProducerInList_WhenNameIsFound(){
        //primeiro elemento da lista
        var producer = producerList.getFirst();
        //
        var ExpectedProducerFound = Collections.singletonList(producer);
        //ao chamar o repositorio findybyname com o nome da nossa lista injetada retorn uma lista com um unico elemento
        BDDMockito.when(repository.findByName(producer.getName())).thenReturn(ExpectedProducerFound);

        var producersFound= service.findAll(producer.getName());
        org.assertj.core.api.Assertions.assertThat(producersFound).containsAll(ExpectedProducerFound);

    }
    @Test
    @DisplayName("findALL returns Empty list when name is not found")
    @Order(3)
    void findByname_ReturnsEmptyList_WhenNameIsNotFound(){
        var name ="not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var producersFound= service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(producersFound).isNotNull().isEmpty();

    }
    @Test
    @DisplayName("findByid returns a producer with given id")
    @Order(4)
    void findByid_returnsProducerById_WhenSucceful(){
        var expectedProducer =producerList.getFirst();

        BDDMockito.when(repository.findByid(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producersFound= service.findById(expectedProducer.getId());
        org.assertj.core.api.Assertions.assertThat(producersFound).isEqualTo(expectedProducer);

    }
    @Test
    @DisplayName("findByid  throws ResponseStatusException when producer is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expectedProducer = producerList.getFirst();

        BDDMockito.when(repository.findByid(expectedProducer.getId())).thenReturn(Optional.empty());

        assertThatException()
                .isThrownBy(() -> service.findById(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("Save creates a producer")
    @Order(6)
    void Save_CreatesProducer_WhenSuccesful(){
        var ProducerToSave=Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        BDDMockito.when(repository.save(ProducerToSave)).thenReturn(ProducerToSave);

        var SavedProducer= service.save(ProducerToSave);
        org.assertj.core.api.Assertions.assertThat(SavedProducer).isEqualTo(ProducerToSave).hasNoNullFieldsOrProperties();

    }
    @Test
    @DisplayName("delete remove a producer")
    @Order(7)
    void delete_RemoveProducers_whenSuccesful(){
        var producerToDelete= producerList.getFirst();
        BDDMockito.when(repository.findByid(producerToDelete.getId())).thenReturn(Optional.of(producerToDelete));
        BDDMockito.doNothing().when(repository).delete(producerToDelete);
        assertThatNoException().isThrownBy(()->service.delete(producerToDelete.getId()));

    }
    @Test
    @DisplayName("delete throws ResponseStatusException when producer is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_whenProducerIsNotFound(){
        var producerToDelete= producerList.getFirst();
        BDDMockito.when(repository.findByid(producerToDelete.getId())).thenReturn(Optional.empty());


       assertThatException()
                .isThrownBy(() -> service.delete(producerToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }
    @Test
    @DisplayName("uptate updates a  producer")
    @Order(9)
    void update_updatesProducer_WhenSuccesful(){
        var producerToUpdate= producerList.getFirst();
        producerToUpdate.setName("Aniplex");
        BDDMockito.when(repository.findByid(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
       // BDDMockito.doNothing().when(repository).update(producerToUpdate);

        assertThatNoException()
                .isThrownBy(() -> service.update(producerToUpdate));





    }
    @Test
    @DisplayName("update throws ResponseStatusException when producer is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_whenProducerIsNotFound(){
        var producerToupdate= producerList.getFirst();
        BDDMockito.when(repository.findByid(ArgumentMatchers.anyLong())
        ).thenReturn(Optional.empty());


        assertThatException()
                .isThrownBy(() -> service.update(producerToupdate))
                .isInstanceOf(ResponseStatusException.class);

    }


}