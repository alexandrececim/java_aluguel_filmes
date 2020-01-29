# Projeto Java
## Controle de Aluguel de Filmes

Este pequeno Sistema para Desktop tem como finalidade o controle de aluguel, foi proposto como desafio para uma vaga de Estágio em Java por uma conceituada empresa em Santa Catarina.

## Finalidade do Sistema:
O Sistema de Controle de Aluguel de Filmes,  cadastra cliente, cadastra filmes e registra o aluguel com os filmes escolhidos pelo cliente, de forma muito simples e limitada para uso pratico no dia-a-dia.

## Sobre os requisitos do Projeto

1) A aplicação tem um único objetivo: Realizar o registro de aluguéis de filmes, desconsiderando o status do aluguel do filme. 

2) No final do teste, a aplicação deve possibilitar o CRUD (cadastro, listagem, alteração e deleção) de clientes, filmes e aluguéis


## Decisões sobre Requisito:
Para melhor atender os dois requisitos optei por não usar a main como local de todos os codigos que manipulam a aplicação, dividindo em:
a) Sistemas_telas - Pasta criada para abrigar a parte visual da aplicação e criando mais três sub-pastas para melhor clareza do codigo, são elas:

	a.1) crud_Cliente
	a.2) crud_filmes
	a.3) crud_aluguel
	
b) Os arquivos que implementam as interfaces "FilmeDAO" e "AluguelDAO" foram feitos com base no ClienteDAOImpl.java, alem do arquivo que configura a conexão com o banco de dados, são eles:

	b.1) FilmeDAOImpl.java
	b.2) AluguelDAOImpl.java
	b.3) BancoDeDados.java
	
	
c) Auterações feitas no código original:

	c.1) Main.java - Foi retirado todo o conteudo e passou a uma simples criação do objeto da classe responsavel pelo "Menu" da aplicação (NavegarMenu.java)
	
	c.2) Pasta entidades - Todos os três arquivos contidos na pasta foram adicionados a função "toString()" para melhor apresentação dos dados.


A divisão feita para codificar a solução teve como partida o crud_Cliente e todas as demais são copias da mesma, porém  com a adição de campos referentes as 
peculiaridades de cada entidade (Filme e Aluguel).

Foi adicionado as regras de validação de dados feita nas JTextFiel atraves do metodo de "saida do foco" tanto para os campos numericos, quanto para a formatação
das datas.

## O crud se despoem em três telas:

1) Inserir"Entidade".java - Responsavel pela inserção dos dados da "Entidade" (ex: Filme) no banco de dados.

2) Editar"Entidade".java - Onde é possível Chamar, auterar e deletar um cadastro do banco de dados do sua respectiva entidade, Ex: EditarCliente.java

3) Listar"Entidade".java - Lista todos os cadastros registrados da respectiva "Entidade".

## Objetivos:
Estou almejando uma oportunidade no mercado de Tecnologia aqui na cidade de FlorianÃ³polis, e estou com este pequeno projeto mostrando um pouco meu trabalho, agradeÃ§o a todos que poderem de alguma forma melhorar este projeto, seja como sujestÃ£o ou se poder uma indicaÃ§Ã£o que serÃ¡ muito bem vinda.

