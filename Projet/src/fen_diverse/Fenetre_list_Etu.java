package fen_diverse;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Fenetre_list_Etu extends JFrame {

  private JPanel result = new JPanel();

  private String requete;
  
  private Connection connect;
  
  public Fenetre_list_Etu(Connection conn){
    setSize(500, 400);
    setTitle("Liste des étudiants");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);
    
    this.connect = conn;
    
    requete = "select identifiant, nom, prenom, groupe from etudiant order by 2";
		
    initContent();
    initTable(requete);
    
    setVisible(true);
  }
	
  public void initContent(){
    result.setLayout(new BorderLayout());
    getContentPane().add(result);	
  }
	
  public void initTable(String query){
    try {
      Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      ResultSet res = state.executeQuery(query);

      ResultSetMetaData meta = res.getMetaData();
      Object[] column = new Object[meta.getColumnCount()];

      for(int i = 1 ; i <= meta.getColumnCount(); i++)
        column[i-1] = meta.getColumnName(i);

      res.last();
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