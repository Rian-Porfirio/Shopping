package com.infnet.projeto.products.selenium.tests;

import com.infnet.projeto.products.selenium.base.BaseTest;
import com.infnet.projeto.products.selenium.pages.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductNegativeTest extends BaseTest {

    @Test
    void deveExibirToastErroAoSalvarComCamposVazios() {
        ProductPage page = new ProductPage(driver, wait);

        page.clicarAdicionar();
        page.salvar();

        String mensagem = page.getToastMensagem();

        assertTrue(mensagem.contains("Erro"));
        assertTrue(mensagem.contains("Erro ao salvar o produto. Verifique os campos."));
    }

    @Test
    void deveExibirToastErroAoSalvarSemNome() {
        ProductPage page = new ProductPage(driver, wait);

        page.clicarAdicionar();
        page.preencherFormulario("", "Eletrônicos", "100");
        page.salvar();

        String mensagem = page.getToastMensagem();

        assertTrue(mensagem.contains("Erro"));
        assertTrue(mensagem.contains("Erro ao salvar o produto. Verifique os campos."));
    }

    @Test
    void deveExibirToastErroAoSalvarSemValor() {
        ProductPage page = new ProductPage(driver, wait);

        page.clicarAdicionar();
        page.preencherFormulario("Teclado", "Periféricos", "");
        page.salvar();

        String mensagem = page.getToastMensagem();

        assertTrue(mensagem.contains("Erro"));
        assertTrue(mensagem.contains("Erro ao salvar o produto. Verifique os campos."));
    }

    @Test
    void deveExibirToastErroAoEditarProdutoInvalido() {
        ProductPage page = new ProductPage(driver, wait);

        page.editarPrimeiroProduto();
        page.limparFormulario();
        page.salvar();

        String mensagem = page.getToastMensagem();

        assertTrue(mensagem.contains("Erro"));
        assertTrue(mensagem.contains("Erro ao salvar o produto. Verifique os campos."));
    }
}
