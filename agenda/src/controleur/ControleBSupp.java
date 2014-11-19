package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.Fenetre;

public class ControleBSupp implements ActionListener{
	private Fenetre f;

	public ControleBSupp(Fenetre f){
		
		this.f = f;
	}
	
	public void actionPerformed(ActionEvent e){
		
			f.supprimerContact();
	
	}
	
	
	
		
}

