package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {
    //injeção de dependencia do mockito
    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;

    private final List<Producer> producerList = new ArrayList<>(); // 1 usage

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();

        producerList.addAll(List.of(ufotable, witStudio, studioGhibli));
    }
    @Test
    @DisplayName("findall return a list whith all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccesful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers= repository.findAll();
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);


    }
    @Test
    @DisplayName("findByid returns a producer with given id")
    @Order(2)
    void findByid_ReturnsProducersByid_WhenSuccesful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer= producerList.getFirst();
        var producers= repository.findByid(expectedProducer.getId());
        org.assertj.core.api.Assertions.assertThat(producers).isPresent().contains(expectedProducer);


    }

    @Test
    @DisplayName("findByname returns empty when name is null")
    @Order(3)
    void findByname_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers= repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().isEmpty();


    }
    @Test
    @DisplayName("findByname returns List with found object when name exists")
    @Order(4)
    void findByname_ReturnsFoundProducerInList_WhenNameIsFound(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer= producerList.getFirst();
        var producers= repository.findByName(expectedProducer.getName());
        org.assertj.core.api.Assertions.assertThat(producers).contains(expectedProducer);


    }
    @Test
    @DisplayName("Save creates a producer")
    @Order(5)
    void Save_CreatesProducerInList_WhenNameIsFound(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var ProducerToSave=Producer.builder().id(99L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        var producers= repository.save(ProducerToSave);
        //org.assertj.core.api.Assertions.assertThat(producers).isEqualTo(ProducerToSave).hasAllNullFieldsOrProperties();
        org.assertj.core.api.Assertions.assertThat(producers)
                .isNotNull()
                .isEqualTo(ProducerToSave);
        var producerSavedOptional = repository.findByid(ProducerToSave.getId());
        org.assertj.core.api.Assertions.assertThat(producerSavedOptional).isPresent().contains(ProducerToSave);


    }
    @Test
    @DisplayName("delete remove a producer")
    @Order(6)
    void delete_RemoveProducers_whenSuccesful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToDelete= producerList.getFirst();
        repository.delete(producerToDelete);


        org.assertj.core.api.Assertions.assertThat(this.producerList)
                .doesNotContain(producerToDelete);


    }
    @Test
    @DisplayName("uptate updates a  producer")
    @Order(7)
    void update_updatesProducer_WhenSuccesful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producerToUpdate = this.producerList.getFirst();
        producerToUpdate.setName("Aniplex");
        repository.update(producerToUpdate);

        Assertions.assertThat(this.producerList).contains(producerToUpdate);
        var producerUpdatedOptional = repository.findByid(producerToUpdate.getId());
        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getName())
                .isEqualTo("Aniplex");
        Assertions.assertThat(producerUpdatedOptional.get())
                .isEqualTo(producerToUpdate);




    }


}