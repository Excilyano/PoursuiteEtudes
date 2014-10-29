package modifBD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Ajout_Etu extends JFrame implements ActionListener {
		
	private Connection connect;
	
	private JButton ajouter = new JButton("Ajouter");
	
	private JTextField iden = new JTextField();
	private JTextField nom = new JTextField();
	private JTextField prenom = new JTextField();
	private JTextField groupe = new JTextField();
	private JTextField mail = new JTextField();
	private JTextField passwd = new JTextField();
			
	public Ajout_Etu(Connection conn) {
		this.connect = conn;
		
		setSize(400, 340);
	    setTitle("Ajouter un étudiant");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent();
	    
	    ajouter.addActionListener(this);
	    setVisible(true);
	}
	
	public void initContent() {
		
		iden.setPreferredSize(new Dimension(150, 30));
		iden.setMaximumSize(iden.getPreferredSize());

		nom.setPreferredSize(new Dimension(150, 30));
		nom.setMaximumSize(nom.getPreferredSize());
	    
		prenom.setPreferredSize(new Dimension(150, 30));
		prenom.setMaximumSize(prenom.getPreferredSize());
	    
		groupe.setPreferredSize(new Dimension(150, 30));
		groupe.setMaximumSize(groupe.getPreferredSize());
	    
		mail.setPreferredSize(new Dimension(150, 30));
		mail.setMaximumSize(mail.getPreferredSize());
	    
		passwd.setPreferredSize(new Dimension(150, 30));
		passwd.setMaximumSize(passwd.getPreferredSize());
		
		Box b1 = Box.createHorizontalBox();
		b1.add(new JLabel("Identifiant:            "));
		b1.add(iden);
		
		Box b2 = Box.createHorizontalBox();
		b2.add(new JLabel("Nom:                      "));
		b2.add(nom);
		
		Box b3 = Box.createHorizontalBox();
		b3.add(new JLabel("Prénom:                "));
		b3.add(prenom);
		
		Box b4 = Box.createHorizontalBox();
		b4.add(new JLabel("Groupe:                  "));
		b4.add(groupe);
		
		Box b5 = Box.createHorizontalBox();
		b5.add(new JLabel("Adresse mail:       "));
		b5.add(mail);
		
		Box b8 = Box.createHorizontalBox();
		b8.add(new JLabel("Mot de passe:       "));
		b8.add(passwd);
		
		Box b6 = Box.createHorizontalBox();
		b6.add(ajouter);
		
		Box content = Box.createVerticalBox();
		content.add(new JLabel("<html><br/></html>"));
		content.add(b1);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b2);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b3);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b4);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b5);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b8);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b6);
		
		setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == ajouter) {
			try {
				
				CallableStatement cstm = connect.prepareCall("call Package_creation.creer_etudiant(?,?,?,?,?,?)");
		    	cstm.setString(1, iden.getText());
		    	cstm.setString(2, nom.getText());
		    	cstm.setString(3, prenom.getText());
		    	cstm.setInt(4, Integer.parseInt(groupe.getText()));
		    	cstm.setString(5, mail.getText());
		    	cstm.setString(6, passwd.getText());
		    	
		    	cstm.execute();
		    				    
			    dispose();
			}
			
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
