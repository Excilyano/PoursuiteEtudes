package fen_diverse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Fenetre_Quit extends JFrame implements ActionListener {
	
	private JLabel info1 = new JLabel("Voulez-vous vraiment quitter ?");
	private JButton oui = new JButton("Oui");
	private JButton non = new JButton("Non");
	
	public Fenetre_Quit() {
		setSize(200, 120);
	    setTitle("Quitter");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent();	    
	         
        oui.addActionListener(this);
        
        non.addActionListener(this);		
	}
	
	public void initContent() {
		Box b1 = Box.createHorizontalBox();
	    b1.add(info1);

	    Box b2 = Box.createHorizontalBox();
	    b2.add(oui);
	    b2.add(new JLabel("     "));
	    b2.add(non);

	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br></html>"));
	    content.add(b2);
	    
	    setContentPane(content);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == oui) {
			System.exit(0);
		}
		
		else if (e.getSource() == non){
			dispose();
		}
	}
}
