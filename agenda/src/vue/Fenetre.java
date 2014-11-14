package vue;


import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import controleur.*;
import modele.*;

public class Fenetre extends JFrame {
	
	
	
	private Carnet c = new Carnet();


private JPanel pPrincipal = new JPanel();
private JTabbedPane onglets	= new JTabbedPane();	
private JPanel pListe = new JPanel();
private JPanel pAjouter = new JPanel();
private JPanel pModifier = new JPanel();

//JPanels internes à pListe
private JPanel listeNom = new JPanel();
private JPanel listeNomC = new JPanel();
private JPanel listeNomH = new JPanel();
private JPanel listeNomB = new JPanel();
private JPanel FicheComplete = new JPanel();


   
//Pour la liste de pListe
private Vector<String> elements	= new Vector<String>();	
private DefaultListModel<String> LDM = new DefaultListModel<String>();
private JList<String> listeContact = new JList<String>(LDM);
JScrollPane ListeDefil = new JScrollPane(listeContact);
private JButton precedent = new JButton("Precedent");
private JButton suivant = new JButton("Suivant");

//Pour FicheComplete de pListe
private JLabel texteFC = new JLabel("<html><i><b>Fiche Complète</b></i></html>");
private JLabel CNom = new JLabel();
private JLabel CPrenom = new JLabel();
private JLabel CPro = new JLabel();
private JLabel CCivilite = new JLabel();
private JLabel CAnniversaire = new JLabel();
private JLabel CNumTelPort = new JLabel();
private JLabel CNumTelDom = new JLabel();
private JLabel CNumTelTrav = new JLabel();
private JLabel CEmail = new JLabel();
private JLabel CCodePostale = new JLabel();
private JLabel CRue = new JLabel();
private JLabel CNumRue = new JLabel();
private JLabel CVille = new JLabel();


//Pour l'onglet pAjouter
private JLabel LeNom = new JLabel("Nom:");
private JTextField Nom = new JTextField("");
private JLabel LePrenom = new JLabel("Prénom:");
private JTextField Prenom = new JTextField("");
private JLabel LaCivilite = new JLabel("Civilité:");
private Vector<String> Civil = new Vector<String>();
private JComboBox<String> Civilite = new JComboBox<String>(Civil);
private JRadioButton Particulier = new JRadioButton("Particulier");
private JRadioButton Professionel = new JRadioButton("Professionel");
private ButtonGroup Groupe = new ButtonGroup();
private JLabel LAnniversaire = new JLabel("Date d'anniversaire:");
private JTextField Anniversaire = new JTextField("JJ/MM/AAAA");
private JLabel LeNumTelPort = new JLabel("Numéro de téléphone Portable:");
private JTextField NumTelPort = new JTextField("");
private JLabel LeNumTelDom = new JLabel("Numéro de téléphone Domicile:");
private JTextField NumTelDom = new JTextField("");
private JLabel LeNumTelTrav = new JLabel("Numéro de téléphone de Travail:");
private JTextField NumTelTrav = new JTextField("");
private JLabel LEmail = new JLabel("Adresse email:");
private JTextField Email = new JTextField("");
private JLabel LeCodePostale = new JLabel("Code Postal:");
private JTextField CodePostal = new JTextField("");
private JLabel LaVille = new JLabel("Nom de la ville:");
private JTextField Ville = new JTextField("");
private JLabel LaRue = new JLabel("Nom de la rue:");
private JTextField Rue = new JTextField("");
private JLabel LeNumRue = new JLabel("Numéro de la rue:");
private JTextField NumRue = new JTextField("");
private JButton Valider = new JButton("Valider");
private String type = new String();
//Pour l'onglet pModifier
private JPanel listeNom2 = new JPanel();
private JPanel FicheComplete2 = new JPanel();
private JPanel Pro = new JPanel();
Vector<String> elements2	= new Vector<String>();	
private JList<String> listeContact2 = new JList<String>(LDM);
JScrollPane ListeDefil2 = new JScrollPane(listeContact2);
	//Champs de textes qui pourront etre remplacé par des get()
private JTextField Nom2 = new JTextField("Nom");
private JTextField Prenom2 = new JTextField("Prénom");
private Vector<String> Civil2 = new Vector<String>();
private JComboBox<String> Civilite2 = new JComboBox<String>(Civil);
private JRadioButton Particulier2 = new JRadioButton("Particulier");
private JRadioButton Professionnel2 = new JRadioButton("Professionel");
private ButtonGroup Groupe2 = new ButtonGroup();
private JTextField Anniversaire2 = new JTextField("Date d'anniversaire");
private JTextField NumTelPort2 = new JTextField("Numéro de téléphone portable");
private JTextField NumTelDom2 = new JTextField("Numéro de téléphone de domicile");
private JTextField NumTelTrav2 = new JTextField("Numéro de téléphone de travail");
private JTextField Email2 = new JTextField("Adresse email");
private JTextField CodePostale2 = new JTextField("Code Postal");
private JTextField Ville2 = new JTextField("Nom de la ville");
private JTextField Rue2 = new JTextField("Nom de la rue");
private JTextField NumRue2 = new JTextField("Numéro de la rue");
private JButton Valider2 = new JButton("Valider");
private JButton Supprimer = new JButton ("Supprimer Contact");

