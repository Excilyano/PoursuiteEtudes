package modele;

import java.lang.Comparable;

public class Personne implements Comparable<Personne>{
	
	private String nom;
	private String prenom;
	private String civilite;
	private Adresse adresse;
	private String pro;
	private String anniv;
	private String telPort;
	private String telDom;
	private String telTrav;
	private String email;
	
	
	

	public Personne(String c, String n, String p, Adresse a, String pr, String an, String tp, String td, String tt, String e){
		this.nom=n;
		this.prenom=p;

		this.civilite=c;
		this.adresse=a;

		this.pro=pr;
		this.anniv=an;
		this.telPort=tp;
		this.telDom=td;
		this.telTrav=tt;
		this.email=e;
		formaterNomPrenom();
	}
	
	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	public String getAnniv() {
		return anniv;
	}

	public void setAnniv(String anniv) {
		this.anniv = anniv;
	}

	public String getTelPort() {
		return telPort;
	}

	public void setTelPort(String telPort) {
		this.telPort = telPort;
	}

	public String getTelDom() {
		return telDom;
	}

	public void setTelDom(String telDom) {
		this.telDom = telDom;
	}

	public String getTelTrav() {
		return telTrav;
	}

	public void setTelTrav(String telTrav) {
		this.telTrav = telTrav;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean equals(Object o){
		boolean e=false;
		if(o instanceof Personne){
			Personne p = (Personne) o;
			if((this.nom == p.getNom())
			&& (this.prenom == p.getPrenom())
			&& (this.civilite == p.getCivilite())
			&& (this.adresse.equals(p.getAdresse()))){
				e=true;
			}
			else e=false;
		}
		return e;
	}
	
	public int compareTo(Personne p){
		return this.getNom().compareTo(p.getNom());
	}
	
	
	public String getNom(){
		return this.nom;
	}
	
	public void setNom(String nom){
		this.nom=nom;
	}
	
	public String getPrenom(){
		return this.prenom;
	}
	
	public void setPrenom(String prenom){
		this.prenom = prenom;
	}
	
	public String getCivilite(){
		return this.civilite;
	}
	
	public void setCivilite(String civilite){
		this.civilite = civilite;
	}
	
	public void setAdresse(Adresse a){
		this.adresse = a;
	}
	
	public Adresse getAdresse(){
		return this.adresse;
	}
	
	public void formaterNomPrenom(){
		String plNom,plPrenom,resteNom,restePrenom;
		plNom=this.nom.substring(0,1);
		plNom = plNom.toUpperCase();
		resteNom = this.nom.substring(1);
		resteNom = resteNom.toLowerCase();
		this.nom = plNom+resteNom;
		
		plPrenom=this.prenom.substring(0,1);
		plPrenom = plPrenom.toUpperCase();
		restePrenom = this.prenom.substring(1);
		restePrenom = restePrenom.toLowerCase();
		this.prenom = plPrenom+restePrenom;
		}

	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", prenom=" + prenom + ", civilite="
				+ civilite + ", adresse=" + this.adresse.toString() + ", pro=" + pro
				+ ", anniv=" + anniv + ", telPort=" + telPort + ", telDom="
				+ telDom + ", telTrav=" + telTrav + ", email=" + email + "]";
	}

	

}
