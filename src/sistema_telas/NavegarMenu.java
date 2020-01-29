package sistema_telas;
import java.awt.event.ActionListener;
import java.awt.MenuBar;
import java.awt.MenuContainer;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import sistema_telas.Inicio;
import sistema_telas.crud_Clientes.EditarCliente;
import sistema_telas.crud_Clientes.InserirCliente;
import sistema_telas.crud_Clientes.ListarClientes;
import sistema_telas.crud_aluguel.EditarAluguel;
import sistema_telas.crud_aluguel.InserirAluguel;
import sistema_telas.crud_aluguel.ListarAluguel;
import sistema_telas.crud_filmes.EditarFilme;
import sistema_telas.crud_filmes.InserirFilme;
import sistema_telas.crud_filmes.ListarFilmes;


public class NavegarMenu {
	//Tela
	public static JFrame frame;
    public static JPanel tela;
    public static JLabel labelTitulo;
    
	//menu
	private static boolean menuConstruido;
	
	private static JMenuBar menuBar;
	private static JMenu menuArquivo, menuCliente, menuFilme, menuAluguel;
	private static JMenuItem miSair, 
							miCadCliente, miEditaCliente, miListaCliente,
							miCadFilme, miEditaFilme, miListaFilme,
							miCadAluguel, miEditaAluguel, miListaAluguel;
	
	
	
	public NavegarMenu() {
		frame = new JFrame("Sistemas");
        frame.setSize(730,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        telaInicial();
	}
		
	public static void telaInicial() {
		tela = new Inicio();
	    frame.setTitle("Bem Vindo ao Sistema");    
	    atualizaTela();
	}
	
	
	
	
	// ******* Menu Cliente
	public static void telaCadastroCliente() {
		tela = new InserirCliente();
	    frame.setTitle("Sistema Adiciona um Novo Cliente");    
	    atualizaTela();
	}
	
	public static void telaEditarCliente() {
		tela = new EditarCliente();
	    frame.setTitle("Sistema Para Editar Cliente");    
	    atualizaTela();
	}
	public static void telaListarClientes() {
		tela = new ListarClientes();
	    frame.setTitle("Sistema Para Listar Clientes");    
	    atualizaTela();
	}
	
	// &&&&&&&& Menu Filme 
	public static void telaCadastroFilme() {
		tela = new InserirFilme();
	    frame.setTitle("Sistema Adiciona um Novo Filme");    
	    atualizaTela();
	}
	
	public static void telaEditarFilme() {
		tela = new EditarFilme();
	    frame.setTitle("Sistema Para Editar Filme");    
	    atualizaTela();
	}
	public static void telaListarFilmes() {
		tela = new ListarFilmes();
	    frame.setTitle("Sistema Para Listar Filmes");    
	    atualizaTela();
	}
	
	// ######## Menu Aluquel 
		public static void telaCadastroAluguel() {
			tela = new InserirAluguel();
		    frame.setTitle("Sistema Adiciona um Novo Aluguel");    
		    atualizaTela();
		}
		
		public static void telaEditarAluguel() {
			tela = new EditarAluguel();
		    frame.setTitle("Sistema Para Editar Filme");    
		    atualizaTela();
		}
		public static void telaListarAluguel() {
			tela = new ListarAluguel();
		    frame.setTitle("Sistema Para Listar Filmes");    
		    atualizaTela();
		}
	
	private static void atualizaTela() {
		frame.getContentPane().removeAll();
		construirMenu();
		tela.setVisible(true);
		frame.add(tela);
		frame.setVisible(true);
	}
	private static void construirMenu() {
		if(!menuConstruido) {
			menuBar = new JMenuBar();
			
			//Menu ARQUIVO
			menuArquivo = new JMenu("Arquivo");
			miSair = new JMenuItem("Sair");
			menuArquivo.add(miSair);
			menuBar.add(menuArquivo);
			
			//Menu Cliente
			menuCliente 	= new JMenu("Cliente");
			miCadCliente 	= new JMenuItem("Novo");
			miEditaCliente	= new JMenuItem("Editar");
			miListaCliente	= new JMenuItem("Listar");
			menuCliente.add(miCadCliente);
			menuCliente.add(miEditaCliente);
			menuCliente.add(miListaCliente);
			menuBar.add(menuCliente);
			
			//Menu Filme
			menuFilme 	= new JMenu("Filme");
			miCadFilme 	= new JMenuItem("Novo");
			miEditaFilme	= new JMenuItem("Editar");
			miListaFilme	= new JMenuItem("Listar");
			menuFilme.add(miCadFilme);
			menuFilme.add(miEditaFilme);
			menuFilme.add(miListaFilme);
			menuBar.add(menuFilme);
			
			//Menu Aluguel
			menuAluguel 	= new JMenu("Aluguel");
			miCadAluguel 	= new JMenuItem("Novo");
			miEditaAluguel	= new JMenuItem("Editar");
			miListaAluguel	= new JMenuItem("Listar");
			menuAluguel.add(miCadAluguel);
			menuAluguel.add(miEditaAluguel);
			menuAluguel.add(miListaAluguel);
			menuBar.add(menuAluguel);
			
			frame.setJMenuBar(menuBar);
			menuConstruido = true;
			criaEventoMenu();
		}
	}
	
	public static void criaEventoMenu() {
		//############ Menu - Arquivo
		miSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				System.exit(0);
				
			}
		});
		
		//########### Menu - Cliente
		miCadCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				telaCadastroCliente();				
			}
		});
		
		miEditaCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				telaEditarCliente();
			}
		});
		
		miListaCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				telaListarClientes();
			}
		});
		
				//########### Menu - Filme
				miCadFilme.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaCadastroFilme();				
					}
				});
				
				miEditaFilme.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaEditarFilme();
					}
				});
				
				miListaFilme.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaListarFilmes();
					}
				});
		
		
				//########### Menu - Aluguel
				miCadAluguel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaCadastroAluguel();				
					}
				});
				
				miEditaAluguel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaEditarAluguel();
					}
				});
				
				miListaAluguel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						telaListarAluguel();
					}
				});
				
		
		
	}
}
