package com.infnet.projeto.products.selenium.tests;

import com.infnet.projeto.products.selenium.base.BaseTest;
import com.infnet.projeto.products.selenium.pages.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest extends BaseTest {

    @Test
    void deveCadastrarProdutoComSucesso() {
        ProductPage page = new ProductPage(driver, wait);

        page.clicarAdicionar();
        page.preencherFormulario("Mouse Pad", "Periféricos", "3000");
        page.salvar();

        assertTrue(page.toastVisivel());
    }

    @Test
    void deveEditarProdutoComSucesso() {
        ProductPage page = new ProductPage(driver, wait);

        page.editarPrimeiroProduto();
        page.preencherFormulario("Editado", "Editado", "120");
        page.salvar();

        assertTrue(page.toastVisivel());
    }

    @Test
    void deveExcluirProdutoComSucesso() {
        ProductPage page = new ProductPage(driver, wait);

        page.excluirPrimeiroProduto();

        assertTrue(page.toastVisivel());
    }
}