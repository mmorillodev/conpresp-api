package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import com.conpresp.conprespapi.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

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

        var responseProperty = userMockMvc.appendPathVar(uuid).get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Edifício São João"))
                .andExpect(jsonPath("$.construction.constructionYear").value("1960"))
                .andDo(print());
    }

    private PropertyCreateRequest getMockedProperty() {
        var propertyResolution = new PropertyResolutionRequest("Edifício São João", "05/91");
        var institutionRequest = new InstitutionRequest("CONPRESP", "Estadual");
        var heritageResolution = new HeritageResolutionRequest(propertyResolution, "2010", institutionRequest);
        var constructionRequest = new ConstructionRequest("1960", "Gótico", "Alvenaria de Tijólos", 3, 45.0, "2", "Média", "Foi modificada algumas vezes com o propósito de manutenção", "Alto", "Está bem conservado", "Nenhuma");
        var addressLot = new AddressLotRequest("Prédio", "São João", "AV. Faria Lima", "532", "Brigadeiro", "São Paulo", "3", "5", "2");
        var graphic = new GraphicRequest("Imagem");
        var photographicRequest = new PhotographicRequest("Imagem");

        return new PropertyCreateRequest(heritageResolution, "Edifício São João", "Imóvel", "Igreja,", "Religioso", constructionRequest, addressLot, "Fieis Anonimos", photographicRequest, graphic, "Eu mesmo", "Nenhuma", "Muito belo o Prédio");
    }
}
