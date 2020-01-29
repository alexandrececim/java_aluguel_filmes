package sistema_telas.crud_Clientes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.ClienteDAO;
import dao.jdbc.BancoDeDados;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;

public class EditarCliente extends JPanel {
	JLabel lb_titulo, lb_cliente;
	JTextField tx_codigoCliente;
	DefaultListModel <Cliente> listaClienteModelo;
	JList <Cliente> listaCliente;
	JButton bt_pesquisar, bt_editar, bt_deletar;
	Cliente cliente_escolhido;
	
	public EditarCliente() {
		
		criarComponentes();
		criarEventos();
		
	}
	
	private void criarComponentes() {
		setLayout(null);
		lb_titulo = new JLabel("Use o código do Cliente para Editar ou Deletar", JLabel.CENTER);
		lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
		lb_cliente = new JLabel("Código:", JLabel.LEFT);
        
        tx_codigoCliente = new JTextField();
        bt_pesquisar = new JButton("Pesquisar");
        
        bt_editar = new JButton("Editar");
        bt_editar.setEnabled(false);
        bt_deletar = new JButton("Excluir");
        bt_deletar.setEnabled(false);
        listaClienteModelo = new DefaultListModel();
        listaCliente = new JList();
        listaCliente.setModel(listaClienteModelo);
        listaCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        lb_titulo.setBounds(20, 20, 660, 40);
        lb_cliente.setBounds(150, 120, 400, 20);
        tx_codigoCliente.setBounds(150, 140, 60, 40);
        bt_pesquisar.setBounds(560, 140, 130, 40); 
        listaCliente.setBounds(150, 200, 400, 200);
        bt_editar.setBounds(560, 360, 130, 40); 
        bt_deletar.setBounds(560, 400, 130, 40);
        
        add(lb_titulo);
        add(lb_cliente);
        add(tx_codigoCliente);
        add(listaCliente);
        add(bt_pesquisar);
        add(bt_editar);
        add(bt_deletar);
        
        setVisible(true);
        
        tx_codigoCliente.hasFocus();
        
	}
	
	private void criarEventos() {
		bt_pesquisar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Caso nao for um numero lança exceção
				int temNumero;
				
				try{
					
					temNumero = Integer.parseInt(tx_codigoCliente.getText());
					pesquisarClientes();
					
				}catch(NumberFormatException nfe){
					
					JOptionPane.showMessageDialog(null, "numero invalido");
					tx_codigoCliente.requestFocus();
				}
				
			}
		});
		
		bt_editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editarCliente();
			}
		});
		
		bt_deletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deletarClientes();
			}
		});
		
		listaCliente.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				cliente_escolhido = listaCliente.getSelectedValue();
                if(cliente_escolhido == null){
                    bt_editar.setEnabled(false);
                    bt_deletar.setEnabled(false);
                }else{
                    bt_editar.setEnabled(true);
                    bt_deletar.setEnabled(true);
                }
			}
		});
		
	}
	
	private void pesquisarClientes(){
		//proteção
		
	        // conexão
			Connection conn = null;
	        // instrucao SQL
	        Statement instrucaoSQL;
	        // resultados
	        ResultSet resultados;
	        ClienteDAO clienteDAO = new ClienteDAOImpl();
	        try {
	        	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
	            conn.setAutoCommit(false);
	            
	            listaClienteModelo.clear();
	                          
	            Cliente pesquisaCliente =  new Cliente();
	          //****************** Chamada ao metodo*************** 
	            pesquisaCliente = clienteDAO.find(conn, Integer.valueOf(tx_codigoCliente.getText()));
	            
	            //prevendo resultados nulos
	            if(pesquisaCliente != null) {
		            Cliente listado = new Cliente();
		            listado.setIdCliente(pesquisaCliente.getIdCliente());
		            listado.setNome(pesquisaCliente.getNome());
		            
		            
		            // Adiciona na Lista o resultado da pesquisa.
		            listaClienteModelo.addElement(listado);
		            
	            }else {
	            	JOptionPane.showMessageDialog(null, "Código não cadastrado");
	            	tx_codigoCliente.requestFocus();
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Cliente." +e1);
	        } finally {
	            try {            	
	                conn.close();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                
	            }
	        }
		
    }


	private void editarCliente() {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        
        try {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
          
                         
            Cliente editarCliente =  new Cliente();
            editarCliente.setIdCliente(cliente_escolhido.getIdCliente());
            
           //Editado pelo usuario
            String editaNome = JOptionPane.showInputDialog("Favor Editar o nome");
            editarCliente.setNome(editaNome);
            
           
          //****************** Chamada ao metodo***************  
            clienteDAO.edit(conn, editarCliente);
            
            listaClienteModelo.clear();
            cliente_escolhido = editarCliente;
            
            JOptionPane.showMessageDialog(null, "ID: " + editarCliente.getIdCliente() + " Nome: " + editarCliente.getNome());
            
            // Adiciona na Lista o resultado da pesquisa.
            
            listaClienteModelo.addElement(cliente_escolhido);
           
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"O cadastro do Cliente NÃO foi modificado.");
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	
	private void deletarClientes() {
		 // conexão
		Connection conn = null;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        
        try {
        	
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
            conn.setAutoCommit(false);
            
           
                         
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Exclusão de Cadastro", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
            	
            	//****************** Chamada ao metodo***************  
                clienteDAO.delete(conn, cliente_escolhido.getIdCliente());
            }
            
                      
            
            JOptionPane.showMessageDialog(null, "Foi Excluido o cadastro com ID: " + cliente_escolhido.getIdCliente() + " e Nome: " + cliente_escolhido.getNome());
            listaClienteModelo.clear(); 
            
            
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Cliente." +e1);
        } finally {
            try {            	
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            }
        }
	}
	
}
