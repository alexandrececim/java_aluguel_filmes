package sistema_telas.crud_aluguel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.BancoDeDados;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class EditarAluguel extends JPanel {
	JLabel lb_titulo, lb_codigo, lb_nome, lb_lancamento, lb_descricao, lb_filme;
    JTextField tx_id_cliente, tx_data, tx_codigoAluguel;
    JTextArea txa_descricao;
	DefaultListModel <Filme> listaFilmeModelo;
	JList <Filme> listaFilme;
	JButton bt_pesquisar, bt_editar, bt_deletar, botaoAddFilme;
	Filme filme_escolhido;
	Aluguel aluguel_escolhido = null;
	Date data_aluguel;
	
	public EditarAluguel() {
		
		criarComponentes();
		criarEventos();
		
	}
	
	private void criarComponentes() {
		setLayout(null);
		lb_titulo = new JLabel("Use o código do Filme para Editar ou Deletar", JLabel.CENTER);
		lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
		lb_nome = new JLabel("Nome do Filme", JLabel.LEFT);
		
		lb_codigo = new JLabel("Código", JLabel.LEFT);
		tx_codigoAluguel = new JTextField();
		tx_codigoAluguel.setBackground(new Color(255, 255, 153));
		
        bt_pesquisar = new JButton("Pesquisar");
        
        bt_editar = new JButton("Editar");
        bt_editar.setEnabled(false);
        bt_deletar = new JButton("Excluir");
        bt_deletar.setEnabled(false);
        
        
        lb_titulo.setBounds(30, 11, 660, 40);
        lb_codigo.setBounds(560, 31, 600, 40);
        tx_codigoAluguel.setBounds(560, 62, 130, 40);
        
        
        
        bt_pesquisar.setBounds(560, 117, 130, 40);
        
        bt_editar.setBounds(560, 286, 130, 40); 
        bt_deletar.setBounds(560, 326, 130, 40);
        
        add(lb_titulo);
        add(lb_nome);
        add(lb_codigo);
        add(tx_codigoAluguel);
        add(bt_pesquisar);
        add(bt_editar);
        add(bt_deletar);
        
        lb_filme = new JLabel("Digite o ID do Aluguel", JLabel.LEFT);
        lb_lancamento = new JLabel("Data do Aluquel", JLabel.LEFT);
        lb_descricao = new JLabel("Lista de Filmes Alugados", JLabel.LEFT);
        tx_id_cliente = new JTextField();
        tx_data = new JTextField();

        listaFilmeModelo = new DefaultListModel();
        listaFilme = new JList();
        listaFilme.setModel(listaFilmeModelo);
        listaFilme.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        botaoAddFilme = new JButton("Adicionar Filme");

        lb_titulo.setBounds(20, 20, 660, 40);
        
        lb_filme.setBounds(150, 71, 400, 20);
        tx_id_cliente.setBounds(150, 90, 400, 40);
        
        lb_lancamento.setBounds(150, 117, 400, 40);
        tx_data.setBounds(150, 149, 400, 40);
        
        botaoAddFilme.setBounds(150, 225, 400, 40);
        
        lb_descricao.setBounds(150, 273, 400, 40);
        listaFilme.setBounds(150, 306, 400, 64);
        
        

        add(lb_titulo);
        add(lb_filme);
        add(tx_id_cliente);
        add(lb_lancamento);
        add(tx_data);
        add(botaoAddFilme);
        add(lb_descricao);       
        add(listaFilme);
        

        setVisible(true);
        
           
	}
	
	private void criarEventos() {
		bt_pesquisar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Caso nao for um numero lança exceção
				int temNumero;
				
				try{
					
					temNumero = Integer.parseInt(tx_codigoAluguel.getText());
					pesquisarAluguel();
					
				}catch(NumberFormatException nfe){
					
					JOptionPane.showMessageDialog(null, "numero invalido");
					tx_codigoAluguel.requestFocus();
				}
				
			}
		});
		
		bt_editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editarAluguel();
			}
		});
		
		bt_deletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deletarAluguels();
			}
		});
		
		listaFilme.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				
                if(listaFilme.getMinSelectionIndex() > -1){
                
                	 int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Exclusão de Item", JOptionPane.YES_NO_OPTION);

                     if (resposta == JOptionPane.YES_OPTION) {
                     	
                     	//****************** Chamada ao metodo***************  
                         listaFilmeModelo.remove(listaFilme.getSelectedIndex());
                         
                     }	
                    
                }
			}
		});
		
		 tx_data.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					 //Valida a data de lançamento digitada				
					if(tx_data.getText() == null || tx_data.getText().trim().equals("")) {
						
					}else{
						try {
		
			            	
					        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");			        
							LocalDate data = LocalDate.parse(tx_data.getText(), formato);
							
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							data_aluguel = new java.sql.Date(format.parse(data.toString()).getTime());
							
							
						} catch (Exception e1) {
							
							JOptionPane.showMessageDialog(null, "Data está invalida, use o formato como Exemplo: 01/01/2020");
							tx_data.requestFocus();
							tx_data.selectAll();
						}
					}
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		 
		 /** 
	    	 * Botão que mostra um JOptionPane para o usuario difgitar o ID do Filme
	    	 * retornando a contulda no Banco de Dados e adicionando na JList
	    	 * */
	    	botaoAddFilme.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int filmeAdd = Integer.valueOf(JOptionPane.showInputDialog("Digite o código do filme")); 
					
		            if (filmeAdd > 0) {
		            	
		            	//****************** Chamada ao metodo***************  
		            	pesquisarFilmes(filmeAdd);
		                
		            }

					
				}
			});

		 

	}//fim do Metodo
	
	private void pesquisarAluguel(){
		
		// conexão
			Connection conn = null;
	        // instrucao SQL
	        Statement instrucaoSQL;
	        // resultados
	        ResultSet resultados;
	        AluguelDAO aluguelDAO = new AluguelDAOImpl();
	        try {
	        	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
	            
	            
	            listaFilmeModelo.clear();
	                          
	            
	          //****************** Chamada ao metodo*************** 
	            aluguel_escolhido = aluguelDAO.find(conn, Integer.valueOf(tx_codigoAluguel.getText()));
	            
	            //prevendo resultados nulos
	            if(aluguel_escolhido != null) {
	            	
	            		            	
		            tx_id_cliente.setText(aluguel_escolhido.getCliente().getIdCliente().toString());
		            
		            tx_data.setText(formata_Data_Retorno_Do_Banco(aluguel_escolhido.getDataAluguel()));
		            data_aluguel = aluguel_escolhido.getDataAluguel();
		            
		            listaFilmeModelo.addAll(aluguel_escolhido.getFilmes());
		            
		            bt_editar.setEnabled(true);
                    bt_deletar.setEnabled(true);
                    
		            
	            }else {
	            	JOptionPane.showMessageDialog(null, "Código não cadastrado");
	            	tx_codigoAluguel.requestFocus();
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Aluguel." +e1);
	        } finally {
	            try {            	
	                conn.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                
	            }
	        }
		
    }


	private void editarAluguel() {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        AluguelDAO aluguelDAO = new AluguelDAOImpl();
        
        try {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
           
                         
            Aluguel editarAluguel =  new Aluguel();
            Cliente cliente = new Cliente();
            cliente.setIdCliente(aluguel_escolhido.getCliente().getIdCliente());
            cliente.setNome("");
            
           //Editado pelo usuario
            String editaValor = (String)JOptionPane.showInputDialog(null, "Deseja alterar o valor ?",
            		"Atenção",JOptionPane.QUESTION_MESSAGE,null,null, aluguel_escolhido.getValor());
            
            //Recebe o valor da variavel a qual o usuario não tem acesso e não do JTextField.
            editarAluguel.setIdAluguel(aluguel_escolhido.getIdAluguel());
            editarAluguel.setFilmes(todosDaLista());
            editarAluguel.setCliente(cliente);
            editarAluguel.setDataAluguel(data_aluguel);
            
            //Validaando o valor digitado
            if(somenteNumero(editaValor) == true) {
            	//Formatando o valor digitado
            	
            	BigDecimal bd = new BigDecimal(editaValor);
            	editarAluguel.setValor(bd.floatValue());
            }
          //****************** Chamada ao metodo***************  
            aluguelDAO.edit(conn, editarAluguel);
            limpaTexto();
            
            
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Aluguel." +e1);
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	
	private void deletarAluguels() {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        AluguelDAO aluguelDAO = new AluguelDAOImpl();
        
        try {
        	
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
           
                         
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Exclusão de Cadastro", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
            	 Aluguel deletarAluguel =  new Aluguel();
            	 Cliente cliente = new Cliente();
                 cliente.setIdCliente(aluguel_escolhido.getCliente().getIdCliente());
                 cliente.setNome("");
                 
            	 deletarAluguel.setIdAluguel(aluguel_escolhido.getIdAluguel());
                 deletarAluguel.setFilmes(todosDaLista());
                 deletarAluguel.setCliente(cliente);
                 deletarAluguel.setDataAluguel(data_aluguel);
                 deletarAluguel.setValor(aluguel_escolhido.getValor());
                 
            	
            	//****************** Chamada ao metodo***************  
            	aluguelDAO.delete(conn, deletarAluguel);
            	limpaTexto();
            }
            
           
            
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Aluguel." +e1);
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	
	/** 
	 * Metodo que formata a data vinda do Banco de Dados para
	 * ser exposto ao usuario.
	 * */
	private String formata_Data_Retorno_Do_Banco(Date dataOrigem) {
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");  
        Date datarecebida = new Date();
        datarecebida = dataOrigem;
    
        String date = new SimpleDateFormat("dd/MM/yyyy",   Locale.getDefault()).format(datarecebida);
		return date;
	}
	
	 /** 
		 * Metodo para limpar os JTextField e a JList
		 * atraves do JListModel
		 * */
		private void limpaTexto() {
			
			tx_codigoAluguel.setText("");
			tx_id_cliente.setText("");
			tx_data.setText("");
			
			listaFilmeModelo.clear();

			bt_editar.setEnabled(false);
            bt_deletar.setEnabled(false);
		}
		
		/** 
		 * Metodo que percorre e captura todos os objetos da JLista
		 * e retorna uma List<Filme> 
		 * */
		private List<Filme> todosDaLista(){
			int lista_Jlist = listaFilme.getModel().getSize();
			List <Filme> listando = new ArrayList<Filme>();
			//System.out.println("# "+lista_Jlist);

			for (int i = 0; i < lista_Jlist; i++) {
				Filme filme = new Filme();
				filme.setIdFilme(listaFilme.getModel().getElementAt(i).getIdFilme());
				listando.add(filme);
			}
			return listando;
		}
		
		/** 
		 * Metodo que restringe e orienta o usuario a digitar somente numeros
		 * */
		public boolean somenteNumero(String valor) {  
			/* Verifica se um numero é inteiro ou não */
			        try {  
			            //Long varLong = Long.parseLong(valor);  
			            float varLong = Float.valueOf(valor).floatValue();

			            System.out.println("# "+varLong);
			            return true;  
			        } catch (Exception e) {  
			        	JOptionPane.showMessageDialog(null, "Valor Invalido");
			            return false;  
			        }  

			}
		
		/** 
	     * Metodo que recebe o codigo do Filme e retorna
	     * os dados adicionando na JList
	     * */
	    private void pesquisarFilmes(int id_filme){
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
		            
		           
		                          
		            Filme pesquisaFilme =  new Filme();
		          //****************** Chamada ao metodo*************** 
		            pesquisaFilme = FilmeDAO.find(conn, id_filme);
		            
		            //prevendo resultados nulos
		            if(pesquisaFilme != null) {
			            Filme listado = new Filme();
			            listado.setIdFilme(pesquisaFilme.getIdFilme());
			            listado.setDataLancamento(pesquisaFilme.getDataLancamento());
			            listado.setNome(pesquisaFilme.getNome());
			            listado.setDescricao(pesquisaFilme.getDescricao());
			            
			            
			            // Adiciona na Lista o resultado da pesquisa.
			            listaFilmeModelo.addElement(listado);
			            
		            }else {
		            	JOptionPane.showMessageDialog(null, "Código não cadastrado");
		            	//tx_codigoFilme.requestFocus();
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


	
}
