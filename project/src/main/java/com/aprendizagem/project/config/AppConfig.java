package com.aprendizagem.project.config;

/**
 * Implementação do padrão de projeto Singleton.
 * Esta classe gere configurações globais da aplicação, garantindo que apenas
 * uma única instância dela seja criada durante todo o ciclo de vida da aplicação.
 */
public class AppConfig {

    // 1. A única instância da classe (inicializada uma vez)
    private static final AppConfig instance = new AppConfig();

    // Propriedades de configuração
    private final String platformName = "Questify";
    private final String platformVersion = "1.0.0-FINAL";

    /**
     * 2. O construtor é privado para impedir a criação de instâncias
     * a partir de fora da classe.
     */
    private AppConfig() {
        // Lógica de inicialização, se necessária
        System.out.println(">>> Singleton AppConfig inicializado.");
    }

    /**
     * 3. O método estático público que fornece o ponto de acesso global
     * à única instância da classe.
     * @return A instância única de AppConfig.
     */
    public static AppConfig getInstance() {
        return instance;
    }

    // Getters para as propriedades de configuração
    public String getPlatformName() {
        return platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }
}
