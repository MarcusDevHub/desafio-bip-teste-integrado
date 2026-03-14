package com.example.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarBeneficios() throws Exception {
        Beneficio beneficio1 = new Beneficio();
        beneficio1.setId(6);
        beneficio1.setNome("Info Falsa1");
        beneficio1.setDescricao("Benefício A");
        beneficio1.setValor(new BigDecimal("500.00"));
        beneficio1.setAtivo(true);

        Beneficio beneficio2 = new Beneficio();
        beneficio2.setId(7);
        beneficio2.setNome("Info Falsa2");
        beneficio2.setDescricao("Benefício B");
        beneficio2.setValor(new BigDecimal("300.00"));
        beneficio2.setAtivo(true);

        when(service.listarTodos()).thenReturn(List.of(beneficio1, beneficio2));

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[0].nome").value("Info Falsa1"))
                .andExpect(jsonPath("$[0].descricao").value("Benefício A"))
                .andExpect(jsonPath("$[0].valor").value(500.00))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(7))
                .andExpect(jsonPath("$[1].nome").value("Info Falsa2"))
                .andExpect(jsonPath("$[1].descricao").value("Benefício B"))
                .andExpect(jsonPath("$[1].valor").value(300.00))
                .andExpect(jsonPath("$[1].ativo").value(true));

    }

}
