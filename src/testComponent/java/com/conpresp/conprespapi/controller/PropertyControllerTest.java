package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import com.conpresp.conprespapi.dto.property.request.*;
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
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockMvcTestBuilder userMockMvc;

    @BeforeEach
    void setup() {
        this.userMockMvc = new MockMvcTestBuilder("/property", mockMvc);
    }

    @Test
    void shouldCreateAProperty() throws Exception {
        var property = getMockedProperty();

        var response = userMockMvc.post(property)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    void shouldGetPropertyByUuid() throws Exception {
        var property = getMockedProperty();

        var response = userMockMvc.post(property)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Edifício São João"))
                .andExpect(jsonPath("$.construction.constructionYear").value("1960"))
                .andDo(print());
    }

    @Test
    void shouldReturnAPageableListOfProperty() throws Exception {
        var property = getMockedProperty();
        var property2 = getMockedProperty2();
        var property3 = getMockedUpdatedProperty();

        userMockMvc.post(property);
        userMockMvc.post(property2);
        userMockMvc.post(property3);

        userMockMvc.appendPathVar("?size=2").get()
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void shouldDeleteAProperty() throws Exception {
        var property = getMockedProperty();

        var response = userMockMvc.post(property).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).delete();

        userMockMvc.get()
                .andExpect(jsonPath("$.totalElements").value(0)).andDo(print());
    }


    @Test
    void shouldReturnNorFoundByDeleteAPropertyWithInvalidId() throws Exception {
        userMockMvc.appendPathVar("invalidId").delete().andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateAProperty() throws Exception {
        var property = getMockedProperty();
        var updateProperty = getMockedUpdatedProperty();

        var response = userMockMvc.post(property).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        var updateResponse = userMockMvc.appendPathVar(uuid).put(updateProperty)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.construction.constructionYear").value("1980"))
                .andExpect(jsonPath("$.construction.architecturalStyle").value("Moderno"))
                .andExpect(jsonPath("$.construction.floorQuantity").value(5))
                .andDo(print());
    }

    private PropertyCreateRequest getMockedProperty() {
        var propertyResolution = new PropertyResolutionRequest("05/91", "CONPRESP", Year.parse("1990"));
        var constructionRequest = new ConstructionRequest("1960", "Gótico", "Alvenaria de Tijólos", 3, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var graphic = new GraphicRequest("Imagem");
        var photographicRequest = new PhotographicRequest("Imagem");

        return new PropertyCreateRequest(List.of(propertyResolution), "Edifício São João", "Imóvel", "Igreja,", "Religioso", constructionRequest, addressLot, "Fieis Anonimos", "Eu mesmo", "Nenhuma", "Muito belo o Prédio", List.of(photographicRequest), List.of(graphic));
    }

    private PropertyCreateRequest getMockedProperty2() {
        var propertyResolution = new PropertyResolutionRequest("08/80", "CODEPHAAT", Year.parse("1980"));
        var constructionRequest = new ConstructionRequest("1980", "Gótico", "Alvenaria de Tijólos", 3, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var graphic = new GraphicRequest("Imagem");
        var photographicRequest = new PhotographicRequest("Imagem");

        return new PropertyCreateRequest(List.of(propertyResolution), "Edifício São João", "Imóvel", "Igreja,", "Religioso", constructionRequest, addressLot, "Fieis Anonimos", "Eu mesmo", "Nenhuma", "Muito belo o Prédio", List.of(photographicRequest), List.of(graphic));
    }

    private PropertyUpdateRequest getMockedUpdatedProperty() {
        var propertyResolution = new PropertyResolutionRequest("08/80", "CODEPHAAT", Year.parse("1980"));
        var constructionRequest = new ConstructionRequest("1980", "Moderno", "Alvenaria de Aço", 5, 45.0, "2", "Alta", "Foi modificada muitas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var graphic = new GraphicRequest("Imagem");
        var photographicRequest = new PhotographicRequest("Imagem");

        return new PropertyUpdateRequest(List.of(propertyResolution), "Edifício São João", "Imóvel", "Igreja,", "Religioso", constructionRequest, addressLot, "Fieis Anonimos", "Eu mesmo", "Nenhuma", "Muito belo o Prédio", List.of(photographicRequest), List.of(graphic));
    }
}
