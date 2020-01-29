# Projeto Java
## Controle de Aluguel de Filmes

Este pequeno Sistema para Desktop tem como finalidade o controle de aluguel, foi proposto como desafio para uma vaga de Est�gio em Java por uma conceituada empresa em Santa Catarina.

## Finalidade do Sistema:
O Sistema de Controle de Aluguel de Filmes,  cadastra cliente, cadastra filmes e registra o aluguel com os filmes escolhidos pelo cliente, de forma muito simples e limitada para uso pratico no dia-a-dia.

## Sobre os requisitos do Projeto

1) A aplica��o tem um �nico objetivo: Realizar o registro de alugu�is de filmes, desconsiderando o status do aluguel do filme. 

2) No final do teste, a aplica��o deve possibilitar o CRUD (cadastro, listagem, altera��o e dele��o) de clientes, filmes e alugu�is


## Decis�es sobre Requisito:
Para melhor atender os dois requisitos optei por n�o usar a main como local de todos os codigos que manipulam a aplica��o, dividindo em:
a) Sistemas_telas - Pasta criada para abrigar a parte visual da aplica��o e criando mais tr�s sub-pastas para melhor clareza do codigo, s�o elas:

	a.1) crud_Cliente
	a.2) crud_filmes
	a.3) crud_aluguel
	
b) Os arquivos que implementam as interfaces "FilmeDAO" e "AluguelDAO" foram feitos com base no ClienteDAOImpl.java, alem do arquivo que configura a conex�o com o banco de dados, s�o eles:

	b.1) FilmeDAOImpl.java
	b.2) AluguelDAOImpl.java
	b.3) BancoDeDados.java
	
	
c) Autera��es feitas no c�digo original:

	c.1) Main.java - Foi retirado todo o conteudo e passou a uma simples cria��o do objeto da classe responsavel pelo "Menu" da aplica��o (NavegarMenu.java)
	
	c.2) Pasta entidades - Todos os tr�s arquivos contidos na pasta foram adicionados a fun��o "toString()" para melhor apresenta��o dos dados.


A divis�o feita para codificar a solu��o teve como partida o crud_Cliente e todas as demais s�o copias da mesma, por�m  com a adi��o de campos referentes as 
peculiaridades de cada entidade (Filme e Aluguel).

Foi adicionado as regras de valida��o de dados feita nas JTextFiel atraves do metodo de "saida do foco" tanto para os campos numericos, quanto para a formata��o
das datas.

## O crud se despoem em tr�s telas:

1) Inserir"Entidade".java - Responsavel pela inser��o dos dados da "Entidade" (ex: Filme) no banco de dados.

2) Editar"Entidade".java - Onde � poss�vel Chamar, auterar e deletar um cadastro do banco de dados do sua respectiva entidade, Ex: EditarCliente.java

3) Listar"Entidade".java - Lista todos os cadastros registrados da respectiva "Entidade".

## Objetivos:
Estou almejando uma oportunidade no mercado de Tecnologia aqui na cidade de Florianópolis, e estou com este pequeno projeto mostrando um pouco meu trabalho, agradeço a todos que poderem de alguma forma melhorar este projeto, seja como sujestão ou se poder uma indicação que será muito bem vinda.

