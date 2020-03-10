# sistema-votacao

## PAUTA

### Criação de nova pauta

REQUEST:

``` 
POST /v1/pauta HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
{
	"titulo": "Implantação de novo sistema",
	"descricao": "Associado concorda com a implantação do novo sistema?"
}
``` 

RESPONSE:

	Status code 201 (created)
	

### Listar todas as pautas cadastradas

REQUEST:

``` 
GET /v1/pauta HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
``` 

RESPONSE:

``` 
[
    {
        "id": 1,
        "dataCriacao": "2019-03-18 15:57:05",
        "titulo": "Implantação de novo sistema",
        "descricao": "Associado concorda com a implantação do novo sistema?"
    }
]
``` 

### Visualizar resultado (parcial/final) de votação da pauta

REQUEST:

``` 
GET /v1/pauta/1/resultado HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
``` 

RESPONSE:

``` 
{
    "status": "EM_ANDAMENTO",
    "totalVotos": 1,
    "totalVotosSIM": 1,
    "totalVotosNAO": 0
}
``` 

### Buscar pauta por ID

REQUEST:

``` 
GET /v1/pauta/1 HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
``` 

RESPONSE:

``` 
{
    "id": 1,
    "dataCriacao": "2019-03-18 15:57:05",
    "titulo": "Implantação de novo sistema",
    "descricao": "Associado concorda com a implantação do novo sistema?"
}
``` 

## SESSÃO DE VOTACAO

### Abrir Sessao de Votacao

REQUEST: 

``` 
POST /v1/sessaoVotacao HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
{
	"idPauta": "1",
	"tempoDuracaoMinutos": "10"
}
``` 

RESPONSE:

	Status code 201 (created)
	

### Buscar Sessao de Votacao por ID
	
REQUEST:

``` 
GET /v1/sessaoVotacao/1 HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
``` 

RESPONSE:

``` 
{
    "id": 1,
    "dataCriacao": "2019-03-18 16:07:07",
    "dataAbertura": "2019-03-18 16:07:07",
    "dataFechamento": "2019-03-18 16:17:07",
    "votos": [],
    "status": "EM_ANDAMENTO"
}
``` 
	
## VOTACAO ASSOCIADO


### Registrar voto associado (votar)

REQUEST:

``` 
POST /v1/votoAssociado HTTP/1.1
Host: sistema-votacao.herokuapp.com
Content-Type: application/json
cache-control: no-cache
{
	"idPauta": "1",
	"cpfAssociado": "76034756006",
	"voto": "SIM"
}
``` 

RESPONSE:

	Status code 201 (created)
