package prof;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;

import alert.Fenetre_alert_Export;

public class ExportCSV_Un {
	
	private Connection connect;
    private String etudiant;
    
    public ExportCSV_Un(String id, Connection conn) {
    	
    	this.etudiant = id;
    	this.connect = conn;
    	
    	FileWriter fileOut;
    	
    	try {
    		Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		
    		JFileChooser f_choisi = new JFileChooser();
    		
    		File file = new File("C:/Users/Arnaud/Desktop/Resultat_ProjetS2/voeux_" + this.etudiant + ".csv");
    		
    		f_choisi.setSelectedFile(file);
    		
    		f_choisi.showDialog(f_choisi,"Enregistrer sous");
    		
    		String filename = f_choisi.getSelectedFile().toString();
    		
    		ResultSet res = state.executeQuery("select identifiant, nom, prenom, groupe, mail from etudiant where identifiant = '" + this.etudiant + "'");
    		
    		int colunmCount = getColumnCount(res);
    		    		
    		try {
    			fileOut = new FileWriter(filename);
    			
    			res.next();
    			
    			for (int i = 1; i <= colunmCount; i++) {
    				if (i == 4) {
    					fileOut.append("Groupe " + res.getObject(i).toString());
                        fileOut.append(System.getProperty("line.separator"));
    				}
    				else {
    					fileOut.append(res.getObject(i).toString());
    					fileOut.append(System.getProperty("line.separator"));
    				}
    			}
    			
    			fileOut.append(System.getProperty("line.separator"));
    			
    			res = state.executeQuery("select f.ecole, f.ville, f.formation, v.avis from formation f, etudiant e, voeux v where v.identifiant = '" + this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 1");
    			
    			colunmCount = getColumnCount(res);
    			
    			for(int i = 1; i <= colunmCount; i++) {
					fileOut.append(res.getMetaData().getColumnName(i));
                    fileOut.append(";");
                }
    			
    			fileOut.append(System.getProperty("line.separator"));
				fileOut.append(System.getProperty("line.separator"));
				
				while(res.next()) {
                    for(int i = 1; i <= colunmCount; i++) {
                         
                    	if(res.getObject(i)!=null) {
                    		String data= res.getObject(i).toString();
                    		fileOut.append(data) ;
                    		fileOut.append(";");
                        }
                    	
                        else {
                            String data= "null";
                            fileOut.append(data) ;
                            fileOut.append(";");
                        }
                         
                    }
                    
                    fileOut.append(System.getProperty("line.separator"));
                }
    			
    			fileOut.flush();
                fileOut.close();  
    		}
    		
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		res.close();
    		state.close();
    	}
    	
    	catch (Exception e) {
    	  Fenetre_alert_Export fae = new Fenetre_alert_Export();
      	  fae.setVisible(true);
      	  e.printStackTrace();
    	}
    }
    
    public static int  getRowCount(ResultSet res) throws SQLException {
    	res.last();
    	int numberOfRows = res.getRow();
    	res.beforeFirst();
    	return numberOfRows;
    }
      
    public static int  getColumnCount(ResultSet res) throws SQLException {
    	return res.getMetaData().getColumnCount();
    }
}
