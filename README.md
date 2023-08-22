# desafioTotvs

No backend foi utilizado Flyway para criar as tabelas no banco de dados. Criei um endpoint de "/client", no qual é possivel listar todos os clientes cadastrados (GET) e é possivel criar novos clientes (Post), ambos retornam um Dto do cliente,
pois é foi necessario para nao repetir dados infinito e para interar esses Dtos utilizei um Mapper. Na parte de cadastro de cliente criei uma Excpetion para retornar de forma mais claras os motivos para o não cadastro do cliente. Todo a classe de
serviço foi desenvolvido utilizando TDD


# Instruções para roda a aplicação

  Criar uma schema chamado totvs no mysql 8.0;

  Fazer clone do projeto backendTotvs;

  Baixar todas as dependencias do maven;

  Rodar o projeto na classe main (BackendTotvsApplication);



O front foi feito com angular 15 e PO UI, utilizei os componentes prontos do PO UI.
