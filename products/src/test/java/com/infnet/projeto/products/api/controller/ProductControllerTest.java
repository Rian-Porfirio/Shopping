package com.infnet.projeto.products.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.projeto.products.controller.ProductController;
import com.infnet.projeto.products.domain.Product;
import com.infnet.projeto.products.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarProdutos() throws Exception {
        when(service.listar()).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deveCriarProduto() throws Exception {
        Product produto = new Product();
        produto.setValor(BigDecimal.valueOf(100));

        when(service.salvar(any(Product.class))).thenReturn(produto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        Product produto = new Product();
        produto.setId(1L);
        produto.setValor(BigDecimal.valueOf(150));

        when(service.salvar(any(Product.class))).thenReturn(produto);

        mockMvc.perform(put("/api/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarProduto() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 1))
                .andExpect(status().isOk());

        verify(service).deletar(1L);
    }
}
