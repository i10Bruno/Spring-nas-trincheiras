package com.brunoprojeto.anime_service.controller;

import com.brunoprojeto.anime_service.commons.AnimeUtils;
import com.brunoprojeto.anime_service.domain.Anime;
import com.brunoprojeto.anime_service.mapper.AnimeMapperImpl;
import com.brunoprojeto.anime_service.repository.AnimeData;
import com.brunoprojeto.anime_service.repository.AnimeHardCodedRepository;
import com.brunoprojeto.anime_service.service.AnimeService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//diz ao spring para carregar apenas os componentes relacionados a controller não carrega @service,@repository,@component
//controllers = AnimeController.class Isso torna o teste ainda mais rápido e isolado pois vc epecifica qual controller vc quer
@WebMvcTest(controllers = AnimeController.class)

//ordem de execução dos métodos de teste.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

// COMO O @WebMvcTest NÃO IMPORTA BEANS DE OUTRAS CLASSES VC DIZ AO SPRING QUE PARA O CONTROLLER FUNCIONAR VC PRECISA DAS CLASSES ABAIXO
@Import({AnimeMapperImpl.class, AnimeService.class, AnimeHardCodedRepository.class, AnimeData.class})
@ComponentScan("com.brunoprojeto.anime_service")
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

    @Autowired
    private AnimeUtils animeUtils;

    private List<Anime> AnimesList;

    //O método anotado com BeforeEach será executado antes de cada método de teste (@Test) na classe.
    @BeforeEach
    void init() {

        AnimesList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("GET v1/anime return a list whith all animes when arguments is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentsIsNull() throws Exception {
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
    @DisplayName(" GET v1/Anime?param=Ninja Kamui findAll returns List with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundAnimesInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);//get-anime-kamui-name-200.json
        String response = readResourceFile("anime/get-anime-kamui-name-200.json");
        var name = "Ninja Kamui";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));

    }

    @Test
    @DisplayName(" GET v1/anime?name=x findALL returns Empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);//get-anime-kamui-name-200.json
        String response = readResourceFile("anime/get-anime-x-name-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));

    }

    @Test
    @DisplayName("GET v1/anime/1 findByid returns a producer with given id")
    @Order(4)
    void findByid_returnsProducerById_WhenSucceful() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        String response = readResourceFile("anime/get-anime-by-id-200.json");
        var id = AnimesList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));


    }

    @Test
    @DisplayName("GET v1/anime/99   throws ResponseStatusException 404 when producer is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isNotFound()).andExpect(status().reason("anime not found"));


    }

    @Test
    @DisplayName("POST v1/anime  creates a producer")
    @Order(6)
    void Save_CreatesProducer_WhenSuccesful() throws Exception {

        var request = readResourceFile("anime/post-request-anime-200.json");

        var response = readResourceFile("anime/post-response-anime-201.json");
        var animeToSave = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/animes")
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName(" PUT v1/anime  updates a  producer")
    @Order(7)
    void update_updatesProducer_WhenSuccesful() throws Exception {
        var request = readResourceFile("anime/put-request-anime-200.json");
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes").content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName(" PUT v1/anime throws ResponseStatusException when producer is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_whenProducerIsNotFound() throws Exception {
        var request = readResourceFile("anime/put-request-anime-404.json");
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes").content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound()).andExpect(MockMvcResultMatchers.status().reason("anime not found"));


    }

    @Test
    @DisplayName("DELETE v1/anime/1 delete remove a producer")
    @Order(9)
    void delete_RemoveProducers_whenSuccesful() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var id = AnimesList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());


    }


    @Test
    @DisplayName("DELETE v1/anime/99 throws ResponseStatusException when producer is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_whenProducerIsNotFound() throws Exception {
        BDDMockito.when(animeData.getANIMES()).thenReturn(AnimesList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound()).andExpect(MockMvcResultMatchers.status().reason("anime not found"));

    }

    @ParameterizedTest
    @MethodSource("postAnimeBadRequestSource")
    @DisplayName("POST v1/anime returns bad request when fields are Invalid")
    @Order(11)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String filename, List<String> errors) throws Exception {

        var request = readResourceFile("anime/%s".formatted(filename));
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


        var resolvedException = mvcResult.getResolvedException();
        org.assertj.core.api.Assertions.assertThat(resolvedException).isNotNull();


        org.assertj.core.api.Assertions.assertThat(resolvedException.getMessage())
                .contains(errors);


    }


    private static Stream<Arguments> postAnimeBadRequestSource() {

        var allErrors = allRequiredErrors();


        return Stream.of(Arguments.of("post-request-anime-blank-fieds-400.json", allErrors),
                Arguments.of("post-request-anime-empty-fieds-400.json", allErrors));

    }


    private static List<String> allRequiredErrors() {
        var NameError = "The field 'Name' is required";

        return new ArrayList<>(List.of(NameError));
    }


    private String readResourceFile(String filename) throws IOException {

        var file = resourceLoader.getResource("classpath:%s".formatted(filename)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }


}