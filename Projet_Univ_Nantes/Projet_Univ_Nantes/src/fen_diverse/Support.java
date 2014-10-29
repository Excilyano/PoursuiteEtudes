package fen_diverse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Support extends JFrame implements ActionListener {
	
	private JButton fermer = new JButton("Fermer");
	
	private JTextField mail = new JTextField("admin@admin.admin",JTextField.CENTER);
	
	public Support() {
		
		setSize(240, 170);
	    setTitle("Support");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    
	    initContent();
	    
	    fermer.addActionListener(this);	    
	}
	
	public void initContent() {
		mail.setPreferredSize(new Dimension(150, 30));
	    mail.setMaximumSize(mail.getPreferredSize());
	    mail.setBackground(new Color(238, 238, 238));
	    mail.setBorder(null);
	    mail.setEditable(false);
	    mail.setHorizontalAlignment(JTextField.CENTER);
		
	    Box b1 = Box.createHorizontalBox();
	    b1.add(new JLabel("En cas de problème,"));
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(new JLabel("vous pouvez nous contacter par mail: "));
	    
	    Box b3 = Box.createHorizontalBox();
	    b3.add(mail);

	    Box b4 = Box.createHorizontalBox();
	    b4.add(fermer);

	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b1);
	    content.add(b2);
	    content.add(b3);
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b4);
        
		setContentPane(content);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fermer) {
			dispose();
		}
	}
}
