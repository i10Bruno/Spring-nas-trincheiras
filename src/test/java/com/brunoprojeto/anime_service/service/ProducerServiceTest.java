package com.brunoprojeto.anime_service.service;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

}