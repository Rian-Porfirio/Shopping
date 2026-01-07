package com.infnet.projeto.products.api.service;

import com.infnet.projeto.products.domain.Product;
import com.infnet.projeto.products.repository.IProductRepository;
import com.infnet.projeto.products.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private IProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void deveSalvarProdutoComValorValido() {
        Product produto = new Product();
        produto.setValor(BigDecimal.valueOf(100));

        when(repository.save(produto)).thenReturn(produto);

        Product salvo = service.salvar(produto);

        assertNotNull(salvo);
        verify(repository).save(produto);
    }

    @Test
    void naoDeveSalvarProdutoComValorZero() {
        Product produto = new Product();
        produto.setValor(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class,
                () -> service.salvar(produto));

        verify(repository, never()).save(any());
    }

    @Test
    void naoDeveSalvarProdutoComValorNegativo() {
        Product produto = new Product();
        produto.setValor(BigDecimal.valueOf(-10));

        assertThrows(IllegalArgumentException.class,
                () -> service.salvar(produto));
    }

    @Test
    void deveListarProdutos() {
        when(repository.findAll()).thenReturn(List.of(new Product()));

        List<Product> produtos = service.listar();

        assertFalse(produtos.isEmpty());
        verify(repository).findAll();
    }

    @Test
    void deveDeletarProdutoPorId() {
        Long id = 1L;

        service.deletar(id);

        verify(repository).deleteById(id);
    }
}
