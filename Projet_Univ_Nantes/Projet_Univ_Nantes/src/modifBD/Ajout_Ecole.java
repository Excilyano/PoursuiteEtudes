package modifBD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

@SuppressWarnings("serial")
public class Ajout_Ecole extends JFrame implements ActionListener {
		
	private Connection connect;
	
	private JButton ajouter = new JButton("Ajouter");
	
	private JTextField ecole = new JTextField();
	private JTextField ville = new JTextField();
	private JTextField sitew = new JTextField();
	private JTextField forma = new JTextField();
	private JTextField numfo = new JTextField();
	private JFormattedTextField dateL;
	private JCheckBox alt = new JCheckBox("Alternance");
			
	public Ajout_Ecole(Connection conn) {
		this.connect = conn;
		
		setSize(400, 340);
	    setTitle("Ajouter une formation");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent();
	    
	    ajouter.addActionListener(this);
	}
	
	public void initContent() {
		
		ecole.setPreferredSize(new Dimension(150, 30));
	    ecole.setMaximumSize(ecole.getPreferredSize());

	    ville.setPreferredSize(new Dimension(150, 30));
	    ville.setMaximumSize(ville.getPreferredSize());
	    
	    sitew.setPreferredSize(new Dimension(150, 30));
	    sitew.setMaximumSize(sitew.getPreferredSize());
	    
	    forma.setPreferredSize(new Dimension(150, 30));
	    forma.setMaximumSize(forma.getPreferredSize());
	    
	    numfo.setPreferredSize(new Dimension(150, 30));
	    numfo.setMaximumSize(numfo.getPreferredSize());
	    
	    try {
	    	
			MaskFormatter mask = new MaskFormatter("##/##/##");
			dateL = new JFormattedTextField(mask);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    dateL.setPreferredSize(new Dimension(150, 30));
	    dateL.setMaximumSize(numfo.getPreferredSize());
		
		Box b1 = Box.createHorizontalBox();
		b1.add(new JLabel("Ecole:                 "));
		b1.add(ecole);
		
		Box b2 = Box.createHorizontalBox();
		b2.add(new JLabel("Ville:                   "));
		b2.add(ville);
		
		Box b3 = Box.createHorizontalBox();
		b3.add(new JLabel("Site web:           "));
		b3.add(sitew);
		
		Box b4 = Box.createHorizontalBox();
		b4.add(new JLabel("Formation:        "));
		b4.add(forma);
		
		Box b5 = Box.createHorizontalBox();
		b5.add(new JLabel("N° formation:   "));
		b5.add(numfo);
		
		Box b8 = Box.createHorizontalBox();
		b8.add(new JLabel("Date limite:       "));
		b8.add(dateL);
		
		Box b6 = Box.createHorizontalBox();
		b6.add(ajouter);
		
		Box b7 = Box.createHorizontalBox();
		b7.add(alt);
		
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
		content.add(b7);
		content.add(new JLabel("<html><br/></html>"));
		content.add(b6);
		
		setContentPane(content);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == ajouter) {
			try {			    
				String alternance = null;
				
				if (alt.isSelected() == true) {
					alternance = "Oui";
				}
				
				else if (alt.isSelected() == false) {
					alternance = "Non";
				}
				
				CallableStatement cstm = connect.prepareCall("call Package_creation.creer_formation(?,?,?,?,?,?,?)");
		    	cstm.setInt(1, Integer.parseInt(numfo.getText()));
		    	cstm.setString(2, ecole.getText());
		    	cstm.setString(3, forma.getText());
		    	cstm.setString(4, alternance);
		    	cstm.setString(5, ville.getText());
		    	cstm.setString(6, sitew.getText());
		    	cstm.setString(7, dateL.getText());
		    	
		    	cstm.execute();
		    				    
			    dispose();
			    
			    dispose();
			}
			
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
		    }
		}
	}
}
