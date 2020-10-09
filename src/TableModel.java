/*
 * file : TableModel.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 * Classe che definisce il modello adottato dalle JTable
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class TableModel extends DefaultTableModel{
    private boolean editable = false;
    ResultSet rs;
    private int cMax;
    
    //crea un nuovo oggetto TableModel
    public TableModel(ResultSet a){
        rs = a;
    }
    
    @Override 
    //restituisce true se le celle sono modificabili, false altrimenti
    public boolean isCellEditable (int row, int column) {
	return editable;
    }
    
    //permette di stabilire se le celle della JTable che implementa il modello sono modificabili
    public void setEditable(boolean edit){
        this.editable = edit;
    }
    
    //imposta il nome delle colonne del modello
    private String[] nomiColonne(ResultSet rst){
        try{
            int dim = rst.getMetaData().getColumnCount();
            String[] nomi = new String[dim];
            
            for(int i = 0; i < dim ; i++){
                nomi[i] = rst.getMetaData().getColumnName(i+1);
            }
            
            return nomi;
        }catch(SQLException e){
            return new String[0];
        }
    }
    
    //imposta il numero di colonne del modello
    public void setNumColumn(){
        try{
            cMax = rs.getMetaData().getColumnCount();
            this.setColumnCount(cMax);
            this.setColumnIdentifiers(nomiColonne(rs));
        }catch(SQLException e){
            e.printStackTrace();
        }
    }    
    
    //metodo che elimina tutte le righe presenti in una JTable
    public void removeAllRows(){
        for(int numRighe = this.getRowCount()-1; numRighe >= 0; numRighe--){
            this.removeRow(numRighe);
        }
    }
}
