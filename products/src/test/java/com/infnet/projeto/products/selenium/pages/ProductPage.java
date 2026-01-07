package com.infnet.projeto.products.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By btnAdicionar = By.className("btn-adicionar");
    private final By inputNome = By.className("input-nome");
    private final By inputCategoria = By.className("input-categoria");
    private final By inputValor = By.className("p-inputnumber-input");
    private final By btnSalvar = By.className("btn-salvar");
    private final By toast = By.className("p-toast-message");
    private final By btnEditar = By.className("btn-editar");
    private final By btnExcluir = By.className("btn-excluir");
    private final By confirmarExclusao = By.xpath("//span[text()='Sim']");

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clicarAdicionar() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAdicionar)).click();
    }

    public void preencherFormulario(String nome, String categoria, String valor) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNome)).sendKeys(nome);
        driver.findElement(inputCategoria).sendKeys(categoria);
        driver.findElement(inputValor).sendKeys(valor);
    }

    public void limparFormulario() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNome)).clear();
        driver.findElement(inputCategoria).clear();
        driver.findElement(inputValor).clear();
    }

    public void salvar() {
        driver.findElement(btnSalvar).click();
    }

    public void editarPrimeiroProduto() {
        wait.until(ExpectedConditions.elementToBeClickable(btnEditar)).click();
    }

    public void excluirPrimeiroProduto() {
        wait.until(ExpectedConditions.elementToBeClickable(btnExcluir)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmarExclusao)).click();
    }

    public boolean toastVisivel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(toast)).isDisplayed();
    }

    public String getToastMensagem() {
        return wait
                .until(ExpectedConditions.visibilityOfElementLocated(toast))
                .getText();
    }
}
