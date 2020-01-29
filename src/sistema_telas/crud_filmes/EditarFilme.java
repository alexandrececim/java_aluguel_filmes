package sistema_telas.crud_filmes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.FilmeDAO;
import dao.jdbc.BancoDeDados;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;
import javax.swing.UIManager;
import java.awt.Color;

public class EditarFilme extends JPanel {
	JLabel lb_titulo, lb_codigo, lb_nome, lb_lancamento, lb_descricao, lb_pesquisa;
    JTextField tx_nomeFilme, tx_codigoFilme, tx_lancamento;
    JTextArea txa_descricao;
	DefaultListModel <Filme> listaFilmeModelo;
	JList <Filme> listaFilme;
	JButton bt_pesquisar, bt_editar, bt_deletar;
	Filme filme_escolhido;
	
	public EditarFilme() {
		
		criarComponentes();
		criarEventos();
		
	}
	
	private void criarComponentes() {
		setLayout(null);
		lb_titulo = new JLabel("Use o código do Filme para Editar ou Deletar", JLabel.CENTER);
		lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
		lb_nome = new JLabel("Nome do Filme", JLabel.LEFT);
		
		lb_codigo = new JLabel("Código", JLabel.LEFT);
		tx_codigoFilme = new JTextField();
		tx_codigoFilme.setBackground(new Color(255, 255, 153));
		
        lb_lancamento = new JLabel("Data de Lançamento", JLabel.LEFT);
        lb_descricao = new JLabel("Descrição", JLabel.LEFT);
        
        tx_lancamento = new JTextField();
        txa_descricao = new JTextArea();
        
        tx_nomeFilme = new JTextField();
        bt_pesquisar = new JButton("Pesquisar");
        
        bt_editar = new JButton("Editar");
        bt_editar.setEnabled(false);
        bt_deletar = new JButton("Excluir");
        bt_deletar.setEnabled(false);
        
        lb_pesquisa = new JLabel("Resultado da pesquisa", JLabel.LEFT);
        listaFilmeModelo = new DefaultListModel();
        listaFilme = new JList();
        listaFilme.setModel(listaFilmeModelo);
        listaFilme.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        lb_titulo.setBounds(30, 11, 660, 40);
        lb_codigo.setBounds(560, 31, 600, 40);
        tx_codigoFilme.setBounds(560, 62, 130, 40);
        
        lb_nome.setBounds(150, 60, 400, 20);
        tx_nomeFilme.setBounds(150, 90, 400, 40);
        
        
        lb_lancamento.setBounds(150, 117, 400, 40);
        tx_lancamento.setBounds(150, 149, 400, 40);
        
        lb_descricao.setBounds(150, 188, 400, 40);
        txa_descricao.setBounds(150, 221, 400, 101);
        
        
        bt_pesquisar.setBounds(560, 117, 130, 40);
        
        lb_pesquisa.setBounds(150, 297, 400, 81);
        listaFilme.setBounds(150, 347, 400, 81);
        bt_editar.setBounds(560, 347, 130, 40); 
        bt_deletar.setBounds(560, 387, 130, 40);
        
        add(lb_titulo);
        add(lb_nome);
        add(lb_codigo);
        add(tx_codigoFilme);
        add(tx_nomeFilme);
        add(lb_lancamento);
        add(tx_lancamento);
        add(lb_descricao);
        add(txa_descricao);
        add(lb_pesquisa);
        add(listaFilme);
        add(bt_pesquisar);
        add(bt_editar);
        add(bt_deletar);
        
        setVisible(true);
        
        tx_codigoFilme.hasFocus();
        
	}
	
