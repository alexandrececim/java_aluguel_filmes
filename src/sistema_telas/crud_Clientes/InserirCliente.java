package sistema_telas.crud_Clientes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;
import dao.jdbc.BancoDeDados;


public class InserirCliente extends JPanel{
    JLabel lb_titulo, lb_cliente;
    JTextField tx_cliente;
    JButton botaoGravar;

    public InserirCliente(){
        criarComponentes();
        addCliente();
    }

    private void criarComponentes(){
        setLayout(null);

        lb_titulo = new JLabel("Cadastro de Cliente", JLabel.CENTER);
        lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
        lb_cliente = new JLabel("Nome do Cliente", JLabel.LEFT);
        tx_cliente = new JTextField();
        botaoGravar = new JButton("Adicionar Cliente");

        lb_titulo.setBounds(20, 20, 660, 40);
        lb_cliente.setBounds(150, 120, 400, 20);
        tx_cliente.setBounds(150, 140, 400, 40);
        botaoGravar.setBounds(250, 380, 200, 40);

        add(lb_titulo);
        add(lb_cliente);
        add(tx_cliente);
        add(botaoGravar);

        setVisible(true);
    }
    
    private void addCliente(){
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	Connection conn = null;
            	
            	ClienteDAO clienteDAO = new ClienteDAOImpl();
                Cliente novoCliente = new Cliente();
                novoCliente.setNome(tx_cliente.getText());
                
                try {
                    Class.forName("org.postgresql.Driver");
                    conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
                    conn.setAutoCommit(false);
                    
                    //****************** Chamada ao metodo***************
                    clienteDAO.insert(conn, novoCliente);
                    
					JOptionPane.showInternalMessageDialog(null,"Cliente " + tx_cliente.getText() + " foi cadastrao com Sucesso !");
					limpaTexto();
                    
                    
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Cliente." +e1);
                } finally {
                    try {
                    	System.out.println("Fechando Conexão.");
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        
                    }
                }
                
               

            }
        });
    }
    
    private void limpaTexto() {
		
		tx_cliente.setText("");
	}    
   
    
}