	public Fenetre(String titre){
		super(titre);
		//Initialisation des onglets
		onglets.add("Liste", pListe);
		onglets.add("Ajouter", pAjouter);
		onglets.add("Modifier", pModifier);
		pPrincipal.add(onglets);
		
		
/****************************************************************************************************************************************************/
/************************************************** ORGANISATIONDE L'ONGLET PLISTE ******************************************************************/
/****************************************************************************************************************************************************/

		pListe.setLayout(new BorderLayout());
		pListe.add(listeNom, BorderLayout.WEST);
		pListe.add(FicheComplete, BorderLayout.CENTER);
		//decoupage de liste nom pour placer les boutons precedents et suivants
		listeNom.setLayout(new BorderLayout());
		listeNom.add(listeNomH, BorderLayout.NORTH);
		listeNom.add(listeNomC, BorderLayout.CENTER);
		listeNom.add(listeNomB, BorderLayout.SOUTH);
		listeNomH.add(precedent);
		listeNomC.add(ListeDefil);
		listeNomB.add(suivant);
		listeContact.setSelectionBackground(Color.YELLOW);
		ListeDefil.setPreferredSize(new Dimension(200,300));
		
		//Controleurs boutons suivant/précédent
		ControleBoutonSuivant clicSuiv = new ControleBoutonSuivant(this,c);	
		suivant.addActionListener(clicSuiv);
		
		ControleBoutonPrecedent clicPre = new ControleBoutonPrecedent(this);	
		precedent.addActionListener(clicPre);

		
		
/****************************************************************************************************************************************************/
/************************************************** AJOUT D'EXEMPLES DANS LA LISTE DE CONTACTS ******************************************************/
/****************************************************************************************************************************************************/

		Personne jeremy = new Personne("Monsieur", "Bernier", "Jeremy", new Adresse(4, "Chanzy",44000, "Nantes"), "Particulier", "10/05/1995","0605103489","0240659865","0452638596","JeremB@gmail.fr");
		c.ajouter(jeremy);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne pierre = new Personne("Monsieur", "Lefrancois", "Pierre",  new Adresse(1, "le motois", 44118, "La Chevrolière"), "Particulier", "18/11/1994","0610369036","024043672","0425369874", "p.lefrancois44@gmail.fr");
		c.ajouter(pierre);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne emilien = new Personne("Monsieur", "Masson", "Emilien", new Adresse(15, "mimosa", 44170, "basse goulaine"), "Particulier", "19/05/1994","0615264815","0236594815","0415263948", "e.masson44@gmail.fr");
		c.ajouter(emilien);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne william = new Personne("Monsieur", "Legendre", "William",  new Adresse(202, "général Buat", 44000, "Nantes"), "Particulier", "25/05/1995","0659481723","024043672","0425369874", "W.legendre@gmail.fr");
		c.ajouter(william);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne jules = new Personne("Monsieur", "Civel", "Jules", new Adresse(65, "rennes longchamps", 44000, "Nantes"), "Particulier", "10/11/1995","0615264815","0236594815","0415263948", "J.Civelone@gmail.fr");
		c.ajouter(jules);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne thomas = new Personne("Monsieur", "Senez", "Thomas", new Adresse(32, "grenerai", 44000, "La Chevrolière"), "Particulier", "5/05/1991","0610369036","024043672","0425369874", "T.Senez@gmail.fr");
		c.ajouter(thomas);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne jimmy = new Personne("Monsieur", "Dore", "Jimmy", new Adresse(15, "mimosa", 44170, "basse goulaine"), "Particulier", "19/05/1994","0615264815","0236594815","0415263948", "J.dore@gmail.fr");
		c.ajouter(jimmy);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());		
		c.contactSuivant();
		
