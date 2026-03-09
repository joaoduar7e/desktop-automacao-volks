package com.automation;

import com.automation.driver.DriverManager;
import com.automation.utils.AppIds;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exemplo de teste da Calculadora do Windows usando WinAppDriver.
 * Requer: Windows 10/11, WinAppDriver em execução, Calculadora instalada.
 */
@DisplayName("Testes da Calculadora Windows")
class CalculatorTest extends BaseTest {

    private WindowsDriver driver;
    private WebElement calculatorResult;

    @BeforeEach
    void setup() {
        driver = DriverManager.startSession(AppIds.CALCULATOR);
        calculatorResult = driver.findElement(By.xpath("//*[@AutomationId='CalculatorResults']"));
        assertNotNull(calculatorResult);
    }

    @BeforeEach
    void clearCalculator() {
        driver.findElement(By.name("Clear")).click();
        assertEquals("0", getResultText());
    }

    @Test
    @DisplayName("Soma: 1 + 7 = 8")
    void addition() {
        driver.findElement(By.name("One")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Seven")).click();
        driver.findElement(By.name("Equals")).click();
        assertEquals("8", getResultText());
    }

    @Test
    @DisplayName("Subtração: 9 - 1 = 8")
    void subtraction() {
        driver.findElement(By.name("Nine")).click();
        driver.findElement(By.name("Minus")).click();
        driver.findElement(By.name("One")).click();
        driver.findElement(By.name("Equals")).click();
        assertEquals("8", getResultText());
    }

    @Test
    @DisplayName("Multiplicação: 9 x 9 = 81")
    void multiplication() {
        driver.findElement(By.name("Nine")).click();
        driver.findElement(By.name("Multiply by")).click();
        driver.findElement(By.name("Nine")).click();
        driver.findElement(By.name("Equals")).click();
        assertEquals("81", getResultText());
    }

    @Test
    @DisplayName("Divisão: 88 ÷ 11 = 8")
    void division() {
        driver.findElement(By.name("Eight")).click();
        driver.findElement(By.name("Eight")).click();
        driver.findElement(By.name("Divide by")).click();
        driver.findElement(By.name("One")).click();
        driver.findElement(By.name("One")).click();
        driver.findElement(By.name("Equals")).click();
        assertEquals("8", getResultText());
    }

    private String getResultText() {
        return calculatorResult.getText()
                .replace("Display is", "")
                .trim();
    }
}
