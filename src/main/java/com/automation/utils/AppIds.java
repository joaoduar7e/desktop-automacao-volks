package com.automation.utils;

/**
 * IDs de aplicativos Windows comuns para uso nos testes.
 * Use o Inspect.exe ou WinAppDriver UI Recorder para descobrir IDs de outros apps.
 */
public final class AppIds {

    /** Calculadora do Windows 10/11 */
    public static final String CALCULATOR = "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App";

    /** Bloco de Notas */
    public static final String NOTEPAD = "C:\\Windows\\System32\\notepad.exe";

    /** Paint */
    public static final String PAINT = "mspaint";

    /** Desktop (para sessão raiz) */
    public static final String DESKTOP = "Root";

    private AppIds() {
    }
}
