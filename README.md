Sistema Veterinário - Microsserviços
Este projeto implementa um sistema de gestão veterinária utilizando arquitetura de microsserviços com autenticação via JWT.

Como rodar o sistema, pré-requisitos:
- Java 17+
- Maven 3.8+
- MongoDB

Serviço	Porta
API de Autenticação	8082
API de Dono	8084
API de Animal	8081
API de Serviço	8080

Endpoints Disponíveis:

📌 Autenticação (API Auth - localhost:8082/sistema-veterinario)
POST /autenticar - Gera token JWT dependendo das credenciais (username e password)
GET /validar - Recebe os tokens das API's externas e valida eles

📌 Dono (API Dono - localhost:8084/sistema-veterinario/dono)
POST / - Cadastrar dono
GET / - Lista todos os Donos
GET /{id} - Buscar dono por ID
DELETE /{id} - Deleta Dono por ID

📌 Animal (API Animal - localhost:8081/sistema-veterinario/animal)
POST / - Cadastrar animal
PUT /{id} - Atualiza animal por ID
GET / - Lista todos os Animais
GET /{id} - Buscar animal por ID
DELETE /{id} - Delete animal pelo ID

📌 Serviço (API Servico - localhost:8080/sistema-veterinario/servico)
POST / - Cadastrar serviço
PUT /{id} - Atualiza serviço por ID
GET / - Lista todos os serviços
GET /{id} - Buscar serviço por ID
GET /{id}/detalhado → Buscar serviço com detalhes (Animal)
DELETE /{id} - Deleta serviço por ID

Como gerar e usar o token JWT utilizando o Postman
Acesse o endpoint de localhost:8082/sistema-veterinario/autenticar utilizando o método POST e escreva um body em Json:

"Content-Type: application/json"
{
  "username":"adm",
  "password":"123"
}
Copie o token retornado.

Em cada requisição, adicione o header:

Authorization: Bearer <TOKEN>

 Exemplos de uso das APIs com Postman
 
➡️ Criar Dono
POST http://localhost:8084/sistema-veterinario/dono?cep=xxxxxxxxx (Cep que desejar)
 "Authorization: Bearer <TOKEN>" 
 "Content-Type: application/json" 
 '{
    "nome": "João Silva",
    "cpf": 12345678900,
    "telefone": "99999-9999",
    "endereco": {
           "rua": "Rua A",
           "numero": "123",
           "cidade": "São Paulo",
           "estado": "SP"
       },
       "idAnimal": "ID_DO_ANIMAL"
     }'

Observações:
- Todos os endpoints, exceto login, exigem token JWT no header.
- Se o token estiver expirado ou inválido, será retornado 401 Unauthorized.
- As APIs se comunicam entre si via RestTemplate com autenticação mútua via token.

Tecnologias utilizadas:
- Spring Boot
- MongoDB
- JWT
- Maven
