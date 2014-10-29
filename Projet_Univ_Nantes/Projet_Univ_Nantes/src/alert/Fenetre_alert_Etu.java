package alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class Fenetre_alert_Etu extends JFrame implements ActionListener {
	
		private JButton ok = new JButton("Ok");
	
		public Fenetre_alert_Etu() {
		setSize(200, 135);
    	setTitle("Erreur");
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setResizable(false);
    	
    	initContent();
    	
    	ok.addActionListener(this);
	}
		
	public void initContent() {
		
		Box b1 = Box.createHorizontalBox();
    	b1.add(new JLabel("Erreur !"));
    	
    	Box b2 = Box.createHorizontalBox();
    	b2.add(new JLabel("Etudiant inexistant"));
    	
    	Box b3 = Box.createHorizontalBox();
    	b3.add(ok);
    
    	Box content = Box.createVerticalBox();
    	content.add(new JLabel("<html><br/>"));
    	content.add(b1);
    	content.add(b2);
    	content.add(new JLabel("<html><br/></html>"));
    	content.add(b3);
    	setContentPane(content);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			dispose();
		}
	}
}
