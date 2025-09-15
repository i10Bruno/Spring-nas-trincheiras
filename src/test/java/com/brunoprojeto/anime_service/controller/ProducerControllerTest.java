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

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("findall return a list whith all producers when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = readResourceFile("producer/get-producer-null-name-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producer"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }




    private String readResourceFile(String filename) throws IOException {

        var file= resourceLoader.getResource("classpath:%s".formatted(filename)).getFile();
        return  new String(Files.readAllBytes(file.toPath()));
    }

}