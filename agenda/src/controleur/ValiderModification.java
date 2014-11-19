package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modele.Carnet;
import vue.*;

public class ValiderModification implements ActionListener{

	private Fenetre f;
	

	public ValiderModification(Fenetre fenetre){
		this.f = f;
		
	}
	
	public void actionPerformed(ActionEvent e){
		f.modifierContact();
	}

}