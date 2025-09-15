package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.mapper.ProducerMapperImpl;
import com.brunoprojeto.anime_service.repository.ProducerData;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import com.brunoprojeto.anime_service.service.ProducerService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class , ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerData producerData;

    @Autowired
    private ResourceLoader resourceLoader;


    private List<Producer> producerList; // 1 usage

    @BeforeEach
    void init() {
        var dateTime = "2025-09-15T09:26:15.8409071";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(localDateTime).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(localDateTime).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(localDateTime).build();

        producerList= new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }



    @Test
    @DisplayName("GET v1/producer findall return a list whith all producers when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-null-name-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName(" GET v1/producer?param=Ufotable findAll returns List with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name="Ufotable";


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer").param("name",name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName(" GET v1/producer?name=x findALL returns Empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-x-name-200.json");
        var name="x";


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer").param("name",name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("GET v1/producer/1 findByid returns a producer with given id")
    @Order(4)
    void findByid_returnsProducerById_WhenSucceful() throws Exception{
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-by-id-200.json");
        var id= 1L;


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer/{id}",id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }
    @Test
    @DisplayName("GET v1/producer/99   throws ResponseStatusException 404 when producer is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound () throws Exception{
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var id= 99L;


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer/{id}",id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("producers not found "));

    }





    private String readResourceFile(String filename) throws IOException {

        var file= resourceLoader.getResource("classpath:%s".formatted(filename)).getFile();
        return  new String(Files.readAllBytes(file.toPath()));
    }

}