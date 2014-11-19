package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modele.Carnet;
import vue.*;

public class ListeFiche implements MouseListener {
	
	private Fenetre f;

	public ListeFiche(Fenetre f){
		this.f = f;
	}


	
	@Override
	public void mouseClicked(MouseEvent listeContact) {
		}
		

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		f.listeFicheAction();
		
	}

	
}
