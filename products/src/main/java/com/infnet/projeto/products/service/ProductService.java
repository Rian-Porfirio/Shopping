package com.infnet.projeto.products.service;

import com.infnet.projeto.products.domain.Product;
import org.springframework.stereotype.Service;
import com.infnet.projeto.products.repository.IProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final IProductRepository repository;

    public ProductService(IProductRepository repository) { this.repository = repository; }

    public Product salvar(Product p) {
        if (p.getValor().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor inválido");
        return repository.save(p);
    }

    public List<Product> listar() { return repository.findAll(); }
    public void deletar(Long id) { repository.deleteById(id); }
}