package controleur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modele.*;
import vue.*;

public class ControleBoutonSuivant implements ActionListener	 {
		
	private Fenetre f;
	private Carnet c;

		public ControleBoutonSuivant(Fenetre f, Carnet c) {
			this.f=f;
			this.c=c;

			}

		public void actionPerformed(ActionEvent e){
			
			try {
				f.contactPrecedent();
		    	}
			
			catch (Exception e1) {
				
			}
			
		}
	}
		