	private void criarEventos() {
		bt_pesquisar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Caso nao for um numero lança exceção
				int temNumero;
				
				try{
					
					temNumero = Integer.parseInt(tx_codigoFilme.getText());
					pesquisarFilmes();
					
				}catch(NumberFormatException nfe){
					
					JOptionPane.showMessageDialog(null, "numero invalido");
					tx_codigoFilme.requestFocus();
				}
				
			}
		});
		
		bt_editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				 //Valida a data de lançamento digitada				
				java.sql.Date data2;
				try {

	            	
			        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");			        
					LocalDate data = LocalDate.parse(tx_lancamento.getText(), formato);
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					data2 = new java.sql.Date(format.parse(data.toString()).getTime());
					editarFilme(data2);
					
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null,"Data está invalida");
				}
				
			}
		});
		
		bt_deletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deletarFilmes();
			}
		});
		
		listaFilme.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				filme_escolhido = listaFilme.getSelectedValue();
                if(filme_escolhido == null){
                    bt_editar.setEnabled(false);
                    bt_deletar.setEnabled(false);
                }else{
                    bt_editar.setEnabled(true);
                    bt_deletar.setEnabled(true);
                    tx_nomeFilme.setText(filme_escolhido.getNome());
                    
                    SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");  
                    Date xp = new Date();
                    xp = filme_escolhido.getDataLancamento();
                
                    String date = new SimpleDateFormat("dd/MM/yyyy",   Locale.getDefault()).format(xp);
                    tx_lancamento.setText(date);
	                txa_descricao.setText(filme_escolhido.getDescricao());
					
                    
                }
			}
		});
		
	}
	
	private void pesquisarFilmes(){
		//proteção
		
	        // conexão
			Connection conn = null;
	        // instrucao SQL
	        Statement instrucaoSQL;
	        // resultados
	        ResultSet resultados;
	        FilmeDAO FilmeDAO = new FilmeDAOImpl();
	        try {
	        	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
	            conn.setAutoCommit(false);
	            
	            listaFilmeModelo.clear();
	                          
	            Filme pesquisaFilme =  new Filme();
	          //****************** Chamada ao metodo*************** 
	            pesquisaFilme = FilmeDAO.find(conn, Integer.valueOf(tx_codigoFilme.getText()));
	            
	            //prevendo resultados nulos
	            if(pesquisaFilme != null) {
		            Filme listado = new Filme();
		            listado.setIdFilme(pesquisaFilme.getIdFilme());
		            listado.setDataLancamento(pesquisaFilme.getDataLancamento());
		            listado.setNome(pesquisaFilme.getNome());
		            listado.setDescricao(pesquisaFilme.getDescricao());
		            
		            
		            // Adiciona na Lista o resultado da pesquisa.
		            listaFilmeModelo.addElement(listado);
		            listaFilme.addSelectionInterval(0, 0);
		            
	            }else {
	            	JOptionPane.showMessageDialog(null, "Código não cadastrado");
	            	tx_codigoFilme.requestFocus();
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Filme." +e1);
	        } finally {
	            try {            	
	                conn.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                
	            }
	        }
		
    }


	private void editarFilme(Date data) {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        FilmeDAO FilmeDAO = new FilmeDAOImpl();
        
        try {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
           
                         
            Filme editarFilme =  new Filme();
            editarFilme.setIdFilme(filme_escolhido.getIdFilme());
            
           //Editado pelo usuario
            editarFilme.setNome(tx_nomeFilme.getText());
            editarFilme.setDataLancamento(data);
            editarFilme.setDescricao(txa_descricao.getText());
            
          //****************** Chamada ao metodo***************  
            FilmeDAO.edit(conn, editarFilme);
            
            
            
            JOptionPane.showMessageDialog(null, "Filme: " + editarFilme.getNome() + "Foi editado com sucesso.");
            limpaTexto();
            
            
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Filme." +e1);
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	
	private void deletarFilmes() {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        FilmeDAO FilmeDAO = new FilmeDAOImpl();
        
        try {
        	
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
           
                         
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Exclusão de Cadastro", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
            	
            	//****************** Chamada ao metodo***************  
                FilmeDAO.delete(conn, filme_escolhido.getIdFilme());
                JOptionPane.showMessageDialog(null, "Foi Excluido o cadastro com ID: " + filme_escolhido.getIdFilme() + " e Nome: " + filme_escolhido.getNome());
                limpaTexto();
            }
            
                      
            
            
            
            
            
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Filme." +e1);
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	/** 
	 * Metodo para limpar os JTextField e a JList
	 * atraves do JListModel
	 * */
	private void limpaTexto() {
		
		tx_codigoFilme.setText("");
		tx_nomeFilme.setText("");
		tx_lancamento.setText("");
		txa_descricao.setText("");
		listaFilmeModelo.clear();
        filme_escolhido = null;
	}
	
}