		Personne arthur = new Personne("Monsieur", "Outier", "Arthur", new Adresse(53, "foch", 44000, "Nantes"), "Particulier", "25/05/1995","0615264815","0236594815","0415263948", "A.outier@gmail.fr");
		c.ajouter(arthur);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();	

		Personne tom = new Personne("Monsieur", "Marrucci", "Tom",  new Adresse(202, "général Buat", 44000, "Nantes"), "Particulier", "26/07/1995","0659481723","024043672","0425369874", "T.Marrucci@gmail.fr");
		c.ajouter(tom);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne marie = new Personne("Madame", "Curie", "Marie",  new Adresse(202, "général Buat", 44000, "Nantes"), "Particulier", "26/07/1995","0659481723","024043672","0425369874", "T.Marrucci@gmail.fr");
		c.ajouter(marie);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		Personne test = new Personne("Monsieur", "TEST", "TEST",  /*new Adresse(202, "général Buat", 44000, "Nantes")*/null, "Particulier", null,"0","0","0", null);
		c.ajouter(test);
		LDM.addElement(c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
		c.contactSuivant();
		
		//triage du carnet et affichage de la liste
		actualiserListe();
		
		/*
		LDM.addElement("Cussoneau Romain");
		LDM.addElement("Delanou Quentin");
		LDM.addElement("Campos Arthur");
		LDM.addElement("Cassin Etienne");
		LDM.addElement("Lemetaye Pierre");
		LDM.addElement("Elouan Bretagne");
		LDM.addElement("Provost Maxime");
		LDM.addElement("Leroy Anthony");
		LDM.addElement("Benyelles Amine");
		LDM.addElement("Helbert Tanguy");
		LDM.addElement("Vidament Jordan");
		LDM.addElement("Rapilly Simon");
		*/
	
/****************************************************************************************************************************************************/
/************************************************** ORGANISATION DE FICHECOMPLETE *******************************************************************/
/****************************************************************************************************************************************************/
		// contactActuel= getIndiceSelectionné
		listeContact.setSelectedIndex(2);
		listeFicheAction();
		
		//Controleur d'affichage
		ListeFiche l = new ListeFiche(this);	
		listeContact.addMouseListener(l);
		
		
		FicheComplete.setLayout(new GridLayout (8,2));
		texteFC.setForeground(Color.RED);
		texteFC.setPreferredSize(new Dimension(100,50));

		
		FicheComplete.add(texteFC);
		FicheComplete.add(new JLabel(""));
		FicheComplete.add(CCivilite);
		FicheComplete.add(CPro);
		FicheComplete.add(CNom);
		FicheComplete.add(CPrenom);
		FicheComplete.add(CAnniversaire);
		FicheComplete.add(CEmail);
		FicheComplete.add(CNumRue);
		FicheComplete.add(CRue);
		FicheComplete.add(CCodePostale);
		FicheComplete.add(CVille);
		FicheComplete.add(CNumTelPort);
		FicheComplete.add(CNumTelDom);
		FicheComplete.add(CNumTelTrav);


		
/****************************************************************************************************************************************************/
/************************************************** ORGANISATION DE L'ONGLET PAJOUTER ***************************************************************/
/****************************************************************************************************************************************************/
		pAjouter.setLayout(new GridLayout(14,2));
		
		Nom.setColumns(15);	
		Prenom.setColumns(15);		
		Anniversaire.setColumns(15);	
		NumTelPort.setColumns(15);	
		NumTelDom.setColumns(15);	
		Email.setColumns(15);	
		CodePostal.setColumns(15);	
		Ville.setColumns(15);	
		Rue.setColumns(15);	
		NumRue.setColumns(15);	
		Groupe.add(Particulier);
		Groupe.add(Professionel);
		Professionel.setSelected(true);
		pAjouter.add(LeNom);
		pAjouter.add(Nom);
		pAjouter.add(LePrenom);
		pAjouter.add(Prenom);
		pAjouter.add(LaCivilite);
		Civil.add("Homme");
		Civil.add("Femme");
		Civil.add("Demoiselle");
		Civilite.setSelectedIndex(0);
		pAjouter.add(Civilite);
		pAjouter.add(LAnniversaire);
		pAjouter.add(Anniversaire);
		pAjouter.add(LeNumTelPort);
		pAjouter.add(NumTelPort);
		pAjouter.add(LeNumTelDom);
		pAjouter.add(NumTelDom);
		pAjouter.add(LeNumTelTrav);
		pAjouter.add(NumTelTrav);
		pAjouter.add(LEmail);
		pAjouter.add(Email);
		pAjouter.add(LeCodePostale);
		pAjouter.add(CodePostal);
		pAjouter.add(LaVille);
		pAjouter.add(Ville);
		pAjouter.add(LaRue);
		pAjouter.add(Rue);
		pAjouter.add(LeNumRue);
		pAjouter.add(NumRue);
		pAjouter.add(Professionel);
		pAjouter.add(Particulier);
		pAjouter.add(Valider);
		
		//controleur du bouton ajouter
		Ajouter clicAction = new Ajouter(this);	
		Valider.addActionListener(clicAction);


		
/****************************************************************************************************************************************************/
/************************************************** ORGANISATION DE L'ONGLET PMODIFIER **************************************************************/
/****************************************************************************************************************************************************/
		pModifier.setLayout(new BorderLayout());
		pModifier.add(listeNom2, BorderLayout.WEST);
		pModifier.add(FicheComplete2, BorderLayout.CENTER);
		listeNom2.add(ListeDefil2);
		listeContact2.setSelectionBackground(Color.YELLOW);
		ListeDefil2.setPreferredSize(new Dimension(200,350));
		FicheComplete2.setLayout(new BoxLayout(FicheComplete2,BoxLayout.Y_AXIS));
		
		Groupe2.add(Particulier2);
		Groupe2.add(Professionnel2);	
		FicheComplete2.add(Nom2);
		FicheComplete2.add(Prenom2);
		Civil2.add("Homme");
		Civil2.add("Femme");
		Civil2.add("Demoiselle");
		Civilite2.setSelectedIndex(0);
		FicheComplete2.add(Civilite2);
		FicheComplete2.add(Anniversaire2);
		FicheComplete2.add(NumTelPort2);
		FicheComplete2.add(NumTelDom2);
		FicheComplete2.add(Email2);
		FicheComplete2.add(CodePostale2);
		FicheComplete2.add(Ville2);
		FicheComplete2.add(Rue2);
		FicheComplete2.add(NumRue2);
		FicheComplete2.add(Pro);
		Pro.setLayout(new GridLayout(2,2));
		Pro.add(Professionnel2);
		Pro.add(Particulier2);
		Professionnel2.setSelected(true);
		Pro.add(Supprimer);
		Pro.add(Valider2);
		
		//Controleur d'affichage
		ListeModifier l2 = new ListeModifier(this);
		listeContact2.addMouseListener(l2);
		
		//Controleur de suppression
		ControleBSupp clicsuppr = new ControleBSupp(this);	
		Supprimer.addActionListener(clicsuppr);
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().add(pPrincipal);
	}

	
	
/****************************************************************************************************************************************************/
/***************************************************************** METHODES *************************************************************************/
/****************************************************************************************************************************************************/
	
