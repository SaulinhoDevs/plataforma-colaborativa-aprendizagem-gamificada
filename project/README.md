# 🎮 Questify - Plataforma Colaborativa de Aprendizagem Gamificada

Uma plataforma de aprendizagem gamificada desenvolvida em Spring Boot, aplicando padrões de projeto GOF e princípios SOLID.

## 📋 Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6+**
- **Navegador web** moderno (Chrome, Firefox, Safari, Edge)
- **Git** (para clonar o repositório)

## 🚀 Como Executar

### 1. Clonar o Repositório

```bash
git clone https://github.com/SaulinhoDevs/plataforma-colaborativa-aprendizagem-gamificada.git
cd plataforma-colaborativa-aprendizagem-gamificada/project
```

### 2. Executar a Aplicação

```bash
# Navegar para a pasta do projeto
cd project

# Executar com Maven (recomendado)
./mvnw spring-boot:run

# OU se preferir Maven instalado globalmente
mvn spring-boot:run
```

### 3. Acessar a Aplicação

- **URL Principal:** <http://localhost:8080>
- **Console H2 (Banco de Dados):** <http://localhost:8080/h2-console>

## 👤 Usuários de Teste

### Administrador/Professor

- **Email:** `admin@admin.com`
- **Senha:** `admin123`
- **Tipo:** Professor

### Como Criar Novos Usuários

1. Acesse: <http://localhost:8080/cadastro>
2. Preencha os dados
3. Escolha o tipo: `ALUNO`, `PROFESSOR` ou `VISITANTE`

## 🎯 Funcionalidades Principais

### Para Alunos

- ✅ Dashboard com quizzes disponíveis
- ✅ Sistema de pontuação gamificada
- ✅ Perfil com estatísticas pessoais
- ✅ Mural coletivo para interação

### Para Professores

- ✅ Criação de quizzes
- ✅ Gestão de quizzes existentes
- ✅ Relatórios de desempenho (PDF/CSV)
- ✅ Funcionalidade de desfazer ações

### Recursos Gerais

- ✅ Sistema de conquistas e medalhas
- ✅ Ranking de usuários
- ✅ Integração com ranking global simulado
- ✅ Notificações de progresso

## 🗄️ Banco de Dados

A aplicação usa **H2 Database** em memória para facilitar execução e testes.

### Acessar Console H2

1. URL: <http://localhost:8080/h2-console>
2. **JDBC URL:** `jdbc:h2:mem:plataforma_db`
3. **Username:** `sa`
4. **Password:** *(deixar vazio)*

## 🏗️ Padrões de Projeto Implementados

### Padrões de Criação

- **Singleton:** `AppConfig` - Configurações globais da aplicação
- **Factory Method:** `UsuarioFactory` - Criação de diferentes tipos de usuários

### Padrões Estruturais

- **Decorator:** `PontuacaoDecorator` - Bônus de pontuação por tempo e streak
- **Composite:** `Conquista/GrupoDeConquistas` - Hierarquia de conquistas
- **Adapter:** `ExternalRankingAdapter` - Integração com ranking externo
- **Facade:** `RelatorioFacade` - Interface simplificada para geração de relatórios

### Padrões Comportamentais

- **Observer:** `QuizCompletionObserver` - Notificações de conquistas
- **Strategy:** `PontuacaoStrategy` - Diferentes algoritmos de pontuação
- **Command:** `SubmitQuizCommand` - Operações que podem ser desfeitas

## 📁 Estrutura do Projeto

```
project/
├── src/main/java/com/aprendizagem/project/
│   ├── config/          # Configurações (Security, Data, etc.)
│   ├── controllers/     # Controllers MVC
│   ├── dto/            # Data Transfer Objects
│   ├── enums/          # Enumerações
│   ├── model/          # Entidades JPA
│   ├── repository/     # Repositórios Spring Data
│   └── service/        # Lógica de negócio
│       ├── command/    # Padrão Command
│       ├── facade/     # Padrão Facade
│       ├── integration/# Integrações externas
│       ├── observer/   # Padrão Observer
│       ├── relatorio/  # Geração de relatórios
│       └── strategy/   # Padrão Strategy
├── src/main/resources/
│   ├── static/         # CSS, JS, imagens
│   └── templates/      # Templates Thymeleaf
└── pom.xml            # Dependências Maven
```

## 🛠️ Resolução de Problemas

### Erro: "Port 8080 already in use"

```bash
# Matar processo na porta 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Erro: "Java version not supported"

- Verifique se está usando Java 21+: `java -version`
- Configure JAVA_HOME se necessário

### Banco de dados não inicializa

- Verifique se não há outro H2 rodando
- Reinicie a aplicação: `Ctrl+C` e execute novamente

## 📊 Dados de Exemplo

A aplicação automaticamente cria:

- Usuário administrador (`admin@admin.com`)
- Quizzes de exemplo (se configurado)
- Conquistas básicas do sistema

## 🔧 Configuração Adicional

### Alterar Porta do Servidor

No arquivo `application.properties`:

```properties
server.port=8081
```

### Configurar Banco Persistente

Para usar banco persistente, altere em `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/questify
```

## 📝 Logs e Debug

Para ver logs detalhados, adicione em `application.properties`:

```properties
logging.level.com.aprendizagem.project=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🤝 Contribuição

Este projeto foi desenvolvido como trabalho acadêmico para a disciplina de Padrões de Projeto.

**Desenvolvido por:** Vinicius Xavier dos Santos, Saulo Bernadino Melo, Flavio Costa Moreira  
**Instituição:** IFBA - Campus Santo Antônio de Jesus  
**Disciplina:** Padrões de Projeto  
**Professor:** Felipe Silva

---

**Última atualização:** Setembro 2025
