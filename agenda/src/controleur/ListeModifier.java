package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modele.Carnet;
import vue.*;

public class ListeModifier implements MouseListener {
	
	private Fenetre f;

	public ListeModifier(Fenetre f){
		this.f = f;
	}
	

	public void mouseClicked(MouseEvent listeContact) {
		}
		

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		f.listeModifierAction();
		
	}

}