	public void contactSuivant(){
		int i = listeContact.getSelectedIndex();
		listeContact.setSelectedIndex(i-1);
		c.setContactActuel(listeContact.getSelectedIndex());
		
		CNom.setText(c.getContactActuel().getNom());
		CPrenom.setText(c.getContactActuel().getPrenom());
		CCivilite.setText(c.getContactActuel().getCivilite());
		CPro.setText(c.getContactActuel().getPro());
		CAnniversaire.setText(c.getContactActuel().getAnniv());
		CNumTelPort.setText(c.getContactActuel().getTelPort());
		CNumTelDom.setText(c.getContactActuel().getTelDom());
		CNumTelTrav.setText(c.getContactActuel().getTelDom());
		CEmail.setText(c.getContactActuel().getEmail());
		CCodePostale.setText(Integer.toString(c.getContactActuel().getAdresse().getCode()));
		CRue.setText(c.getContactActuel().getAdresse().getRue());
		CNumRue.setText(Integer.toString(c.getContactActuel().getAdresse().getNumero()));
		CVille.setText(c.getContactActuel().getAdresse().getVille());

	}
	
	public void contactPrecedent(){
		int i = listeContact.getSelectedIndex();
		listeContact.setSelectedIndex(i+1);
		c.setContactActuel(listeContact.getSelectedIndex());
		
		CNom.setText(c.getContactActuel().getNom());
		CPrenom.setText(c.getContactActuel().getPrenom());
		CCivilite.setText(c.getContactActuel().getCivilite());
		CPro.setText(c.getContactActuel().getPro());
		CAnniversaire.setText(c.getContactActuel().getAnniv());
		CNumTelPort.setText(c.getContactActuel().getTelPort());
		CNumTelDom.setText(c.getContactActuel().getTelDom());
		CNumTelTrav.setText(c.getContactActuel().getTelDom());
		CEmail.setText(c.getContactActuel().getEmail());
		CCodePostale.setText(Integer.toString(c.getContactActuel().getAdresse().getCode()));
		CRue.setText(c.getContactActuel().getAdresse().getRue());
		CNumRue.setText(Integer.toString(c.getContactActuel().getAdresse().getNumero()));
		CVille.setText(c.getContactActuel().getAdresse().getVille());

	}
	
	
	public void actualiserListe(){
		int i;
		c.trier();
		c.setContactActuel(0);
		LDM.removeAllElements();
		String nP="";
		for (i=0;i<c.getNbContact();i++){
			nP = (c.getContactActuel().getNom() + " " + c.getContactActuel().getPrenom());
			c.contactSuivant();
			LDM.addElement(nP);
		}
	
	
		
	}
	
