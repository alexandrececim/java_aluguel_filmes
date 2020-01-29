package sistema_telas.crud_filmes;

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

import dao.FilmeDAO;
import dao.jdbc.BancoDeDados;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;

public class ListarFilmes extends JPanel{
	
	JLabel lb_titulo;
	DefaultListModel <Filme> listaFilmeModelo;
	JList <Filme> listaFilme;
	JButton bt_pesquisar;
	Filme filme_escolhido;
	
public ListarFilmes() {
		
		criarComponentes();
		criarEventos();
		
	}
	
	private void criarComponentes() {
		setLayout(null);
		lb_titulo = new JLabel("Clique em Pesquisar para Listar os Filmes", JLabel.CENTER);
		lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
		
        bt_pesquisar = new JButton("Pesquisar");
        
        listaFilmeModelo = new DefaultListModel();
        listaFilme = new JList();
        listaFilme.setModel(listaFilmeModelo);
        listaFilme.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        lb_titulo.setBounds(20, 20, 660, 40);
       
        bt_pesquisar.setBounds(560, 140, 130, 40); 
        
        add(lb_titulo);

      //Solução de "ultima hora"        
        JScrollPane scrollPane = new JScrollPane(listaFilme);
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
				
					pesquisarFilmes();
					
				
			}
		});
		
		listaFilme.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				filme_escolhido = listaFilme.getSelectedValue();
				JOptionPane.showMessageDialog(null, "ID: " + filme_escolhido.getIdFilme() + 
						"\n FILME: \n" + filme_escolhido.getNome() +
						"\n DESCRIÇÃO: \n" + filme_escolhido.getDescricao());
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
		        FilmeDAO filmeDAO = new FilmeDAOImpl();
		        try {
		        	Class.forName("org.postgresql.Driver");
		            conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
		            conn.setAutoCommit(false);
		            
		            listaFilmeModelo.clear();
		            
		          //****************** Chamada ao metodo*************** 
		            listaFilmeModelo.addAll(filmeDAO.list(conn)); 
		            
		           
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
