package prof;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javax.swing.JTextField;

import modifBD.Ajout_Choix;
import modifBD.Ajout_Ecole;
import modifBD.Ajout_Etu;
import modifBD.Fenetre_Jury;
import modifBD.Suppr_Choix;
import modifBD.Suppr_Ecole;
import modifBD.Suppr_Etu;
import oracle.jdbc.OracleTypes;
import alert.Fenetre_alert_Etu;
import alert.Fenetre_alert_Voeux;
import fen_diverse.A_Propos;
import fen_diverse.Fenetre_Quit;
import fen_diverse.Fenetre_Res;
import fen_diverse.Fenetre_list_Etu;
import fen_diverse.Support;

@SuppressWarnings("serial")
public class Fenetre_Adm extends JFrame implements ActionListener {
	private JMenuBar barreMenu = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu export = new JMenu("Exporter");
	private JMenu modifier = new JMenu("Modifier");
	private JMenu arreter = new JMenu("Arrêter");
	private JMenu jury = new JMenu("Jury");
	
	private JMenuItem aPropos = new JMenuItem("A propos");
	private JMenuItem support = new JMenuItem("Support");
	private JMenuItem exportTous = new JMenuItem("pour tous les étudiants");
	private JMenuItem exportUn = new JMenuItem("pour un étudiant");
	private JMenuItem ajoutChoix = new JMenuItem("Ajouter choix");
	private JMenuItem supprChoix = new JMenuItem("Supprimer choix");
	private JMenuItem ajoutEcole = new JMenuItem("Ajouter une formation");
	private JMenuItem supprEcole = new JMenuItem("Supprimer une formation");
	private JMenuItem ajoutEtu = new JMenuItem("Ajouter un étudiant");
	private JMenuItem supprEtu = new JMenuItem("Supprimer un étudiant");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem addAvis = new JMenuItem("Ajouter un avis");
	
	private JLabel user = new JLabel();
	private JLabel info = new JLabel("Vous regardez le compte de:   ");
	private JLabel etu = new JLabel();
	
	private JButton voeux = new JButton("Voir tous les voeux");
	private JButton recher = new JButton("Rechercher");
	private JButton tousEtu = new JButton("Liste étudiants");
	
	private JTextField jtf = new JTextField("");
	
	private JPanel result = new JPanel();
	
	private String requete = "call package_info.getInfoEtu(?,?)";
	
	private Connection connect;
	
	private String prof;
			
