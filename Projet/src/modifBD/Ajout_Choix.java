package modifBD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
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
public class Ajout_Choix extends JFrame implements ActionListener {
	
	private String etudiant;
	
	private Connection connect;
	
	private JButton ajouter = new JButton("Ajouter");
	
	private JComboBox<String> ecole = new JComboBox<String>();
	
	public Ajout_Choix(String id, Connection conn) {
		this.etudiant = id;
		this.connect = conn;
		
		setSize(400, 200);
	    setTitle("Ajouter un choix");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent(conn);
	    
	    ajouter.addActionListener(this);
	    
	    try {
	    	Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    
		    ResultSet res = state.executeQuery("select numero, formation, ecole, ville from formation order by numero");
		    
		    ResultSetMetaData resultMeta = res.getMetaData();
		    
		    String resultat = "";
		    
		    while(res.next()){         
		       for(int i = 1; i <= resultMeta.getColumnCount()-1; i++) {
		          resultat = resultat + res.getObject(i).toString() + ", ";
		       }
		       resultat = resultat + res.getObject(resultMeta.getColumnCount());
		       ecole.addItem(resultat);
		       resultat = "";
		    }
		    
		    state.close();
		    res.close();
	    } 
	    
	    catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void initContent(Connection connect) {
		
		ecole.setPreferredSize(new Dimension(300,30));
		ecole.setMaximumSize(ecole.getPreferredSize());
		
		Box b1 = Box.createHorizontalBox();
	    b1.add(ecole);
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(ajouter);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    
	    setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == ajouter) {
			try {
				Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
			    
			    String choix = ecole.getSelectedItem().toString();
			    
			    int s = Integer.parseInt(new String(choix.substring(0, choix.indexOf(","))));
			    
			    ResultSet res = state.executeQuery("select v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '"+ this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 1");
			    
			    res.last();
			    
			    int ordre = res.getRow();
			    			    
			    CallableStatement cstm = state.getConnection().prepareCall("call Package_creation.creer_voeux(?,?,?)");
				cstm.setString(1, this.etudiant);
				cstm.setInt(2, s);
				cstm.setInt(3, ordre+1);
				cstm.execute();
			    			 
			    state.close();
			    res.close();
			    
			    dispose();
			}
			
			catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
