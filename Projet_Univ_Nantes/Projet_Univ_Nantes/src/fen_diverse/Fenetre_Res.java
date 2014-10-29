package fen_diverse;

import java.awt.BorderLayout;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import oracle.jdbc.OracleTypes;

@SuppressWarnings("serial")
public class Fenetre_Res extends JFrame {

  private JPanel result = new JPanel();

  private String requete;
  private String etudiant;
  
  private Connection connect;
  
  public Fenetre_Res(String id, Connection conn){
    setSize(500, 400);
    setTitle("Voeux de l'étudiant");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);
    
    this.connect = conn;
    this.etudiant = id;
    
    requete = "call package_info.getInfoEtu(?,?)";
		
    initContent();
    initTable(requete);
  }
	
  public void initContent(){
    result.setLayout(new BorderLayout());
    getContentPane().add(result);	
  }
	
  public void initTable(String query){
    try {
      /*Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      ResultSet res = state.executeQuery(query);

      ResultSetMetaData meta = res.getMetaData();
      Object[] column = new Object[meta.getColumnCount()];

      for(int i = 1 ; i <= meta.getColumnCount(); i++)
        column[i-1] = meta.getColumnName(i);

      res.last();
      int rowCount = res.getRow();
      Object[][] data = new Object[res.getRow()][meta.getColumnCount()];

      res.beforeFirst();
      int j = 1;

      while(res.next()){
        for(int i = 1 ; i <= meta.getColumnCount(); i++)
          data[j-1][i-1] = res.getObject(i);
				
        j++;
      }

      res.close();
      state.close();

      result.removeAll();
      result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);
      result.add(new JLabel("Il y a " + rowCount + " choix d'effectué(s)"), BorderLayout.SOUTH);
      result.revalidate();*/
      
    	CallableStatement cstm = connect.prepareCall(query);
	    cstm.setString(1, this.etudiant);
	    cstm.registerOutParameter(2, OracleTypes.CURSOR);
	    cstm.execute();
	    ResultSet res = (ResultSet)cstm.getObject(2);
	    	
	    Object[] column = new Object[] {"Ecole","Ville","Formation","Date limite","Avis"};

		Object[][] data = new Object[20][5];

		int j = 1;

		while(res.next()){
			for(int i = 1 ; i <= 5; i++)
				data[j-1][i-1] = res.getObject(i);
						
		        j++;
		}
    
		result.removeAll();
		result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);
		result.add(new JLabel(" Il y a " + (j-1) + " choix d'effectué(s)"), BorderLayout.SOUTH);
		result.revalidate();
			
    } 
    
    catch (SQLException e) {
      result.removeAll();
      result.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
      result.revalidate();
      JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
    }
  }
}