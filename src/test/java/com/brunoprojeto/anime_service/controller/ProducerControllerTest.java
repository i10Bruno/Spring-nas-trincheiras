package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.commons.FileUtils;
import com.brunoprojeto.anime_service.commons.ProducerUtils;
import com.brunoprojeto.anime_service.domain.Producer;
import com.brunoprojeto.anime_service.mapper.ProducerMapperImpl;
import com.brunoprojeto.anime_service.repository.ProducerData;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import com.brunoprojeto.anime_service.service.ProducerService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class, ProducerService.class, ProducerHardCodedRepository.class, ProducerData.class})
@ComponentScan("com.brunoprojeto.anime_service")
class ProducerControllerTest {
    private static final String URL="/v1/producer";
    @Autowired
    private MockMvc mockMvc;


    @MockitoSpyBean
    private ProducerHardCodedRepository repository;


    @MockitoBean
    private ProducerData producerData;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProducerUtils producerUtils;

    private List<Producer> producerList; // 1 usage

    @BeforeEach
    void init() {

        producerList = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("GET v1/producer findall return a list whith all producers when arguments is null")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenArgumentsIsNull() throws Exception {

        // Quando 'producerData.getProducers()' for chamado, retorne a 'producerList'.
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

        //
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName(" GET v1/producer?param=Ufotable findAll returns List with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundProducerInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-ufotable-name-200.json");
        var name = "Ufotable";


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName(" GET v1/producer?name=x findALL returns Empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-x-name-200.json");
        var name = "x";


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("GET v1/producer/1 findByid returns a producer with given id")
    @Order(4)
    void findByid_returnsProducerById_WhenSucceful() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var response = fileUtils.readResourceFile("producer/get-producer-by-id-200.json");
        var id = 1L;


        mockMvc.perform(MockMvcRequestBuilders.get(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("GET v1/producer/99   throws ResponseStatusException 404 when producer is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var id = 99L;


        mockMvc.perform(MockMvcRequestBuilders.get(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("producers not found"));


    }

    @Test
    @DisplayName("POST   v1/producer  creates a producer")
    @Order(6)
    void Save_CreatesProducer_WhenSuccesful() throws Exception {
        var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
        var response = fileUtils.readResourceFile("producer/post-response-producer-201.json");
        var ProducerToSave = producerUtils.newProducerToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(ProducerToSave);

        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(request).header("x-api-key", "v1").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());


    }

    @Test
    @DisplayName(" PUT v1/producer  updates a  producer")
    @Order(7)
    void update_updatesProducer_WhenSuccesful() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-200.json");
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName(" PUT v1/producer throws ResponseStatusException when producer is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_whenProducerIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders.put(URL).content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound()).andExpect(MockMvcResultMatchers.status().reason("producers not found"));


    }

    @Test
    @DisplayName("DELETE v1/producer/1 delete remove a producer")
    @Order(9)
    void delete_RemoveProducers_whenSuccesful() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var id = producerList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());


    }


    @Test
    @DisplayName("DELETE v1/producer/99 throws ResponseStatusException when producer is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_whenProducerIsNotFound() throws Exception {
        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete(URL+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound()).andExpect(MockMvcResultMatchers.status().reason("producers not found"));

    }


}