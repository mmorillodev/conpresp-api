package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import com.conpresp.conprespapi.dto.patrimony.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentTest
@WithMockUser(authorities = {"ADMINISTRATOR"})
public class PatrimonyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockMvcTestBuilder userMockMvc;

    @BeforeEach
    void setup() {
        this.userMockMvc = new MockMvcTestBuilder("/patrimony", mockMvc);
    }

    @Test
    void shouldCreateAPatrimony() throws Exception {
        var patrimony = getMockedPatrimony();

        var response = userMockMvc.post(patrimony)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    void shouldGetPatrimonyByUuid() throws Exception {
        var patrimony = getMockedPatrimony();

        var response = userMockMvc.post(patrimony)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.denomination").value("Edifício São João"))
                .andExpect(jsonPath("$.construction.constructionYear").value("1960"))
                .andDo(print());
    }

    @Test
    void shouldReturnAPageableListOfPatrimony() throws Exception {
        var patrimony = getMockedPatrimony();
        var patrimony2 = getMockedPatrimony2();
        var patrimony3 = getMockedUpdatedPatrimony();

        userMockMvc.post(patrimony);
        userMockMvc.post(patrimony2);
        userMockMvc.post(patrimony3);

        userMockMvc.appendPathVar("?size=2").get()
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void shouldReturnAPatrimonyByDenomination() throws Exception {
        var patrimony = getMockedPatrimony();
        var patrimony2 = getMockedPatrimony2();
        var patrimony3 = getMockedUpdatedPatrimony();

        userMockMvc.post(patrimony);
        userMockMvc.post(patrimony2);
        userMockMvc.post(patrimony3);

        userMockMvc.appendPathVar("?denomination=Estádio do Corinthians").get()
                .andExpect(jsonPath("$.content[0].denomination").value("Estádio do Corinthians"))
                .andDo(print());
    }

    @Test
    void shouldDeleteAPatrimony() throws Exception {
        var patrimony = getMockedPatrimony();

        var response = userMockMvc.post(patrimony).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).delete();

        userMockMvc.get()
                .andExpect(jsonPath("$.totalElements").value(0)).andDo(print());
    }


    @Test
    void shouldReturnNorFoundByDeleteAPatrimonyWithInvalidId() throws Exception {
        userMockMvc.appendPathVar("invalidId").delete().andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateAPatrimony() throws Exception {
        var patrimony = getMockedPatrimony();
        var updatePatrimony = getMockedUpdatedPatrimony();

        var response = userMockMvc.post(patrimony).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        var updateResponse = userMockMvc.appendPathVar(uuid).put(updatePatrimony)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.construction.constructionYear").value("1980"))
                .andExpect(jsonPath("$.construction.architecturalStyle").value("Moderno"))
                .andExpect(jsonPath("$.construction.floorQuantity").value(5))
                .andDo(print());
    }

    private PatrimonyCreateRequest getMockedPatrimony() {
        var patrimonyResolution = new HeritageResolutionRequest("05/91", "CONPRESP", Year.parse("1990"));
        var constructionRequest = new ConstructionRequest("1960", true, "Fieis Anonimos", "Fieis Anonimos","Gótico", "Alvenaria de Tijólos", 3, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var description = new DescriptionRequest("Antigo", "Antigo, muito antigo", "Destruiu a floresta", "Eu mesmo", "Arroz", "Feijão");
        var graphic = new GraphicRequest("Imagem", "");
        var photographicRequest = new PhotographicRequest("Imagem", "");

        return new PatrimonyCreateRequest(List.of(patrimonyResolution), "2", "Edifício São João", "Imóvel", "Igreja", "Igreja", "Religioso", constructionRequest, addressLot, description, List.of(photographicRequest), List.of(graphic));
    }

    private PatrimonyCreateRequest getMockedPatrimony2() {
        var patrimonyResolution = new HeritageResolutionRequest("08/80", "CODEPHAAT", Year.parse("1980"));
        var constructionRequest = new ConstructionRequest("1960", true, "Fieis Anonimos", "Fieis Anonimos","Gótico", "Alvenaria de Tijólos", 3, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var description = new DescriptionRequest("Antigo", "Antigo, muito antigo", "Destruiu a floresta", "Eu mesmo", "Arroz", "Feijão");
        var graphic = new GraphicRequest("Imagem", "");
        var photographicRequest = new PhotographicRequest("Imagem", "");

        return new PatrimonyCreateRequest(List.of(patrimonyResolution), "2", "Edifício São João", "Imóvel", "Igreja", "Igreja", "Religioso", constructionRequest, addressLot, description, List.of(photographicRequest), List.of(graphic));

    }

    private PatrimonyUpdateRequest getMockedUpdatedPatrimony() {
        var patrimonyResolution = new HeritageResolutionRequest("08/80", "CODEPHAAT", Year.parse("1980"));
        var constructionRequest = new ConstructionRequest("1980", true ,"Fieis Anonimos", "Fieis Anonimos","Moderno", "Alvenaria de Tijólos", 5, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var description = new DescriptionRequest("Antigo", "Antigo, muito antigo", "Destruiu a floresta", "Eu mesmo", "Arroz", "Feijão");
        var graphic = new GraphicRequest("Imagem", "");
        var photographicRequest = new PhotographicRequest("Imagem", "");

        return new PatrimonyUpdateRequest(List.of(patrimonyResolution), "2", "Estádio do Corinthians", "Imóvel", "Igreja", "Igreja", "Religioso", constructionRequest, addressLot, description, List.of(photographicRequest), List.of(graphic));
    }
}
