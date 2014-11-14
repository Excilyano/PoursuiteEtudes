package controleur;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.*;
import vue.*;



public class ControleBoutonPrecedent implements ActionListener	 {
		
	private Fenetre f;
	

		public ControleBoutonPrecedent(Fenetre f) {
			this.f=f;
			

			}

		public void actionPerformed(ActionEvent e){
			
			try {			
				f.contactSuivant();
		    	}
			
			catch (Exception e1) {
				
			}
			
		}
	}
		
