package com.brunoprojeto.anime_service.repository;

import com.brunoprojeto.anime_service.domain.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    void findAll_ReturnsAllProducers_WhenSuccesful(){

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers= repository.findAll();
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);


    }
    @Test
    @DisplayName("findByid returns a producer with given id")
    void findByid_ReturnsProducersByid_WhenSuccesful(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer= producerList.getFirst();
        var producers= repository.findByid(expectedProducer.getId());
        org.assertj.core.api.Assertions.assertThat(producers).isPresent().contains(expectedProducer);


    }

    @Test
    @DisplayName("findByname returns empty when name is null")
    void findByname_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var producers= repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(producers).isNotNull().isEmpty();


    }
    @Test
    @DisplayName("findByname returns List with found object when name exists")
    void findByname_ReturnsFoundProducerInList_WhenNameIsFound(){
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var expectedProducer= producerList.getFirst();
        var producers= repository.findByName(expectedProducer.getName());
        org.assertj.core.api.Assertions.assertThat(producers).contains(expectedProducer);


    }


}