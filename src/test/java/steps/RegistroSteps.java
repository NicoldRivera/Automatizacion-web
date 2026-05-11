package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistroSteps {
    WebDriver driver;
    WebDriverWait wait;

    @Given("estoy en la página de la tienda")
    public void estoy_en_la_pagina_de_la_tienda() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://qalab.bensg.com/store/pe/");
    }

    @When("doy click en la opción iniciar sesión")
    public void doy_click_en_la_opcion_iniciar_sesion() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Iniciar sesión"))).click();
    }

    @And("doy click en el link text cree una cuenta aquí")
    public void doy_click_en_el_link_text_cree_una_cuenta_aqui() {
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Cree una aquí"))).click();
    }

    @And("lleno los campos del formulario para el registro")
    public void lleno_los_campos_del_formulario_para_el_registro() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-firstname")));

        // Email único siempre para evitar duplicados
        String email = "test" + System.currentTimeMillis() + "@mail.com";
        System.out.println("Email usado: " + email);

        driver.findElement(By.id("field-id_gender-2")).click();
        driver.findElement(By.id("field-firstname")).sendKeys("Juan");
        driver.findElement(By.id("field-lastname")).sendKeys("Perez");
        driver.findElement(By.id("field-email")).sendKeys(email);
        driver.findElement(By.id("field-password")).sendKeys("Jperez2026!"); // Contraseña fuerte
        driver.findElement(By.id("field-birthday")).sendKeys("01/01/1990");

        // Espera 1 seg para que valide la contraseña
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @And("doy click en el botón guardar")
    public void doy_click_en_el_boton_guardar() {
        // Scroll hasta abajo
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try { Thread.sleep(1500); } catch (InterruptedException e) {}

        // Marca las 2 casillas
        WebElement termsCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("customer_privacy")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", termsCheckbox);

        WebElement privacyCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("psgdpr")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", privacyCheckbox);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Busca el botón por CSS selector más genérico
        WebElement btnGuardar = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("button[type='submit']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnGuardar);
    }

    @Then("debería visualizar mi usuario logeado en la pantalla")
    public void deberia_visualizar_mi_usuario_logeado_en_la_pantalla() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("account")));
        String usuario = driver.findElement(By.className("account")).getText();
        if (usuario.contains("Juan Perez")) {
            System.out.println("Test pasado: Usuario logeado " + usuario);
        } else {
            throw new AssertionError("No se visualiza el usuario logeado");
        }

        try { Thread.sleep(5000);} catch (InterruptedException e) {}
        driver.quit();
    }
}
