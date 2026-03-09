package com.automation;

import com.automation.driver.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Classe base para todos os testes.
 * Garante que o driver seja encerrado após cada teste.
 */
public abstract class BaseTest {

    @BeforeEach
    void baseSetUp() {
        // Sobrescreva em subclasses para configurar o driver
    }

    @AfterEach
    void baseTearDown() {
        DriverManager.quit();
    }
}
