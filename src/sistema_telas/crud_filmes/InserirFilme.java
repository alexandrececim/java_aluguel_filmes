package sistema_telas.crud_filmes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;


import dao.FilmeDAO;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;
import dao.jdbc.BancoDeDados;


public class InserirFilme extends JPanel{
    JLabel lb_titulo, lb_filme, lb_lancamento, lb_descricao;
    JTextField tx_filme, tx_lancamento;
    JTextArea txa_descricao;
    JButton botaoGravar;

    public InserirFilme(){
        criarComponentes();
        criarEvento();
    }

    private void criarComponentes(){
        setLayout(null);

        lb_titulo = new JLabel("Cadastro de Filme", JLabel.CENTER);
        lb_titulo.setFont(new Font(lb_titulo.getFont().getName(), Font.PLAIN, 20));
        lb_filme = new JLabel("Nome do Filme", JLabel.LEFT);
        lb_lancamento = new JLabel("Data de Lançamento", JLabel.LEFT);
        lb_descricao = new JLabel("Descrição", JLabel.LEFT);
        tx_filme = new JTextField();
        tx_lancamento = new JTextField();
        txa_descricao = new JTextArea();
        botaoGravar = new JButton("Adicionar Filme");

        lb_titulo.setBounds(20, 20, 660, 40);
        
        lb_filme.setBounds(150, 71, 400, 20);
        tx_filme.setBounds(150, 90, 400, 40);
        
        lb_lancamento.setBounds(150, 117, 400, 40);
        tx_lancamento.setBounds(150, 149, 400, 40);
        
        lb_descricao.setBounds(150, 188, 400, 40);
        txa_descricao.setBounds(150, 221, 400, 133);
        
        botaoGravar.setBounds(250, 380, 200, 40);

        add(lb_titulo);
        add(lb_filme);
        add(tx_filme);
        add(lb_lancamento);
        add(tx_lancamento);
        add(lb_descricao);
        add(txa_descricao);
        add(botaoGravar);

        setVisible(true);
    }
    
    public void criarEvento() {
    	botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	
		        //Valida a data de lançamento digitada				
				java.sql.Date data2;
				try {

	            	
			        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");			        
					LocalDate data = LocalDate.parse(tx_lancamento.getText(), formato);
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					data2 = new java.sql.Date(format.parse(data.toString()).getTime());
					addFilme(data2);
					
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(null,"Data está invalida");
				}
				
            }
        });
		        
    }
    
    private void addFilme(Date data){
        
            	Connection conn = null;
            	
            	FilmeDAO FilmeDAO = new FilmeDAOImpl();
                Filme novoFilme = new Filme();
                novoFilme.setNome(tx_filme.getText());
                
                novoFilme.setDataLancamento(data);
                novoFilme.setDescricao(txa_descricao.getText());
                
                try {
                    Class.forName("org.postgresql.Driver");
                    conn = DriverManager.getConnection(BancoDeDados.BANCO, BancoDeDados.USUARIO, BancoDeDados.SENHA);
                    conn.setAutoCommit(false);
                    
                    //****************** Chamada ao metodo***************
                    FilmeDAO.insert(conn, novoFilme);
                    limpaTexto();
					JOptionPane.showMessageDialog(null,"Filme " + tx_filme.getText() + " foi cadastrao com Sucesso !");
					
                    
                    
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showInternalMessageDialog(null,"Erro ao cadastrar Filme." +e1);
                } finally {
                    try {
                    	System.out.println("Fechando Conexão.");
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        
                    }
                }
                
               

    }
    
    private void limpaTexto() {
		
		tx_filme.setText("");
		tx_lancamento.setText("");
		txa_descricao.setText("");
	}
    
}