package com.automation.driver;

import com.automation.config.AppConfig;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Gerenciador central do driver WinAppDriver.
 * Responsável por criar, configurar e disponibilizar a instância do WindowsDriver.
 */
public final class DriverManager {

    private static WindowsDriver driver;

    private DriverManager() {
    }

    /**
     * Inicia uma sessão com um aplicativo Windows.
     *
     * @param appId ID do aplicativo (ex: "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App")
     *              ou caminho do executável (ex: "C:\\Program Files\\App\\app.exe")
     * @return instância configurada do WindowsDriver
     */
    public static WindowsDriver startSession(String appId) {
        return startSession(appId, null);
    }

    /**
     * Inicia uma sessão com um aplicativo Windows.
     *
     * @param appId   ID ou caminho do aplicativo
     * @param options capacidades adicionais (opcional)
     * @return instância configurada do WindowsDriver
     */
    public static WindowsDriver startSession(String appId, DesiredCapabilities options) {
        if (driver != null) {
            return driver;
        }

        DesiredCapabilities capabilities = options != null ? options : new DesiredCapabilities();
        capabilities.setCapability("app", appId);
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("deviceName", "WindowsPC");

        try {
            URL serverUrl = new URL(AppConfig.getWinAppDriverUrl());
            driver = new WindowsDriver(serverUrl, capabilities);
            driver.manage().timeouts().implicitlyWait(
                    AppConfig.getImplicitWaitSeconds(),
                    TimeUnit.SECONDS
            );
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL do WinAppDriver inválida: " + AppConfig.getWinAppDriverUrl(), e);
        }
    }

    /**
     * Inicia sessão com o Desktop (para interagir com janelas existentes).
     */
    public static WindowsDriver startDesktopSession() {
        return startSession("Root");
    }

    /**
     * Retorna o driver atual. Lança exceção se não houver sessão ativa.
     */
    public static WindowsDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver não inicializado. Chame startSession() primeiro.");
        }
        return driver;
    }

    /**
     * Encerra a sessão e libera recursos.
     */
    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Verifica se há uma sessão ativa.
     */
    public static boolean isSessionActive() {
        return driver != null;
    }
}
