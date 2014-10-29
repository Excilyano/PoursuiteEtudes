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
public class Suppr_Ecole extends JFrame implements ActionListener {
		
	private Connection connect;
	
	private JButton suppr = new JButton("Supprimer");
	
	private JComboBox<String> ecole = new JComboBox<String>();
	
	public Suppr_Ecole(Connection conn) {
		
		this.connect = conn;
		
		setSize(400, 200);
	    setTitle("Supprimer une formation");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent(conn);
	    
	    suppr.addActionListener(this);
	    
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
	    b2.add(suppr);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    
	    setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == suppr) {
			try {
			    
			    String choix = ecole.getSelectedItem().toString();
			    
			    int s = Integer.parseInt(new String(choix.substring(0, choix.indexOf(","))));
			    			    
			    CallableStatement cstm = connect.prepareCall("call Package_suppression.suppr_formation(?)");
		    	cstm.setInt(1, s);
		    	
		    	cstm.execute();
			    
			    dispose();
			}
			
			catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
