package modele;

import java.util.*;

public class Carnet{

	private ArrayList<Personne> repertoire;
	private int contactActuel;
	private int nbContacts;
	
	public Carnet(){
		this.repertoire = new ArrayList<Personne>();
		this.contactActuel=0;
		this.nbContacts=this.repertoire.size();
	}
	
	public void ajouter(Personne p){
		this.repertoire.add(p);
		this.nbContacts++;
	}
	
	public void supprimer(int index){
		this.repertoire.remove(index);
	}
	
	public void modifier(int index,Personne p){
		this.repertoire.set(index,p);
	}
	
	
	public void trier(){
			Collections.sort(this.repertoire);
	}
	
	public void retirer(Object o){
		this.repertoire.remove(o);
		this.nbContacts--;
	}
	
	public boolean contactSuivantPossible(){
		return this.contactActuel<=this.nbContacts-1;
	}
	
	public void contactSuivant(){
		if(this.contactSuivantPossible())
			this.contactActuel++;
	}
	
	public boolean contactPrecedentPossible(){
		return this.contactActuel>=0;
	}
	
	public void contactPrecedent(){
		if(this.contactPrecedentPossible())
			this.contactActuel--;
	}
	
	public int getNbContact(){
		return this.nbContacts;
	}
	
	public Personne getContactActuel(){
		return this.repertoire.get(contactActuel);
	}
	public void setContactActuel(int index){
		this.contactActuel=index;
	}
	public String affichageContact(){
		return this.repertoire.get(nbContacts).toString();
	}
}