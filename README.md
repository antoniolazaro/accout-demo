# accout-demo

## Inserção de agendamento


## Parametros Consulta

- Endpoint: http://localhost:8080/cvc/v1/schedule?page=0&size=20&lang=BR
- page = Pagina atual. Default = 0
- size = Tamanho da página a ser buscada no banco. Default = 20
- lang = Idioma (BR ou US). Default = US
- curl: curl -X GET "http://localhost:8080/cvc/v1/schedule?page=0&size=20&lang=BR" -H  "accept: */*"

## Swagger

- http://localhost:8080/cvc/swagger
- Body:
- {
  "id": 0,
  "amount": 0,
  "taxAmount": 0,
  "transferDate": "2021-11-13",
  "scheduleDate": "2021-11-13",
  "accountOrigin": "string",
  "accountDestination": "string",
  "language": "string"
  }

Requisitos:
 - Persistência de dados em memória => Banco H2
 - Código em última versão do Java => 17
 - Código com ferramenta de build => Maven
 - Casos de testes => Ok
 - Testes unitários => Ok
 - Sem arquivos de IDE => .gitignore atualizado
 - Código em inglês => Ok
 - Preparo para intercionalização => Demonstrado no Controller. I18NConfig faz a configuração e o método de leitura exibe como usar.

Recomendações:
- Utilizado Spring Boot em sua última versão com arquitetura modular separada em camadas baseada em suas responsabilidades
- Para executar o projeto Executar a classe SelectApplication ou rodar o build e executar o jar.

Regras de negócio:

- Aplicadas.