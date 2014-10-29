package modifBD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Suppr_Etu extends JFrame implements ActionListener {
		
	private Connection connect;
	
	private JButton suppr = new JButton("Supprimer");
	
	private JComboBox<String> etu = new JComboBox<String>();
	
	public Suppr_Etu(Connection conn) {
		
		this.connect = conn;
		
		setSize(400, 200);
	    setTitle("Supprimer un étudiant");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent(conn);
	    
	    suppr.addActionListener(this);
	    
	    try {
	    	Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    
		    ResultSet res = state.executeQuery("select identifiant from etudiant");
		    		    
		    while(res.next()){         
		       etu.addItem(res.getObject(1).toString());
		    }
		    
		    state.close();
		    res.close();
	    } 
	    
	    catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    setVisible(true);
	}
	
	public void initContent(Connection connect) {
		
		etu.setPreferredSize(new Dimension(300,30));
		etu.setMaximumSize(etu.getPreferredSize());
		
		Box b1 = Box.createHorizontalBox();
	    b1.add(etu);
	    
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
			    String choix = etu.getSelectedItem().toString();
			    
			    CallableStatement cstm = connect.prepareCall("call Package_suppression.suppr_etudiant(?)");
		    	cstm.setString(1, choix);
		    	
		    	cstm.execute();
			    
			    dispose();
			}
			
			catch (Exception e) {
		    	JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
