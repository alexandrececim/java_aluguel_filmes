package sistema_telas.crud_aluguel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;
import dao.jdbc.BancoDeDados;
import dao.jdbc.FilmeDAOImpl;


public class InserirAluguel extends JPanel{
	JLabel lb_titulo, lb_filme, lb_lancamento, lb_descricao;
    JTextField tx_id_cliente, tx_data;
    DefaultListModel <Filme> listaFilmeModelo;
	JList <Filme> listaFilme;
    JButton botaoAddFilme, botaoGravar;
    java.sql.Date data_aluguel;
    
    public InserirAluguel(){
        criarComponentes();
        criarEventos();
       
    }

    private void criarComponentes(){
        setLayout(null);

        lb_titulo = new JLabel("Registro de Aluguel de Filmes", JLabel.CENTER);
        lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
        lb_filme = new JLabel("Digite o ID do Cliente", JLabel.LEFT);
        lb_lancamento = new JLabel("Data do Aluquel", JLabel.LEFT);
        lb_descricao = new JLabel("Lista de Filmes Alugados", JLabel.LEFT);
        tx_id_cliente = new JTextField();
        tx_data = new JTextField();

        listaFilmeModelo = new DefaultListModel();
        listaFilme = new JList();
        listaFilme.setModel(listaFilmeModelo);
        listaFilme.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        botaoAddFilme = new JButton("Adicionar Filme");
        botaoGravar = new JButton("Registrar Aluguel");

        lb_titulo.setBounds(20, 20, 660, 40);
        
        lb_filme.setBounds(150, 71, 400, 20);
        tx_id_cliente.setBounds(150, 90, 400, 40);
        
        lb_lancamento.setBounds(150, 117, 400, 40);
        tx_data.setBounds(150, 149, 400, 40);
        
        botaoAddFilme.setBounds(150, 225, 400, 40);
        
        lb_descricao.setBounds(150, 273, 400, 40);
        listaFilme.setBounds(150, 306, 400, 64);
        
        botaoGravar.setBounds(252, 381, 200, 40);

        add(lb_titulo);
        add(lb_filme);
        add(tx_id_cliente);
        add(lb_lancamento);
        add(tx_data);
        add(botaoAddFilme);
        add(lb_descricao);       
        add(listaFilme);
        add(botaoGravar);

        setVisible(true);
    }
    
    private void criarEventos(){
    	
    	
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
    	
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	Connection conn = null;
            	
            	AluguelDAO aluguelDAO = new AluguelDAOImpl();
                Aluguel novoAluguel = new Aluguel();
                Cliente cliente = new Cliente();
                cliente.setIdCliente(Integer.valueOf(tx_id_cliente.getText()));
                novoAluguel.setCliente(cliente);
                novoAluguel.setDataAluguel(data_aluguel);
                
                
                novoAluguel.setFilmes(todosDaLista());
                
               String valor = JOptionPane.showInputDialog("Qual o valor total do Aluguel ? ");
                
                
                //Validaando o valor digitado
                if(somenteNumero(valor) == true) {
                	//Formatando o valor digitado
                	
                	BigDecimal bd = new BigDecimal(valor);
                	novoAluguel.setValor(bd.floatValue());
	                try {
	                    Class.forName("org.postgresql.Driver");
	                    conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
	                    conn.setAutoCommit(false);
	                    
	                     
	                    
	                    
	                    //****************** Chamada ao metodo***************
	                    aluguelDAO.insert(conn, novoAluguel);
	                    
						JOptionPane.showMessageDialog(null,"Aluguel do Cliente  foi cadastrao com Sucesso !");
						limpaTexto();
	                    
	                    
	                } catch (Exception e1) {
	                    e1.printStackTrace();
	                    JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Aluguel." +e1);
	                } finally {
	                    try {
	                    	System.out.println("Fechando Conexão.");
	                        conn.close();
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                        
	                    }
	                }
                
                }

            }
        });
        
        /** 
         * Ação que remove a linha selecionada na JList
         * 
         * */
        
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
        
        /** 
         * Ação que valida o campo numerico
         * */
        tx_id_cliente.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
				if(tx_id_cliente.getText()==null || tx_id_cliente.getText().trim().equals("")) {
					
				}else{
					int temNumero;
					
					try{
						
						temNumero = Integer.parseInt(tx_id_cliente.getText());
						
						
					}catch(NumberFormatException nfe){
						
						JOptionPane.showMessageDialog(null, "numero invalido");
						tx_id_cliente.requestFocus();
						tx_id_cliente.selectAll();
					}
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
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

    /** 
	 * Metodo para limpar os JTextField e a JList
	 * atraves do JListModel
	 * */
	private void limpaTexto() {
		
		tx_id_cliente.setText("");
		tx_data.setText("");
		tx_data.setText("");
		listaFilmeModelo.clear();
	}
	/** 
	 * Metodo que restringe e orienta o usuario a digitar somente numeros
	 * */
	public boolean somenteNumero(String valor) {  
		/* Verifica se um numero é inteiro ou não */
		        try {  
		             
		            float varLong = Float.valueOf(valor).floatValue();

		            System.out.println("# "+varLong);
		            return true;  
		        } catch (Exception e) {  
		        	JOptionPane.showMessageDialog(null, "Valor Invalido");
		            return false;  
		        }  

		}
	/** 
	 * Metodo que percorre e captura todos os objetos da JLista
	 * e retorna uma List<Filme> 
	 * */
	private List<Filme> todosDaLista(){
		int lista_Jlist = listaFilme.getModel().getSize();
		List <Filme> listando = new ArrayList<Filme>();

		for (int i = 0; i < lista_Jlist; i++) {
			Filme filme = new Filme();
			filme.setIdFilme(listaFilme.getModel().getElementAt(i).getIdFilme());
			listando.add(filme);
		}
		return listando;
	}
    
	
}