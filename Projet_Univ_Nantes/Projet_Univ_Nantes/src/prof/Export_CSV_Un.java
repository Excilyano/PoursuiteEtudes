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

public class Export_CSV_Un {
	 
    private Connection connect;
    private String etudiant;
	
    @SuppressWarnings("unused")
	public Export_CSV_Un(String id, Connection conn) {
    	
    	this.connect = conn;
    	this.etudiant = id;
          
    	FileWriter fw ;
          
    	try {
    		Statement st = connect.createStatement();
           
          //this query gets all the tables in your database(put your db name in the query)
    		ResultSet res = st.executeQuery("select e.identifiant, e.nom, e.prenom, v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '" + this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 4");
           
          //Preparing List of table Names
    		List <String> tableNameList = new ArrayList<String>();
    		while(res.next()) {
    			tableNameList.add(res.getString(1));
    		}
    		
    		JFileChooser choix = new JFileChooser();
    		
    		File file = new File("C:/Users/Arnaud/Desktop/Resultat_ProjetS2/voeux_" + this.etudiant + ".csv");
    		
    		choix.setSelectedFile(file);
    		    		
    		int reponse = choix.showDialog(choix,"Enregistrer sous");
           
          //path to the folder where you will save your csv files
    		String filename = choix.getSelectedFile().toString(); //"C:/Users/Arnaud/Desktop/Resultat_ProjetS2/voeux_" + this.etudiant + ".csv";
           
          //star iterating on each table to fetch its data and save in a .csv file
    		for (String tableName:tableNameList) {
    			//System.out.println(tableName);
                 
                //select all data from table
    			res = st.executeQuery("select e.identifiant, e.nom, e.prenom, e.mail, v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '" + this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 5");
                 
                //colunm count is necessay as the tables are dynamic and we need to figure out the numbers of columns
    			int colunmCount = getColumnCount(res);
                 
    			try {
    				fw = new FileWriter(filename);
                    
    				res.next();
    				fw.append(res.getObject(1).toString());
                    fw.append(System.getProperty("line.separator"));
                    fw.append(res.getObject(2).toString());
                    fw.append(System.getProperty("line.separator"));
                    fw.append(res.getObject(3).toString());
                    fw.append(System.getProperty("line.separator"));
                    fw.append(res.getObject(4).toString());
                    fw.append(System.getProperty("line.separator"));
                    fw.append(System.getProperty("line.separator"));
                    
        			res = st.executeQuery("select e.identifiant, e.nom, e.prenom, e.mail, v.ordre, f.ecole, f.ville, f.formation from formation f, etudiant e, voeux v where v.identifiant = '" + this.etudiant + "' and f.numero = v.numero and e.identifiant = v.identifiant order by 5");
                    
                    //this loop is used to add column names at the top of file , if you do not need it just comment this loop
    				for(int i = 5; i <= colunmCount; i++) {
    					fw.append(res.getMetaData().getColumnName(i));
                        fw.append(";");
                    }
                     
    				fw.append(System.getProperty("line.separator"));
    				fw.append(System.getProperty("line.separator"));
                     
                    while(res.next()) {
                        for(int i = 5; i <= colunmCount; i++) {
                             
                            //you can update it here by using the column type but i am fine with the data so just converting
                            //everything to string first and then saving
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
                        //new line entered after each row
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
     
    //to get numbers of rows in a result set
    public static int  getRowCount(ResultSet res) throws SQLException {
    	res.last();
    	int numberOfRows = res.getRow();
    	res.beforeFirst();
    	return numberOfRows;
    }
 
    //to get no of columns in result set
     
    public static int  getColumnCount(ResultSet res) throws SQLException {
    	return res.getMetaData().getColumnCount();
    }
     
 
}