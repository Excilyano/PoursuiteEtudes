package modifBD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Fenetre_Jury extends JFrame implements ActionListener {
	
	private String etudiant;
	
	private Connection connect;
	
	private JButton valider = new JButton("Valider");
	
	private JComboBox<String> lesVoeux = new JComboBox<String>();
	private JComboBox<String> lesAvis = new JComboBox<String>();
	
	public Fenetre_Jury(String id, Connection conn) {
		this.etudiant = id;
		this.connect = conn;
		
		setSize(500, 200);
	    setTitle("Donner un avis");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent(conn);
	    
	    valider.addActionListener(this);
	    
	    try {
	    	Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    
		    ResultSet res1 = state.executeQuery("select f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '"+ this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 1");
		    		    
		    ResultSetMetaData resultMeta = res1.getMetaData();
		    
		    String resultat = "";
		    
		    while(res1.next()){         
		       for(int i = 1; i <= resultMeta.getColumnCount()-1; i++) {
		          resultat = resultat + res1.getObject(i).toString() + ", ";
		       }
		       resultat = resultat + res1.getObject(resultMeta.getColumnCount());
		       lesVoeux.addItem(resultat);
		       resultat = "";
		    }
		    
		    state.close();
		    res1.close();
	    } 
	    
	    catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    setVisible(true);
	}
	
	public void initContent(Connection connect) {
		
		lesVoeux.setPreferredSize(new Dimension(300,30));
		lesVoeux.setMaximumSize(lesVoeux.getPreferredSize());
		
		lesAvis.setPreferredSize(new Dimension(100,30));
		lesAvis.setMaximumSize(lesAvis.getPreferredSize());
		
		lesAvis.addItem("Pas d'avis");
		lesAvis.addItem("Favorable");
		lesAvis.addItem("Assez favorable");
		lesAvis.addItem("Non favorable");
		
		Box b1 = Box.createHorizontalBox();
	    b1.add(lesVoeux);
	    b1.add(new JLabel("       "));
	    b1.add(lesAvis);
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(valider);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    
	    setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == valider) {
			try {
				Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
			    
			    String choixV = lesVoeux.getSelectedItem().toString();
			    
			    String choixA = lesAvis.getSelectedItem().toString();
			    
			    int numVoeux = Integer.parseInt(new String(choixV.substring(0, choixV.indexOf(","))));

			    state.executeUpdate("update voeux set avis = '" + choixA + "' where ordre = " + numVoeux);
			    			 
			    state.close();
			    
			    dispose();
			}
			
			catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