	public void ajouterContact(){
		
			int numRue=Integer.parseInt(NumRue.getText());
			int codePost=Integer.parseInt(CodePostal.getText());
		
			
			if (Particulier.isSelected()==true){
				type ="Particulier";
				}
			else {
				type="Professionnel";
				}
					
			Personne p = new Personne(Civil.get(Civilite.getSelectedIndex()), Nom.getText(), Prenom.getText(),new Adresse(numRue, Rue.getText(), codePost ,Ville.getText()), type, Anniversaire.getText(), NumTelPort.getText(), NumTelDom.getText(), NumTelTrav.getText(), Email.getText());
			c.ajouter(p);
			System.out.println("Contact Ajouté !");
			System.out.println(p.toString());
			this.actualiserListe();
			
			c.contactSuivant();
			Nom.setText(" ");
			Prenom.setText(" ");
			Rue.setText(" ");
			Ville.setText(" ");
			NumRue.setText(" ");
			CodePostal.setText(" ");
			Anniversaire.setText(" ");
			NumTelPort.setText(" ");
			NumTelDom.setText(" ");
			NumTelTrav.setText(" ");
			Email.setText(" ");
	}
	
	
	
	
	
	public void listeModifierAction(){
		try{
			c.setContactActuel(listeContact2.getSelectedIndex());
			Nom2.setText(c.getContactActuel().getNom());
			Prenom2.setText(c.getContactActuel().getPrenom());
			Civilite2.setSelectedIndex(findSelectedIndex());
			selectCorrectPrivacy();
			
			if((c.getContactActuel().getAnniv() == null) || (c.getContactActuel().getAnniv() == ""))
				Anniversaire2.setText("Anniversaire");
			else
				Anniversaire2.setText(c.getContactActuel().getAnniv());
			
			//Vérifications numéros renseignés
			//num portable
			if((c.getContactActuel().getTelPort()) == "")
				NumTelPort2.setText("Numero de portable");
			else
				NumTelPort2.setText(c.getContactActuel().getTelPort());
			//num domicile
			if((c.getContactActuel().getTelDom()) == "")
				NumTelDom2.setText("Numero Domicile");
			else
				NumTelDom2.setText(c.getContactActuel().getTelPort());		
			//num travail
			if((c.getContactActuel().getTelTrav()) == "")
				NumTelTrav2.setText("Numero Travail");
			else
				NumTelTrav2.setText(c.getContactActuel().getTelPort());
			
			//Vérification e-mail
			if((c.getContactActuel().getEmail() == null) || (c.getContactActuel().getEmail() == ""))
				Email2.setText("Adresse e-mail");
			else
				Email2.setText(c.getContactActuel().getEmail());
			
			
				CodePostale2.setText(Integer.toString(c.getContactActuel().getAdresse().getCode()));
				Rue2.setText(c.getContactActuel().getAdresse().getRue());
				NumRue2.setText(Integer.toString(c.getContactActuel().getAdresse().getNumero()));
				Ville2.setText(c.getContactActuel().getAdresse().getVille());

			
			System.out.println(c.getContactActuel().getNom());

			System.out.println(listeContact2.getSelectedIndex());
			}
		catch (Exception e1) {
			
			}
	}
	
