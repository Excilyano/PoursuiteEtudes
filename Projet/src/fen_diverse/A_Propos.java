package fen_diverse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class A_Propos extends JFrame implements ActionListener {
	
	private JButton fermer = new JButton("Fermer");
	
	public A_Propos() {
		
		setSize(300, 200);
	    setTitle("A Propos");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent();
	    
	    fermer.addActionListener(this);
	}
	
	public void initContent() {
		Box b1 = Box.createHorizontalBox();
	    b1.add(new JLabel("Logiciel de gestion des poursuites d'études"));

	    Box b2 = Box.createHorizontalBox();
	    b2.add(new JLabel("Version: Beta 1.0"));

	    Box b3 = Box.createHorizontalBox();
	    b3.add(new JLabel("Université de Nantes"));
	    
	    Box b4 = Box.createHorizontalBox();
	    b4.add(fermer);

	    Box b5 = Box.createVerticalBox();
	    b5.add(new JLabel("<html><br/></html>"));
	    b5.add(b1);
	    b5.add(new JLabel("<html><br/></html>"));
	    b5.add(b2);
	    b5.add(new JLabel("<html><br/></html>"));
	    b5.add(b3);
	    b5.add(new JLabel("<html><br/><br/></html>"));
	    b5.add(b4);
        
		setContentPane(b5);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fermer) {
			dispose();
		}
	}
}
