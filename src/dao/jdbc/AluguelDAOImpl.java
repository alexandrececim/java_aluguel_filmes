package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import dao.AluguelDAO;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO {

	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
		
		PreparedStatement myStmt = conn.prepareStatement("insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");

        Integer returnId = this.getNextId(conn);

        myStmt.setInt(1, returnId);
        myStmt.setInt(2, aluguel.getCliente().getIdCliente());
        myStmt.setDate(3, (java.sql.Date) aluguel.getDataAluguel());
        myStmt.setDouble(4, aluguel.getValor());

        myStmt.execute();
        System.out.println(aluguel.getFilmes().size());
            	
            	for (int i = 0; i < aluguel.getFilmes().size(); i++) {
            		 //------------------------------------------------------------------
                	
                    // Segundo INSERT - Tabela usuario
                   
            	        PreparedStatement myStmt2 = conn.prepareStatement( "INSERT INTO re_aluguel_filme (id_filme, id_aluguel) VALUES (?, ?)");
            	       
            	
            	        myStmt2.setInt(1, aluguel.getFilmes().get(i).getIdFilme());
            	        myStmt2.setInt(2, returnId);
            	        
            	
            	        myStmt2.execute();
            	        
            	        System.out.println("Loop "+i);
            	            	
            	  
        		}
            	
            	conn.commit();
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("update en_aluguel set id_cliente = (?), "
				+ "data_aluguel = (?), valor = (?) where id_aluguel = (?)");
		 
	        myStmt.setInt(1, aluguel.getCliente().getIdCliente());
	        myStmt.setDate(2, (java.sql.Date) aluguel.getDataAluguel());
	        myStmt.setDouble(3, aluguel.getValor());
	        myStmt.setInt(4, aluguel.getIdAluguel());
	        

	        myStmt.execute();
	        

	        //deletando os lançamentos
	        PreparedStatement myStmtDelet = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");
	        myStmtDelet.setInt(1, aluguel.getIdAluguel());

	        myStmtDelet.execute();
	        conn.commit();

	        System.out.println(aluguel.getFilmes().size());
	            	for (int i = 0; i < aluguel.getFilmes().size(); i++) {
	            		 //------------------------------------------------------------------
	                	try {
	                		 // Segundo INSERT - Tabela usuario
	                        
	            	        PreparedStatement myStmt2 = conn.prepareStatement( "INSERT INTO re_aluguel_filme (id_filme, id_aluguel) VALUES (?, ?)");
	            	        
	            	
	            	        myStmt2.setInt(1, aluguel.getFilmes().get(i).getIdFilme());
	            	        myStmt2.setInt(2, aluguel.getIdAluguel());
	            	        
	            	
	            	        myStmt2.execute();
	            	
	            	        
	            	
	            	    } catch (SQLException ex) {
	            	        //Tools_SQL.mensagemErro(ex); // exibe mensagem de erro, caso ocorra
	            	    } finally {
	            	       // Connection_BD.closeConnection(con, stmt, rs); // encerra conexão
	            	    }
	        		}
	            	conn.commit();	
	         
	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = ?");
        PreparedStatement myStmt2 = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");

        myStmt.setInt(1, aluguel.getIdAluguel());
        myStmt2.setInt(1, aluguel.getIdAluguel());
        
        myStmt2.execute();
        myStmt.execute();
        
        conn.commit();
		
	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		
		Cliente cliente = new Cliente();
        
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");
		
        myStmt.setInt(1, idAluguel);
        

        ResultSet myRs = myStmt.executeQuery();
        

        if (!myRs.next()) {
            return null;
        }
        
        
        cliente.setIdCliente(myRs.getInt("id_cliente"));
        Date data = myRs.getDate("data_aluguel");        
        Float valor = myRs.getFloat("valor");
        
       
        PreparedStatement myStmt2 = conn.prepareStatement("select * from re_aluguel_filme  where id_aluguel = ?");
        myStmt2.setInt(1, idAluguel);
        ResultSet myRs2= myStmt2.executeQuery();
        
        List<Filme> listFilmes = new ArrayList<Filme>();
        
        while(myRs2.next()) {
        	Filme filme = new Filme();
        	filme.setIdFilme(myRs2.getInt("id_filme"));
        	
        	 PreparedStatement myStmt3 = conn.prepareStatement("select * from en_filme where id_Filme = ?");
             myStmt3.setInt(1, myRs2.getInt("id_filme"));
             ResultSet myRs3 = myStmt3.executeQuery();
        	if(myRs3.next()) {
	        	filme.setNome(myRs3.getString("nome"));
	        	filme.setDataLancamento(myRs3.getDate("data_lancamento"));
	        	filme.setDescricao(myRs3.getString("descricao"));
	        	listFilmes.add(filme);
        	}
        }
        
        return new Aluguel(idAluguel, listFilmes, cliente, data, valor);
	
	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel order by data_aluguel");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Aluguel> items = new ArrayList<>();

        while (myRs.next()) {
        	
        	Cliente cliente = new Cliente();
            
            
            int idAluguel = myRs.getInt("id_aluguel");
            cliente.setIdCliente(myRs.getInt("id_cliente"));
            Date data = myRs.getDate("data_aluguel");        
            Float valor = myRs.getFloat("valor");
            
            
            PreparedStatement myStmt2 = conn.prepareStatement("select * from re_aluguel_filme  where id_aluguel = ?");
            myStmt2.setInt(1, idAluguel);
            ResultSet myRs2= myStmt2.executeQuery();
            
            List<Filme> listFilmes = new ArrayList<Filme>();
            
            while(myRs2.next()) {
            	Filme filme = new Filme();
            	filme.setIdFilme(myRs2.getInt("id_filme"));
            	
            	 PreparedStatement myStmt3 = conn.prepareStatement("select * from en_filme where id_Filme = ?");
                 myStmt3.setInt(1, myRs2.getInt("id_filme"));
                 ResultSet myRs3 = myStmt3.executeQuery();
            	if(myRs3.next()) {
    	        	filme.setNome(myRs3.getString("nome"));
    	        	filme.setDataLancamento(myRs3.getDate("data_lancamento"));
    	        	filme.setDescricao(myRs3.getString("descricao"));
    	        	listFilmes.add(filme);
            	}
            }
            
            items.add(new Aluguel(idAluguel, listFilmes, cliente, data, valor));
    	
        }

        return items;
        

	}

}
