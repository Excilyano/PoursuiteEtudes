package etu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import modifBD.Ajout_Choix;
import modifBD.Suppr_Choix;
import oracle.jdbc.OracleTypes;
import fen_diverse.A_Propos;
import fen_diverse.Fenetre_Quit;
import fen_diverse.Fenetre_Res;
import fen_diverse.Support;

@SuppressWarnings("serial")
public class Fenetre_Etu extends JFrame implements ActionListener {
	private JMenuBar barreMenu = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu modifier = new JMenu("Modifier");
	private JMenu arreter = new JMenu("Arrêter");
	
	private JMenuItem aPropos = new JMenuItem("A propos");
	private JMenuItem support = new JMenuItem("Support");
	private JMenuItem ajoutChoix = new JMenuItem("Ajouter choix");
	private JMenuItem supprChoix = new JMenuItem("Supprimer choix");
	private JMenuItem deconnect = new JMenuItem("Deconnecter");
	private JMenuItem quitter = new JMenuItem("Quitter");
	
	private JLabel user = new JLabel();
	
	private JButton voeux = new JButton("Voir tous les voeux");
	
	private JPanel result = new JPanel();
		
	private String requete;
	
	private Connection connect;
	
	private String etudiant;
	
	public Fenetre_Etu(String id, Connection conn) {
		
		this.connect = conn;
		this.etudiant = id;
		
		setSize(500, 392);
	    setTitle("Choix étudiant");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    setResizable(false);
	    
	    //requete = "select f.ecole, f.ville, f.formation, f.datelimite, v.avis from formation f, etudiant e, voeux v where v.identifiant = '"+ this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant";
	    requete = "call package_info.getInfoEtu(?,?)";
	    
	    addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Fenetre_Quit();
			}
		});
	       
	    initMenuBar();
	    initContent(this.etudiant);
	    initTable(requete);
	}
	
	public void initTable(String query){
	    try {
	      /*Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);

	      ResultSet res = state.executeQuery(query);

	      ResultSetMetaData meta = res.getMetaData();
	      Object[] column = new Object[meta.getColumnCount()];

	      for(int i = 1 ; i <= meta.getColumnCount(); i++)
	        column[i-1] = meta.getColumnName(i);

	      res.last();
	      Object[][] data = new Object[res.getRow()][meta.getColumnCount()];

	      res.beforeFirst();
	      int j = 1;

	      while(res.next() && j < 6){
	        for(int i = 1 ; i <= meta.getColumnCount(); i++)
	          data[j-1][i-1] = res.getObject(i);
					
	        j++;
	      }
	      	      

	      res.close();
	      state.close();
	      
	      result.removeAll();
	      result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);
	      result.revalidate();*/
	    	
	    	CallableStatement cstm = connect.prepareCall(query);
	    	cstm.setString(1, this.etudiant);
	    	cstm.registerOutParameter(2, OracleTypes.CURSOR);
	    	cstm.execute();
	    	ResultSet res = (ResultSet)cstm.getObject(2);
	    	
	    	/*ResultSetMetaData meta = res.getMetaData();
		    Object[] column = new Object[meta.getColumnCount()];

		    for(int i = 1 ; i <= meta.getColumnCount(); i++)
		      column[i-1] = meta.getColumnName(i);*/
	    	
	    	Object[] column = new Object[] {"Ecole","Ville","Formation","Date limite","Avis"};
	    	
		    Object[][] data = new Object[5][5];

		    int j = 1;

		    while(res.next() && j < 6){
		      for(int i = 1 ; i <= 5; i++)
		        data[j-1][i-1] = res.getObject(i);
						
		        j++;
		    }
		    
		    
		    result.removeAll();
		    result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);
		    result.revalidate();
	    } 
	    
	    catch (SQLException e) {
	      result.removeAll();
	      result.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
	      result.revalidate();
	      JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	    }	
	  }
	
	public void initMenuBar() {
		
		this.fichier.add(aPropos);
		
		aPropos.addActionListener(this);
		
		this.fichier.add(support);
		
		support.addActionListener(this);
				
		this.modifier.add(ajoutChoix);
		
		ajoutChoix.addActionListener(this);
		
		this.modifier.add(supprChoix);
		
		supprChoix.addActionListener(this);
		
		this.arreter.add(quitter);
		
		quitter.addActionListener(this);
		
		this.barreMenu.add(fichier);
		this.barreMenu.add(modifier);
		this.barreMenu.add(arreter);		
	}
	
	public void initContent(String id) {
		user.setText("Connecté en tant que: " + id);
		
		voeux.addActionListener(this);
			
		Box b1 = Box.createHorizontalBox();
	    b1.add(user);
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(result);
	    
	    Box b3 = Box.createHorizontalBox();
	    b3.add(voeux);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
	    content.add(b3);
	    content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
				
		setJMenuBar(barreMenu);
		setContentPane(content);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aPropos) {
			new A_Propos();
		}
		
		else if (e.getSource() == support) {
			new Support();
		}
		
		else if (e.getSource() == deconnect) {
			new Fenetre_Quit();
		}
		
		else if (e.getSource() == quitter) {
			new Fenetre_Quit();
		}
		
		else if (e.getSource() == voeux) {
			Fenetre_Res f = new Fenetre_Res(etudiant , connect);
    		f.setVisible(true);
		}
		
		else if (e.getSource() == supprChoix) {
			Suppr_Choix f = new Suppr_Choix(etudiant, connect);
			f.setVisible(true);
			f.addWindowListener(new WindowListener() {
				@Override
				public void windowClosed(WindowEvent arg0) {
					initTable(requete);
				}

				@Override
				public void windowActivated(WindowEvent arg0) {
					
				}

				@Override
				public void windowClosing(WindowEvent arg0) {
					
				}

				@Override
				public void windowDeactivated(WindowEvent arg0) {
					
				}

				@Override
				public void windowDeiconified(WindowEvent arg0) {
					
				}

				@Override
				public void windowIconified(WindowEvent arg0) {
					
				}

				@Override
				public void windowOpened(WindowEvent arg0) {
					
				}
			});
		}
		
		else if (e.getSource() == ajoutChoix) {
			Ajout_Choix f = new Ajout_Choix(etudiant, connect);
			f.setVisible(true);
			f.addWindowListener(new WindowListener() {
				@Override
				public void windowClosed(WindowEvent arg0) {
					initTable(requete);
				}

				@Override
				public void windowActivated(WindowEvent arg0) {
					
				}

				@Override
				public void windowClosing(WindowEvent arg0) {
					
				}

				@Override
				public void windowDeactivated(WindowEvent arg0) {
					
				}

				@Override
				public void windowDeiconified(WindowEvent arg0) {
					
				}

				@Override
				public void windowIconified(WindowEvent arg0) {
					
				}

				@Override
				public void windowOpened(WindowEvent arg0) {
					
				}
			});
		}
	}
}
