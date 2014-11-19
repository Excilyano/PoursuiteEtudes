package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.*;
import vue.*;

public class Ajouter implements ActionListener{

	
	private Fenetre f;
	
	public Ajouter(Fenetre f){
		
		this.f = f;
	
	}
	public void actionPerformed(ActionEvent e){
		try{
			f.ajouterContact();
		}
		catch (Exception e1){
			
		}
	    }

}
 
