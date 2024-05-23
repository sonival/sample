# Teste Ekan #

# Sobre o projeto
Projeto Teste DESENVOLVEDOR JAVA.

# Tecnologias utilizadas
## Back end
- Java 17
- Spring Boot 
- Spring JPA / Hibernate
- Spring Security
- Lombok
- Maven
- OpenApi
- HW

# Como executar o projeto
## Back end
Pré-requisitos: Java 17 e MySql Server


```bash
# clonar repositório
git clone https://github.com/sonival/sample

# executar o comando
mvn clean install

# rodar aplicação
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# abrindo o openapi da aplicação no navegador (Chrome/FireFox)
http://localhost:8080/swagger-ui/index.html


# Acesso ao banco de dados H2
hhttp://localhost:8080/h2


# Curl para gerar TOKEN autorizacao:
curl --location 'http://localhost:8080/token' \
--header 'Authorization;' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=44571C31920C0141EF3A8B22621FDA9B; XSRF-TOKEN=a7606ae6-b662-4e72-901f-7120cf456442' \
--data '{
  "login": "demo",
  "senha": "demo"
}'

```

