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
public class Modif_Ordre extends JFrame implements ActionListener {
	
	private String etudiant;
	
	private Connection connect;
	
	private JButton modif = new JButton("Modifier");
	
	private JComboBox<String> choix = new JComboBox<String>();
	private JComboBox<Integer> ordre = new JComboBox<Integer>();
		
	public Modif_Ordre(String id, Connection conn) {
		this.etudiant = id;
		this.connect = conn;
		
		setSize(400, 200);
	    setTitle("Supprimer un choix");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent(conn);
	    
	    modif.addActionListener(this);
	    
	    try {
	    	Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
		    
		    ResultSet res = state.executeQuery("select v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '"+ this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 1");
		    
		    ResultSetMetaData resultMeta = res.getMetaData();
		    
		    String resultat = "";
		    
		    while(res.next()){         
		       for(int i = 1; i <= resultMeta.getColumnCount()-1; i++) {
		          resultat = resultat + res.getObject(i).toString() + ", ";
		       }
		       resultat = resultat + res.getObject(resultMeta.getColumnCount());
		       choix.addItem(resultat);
		       resultat = "";
		    }
		    
		    res.beforeFirst();
		    
		    while(res.next()){         
			     ordre.addItem(res.getRow());
			}
		    
		    state.close();
		    res.close();
	    } 
	    
	    catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void initContent(Connection connect) {
		
		choix.setPreferredSize(new Dimension(300,30));
		choix.setMaximumSize(choix.getPreferredSize());
		
		ordre.setPreferredSize(new Dimension(50,30));
		ordre.setMaximumSize(ordre.getPreferredSize());
		
		Box b1 = Box.createHorizontalBox();
	    b1.add(choix);
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(new JLabel("Placer comme choix n°:   "));
	    b2.add(ordre);
	    
	    Box b3 = Box.createHorizontalBox();
	    b3.add(modif);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b3);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    
	    setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == modif) {
			try {
				Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
			    			    
			    String choisi = choix.getSelectedItem().toString();
			    
			    int voeux = Integer.parseInt(new String(choisi.substring(0, choisi.indexOf(","))));
			    int rang = Integer.parseInt(new String(ordre.getSelectedItem().toString()));
			    
			    if (voeux < rang) {
			    	state.executeUpdate("update voeux set ordre = -1 where ordre = " + voeux);
			    	for (int i = voeux + 1; i <= rang; i++) {
			    		state.executeUpdate("update voeux set ordre = ordre - 1 where ordre = " + i  + "and voeux.identifiant = '" + this.etudiant + "'");
			    	}
			    	state.executeUpdate("update voeux set ordre = " + rang + " where ordre = -1 and voeux.identifiant = '" + this.etudiant + "'");
			    }
			    
			    else if (voeux > rang) {
			    	state.executeUpdate("update voeux set ordre = -1 where ordre = " + voeux);
			    	for (int i = voeux - 1; i >= rang ; i--) {
			    		state.executeUpdate("update voeux set ordre = ordre + 1 where ordre = " + i + "and voeux.identifiant = '" + this.etudiant + "'");
			    	}
			    	state.executeUpdate("update voeux set ordre = " + rang + " where ordre = -1 and voeux.identifiant = '" + this.etudiant + "'");
			    }
			    			    			    
			    dispose();
			}
			
			catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
