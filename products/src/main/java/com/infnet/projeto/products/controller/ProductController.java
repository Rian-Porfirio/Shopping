package com.infnet.projeto.products.controller;

import com.infnet.projeto.products.domain.Product;
import org.springframework.web.bind.annotation.*;
import com.infnet.projeto.products.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<Product> listar() { return service.listar(); }

    @PostMapping
    public Product criar(@RequestBody Product p) { return service.salvar(p); }

    @PutMapping("/{id}")
    public Product atualizar(@PathVariable Long id, @RequestBody Product p) {
        p.setId(id);
        return service.salvar(p);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) { service.deletar(id); }
}