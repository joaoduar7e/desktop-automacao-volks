# Desktop Automation - WinAppDriver + Java

Projeto simples e organizado para automação de aplicativos desktop Windows utilizando **WinAppDriver** e **Java**.

---

## 📋 Índice

- [O que é WinAppDriver?](#o-que-é-winappdriver)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Como usar](#como-usar)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Escrevendo seus próprios testes](#escrevendo-seus-próprios-testes)
- [Localizando elementos](#localizando-elementos)
- [Solução de problemas](#solução-de-problemas)

---

## O que é WinAppDriver?

**Windows Application Driver (WinAppDriver)** é uma ferramenta de automação de interface desenvolvida pela Microsoft que implementa o protocolo **WebDriver**. Com ela, você pode automatizar aplicativos Windows (UWP, Win32, WPF) usando as mesmas abordagens do Selenium, em Java ou outras linguagens.

### Principais características

- ✅ Suporta aplicativos UWP, Win32 e WPF  
- ✅ Usa o protocolo WebDriver (compatível com Selenium)  
- ✅ Gratuito e open-source  
- ✅ Funciona com JUnit, TestNG e outros frameworks de teste  

---

## Pré-requisitos

| Requisito | Descrição |
|-----------|-----------|
| **Sistema operacional** | Windows 10 ou Windows 11 |
| **Java** | JDK 11 ou superior |
| **Maven** | 3.6 ou superior |
| **Modo Desenvolvedor** | Habilitado nas configurações do Windows |

### Habilitar Modo Desenvolvedor

1. Abra **Configurações** do Windows  
2. Vá em **Privacidade e segurança** → **Para desenvolvedores**  
3. Ative **Modo de desenvolvedor**  

---

## Instalação

### 1. Instalar o WinAppDriver

1. Baixe o instalador MSI na página de [releases do WinAppDriver](https://github.com/Microsoft/WinAppDriver/releases)  
2. Execute o instalador (instalação padrão em `C:\Program Files (x86)\Windows Application Driver`)  
3. O WinAppDriver será instalado e estará pronto para uso  

### 2. Clonar ou baixar o projeto

```bash
cd desktop-automacao-volks
```

### 3. Instalar dependências com Maven

```bash
mvn clean install
```

---

## Como usar

### Passo 1: Iniciar o WinAppDriver

Antes de rodar os testes, o servidor WinAppDriver precisa estar em execução:

1. Abra o **Prompt de Comando** ou **PowerShell** como **Administrador**  
2. Navegue até a pasta de instalação:
   ```
   cd "C:\Program Files (x86)\Windows Application Driver"
   ```
3. Execute:
   ```
   WinAppDriver.exe
   ```
4. Aguarde a mensagem indicando que o servidor está ouvindo em `http://127.0.0.1:4723`  

> **Dica:** Mantenha essa janela aberta enquanto os testes estiverem rodando.

### Passo 2: Executar os testes

```bash
mvn test
```

Para executar apenas a classe de teste da Calculadora:

```bash
mvn test -Dtest=CalculatorTest
```

Para executar um método específico:

```bash
mvn test -Dtest=CalculatorTest#addition
```

---

## Estrutura do projeto

```
desktop-automacao-volks/
├── pom.xml                          # Configuração Maven e dependências
├── README.md                        # Este arquivo
│
├── src/main/java/com/automation/
│   ├── config/
│   │   └── AppConfig.java           # Configurações centralizadas
│   ├── driver/
│   │   └── DriverManager.java       # Gerenciador do WinAppDriver
│   └── utils/
│       └── AppIds.java              # IDs de aplicativos comuns
│
├── src/main/resources/
│   └── config.properties            # Propriedades de configuração
│
└── src/test/java/com/automation/
    ├── BaseTest.java                # Classe base para testes
    └── CalculatorTest.java          # Exemplo: testes da Calculadora
```

### Descrição dos componentes

| Componente | Função |
|------------|--------|
| **AppConfig** | Carrega URL do WinAppDriver, timeouts e outras configurações |
| **DriverManager** | Cria e gerencia a sessão do `WindowsDriver` |
| **AppIds** | Constantes com IDs de aplicativos (Calculadora, Notepad, etc.) |
| **BaseTest** | Classe base que garante o encerramento do driver após cada teste |
| **CalculatorTest** | Exemplo prático de testes na Calculadora do Windows |

---

## Configuração

Edite o arquivo `src/main/resources/config.properties` para ajustar o comportamento:

```properties
# URL do servidor WinAppDriver (para máquina remota, use o IP)
winappdriver.url=http://127.0.0.1:4723

# Tempo de espera implícita (segundos)
implicit.wait.seconds=5

# Tempo de espera explícita (segundos)
explicit.wait.seconds=10
```

---

## Escrevendo seus próprios testes

### Exemplo básico

```java
package com.automation;

import com.automation.driver.DriverManager;
import com.automation.utils.AppIds;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

class MeuTeste extends BaseTest {

    private WindowsDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverManager.startSession(AppIds.CALCULATOR);
    }

    @Test
    void meuPrimeiroTeste() {
        driver.findElement(By.name("One")).click();
        driver.findElement(By.name("Plus")).click();
        driver.findElement(By.name("Two")).click();
        driver.findElement(By.name("Equals")).click();
        // Assertions...
    }
}
```

### Iniciando um aplicativo por caminho

```java
// Por caminho do executável
driver = DriverManager.startSession("C:\\Program Files\\MeuApp\\app.exe");

// Por nome do processo (ex: Paint)
driver = DriverManager.startSession("mspaint");
```

### Sessão com o Desktop

Para interagir com janelas já abertas ou com o desktop:

```java
driver = DriverManager.startDesktopSession();
```

---

## Localizando elementos

O WinAppDriver suporta os mesmos localizadores do Selenium. Exemplos:

| Estratégia | Exemplo |
|------------|---------|
| **Por nome** | `By.name("Clear")` |
| **Por AutomationId** | `By.id("CalculatorResults")` ou `By.xpath("//*[@AutomationId='CalculatorResults']")` |
| **Por classe** | `By.className("Button")` |
| **Por XPath** | `By.xpath("//Button[@Name='One']")` |

### Ferramentas para inspecionar elementos

- **Inspect.exe** – Ferramenta do Windows SDK (UI Automation)  
- **WinAppDriver UI Recorder** – Grava ações e gera código  
- **Appium Desktop** – Inspeciona elementos e executa sessões  

---

## Solução de problemas

### Erro: "Connection refused" ou "Unable to connect"

- Verifique se o **WinAppDriver.exe** está em execução  
- Confirme que está rodando como **Administrador**  
- Verifique se a porta **4723** não está em uso por outro processo  

### Erro: "Could not find a connected device"

- Ative o **Modo Desenvolvedor** nas configurações do Windows  
- Reinicie o WinAppDriver após ativar  

### Aplicativo não inicia

- Verifique se o **App ID** ou caminho está correto  
- Para UWP: use o formato `Pacote!App` (ex: `Microsoft.WindowsCalculator_8wekyb3d8bbwe!App`)  
- Para Win32: use o caminho completo do `.exe`  

### Elemento não encontrado

- Use o **Inspect.exe** para verificar o `AutomationId` e `Name` reais  
- Aumente o `implicit.wait.seconds` no `config.properties`  
- Alguns elementos podem estar em painéis que precisam ser expandidos antes  

---

## Licença

Este projeto é de uso livre para fins de estudo e automação.

---

## Referências

- [WinAppDriver - Documentação oficial](https://github.com/Microsoft/WinAppDriver)
- [Getting Started - WinAppDriver](https://microsoft.github.io/WinAppDriver/)
- [Appium Java Client](https://github.com/appium/java-client)
# desktop-automacao-volks
# desktop-automacao-volks
