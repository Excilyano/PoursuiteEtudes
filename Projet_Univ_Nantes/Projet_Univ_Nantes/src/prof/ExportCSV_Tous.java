package prof;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;

import alert.Fenetre_alert_Export;

public class ExportCSV_Tous {

	private Connection connect;
	
	public ExportCSV_Tous(Connection conn) {
		
		this.connect = conn;
		
		FileWriter fileOut;
		
		try {
			Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
    		ResultSet res = state.executeQuery("select identifiant from etudiant");
    		res.beforeFirst();
    		    		    		
    		while (res.next()) {
    			try {
    				    				
    				String etu = res.getObject(1).toString();
    				
    				JFileChooser f_choisi = new JFileChooser();
    				f_choisi.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	    		
    	    		File file = new File("C:/Users/Arnaud/Desktop/Resultat_ProjetS2");
    	    		
    	    		f_choisi.setSelectedFile(file);
    	    		
    	    		f_choisi.showDialog(f_choisi,"Enregistrer sous");
    	    		
    	    		String filename = f_choisi.getSelectedFile().toString() + "/voeux_" + etu + ".csv";
    				
    				ResultSet res2 = state.executeQuery("select identifiant, nom, prenom, groupe, mail from etudiant where identifiant = '" + etu + "'");
    	    		
    	    		int colunmCount = getColumnCount(res2);
    	    		
        			fileOut = new FileWriter(filename);
        			
        			res2.next();
        			
        			for (int j = 1; j <= colunmCount; j++) {
        				if (j == 4) {
        					fileOut.append("Groupe " + res2.getObject(j).toString());
                            fileOut.append(System.getProperty("line.separator"));
        				}
        				else {
        					fileOut.append(res2.getObject(j).toString());
        					fileOut.append(System.getProperty("line.separator"));
        				}
        			}
        			
        			fileOut.append(System.getProperty("line.separator"));
        			
        			res2 = state.executeQuery("select v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '" + etu + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 1");
        			
        			colunmCount = getColumnCount(res2);
        			
        			for(int j = 1; j <= colunmCount; j++) {
    					fileOut.append(res2.getMetaData().getColumnName(j));
                        fileOut.append(";");
                    }
        			
        			fileOut.append(System.getProperty("line.separator"));
    				fileOut.append(System.getProperty("line.separator"));
    				
    				while(res2.next()) {
                        for(int j = 1; j <= colunmCount; j++) {
                             
                        	if(res2.getObject(j)!=null) {
                        		String data= res2.getObject(j).toString();
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