	public Fenetre_Adm(String id, Connection conn) {
		
		setSize(600, 499);
	    setTitle("Professeur");
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    setResizable(false);
	    
	    this.connect = conn;
	    this.prof = id;
	    
	    addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Fenetre_Quit();
			}
		});
	    
		initMenu();
		initContent(prof);
		initTable(requete);
    }
	
	public void initMenu() {
		
		this.export.add(exportTous);
		this.export.add(exportUn);
		this.fichier.add(export);
		
		exportTous.addActionListener(this);
		
		exportUn.addActionListener(this);
		
		this.fichier.add(aPropos);
				
		aPropos.addActionListener(this);
		
		this.fichier.add(support);
		
		support.addActionListener(this);
		
		this.modifier.add(ajoutChoix);
		
		ajoutChoix.addActionListener(this);
		
		this.modifier.add(supprChoix);
		
		supprChoix.addActionListener(this);
		
		this.modifier.addSeparator();

		this.modifier.add(ajoutEcole);
		
		ajoutEcole.addActionListener(this);
		
		this.modifier.add(supprEcole);
		
		supprEcole.addActionListener(this);
		
		this.modifier.addSeparator();
		
		this.modifier.add(ajoutEtu);
		
		ajoutEtu.addActionListener(this);
		
		this.modifier.add(supprEtu);
		
		supprEtu.addActionListener(this);
		
		this.arreter.add(quitter);
		
		quitter.addActionListener(this);
		
		this.jury.add(addAvis);
		
		this.addAvis.addActionListener(this);

		this.tousEtu.addActionListener(this);
		
		this.barreMenu.add(fichier);
		this.barreMenu.add(modifier);
		this.barreMenu.add(jury);
		this.barreMenu.add(arreter);
	}
	
	public void initContent(String id) {
		user.setText("Connecté en tant que: " + id);
				
		recher.addActionListener(this);
		
		voeux.addActionListener(this);
				
		jtf.setPreferredSize(new Dimension(200,30));
		jtf.setMaximumSize(jtf.getPreferredSize());
    	    	
    	Box b1 = Box.createHorizontalBox();
	    b1.add(user);
	    
	    Box b5 = Box.createHorizontalBox();
	    b5.add(new JLabel("     "));
	    b5.add(jtf);
	    b5.add(new JLabel("   "));
	    b5.add(recher);
	    b5.add(tousEtu);
	    b5.add(new JLabel("     "));
	    b5.setSize(new Dimension(100, 20));
	    
	    Box b6 = Box.createHorizontalBox();
	    b6.add(info);
	    b6.add(etu);
	    
	    Box b2 = Box.createHorizontalBox();
	    b2.add(result);
	    
	    Box b3 = Box.createHorizontalBox();
	    b3.add(voeux);
	    
	    Box content = Box.createVerticalBox();
	    content.add(new JLabel("<html><br/></html>"));
	    content.add(b1);
	    content.add(new JLabel("<html><br/><br/><br/></html>"));
	    content.add(b5);
	    content.add(new JLabel("<html><br/><br/></html>"));
	    content.add(b6);
	    content.add(new JLabel("<html><br/><br/></html>"));
	    content.add(b2);
	    content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
	    content.add(b3);
	    content.add(new JLabel("<html><br/><br/><br/><br/></html>"));
				
		setJMenuBar(barreMenu);
		setContentPane(content);
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
	    	cstm.setString(1, jtf.getText());
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == voeux) {
			try {

				Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    
		    	ResultSet res = state.executeQuery("select identifiant from etudiant e where e.identifiant = '" + jtf.getText() + "'");
		    	
		    	res.next();
		    	String idEtu = (res.getObject(1).toString());
		    	
		    	if (idEtu.equals(jtf.getText())) {
		    		Fenetre_Res fr = new Fenetre_Res(jtf.getText(), connect);
		    		fr.setVisible(true);
		    	}
			}
			
			catch (Exception e) {
				Fenetre_alert_Voeux fae = new Fenetre_alert_Voeux();
	    		fae.setVisible(true);
			}
		}
		
		else if (arg0.getSource() == recher) {
			try {

				Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    
		    	ResultSet res = state.executeQuery("select identifiant from etudiant e where e.identifiant = '" + jtf.getText() + "'");
		    	
		    	res.next();
		    	String idEtu = (res.getObject(1).toString());
		    	
		    	if (idEtu.equals(jtf.getText())) {
		    		etu.setText(jtf.getText());
					initTable(requete);
		    	}
			}
			
			catch (Exception e) {
				Fenetre_alert_Etu fae = new Fenetre_alert_Etu();
	    		fae.setVisible(true);
			}
		}
		
		else if (arg0.getSource() == tousEtu) {
			new Fenetre_list_Etu(connect);
		}
		
		else if (arg0.getSource() == exportTous) {
			new Export_CSV_Tous(connect);
		}
		
		else if (arg0.getSource() == exportUn) {
			new ExportCSV_Un(jtf.getText(), connect);
		}
		
		else if (arg0.getSource() == aPropos) {
			new A_Propos();
		}
		
		else if (arg0.getSource() == support) {
			new Support();
		}
		
		else if (arg0.getSource() == ajoutChoix) {
			Ajout_Choix f = new Ajout_Choix(jtf.getText(), connect);
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
		
		else if (arg0.getSource() == supprChoix) {
			Suppr_Choix f = new Suppr_Choix(jtf.getText(), connect);
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
		
		else if (arg0.getSource() == ajoutEcole) {
			Ajout_Ecole f = new Ajout_Ecole(connect);
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
		
		else if (arg0.getSource() == supprEcole) {
			Suppr_Ecole f = new Suppr_Ecole(connect);
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
		
		else if (arg0.getSource() == ajoutEtu) {
			new Ajout_Etu(connect);
		}
		
		else if (arg0.getSource() == supprEtu) {
			new Suppr_Etu(connect);
		}
		
		else if (arg0.getSource() == quitter) {
			new Fenetre_Quit();
		}
		
		else if (arg0.getSource() == addAvis) {
			Fenetre_Jury fj = new Fenetre_Jury(jtf.getText(), connect);
			fj.addWindowListener(new WindowListener() {
				@Override
				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowClosed(WindowEvent arg0) {
					initTable(requete);
				}

				@Override
				public void windowClosing(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
}
