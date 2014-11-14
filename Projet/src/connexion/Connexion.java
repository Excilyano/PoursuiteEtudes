package connexion;

import etu.Fenetre_Etu;
import prof.Fenetre_Adm;
import alert.Fenetre_alert_Mdp;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Connexion extends JFrame implements ActionListener{

	private JLabel titre = new JLabel("Procedure de poursuite d'etudes"); // Instanciation de l'affichage du titre
	private JLabel login = new JLabel("Identifiant: "); // Label de l'identifiant
	
	private JTextField jtf1 = new JTextField(""); // Champ pour entrer son identifiant
	
	private JLabel pass = new JLabel("Mot de Passe: "); // Label du mot de passe
	
	private JPasswordField jtf2 = new JPasswordField(""); // Champ pour entrer son mot de passe (crypter)
	
	private JButton connect = new JButton("Connexion"); // Bouton pour se connecter
	private JButton signIn = new JButton("Inscription"); // Bouton pour l'inscription
			
	public Connexion() {
		this.setTitle("Poursuite d'etudes"); // Titre de la fen�tre
    	this.setSize(600,400); // Taille de la fen�tre
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Si on ferme la fen�tre, on ferme l'application
    	this.setLocationRelativeTo(null); // Appara�t au milieu de l'�cran
    	this.setResizable(true); // A une taille modifiable (ou non si false)
    	
    	initContent(); //Initialise le contenu
    	
        connect.addActionListener(this); // Si on clique sur le bouton de Connexion, on active la m�thode "actionPerformed"
        signIn.addActionListener(this); // Si on clique sur le bouton Inscription, on active la m�thode "actionPerformed" aussi mais avec un argument diff�rent
        
    	this.setVisible(true); // Affiche le contenu de la page
	}
	
	public void initContent() {
		
		jtf1.setPreferredSize(new Dimension(100, 30)); // Taille du champ de l'identifiant
    	jtf2.setPreferredSize(new Dimension(100, 30)); // Taille du champ du mot de passe
    	
    	Image icone = Toolkit.getDefaultToolkit().getImage("./logoiut.jpeg");
    	
    	Box b1 = Box.createHorizontalBox(); // Cr�ation d'une "box" ou se situera le titre
    	b1.add(titre); // On y ajoutera le titre
    	
    	Box b2 = Box.createHorizontalBox();
    	b2.add(login);
    	b2.add(new JLabel("           "));
    	b2.add(jtf1);
    	
    	jtf1.setPreferredSize(new Dimension(100,30)); // D�finit la taille du champ de texte
        jtf1.setMaximumSize(jtf1.getPreferredSize()); // D�finit la taille maximale du champ de texte
    	
    	Box b3 = Box.createHorizontalBox();
    	b3.add(pass);
    	b3.add(new JLabel("    "));
    	b3.add(jtf2);
    	
    	jtf2.setPreferredSize(new Dimension(100,30));
        jtf2.setMaximumSize(jtf2.getPreferredSize());
        
        Box b4 = Box.createHorizontalBox();
        b4.add(connect);
        
        Box b5 = Box.createHorizontalBox();
        b5.add(new JLabel("                                         Première connexion ?       "));
        b5.add(signIn);
    	
    	Box content = Box.createVerticalBox(); // Cr�er une box ou on ajoute les box les unes sous les autres (pour l'affichage)
    	content.add(new JLabel("<html><br/><br/></html>")); // Insertion de balise HTML pour la mise en page
    	content.add(b1);
    	content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
    	content.add(b2);
    	content.add(new JLabel("<html><br/></html>"));
    	content.add(b3);
    	content.add(new JLabel("<html><br/><br/><br/></html>"));
    	content.add(b4);
    	content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
    	content.add(b5);
    	this.setIconImage(icone);
    	this.setContentPane(content); // Met en place le contenu de la page
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == connect) {
			
			try 
			{
				String url = "jdbc:oracle:thin:@soracle2:1521:DB1";
			    String user = "i2c15b";
			    String passwd = "pe2015";
			    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			    Connection conn = DriverManager.getConnection(url, user, passwd);
			    
			    if ((jtf1.getText().charAt(0) == 'E')||(jtf1.getText().charAt(0) == 'e')) {
			    
			    	Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    
			    	ResultSet res = state.executeQuery("select passwd from etudiant e where e.identifiant = '" + jtf1.getText() + "'");
			    			    
			    	res.next();
			    	String mdp = (res.getObject(1).toString());
			    
			    	String testMdp = new String(jtf2.getPassword());
			    			    
			    	if (mdp.equals(testMdp)) {
			    		Fenetre_Etu f = new Fenetre_Etu(jtf1.getText(), conn);
			    		f.setVisible(true);
			    	}
			    	
			    	else {
				    	Fenetre_alert_Mdp fam = new Fenetre_alert_Mdp();
				    	fam.setVisible(true);
				    }
			    }
			    
			    else if (jtf1.getText().charAt(0) == 'A') {
			    	Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				    
			    	ResultSet res = state.executeQuery("select passwd from administrateur a where a.identifiant = '" + jtf1.getText() + "'");
			    			    
			    	res.next();
			    	String mdp = (res.getObject(1).toString());
			    
			    	String testMdp = new String(jtf2.getPassword());
			    			    
			    	if (mdp.equals(testMdp)) {				    
			    		Fenetre_Adm f2 = new Fenetre_Adm(jtf1.getText(), conn);
				    	f2.setVisible(true);
			    	}
			    	
			    	else {
				    	Fenetre_alert_Mdp fam = new Fenetre_alert_Mdp();
				    	fam.setVisible(true);
				    }
			    }
			} 
			
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
			}   
		}
		
		/*else if (arg0.getSource() == signIn) {
			try {
				new Web();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	
	public static void main(String[] args) {
		new Connexion();
	}
}
