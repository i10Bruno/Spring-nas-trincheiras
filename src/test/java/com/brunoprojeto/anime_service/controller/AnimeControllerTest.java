package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.mapper.AnimeMapperImpl;
import com.brunoprojeto.anime_service.mapper.ProducerMapperImpl;
import com.brunoprojeto.anime_service.repository.AnimeData;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import com.brunoprojeto.anime_service.repository.ProducerData;
import com.brunoprojeto.anime_service.repository.ProducerHardCodedRepository;
import com.brunoprojeto.anime_service.service.AnimeService;
import com.brunoprojeto.anime_service.service.ProducerService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//diz ao spring para carregar apenas os componentes relacionados a controller não carrega @service,@repository,@component
//controllers = AnimeController.class Isso torna o teste ainda mais rápido e isolado pois vc epecifica qual controller vc quer
@WebMvcTest(controllers = AnimeController.class)

//ordem de execução dos métodos de teste.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

// COMO O @WebMvcTest NÃO IMPORTA BEANS DE OUTRAS CLASSES VC DIZ AO SPRING QUE PARA O CONTROLLER FUNCIONAR VC PRECISA DAS CLASSES ABAIXO
@Import({AnimeMapperImpl.class , AnimeService.class, AnimeHardCodedRepository.class, AnimeData.class})
class AnimeControllerTest {


    @Autowired
    //É um objeto que permite simular requisições HTTP (GET, POST, PUT, etc.) para os seus endpoints sem precisar de um servidor web real.
    private MockMvc mockMvc;

    //Para testar o comportamento real, mas forçar um cenário de erro ou exceção.
    @MockitoSpyBean
    private AnimeHardCodedRepository repository;


    @MockitoBean
    //Você precisa programar explicitamente como ele deve se comportar usando o Mockito (ex: when(animeData.getSomething()).thenReturn(...)).
    private AnimeData animeData;

    @Autowired
    //A principal função do ResourceLoader é encontrar e ler arquivos, não importa onde eles estejam. Ele usa prefixos especiais para saber onde procurar:
    private ResourceLoader resourceLoader;


    private List<Anime> AnimesList;

//O método anotado com BeforeEach será executado antes de cada método de teste (@Test) na classe.
    @BeforeEach
    void  init () {
        var ninjaKamui = Anime.builder().id(1L).name("Ninja Kamui").build();
        var kaijuu = Anime.builder().id(2L).name("Kaijuu-8gou").build();
        var kimetsuNoYaiba = Anime.builder().id(3L).name("Kimetsu No Yaiba").build();

        AnimesList= new ArrayList<>(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }

    @Test
    @DisplayName("GET v1/producer return a list whith all animes when arguments is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentsIsNull () throws Exception {
        // Quando 'animeData.getProducers()' for chamado, retorne a 'animerList'.
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);

        String response = readResourceFile("anime/get-anime-null-name-200.json");
        // AQUI COMEÇA A AÇÃO -> .perform() pega a requisição construída por get() e a executa.
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                // Adicionamos o .andDo(print()) para inspecionar a interação
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).
                andExpect(content().json(response));


    }
    @Test
    @DisplayName(" GET v1/Animes?param=Ninja Kamui findAll returns List with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundAnimesInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);//get-anime-kamui-name-200.json
        String response = readResourceFile("anime/get-anime-kamui-name-200.json");
        var name="Ninja Kamui";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));

    }
    @Test
    @DisplayName(" GET v1/anime?name=x findALL returns Empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);//get-anime-kamui-name-200.json
        String response = readResourceFile("anime/get-anime-x-name-200.json");
        var name ="x";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));

    }










    private String readResourceFile(String filename) throws IOException {

        var file= resourceLoader.getResource("classpath:%s".formatted(filename)).getFile();
        return  new String(Files.readAllBytes(file.toPath()));
    }


}