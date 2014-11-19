package modele;

/**
 * 
 * @author Bernier-J
 * @version 1
 */

public class Adresse{

	private int numero;
	private String rue;
	private String ville;
	private int code;

	/**
	 * créer une nouvelle adresse postale
	 * 
	 * @param n
	 *            numéro de la rue
	 * @param r
	 *            nom de la rue
	 * @param v
	 *            nom de la ville
	 * @param c
	 *            code postal
	 */
	public Adresse(int n, String r, int c, String v){
		this.numero = n;
		this.rue = r;
		this.ville = v;
		this.code = c;
	}
	
	public boolean equals(Object o){
		boolean e=false;
		if(o instanceof Adresse){
			Adresse a = (Adresse) o;
			if((this.numero == a.getNumero())
			&& (this.rue == a.getRue())
			&& (this.ville == a.getVille())
			&& (this.code == a.getCode())){
				e=true;
			}
			else e=false;
		}
		return e;
	}

	/**
	 * @return donne le numéro de l'adresse
	 */
	public int getNumero(){
		return this.numero;
	}

	/**
	 * @return donne la rue de l'adresse
	 */
	public String getRue(){
		return this.rue;
	}

	/**
	 * @return donne la ville de l'adresse
	 */
	public String getVille(){
		return this.ville;
	}

	/**
	 * @return donne le code postal de l'adresse
	 */
	public int getCode(){
		return this.code;
	}

	/***
	 * @return l'adresse courante sous forme d'une chaine de caractères
	 */
	public String toString(){
		return ("" + this.numero + " " + this.rue + ", " + this.code + " " + this.ville);
	}

}
