/*
 * file : SearchTrigger.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette di effettuare la ricerca di un elemento Trigger nel database
 * e di visualizzare le Variabili e le Exception che lo compongono
 * 
 * @author Luca Pastore
 * @version 2019
 */
public class SearchTrigger extends javax.swing.JPanel {

    private TableModel triggerTableModel; //modello tabella visualizzazione Trigger cercato
    private TableModel objectTableModel; //modello tabella visualizzazione Variabili ed Exception legati al Dominio cercato
    
    private int trigTrovato; //indicherà se la ricrca del Trigger ha prodotto risultati o meno
    private final int NO = 0;
    private final int SI = 1;
    
    private int IDtrigger = -1; //conterrà l'ID del Trigger cercato
    
    /**
     * Creates new form SearchTrigger
     */
    public SearchTrigger() {
        this.setSize(770,418);
        initComponents();
        
        trigTrovato = NO;
        triggerTable.setModel(new DefaultTableModel());
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
    
    //ricava l'ID del Trigger selezionato nella JTable triggerTable
    private void ricavaID(){
        Statement stmt;
        ResultSet rst;
        String query = "";
        
        if(triggerTable.getRowCount() == 1){
            /**
             * se è stata trovata solo una riga che rispetta i criteri di ricerca, allora viene ricavato l'ID 
             * del Trigger relativo all'unica riga trovata, senza prima dover selezionare una riga 
             */
            query = "SELECT id_trigger FROM Trigger1 WHERE nomeTrigger = '" + triggerTable.getValueAt(0, 0).toString() + "' AND schema = '" + triggerTable.getValueAt(0, 7).toString() + "'";
        }else if(triggerTable.getSelectedRow() != -1){
            /**
             * se è stata torvata più di una riga che rispetta i criteri di ricerca, allora prima di poter ricavare l'ID di un Trigger
             * l'utente dovrà selezionare una riga dalla triggerTable
             */
            query = "SELECT id_trigger FROM Trigger1 WHERE nomeTrigger = '" + triggerTable.getValueAt(triggerTable.getSelectedRow(), 0).toString() + "' AND schema = '" + triggerTable.getValueAt(triggerTable.getSelectedRow(), 7).toString() + "'";
        }else{
            JOptionPane.showMessageDialog(this, "Errore: selezionare prima una riga", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
        //ricava l'ID del Trigger in base alla query costruita nell'if precedente
        if(!query.equals("")){
            
            try{
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
                
                while(rst.next()){
                    IDtrigger = rst.getInt(1);
                }
                
                stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }   
        }else{
            IDtrigger = -1;
        }
    }
    
    //in base al RadioButton selezionato e alla riga selezionata dalla triggerTable, recupera le righe dalla tabella corrispondente e le mostra nell'objectTable
    private void mostraOggetti(String query){
        PreparedStatement pstmt;
        ResultSet rs;
        
        ricavaID(); //ricavo l'ID del Trigger della quale ci insteressa visualizzare gli oggetto associati
        
        if(trigTrovato == SI && IDtrigger != -1){ 
            try{
                pstmt = Database.getDefaultConnection().prepareStatement(query + " WHERE ID_trigger = " + IDtrigger);
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
        seacrhPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        triggerTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        triggerTable = new javax.swing.JTable();
        istr1 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        istr3 = new javax.swing.JLabel();
        varRadioButton = new javax.swing.JRadioButton();
        exceptionRadioButton = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        objectTable = new javax.swing.JTable();

        searchLabel.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchLabel.setText("Inserire il nome del Trigger da cercare :");

        triggerTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchButton.setText("Cerca");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout seacrhPanelLayout = new javax.swing.GroupLayout(seacrhPanel);
        seacrhPanel.setLayout(seacrhPanelLayout);
        seacrhPanelLayout.setHorizontalGroup(
            seacrhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seacrhPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addGap(51, 51, 51)
                .addComponent(triggerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        seacrhPanelLayout.setVerticalGroup(
            seacrhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seacrhPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seacrhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(triggerTextField)
                    .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        triggerTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(triggerTable);

        istr1.setText("Di seguito verranno mostrate le informazioni del Trigger cercato; selezionare un bottone per visualizzare le relative informazioni.");

        istr2.setText("Se la ricerca ha restituito più risultati, selezionare una riga per visualizzare le relative informazioni; ");

        istr3.setText("se ha restituito una sola riga, non è neccessario selezionarla.");

        objectButtonGroup.add(varRadioButton);
        varRadioButton.setText("Variabili");
        varRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(exceptionRadioButton);
        exceptionRadioButton.setText("Exception");
        exceptionRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exceptionRadioButtonActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(seacrhPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(varRadioButton)
                                .addGap(71, 71, 71)
                                .addComponent(exceptionRadioButton))
                            .addComponent(istr1)
                            .addComponent(istr2)
                            .addComponent(istr3))
                        .addGap(0, 120, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(seacrhPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr1)
                .addGap(0, 0, 0)
                .addComponent(istr2)
                .addGap(0, 0, 0)
                .addComponent(istr3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(varRadioButton)
                    .addComponent(exceptionRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //mostra tutte le Exception dichiarate nel Trigger
    private void exceptionRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exceptionRadioButtonActionPerformed
        mostraOggetti("SELECT nomeEccezione AS Exception, bloccoCodice AS Blocco_Codice FROM Eccezioni ");
    }//GEN-LAST:event_exceptionRadioButtonActionPerformed

    //ricerca il Trigger in base al nome inserito
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        PreparedStatement pstmt;
        ResultSet rs;
        String query = "SELECT nomeTrigger AS Nome_Trigger, Tempo, ForEachRow AS For_Each_Row, condizioneWhen AS Condizione_When, causa, bloccoCodice as Blocco_Codice, Oggetto, schema, Proprietario FROM infoTrigger ";
        
        if(!triggerTextField.getText().equals("")){
            query += " WHERE nomeTrigger = ?";
        }
        
        try{
            pstmt = Database.getDefaultConnection().prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!triggerTextField.getText().equals("")){
                pstmt.setString(1, triggerTextField.getText());
            }
            rs = pstmt.executeQuery();

            if(rs.last()){
                rs.beforeFirst();

                triggerTableModel = new TableModel(rs);
                triggerTableModel.setEditable(false);
                triggerTableModel.setNumColumn();

                while(rs.next()) {
                    int riga = triggerTableModel.getRowCount();
                    triggerTableModel.setRowCount(triggerTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < triggerTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        triggerTableModel.setValueAt(valore, riga, c);
                    }
                    }

                trigTrovato = SI;
                triggerTable.setModel(triggerTableModel);
                
                pstmt.close();
            }else{
                JOptionPane.showMessageDialog(this, "Nessun Trigger trovato !", "Ricerca vuota", JOptionPane.WARNING_MESSAGE);
                triggerTable.setModel(new DefaultTableModel());
                objectTable.setModel(new DefaultTableModel());
                trigTrovato = NO;
                IDtrigger = -1;
            }

        }catch(SQLException e){
            mostraErrore(e);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    //mostra le variabili dichiarate nel Trigger cercato
    private void varRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varRadioButtonActionPerformed
        mostraOggetti("SELECT NomeVariabile AS Variabile, tipo FROM Variabili ");
    }//GEN-LAST:event_varRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton exceptionRadioButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup objectButtonGroup;
    private javax.swing.JTable objectTable;
    private javax.swing.JPanel seacrhPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTable triggerTable;
    private javax.swing.JTextField triggerTextField;
    private javax.swing.JRadioButton varRadioButton;
    // End of variables declaration//GEN-END:variables
}
