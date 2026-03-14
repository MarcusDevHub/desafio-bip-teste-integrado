package com.example.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                beneficio1.setId(6L);
                beneficio1.setNome("Info Falsa1");
                beneficio1.setDescricao("Benefício A");
                beneficio1.setValor(new BigDecimal("500.00"));
                beneficio1.setAtivo(true);

                Beneficio beneficio2 = new Beneficio();
                beneficio2.setId(7L);
                beneficio2.setNome("Info Falsa2");
                beneficio2.setDescricao("Benefício B");
                beneficio2.setValor(new BigDecimal("300.00"));
                beneficio2.setAtivo(true);

                when(service.listarTodos()).thenReturn(List.of(beneficio1, beneficio2));

                mockMvc.perform(get("/api/v1/beneficios"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/json"))
                                .andExpect(jsonPath("$[0].id").value(6L))
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

        @Test
        void deveBuscarBeneficioPorId() throws Exception {
                Beneficio beneficio = new Beneficio();
                beneficio.setId(6L);
                beneficio.setNome("Info Falsa1");
                beneficio.setDescricao("Benefício A");
                beneficio.setValor(new BigDecimal("500.00"));
                beneficio.setAtivo(true);

                when(service.buscarPorId(6L)).thenReturn(beneficio);

                mockMvc.perform(get("/api/v1/beneficios/{id}", 6L))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(6L))
                                .andExpect(jsonPath("$.nome").value("Info Falsa1"))
                                .andExpect(jsonPath("$.descricao").value("Benefício A"))
                                .andExpect(jsonPath("$.ativo").value(true));

                when(service.buscarPorId(6L)).thenReturn(beneficio);

        }

        @Test
        void deveCriarBeneficio() throws Exception {
                Beneficio request = criarBeneficio(null, "Auxílio Home Office", "Benefício para trabalho remoto",
                                "200.00",
                                true);
                Beneficio response = criarBeneficio(3L, "Auxílio Home Office", "Benefício para trabalho remoto",
                                "200.00",
                                true);

                when(service.criar(org.mockito.ArgumentMatchers.any(Beneficio.class))).thenReturn(response);

                mockMvc.perform(post("/api/v1/beneficios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(3))
                                .andExpect(jsonPath("$.nome").value("Auxílio Home Office"))
                                .andExpect(jsonPath("$.descricao").value("Benefício para trabalho remoto"))
                                .andExpect(jsonPath("$.ativo").value(true));
        }

        @Test
        void deveAtualizarBeneficio() throws Exception {
                Beneficio request = criarBeneficio(null, "Info Falsa 1 Atualizada", "Descrição atualizada", "650.00",
                                true);
                Beneficio response = criarBeneficio(6L, "Info Falsa 1 Atualizada", "Descrição atualizada", "650.00",
                                true);

                when(service.atualizar(org.mockito.ArgumentMatchers.eq(6L),
                                org.mockito.ArgumentMatchers.any(Beneficio.class)))
                                .thenReturn(response);

                mockMvc.perform(put("/api/v1/beneficios/{id}", 6L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(6))
                                .andExpect(jsonPath("$.nome").value("Info Falsa 1 Atualizada"))
                                .andExpect(jsonPath("$.descricao").value("Descrição atualizada"))
                                .andExpect(jsonPath("$.ativo").value(true));
        }

        @Test
        void deveDeletarBeneficio() throws Exception {
                doNothing().when(service).deletar(6L);

                mockMvc.perform(delete("/api/v1/beneficios/{id}", 6L))
                                .andExpect(status().isOk());

                verify(service).deletar(6L);
        }

        @Test
        void deveTransferirValorEntreBeneficios() throws Exception {
                TransferRequest request = new TransferRequest();
                request.setFromId(6L);
                request.setToId(7L);
                request.setAmount(new BigDecimal("100.00"));

                doNothing().when(service).transferir(6L, 7L, new BigDecimal("100.00"));

                mockMvc.perform(post("/api/v1/beneficios/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk());

                verify(service).transferir(6L, 7L, new BigDecimal("100.00"));
        }

        private Beneficio criarBeneficio(Long id, String nome, String descricao, String valor, Boolean ativo) {
                Beneficio beneficio = new Beneficio();
                beneficio.setId(id);
                beneficio.setNome(nome);
                beneficio.setDescricao(descricao);
                beneficio.setValor(new BigDecimal(valor));
                beneficio.setAtivo(ativo);
                beneficio.setVersion(0L);
                return beneficio;
        }

}
