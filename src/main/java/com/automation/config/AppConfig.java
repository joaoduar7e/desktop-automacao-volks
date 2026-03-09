package com.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configurações centralizadas do projeto de automação.
 * Carrega valores do arquivo config.properties ou usa valores padrão.
 */
public class AppConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.out.println("Arquivo config.properties não encontrado. Usando valores padrão.");
        }
    }

    /** URL do servidor WinAppDriver (padrão: localhost:4723) */
    public static String getWinAppDriverUrl() {
        return properties.getProperty("winappdriver.url", "http://127.0.0.1:4723");
    }

    /** Tempo de espera implícita em segundos */
    public static int getImplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("implicit.wait.seconds", "5"));
    }

    /** Tempo de espera explícita em segundos */
    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("explicit.wait.seconds", "10"));
    }

    private AppConfig() {
    }
}
