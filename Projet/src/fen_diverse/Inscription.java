package fen_diverse;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Inscription extends JFrame implements ActionListener{

	private JLabel titre = new JLabel("Inscription"); // Instanciation de l'affichage du titre
	private JPanel pPrincipal = new JPanel();
	private JLabel login = new JLabel("Nom"); // Label du nom
	private JTextField jtnom = new JTextField(""); // Champ pour entrer son identifiant
	
	private JLabel prenom = new JLabel("Prénom"); 
	private JTextField jtprenom = new JTextField("");
	
	private JLabel adresse = new JLabel("Adresse étudiante"); 
	private JTextField jtadresse = new JTextField("");
	private JLabel etu = new JLabel("@etu.univ-nantes.fr");
	private JPanel mail = new JPanel();
	private JLabel identifiant = new JLabel("Identifiant"); 
	private JTextField jtid = new JTextField("");
	
	private JLabel pass = new JLabel("Mot de Passe"); // Label du mot de passe
	private JPasswordField jtpasswd = new JPasswordField(""); // Champ pour entrer son mot de passe (crypter)
	private Connection conn;
	
	private JButton signIn = new JButton("Valider l'inscription"); // Bouton pour l'inscription

	public Inscription(Connection conn) {
		this.conn = conn;
		this.setTitle("Inscription"); // Titre de la fen�tre
    	this.setSize(600,400); // Taille de la fen�tre
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Si on ferme la fen�tre, on ferme l'application
    	this.setLocationRelativeTo(null); // Appara�t au milieu de l'�cran
    	this.setResizable(true); // A une taille modifiable (ou non si false)
    	
    	initInscription(); //Initialise le contenu
    	this.getContentPane().add(pPrincipal);
    	this.setVisible(true); // Affiche le contenu de la page
    	
	}
	
	public void initInscription() {
		jtnom.setPreferredSize(new Dimension(100, 30)); // Taille du champ de l'identifiant
		jtprenom.setPreferredSize(new Dimension(100, 30)); 
		jtadresse.setPreferredSize(new Dimension(200, 30)); 
		jtid.setPreferredSize(new Dimension(100, 30)); 
		jtpasswd.setPreferredSize(new Dimension(100, 30)); 
		mail.setLayout(new FlowLayout());
		Box b1 = Box.createVerticalBox();
		
		b1.add(login);
		b1.add(jtnom);
		b1.add(prenom);
		b1.add(jtprenom);
		b1.add(adresse);
		mail.add(jtadresse);
		mail.add(etu);
		b1.add(mail);
		b1.add(identifiant);
		b1.add(jtid);
		b1.add(pass);
		b1.add(jtpasswd);
		b1.add(new JLabel(" "));
		b1.add(signIn);
		pPrincipal.add(b1);
		
		
		signIn.addActionListener(this);
		

		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		récupererDonnee();
	}
	
	public static void main(String[] args) {
		try{
		String url = "jdbc:oracle:thin:@soracle2:1521:DB1";
	    String user = "i2c15b";
	    String passwd = "pe2015";
	    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	    Connection conn = DriverManager.getConnection(url, user, passwd);
		new Inscription(conn);
		}
		catch(Exception e){
			
		}
		
	}
	@SuppressWarnings("deprecation") //pour le mot de passe
	public void récupererDonnee(){
			try {
				//if (jtadresse.getText in base de donnée)
				
				
			    
				CallableStatement cstm = conn.prepareCall("call Package_creation.creer_etudiant(?,?,?,?,?)");
		    	cstm.setString(1, jtid.getText());
		    	cstm.setString(2, jtnom.getText());
		    	cstm.setString(3, jtprenom.getText());
		    	cstm.setString(4, jtadresse.getText());
		    	cstm.setString(5, jtpasswd.getText());
		    	
		    	cstm.execute();
		    				    
			    dispose();
			    System.out.println("Inscription validée !!");
			}
			
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
		    }
		}
}

