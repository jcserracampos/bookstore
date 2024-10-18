# Bookstore

Este é um projeto de uma livraria online desenvolvido com Spring Boot e PostgreSQL.

## Descrição

O Bookstore é uma aplicação web que permite gerenciar autores, livros e usuários. Ela oferece funcionalidades como cadastro de autores e livros, autenticação de usuários e muito mais.

## Tecnologias Utilizadas

- Java
- Spring Boot
- PostgreSQL
- Maven
- JUnit (para testes)
- Docker
- Spring Security
- Swagger (para documentação da API)
- Spring Actuator (para monitoramento)
- Bean Validation (para validação de dados)

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas:

- `controller`: Contém os controladores REST da aplicação.
- `service`: Contém a lógica de negócios.
- `repository`: Interfaces para acesso ao banco de dados.
- `model`: Entidades JPA que representam as tabelas do banco de dados.
- `config`: Configurações do Spring, incluindo segurança e Swagger.
- `exception`: Classes para tratamento de exceções personalizadas.

## Configuração

Para configurar o projeto, atualize o arquivo `application.properties` com as credenciais do seu banco de dados PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

Se necessário, conceda as permissões necessárias ao usuário do banco de dados:

```sql
CREATE DATABASE yourdatabase;
CREATE USER yourusername WITH ENCRYPTED PASSWORD 'yourpassword';
\c yourdatabase
GRANT ALL PRIVILEGES ON DATABASE yourdatabase TO yourusername;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO yourusername;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO yourusername;
```

## Perfis do Spring

O projeto utiliza perfis do Spring para diferentes ambientes:

- `dev`: Para desenvolvimento local
- `prod`: Para ambiente de produção

Para ativar um perfil, adicione a seguinte linha ao `application.properties`:

```properties
spring.profiles.active=dev
```

## Construindo o Projeto

Para construir o projeto, execute o seguinte comando na raiz do projeto:

```bash
./mvnw clean install
```

Este comando irá baixar todas as dependências, compilar o código e executar os testes.

## Executando o Projeto

Para iniciar a aplicação, use o seguinte comando:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

## Usuários Padrão

O sistema cria automaticamente dois usuários padrão na primeira execução:

1. Usuário Administrador:
   - Username: admin
   - Senha: admin123
   - Função: ROLE_ADMIN

2. Usuário Comum:
   - Username: user
   - Senha: user123
   - Função: ROLE_USER

Estes usuários são criados para facilitar o teste inicial do sistema. Em um ambiente de produção, recomenda-se alterar essas senhas ou remover esses usuários após a configuração inicial.

## Executando os Testes

Para executar os testes do projeto, use o comando:

```bash
./mvnw test
```

Os testes incluem:
- Testes unitários para serviços e repositórios
- Testes de integração para controladores
- Testes de validação para garantir que as regras de negócio sejam aplicadas corretamente

## Documentação da API

A documentação da API está disponível através do Swagger UI. Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

## Monitoramento com Spring Actuator

O Spring Actuator está configurado para fornecer endpoints de monitoramento. Você pode acessar esses endpoints em:

```
http://localhost:8080/actuator
```

Nota: Em ambiente de produção, certifique-se de proteger adequadamente esses endpoints.

## Usando Docker

Este projeto inclui configurações para Docker, permitindo uma fácil configuração e execução do ambiente de desenvolvimento.

### Pré-requisitos

- Docker
- Docker Compose

### Construindo e Executando com Docker

1. Construa a imagem Docker da aplicação:

```bash
docker build -t bookstore-app .
```

2. Execute a aplicação usando Docker Compose:

```bash
docker-compose up
```

Isso iniciará tanto o banco de dados PostgreSQL quanto a aplicação Spring Boot. A aplicação estará disponível em `http://localhost:8080`.

### Detalhes da Configuração Docker

- O `Dockerfile` usa uma abordagem multi-stage para construir a aplicação, resultando em uma imagem final menor e mais segura.
- Um usuário não-root é criado na imagem Docker para executar a aplicação, melhorando a segurança.
- O `docker-compose.yml` configura tanto o serviço da aplicação quanto o banco de dados PostgreSQL.
- Um healthcheck é configurado para o banco de dados, garantindo que a aplicação só inicie quando o PostgreSQL estiver pronto.
- Variáveis de ambiente são usadas para configurar a conexão com o banco de dados e o perfil do Spring.

Para parar os contêineres, use:

```bash
docker-compose down
```

## Gerenciamento de Versão

Este projeto utiliza o GitFlow para gerenciamento de versões. As principais branches são:

- `main`: Contém o código em produção
- `develop`: Branch de desenvolvimento
- `feature/*`: Para novas funcionalidades
- `hotfix/*`: Para correções urgentes em produção

## Contribuindo

Contribuições são bem-vindas! Por favor, siga estes passos:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Faça commit das suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Faça push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

Por favor, certifique-se de atualizar os testes conforme apropriado.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
