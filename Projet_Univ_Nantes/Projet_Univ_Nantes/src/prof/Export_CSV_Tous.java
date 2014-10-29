package prof;

import alert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class Export_CSV_Tous {
	 
    private Connection connect;
	
    @SuppressWarnings("unused")
	public Export_CSV_Tous(Connection conn) {
    	
    	this.connect = conn;
          
    	FileWriter fw ;
          
    	try {
    		Statement st = connect.createStatement();
           
    		ResultSet res = st.executeQuery("select e.identifiant, e.nom, e.prenom, f.ecole, f.ville, f.formation, v.avis from formation f, etudiant e, voeux v where f.numero = v.numero and e.identifiant = v.identifiant order by 2");
           
    		List <String> tableNameList = new ArrayList<String>();
    		while(res.next()) {
    			tableNameList.add(res.getString(1));
    		}
    		
    		JFileChooser choix = new JFileChooser();
    		
    		File file = new File("C:/Users/Arnaud/Desktop/Resultat_ProjetS2/voeux_tous.csv");
    		
    		choix.setSelectedFile(file);
    		    		
    		int reponse = choix.showDialog(choix,"Enregistrer sous");
           
    		String filename = choix.getSelectedFile().toString();
           
    		for (String tableName:tableNameList) {
                 
    			res = st.executeQuery("select e.identifiant, e.nom, e.prenom, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where f.numero = v.numero and e.identifiant = v.identifiant order by 1");
                 
    			int colunmCount = getColumnCount(res);
                 
    			try {
    				fw = new FileWriter(filename);
                     
           				for(int i=1 ; i<= colunmCount ;i++) {
    					fw.append(res.getMetaData().getColumnName(i));
                        fw.append(";");
                    }
                     
    				fw.append(System.getProperty("line.separator"));
    				fw.append(System.getProperty("line.separator"));
                     
                    while(res.next()) {
                        for(int i=1;i<=colunmCount;i++) {
                             
                        	if(res.getObject(i)!=null) {
                        		String data= res.getObject(i).toString();
                        		fw.append(data) ;
                        		fw.append(";");
                            }
                        	
                            else {
                                String data= "null";
                                fw.append(data) ;
                                fw.append(";");
                            }
                             
                        }
                        fw.append(System.getProperty("line.separator"));
                    }
                     
                    fw.flush();
                    fw.close();   
                } 
    			
    			catch (IOException e) {
                    e.printStackTrace();
                }  
    		}    
    		res.close();
    		st.close();
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