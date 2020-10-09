/*
 * file : SearchTabella.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette la ricerca di una Tabella e la visualizzazzione di 
 * tutte le sue componenti
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class SearchTabella extends javax.swing.JPanel {

    private TableModel tabTableModel; //modello tabella visualizzazione Tabella cercata
    private TableModel objectTableModel; //modello tabella visualizzazione oggetti appartenenti a Tabella cercata
    
    private int tabTrovata; //indicherà se la ricrca della tabella ha prodotto risultati o meno
    private final int NO = 0;
    private final int SI = 1;
    
    private int IDtabella = -1; //conterrà l'ID della tabella cercata
    
    /**
     * Creates new form SearchTabella
     */
    public SearchTabella() {
        this.setSize(770,418);
        initComponents();
        
        tabTrovata = NO;
        tabellaTable.setModel(new DefaultTableModel());
        objectTable.setModel(new DefaultTableModel());
    }

    //Metodo che mostra a video l'errore generatosi durante l'operazione SQL
    public void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //ricava l'ID della tabella selezionata nella JTable tabellaTable
    private void ricavaID(){
        Statement stmt;
        ResultSet rst;
        String query = "";
        
        if(tabellaTable.getRowCount() == 1){
            /**
             * se è stata trovata solo una riga che rispetta i criteri di ricerca, allora viene ricavato l'ID 
             * della Tabella relativa all'unica riga trovata, senza prima dover selezionare una riga 
             */
            query = "SELECT id_tabella FROM tabella WHERE nomeTabella = '" + tabellaTable.getValueAt(0, 0).toString() + "' AND schema = '" + tabellaTable.getValueAt(0, 4).toString() + "'";
        }else if(tabellaTable.getSelectedRow() != -1){
            /**
             * se è stata torvata più di una riga che rispetta i criteri di ricerca, allora prima di poter ricavare l'ID di una tabella
             * l'utente dovrà selezionare una riga dalla tabellaTable
             */
            query = "SELECT id_tabella FROM tabella WHERE nomeTabella = '" + tabellaTable.getValueAt(tabellaTable.getSelectedRow(), 0).toString() + "' AND schema = '" + tabellaTable.getValueAt(tabellaTable.getSelectedRow(), 4).toString() + "'";
        }else{
            JOptionPane.showMessageDialog(this, "Errore: selezionare prima una riga", "Errore", JOptionPane.ERROR_MESSAGE);
            IDtabella = -1;
        }
        
        //ricava l'ID della tabella in base alla query costruita nell'if precedente
        if(!query.equals("")){
            
            try{
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
                
                while(rst.next()){
                    IDtabella = rst.getInt(1);
                }
                
                stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }   
        }
    }
    
    //in base al RadioButton selezionato e alla riga selezionata dalla tabellaTable, recupera le righe dalla tabella corrispondente e le mostra nell'objectTable
    private void mostraOggetti(String query){
        PreparedStatement pstmt;
        ResultSet rs;
        
        ricavaID(); //ricavo l'ID della tabella della quale ci insteressa visualizzare gli oggetto associati
        
        if(tabTrovata == SI && IDtabella != -1){ 
            try{
                pstmt = Database.getDefaultConnection().prepareStatement(query + " WHERE ID = " + IDtabella);
                rs = pstmt.executeQuery();
                
                objectTableModel = new TableModel(rs);
                objectTableModel.setEditable(false);
                objectTableModel.setNumColumn();
                
                while(rs.next()) {
                    int riga = objectTableModel.getRowCount(); 
                    objectTableModel.setRowCount(objectTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < objectTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        objectTableModel.setValueAt(valore, riga, c);
                    }
                }
                
                objectTable.setModel(objectTableModel);
                
                pstmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        objectButtonGroup = new javax.swing.ButtonGroup();
        selectTableLabel = new javax.swing.JLabel();
        tableTextField = new javax.swing.JTextField();
        searchTableButton = new javax.swing.JButton();
        istr1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabellaTable = new javax.swing.JTable();
        istr3 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        objectTable = new javax.swing.JTable();
        colonnaRadioButton = new javax.swing.JRadioButton();
        vincoloRadioButton = new javax.swing.JRadioButton();
        triggerRadioButton = new javax.swing.JRadioButton();
        istr4 = new javax.swing.JLabel();

        selectTableLabel.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        selectTableLabel.setText("Inserire il nome della tabella da cercare : ");

        tableTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchTableButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchTableButton.setText("Cerca");
        searchTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTableButtonActionPerformed(evt);
            }
        });

        istr1.setText("Di seguito verranno mostrate i risultati della ricerca.");

        tabellaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabellaTable);

        istr3.setText("altrimenti,  se la ricerca ha restituito un solo risultato,  verranno mostrate le informazioni relative all' unica riga trovata");

        istr2.setText("Di seguito verranno mostrate tutti gli oggetti legati alla Tabella cercata.");

        objectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(objectTable);

        objectButtonGroup.add(colonnaRadioButton);
        colonnaRadioButton.setText("Colonne");
        colonnaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colonnaRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(vincoloRadioButton);
        vincoloRadioButton.setText("Vincoli");
        vincoloRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vincoloRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(triggerRadioButton);
        triggerRadioButton.setText("Trigger");
        triggerRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerRadioButtonActionPerformed(evt);
            }
        });

        istr4.setText("Se la ricerca ha prodotto più di un risultato, selezionare una riga per visualizzare le informazioni della Tabella corrispondente.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istr1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectTableLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(istr2)
                            .addComponent(istr3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(colonnaRadioButton)
                                .addGap(62, 62, 62)
                                .addComponent(vincoloRadioButton)
                                .addGap(83, 83, 83)
                                .addComponent(triggerRadioButton))
                            .addComponent(istr4))
                        .addGap(0, 53, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTableLabel)
                    .addComponent(tableTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchTableButton))
                .addGap(10, 10, 10)
                .addComponent(istr1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(istr2)
                .addGap(0, 0, 0)
                .addComponent(istr4)
                .addGap(0, 0, 0)
                .addComponent(istr3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colonnaRadioButton)
                    .addComponent(vincoloRadioButton)
                    .addComponent(triggerRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //mostra le colonne della Tabella cercata
    private void colonnaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colonnaRadioButtonActionPerformed
        mostraOggetti("SELECT Nome_Colonna, Tipo_Dato, Lunghezza_Dati, Annullabile, Valore_Default FROM infoColonna ");
    }//GEN-LAST:event_colonnaRadioButtonActionPerformed

    //mostra i vincoli definiti nella Tabella cercata
    private void vincoloRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vincoloRadioButtonActionPerformed
        mostraOggetti("SELECT NomeVincolo, TipoVincolo, CondizioneCheck, Stato, Proprietario, Schema FROM infoVincolo ");
    }//GEN-LAST:event_vincoloRadioButtonActionPerformed

    //mostra i trigger che hannno per oggetto la Tabella cercata
    private void triggerRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerRadioButtonActionPerformed
        mostraOggetti("SELECT NomeTrigger, Tempo, ForEachRow, CondizioneWhen, Causa, BloccoCodice, Schema, Proprietario FROM infoTrigger ");
    }//GEN-LAST:event_triggerRadioButtonActionPerformed

    //esegue l'istruzione SQL che cerca le tabelle che rispettano i criteri di ricerca
    private void searchTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTableButtonActionPerformed
        PreparedStatement pstmt;
        ResultSet rs;
        String query = "SELECT nomeTabella, dataCreazione , ultimaModifica , proprietario, schema FROM Tabella ";

        if(!tableTextField.getText().equals("")){
            query += "WHERE nomeTabella = ?";
        }
            
        try{
            pstmt = Database.getDefaultConnection().prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!tableTextField.getText().equals("")){
                pstmt.setString(1, tableTextField.getText());
            }
            rs = pstmt.executeQuery();

            if(rs.last()){
                rs.beforeFirst();

                tabTableModel = new TableModel(rs);
                tabTableModel.setEditable(false);
                tabTableModel.setNumColumn();

                while(rs.next()) {
                    int riga = tabTableModel.getRowCount();
                    tabTableModel.setRowCount(tabTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < tabTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        tabTableModel.setValueAt(valore, riga, c);
                    }
                }

                tabTrovata = SI;
                tabellaTable.setModel(tabTableModel);
            }else{
                JOptionPane.showMessageDialog(this, "Nessuna Tabella trovata !", "Ricerca vuota", JOptionPane.WARNING_MESSAGE);
                tabellaTable.setModel(new DefaultTableModel());
                objectTable.setModel(new DefaultTableModel());
                tabTrovata = NO;
                IDtabella = -1;
            }

            pstmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }//GEN-LAST:event_searchTableButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton colonnaRadioButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JLabel istr4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup objectButtonGroup;
    private javax.swing.JTable objectTable;
    private javax.swing.JButton searchTableButton;
    private javax.swing.JLabel selectTableLabel;
    private javax.swing.JTable tabellaTable;
    private javax.swing.JTextField tableTextField;
    private javax.swing.JRadioButton triggerRadioButton;
    private javax.swing.JRadioButton vincoloRadioButton;
    // End of variables declaration//GEN-END:variables
}
