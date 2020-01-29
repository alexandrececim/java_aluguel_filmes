package sistema_telas;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 * Classe que carrega a tela principal
 * */
public class Inicio extends JPanel {
    JLabel labelTitulo;

    public Inicio(){
        criarComponentes();
    }

    private void criarComponentes(){
        setLayout(null);
        labelTitulo = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
        labelTitulo.setBounds(20, 100, 660, 40);

        add(labelTitulo);
        setVisible(true);
    }
    
}