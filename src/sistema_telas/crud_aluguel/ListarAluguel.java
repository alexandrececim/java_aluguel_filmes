package sistema_telas.crud_aluguel;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.ClienteDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.BancoDeDados;
import dao.jdbc.ClienteDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;

public class ListarAluguel extends JPanel{
	
	JLabel lb_titulo;
	DefaultListModel <Aluguel> listaAluguelModelo;
	JList <Aluguel> listaAluguel;
	JButton bt_pesquisar;
	Aluguel aluguel_escolhido;
	
public ListarAluguel() {
		
		criarComponentes();
		criarEventos();
		
	}
	
	private void criarComponentes() {
		setLayout(null);
		lb_titulo = new JLabel("Clique em Pesquisar para Listar os Alugueis", JLabel.CENTER);
		lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
		
        bt_pesquisar = new JButton("Pesquisar");
        
        listaAluguelModelo = new DefaultListModel();
        listaAluguel = new JList();
        listaAluguel.setModel(listaAluguelModelo);
        listaAluguel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        lb_titulo.setBounds(20, 20, 660, 40);
       
        bt_pesquisar.setBounds(560, 140, 130, 40); 
        
        add(lb_titulo);

      //Solução de "ultima hora"        
        JScrollPane scrollPane = new JScrollPane(listaAluguel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150, 140, 400, 200);
        add(scrollPane);
        
        add(bt_pesquisar);
        
        setVisible(true);
        
	}
	
	private void criarEventos() {
		bt_pesquisar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					pesquisarAluguels();
					
				
			}
		});
		
		listaAluguel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				aluguel_escolhido = listaAluguel.getSelectedValue();
				JOptionPane.showMessageDialog(null, "ID: " + aluguel_escolhido.getIdAluguel() + 
						" ID do Cliente: " + aluguel_escolhido.getCliente().getIdCliente()+
						"  Valor: $ "+aluguel_escolhido.getValor());
			}
		});
	}
	 private void pesquisarAluguels(){
			//proteção
			
		        // conexão
				Connection conn = null;
		        // instrucao SQL
		        Statement instrucaoSQL;
		        // resultados
		        ResultSet resultados;
		        AluguelDAOImpl aluguelDAO = new AluguelDAOImpl();
		        try {
		        	Class.forName("org.postgresql.Driver");
		            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
		            conn.setAutoCommit(false);
		            
		            listaAluguelModelo.clear();
		            
		          //****************** Chamada ao metodo*************** 
		            listaAluguelModelo.addAll(aluguelDAO.list(conn)); 
		            
		           
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
}