	public void listeFicheAction(){
		try{
			c.setContactActuel(listeContact.getSelectedIndex());
			CNom.setText(c.getContactActuel().getNom());
			CPrenom.setText(c.getContactActuel().getPrenom());
			CCivilite.setText(c.getContactActuel().getCivilite());
			CPro.setText(c.getContactActuel().getPro());
			CAnniversaire.setText(c.getContactActuel().getAnniv());
			
			//Vérifications numéros renseignés
				//num portable
			if((c.getContactActuel().getTelPort()) == "")
				CNumTelPort.setText("");
			else
				CNumTelPort.setText(c.getContactActuel().getTelPort());
				//num domicile
			if((c.getContactActuel().getTelDom()) == "")
				CNumTelDom.setText("");
			else
				CNumTelDom.setText(c.getContactActuel().getTelPort());		
				//num
			if((c.getContactActuel().getTelTrav()) == "")
				CNumTelTrav.setText("");
			else
				CNumTelTrav.setText(c.getContactActuel().getTelPort());
			
			CEmail.setText(c.getContactActuel().getEmail());
			CCodePostale.setText(Integer.toString(c.getContactActuel().getAdresse().getCode()));
			CRue.setText(c.getContactActuel().getAdresse().getRue());
			CNumRue.setText(Integer.toString(c.getContactActuel().getAdresse().getNumero()));
			CVille.setText(c.getContactActuel().getAdresse().getVille());

			
			System.out.println(c.getContactActuel().getNom());

			System.out.println(listeContact.getSelectedIndex());
			}
		catch (Exception e1) {
			
			}
	}
	
	public void supprimerContact(){
			c.supprimer(listeContact.getSelectedIndex());
			actualiserListe();
		}

	
	
	public void modifierContact(){/*
		try{
			int numRue=Integer.parseInt(NumRue2.getText());
			int codePost=Integer.parseInt(CodePostal2.getText());
			int telP=Integer.parseInt(NumTelPort2.getText());
			int telD=Integer.parseInt(NumTelDom2.getText());
			int telT=Integer.parseInt(NumTelTrav2.getText());
			
			if (Particulier2.isSelected()==true){
				type ="Particulier";
				}
			else {
				type="Professionnel";
				}
			
			Personne p = new Personne(Civil2.get(Civilite2.getSelectedIndex()), Nom2.getText(), Prenom2.getText(),new Adresse( numRue, Rue2.getText(), codePost ,Ville2.getText()), type, f.Anniversaire2.getText(), telP, telD, telT, f.Email2.getText());
			c.modifier(p);
			System.out.println("Contact Ajouté !");
			System.out.println(p.toString());
			f.actualiserListe();
			
		}
		catch(Exception e1) {
			
			 
			
		} */
	}
	
	public int findSelectedIndex(){
		int index = 0;
		if(c.getContactActuel().getCivilite() == "Madame")
			index = 1;
		else if(c.getContactActuel().getCivilite() == "Mademoiselle")
			index = 2;
		return index;
	}
	
	public void selectCorrectPrivacy(){
		if(c.getContactActuel().getPro() == "Professionnel")
			Professionnel2.setSelected(true);
		else
			Particulier2.setSelected(true);
	}
	
	
	public static void main(String[] args) {

		Fenetre fenetre = new Fenetre("Carnet d'adresse");
		fenetre.pack();
	}
}
