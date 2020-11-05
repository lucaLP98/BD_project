/*
 * file: SearchProccedura.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette di effettuare la ricerca di un elemento Procedura nel database
 * e di visualizzare i Parametri, le Variabili e le Exception che lo compongono e le 
 * Procedure/Funzioni richiamate al suo interno
 * 
 * @author Luca Pastore
 * @version 2019
 */
public class SearchProcedura extends javax.swing.JPanel {

    private TableModel proceduraTableModel; //modello tabella visualizzazione Procedura cercata
    private TableModel objectTableModel; //modello tabella visualizzazione Variabili, Exception, Parametri e Procedure Richiamate
    
    private int procTrovata; //indicherà se la ricrca della Procedure ha prodotto risultati o meno
    private final int NO = 0;
    private final int SI = 1;
    
    private int IDproc = -1; //conterrà l'ID della Procedura/Funzione cercata
    
    /**
     * Creates new form SearchTrigger
     */
    public SearchProcedura() {
        this.setSize(770,418);
        initComponents();
        
        procTrovata = NO;
        proceduraTable.setModel(new DefaultTableModel());
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
    
    //ricava l'ID della Procedura selezionata nella JTable proceduraTable
    private void ricavaID(){
        Statement stmt;
        ResultSet rst;
        String query = "";
        
        if(proceduraTable.getRowCount() == 1){
            /**
             * se è stata trovata solo una riga che rispetta i criteri di ricerca, allora viene ricavato l'ID 
             * della Procedura relativo all'unica riga trovata, senza prima dover selezionare una riga 
             */
            query = "SELECT id_procedura FROM Procedura WHERE nomeProcedura = '" + proceduraTable.getValueAt(0, 0).toString() + "' AND schema = '" + proceduraTable.getValueAt(0, 4).toString() + "'";
        }else if(proceduraTable.getSelectedRow() != -1){
            /**
             * se è stata torvata più di una riga che rispetta i criteri di ricerca, allora prima di poter ricavare l'ID di una Procedura
             * l'utente dovrà selezionare una riga dalla proceduraTable
             */
            query = "SELECT id_procedura FROM Procedura WHERE nomeProcedura = '" + proceduraTable.getValueAt(proceduraTable.getSelectedRow(), 0).toString() + "' AND schema = '" + proceduraTable.getValueAt(proceduraTable.getSelectedRow(), 4).toString() + "'";
        }else{
            JOptionPane.showMessageDialog(this, "Errore: selezionare prima una riga", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
        //ricava l'ID della Procedura in base alla query costruita nell'if precedente
        if(!query.equals("")){
            
            try{
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
                
                while(rst.next()){
                    IDproc = rst.getInt(1);
                }
                
                stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }   
        }else{
            IDproc = -1;
        }
    }
    
    //in base al RadioButton selezionato e alla riga selezionata dalla proceduraTable, recupera le righe dalla tabella corrispondente e le mostra nell'objectTable
    private void mostraOggetti(String query){
        PreparedStatement pstmt;
        ResultSet rs;
        
        ricavaID(); //ricavo l'ID della Procedura della quale ci insteressa visualizzare gli oggetto associati
        
        if(procTrovata == SI && IDproc != -1){ 
            try{
                pstmt = Database.getDefaultConnection().prepareStatement(query + IDproc);
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
        searchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        procTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        proceduraTable = new javax.swing.JTable();
        istr2 = new javax.swing.JLabel();
        istr1 = new javax.swing.JLabel();
        istr3 = new javax.swing.JLabel();
        parametriRadioButton = new javax.swing.JRadioButton();
        variabiliRadioButton = new javax.swing.JRadioButton();
        exceptionRadioButton = new javax.swing.JRadioButton();
        procChiamateRadioButton = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        objectTable = new javax.swing.JTable();

        searchLabel.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchLabel.setText("Inserire il nome della Procedura o Funzione :");

        procTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchButton.setText("Cerca");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addGap(18, 18, 18)
                .addComponent(procTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanelLayout.createSequentialGroup()
                        .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(procTextField))
                        .addGap(2, 2, 2))))
        );

        proceduraTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(proceduraTable);

        istr2.setText("Se la ricerca ha restituito più risultati, selezionare una riga per visualizzare le relative informazioni; ");

        istr1.setText("Di seguito verranno mostrate le informazioni della Procedura cercata; selezionare un bottone per visualizzare le relative informazioni.");

        istr3.setText("se ha restituito una sola riga, non è neccessario selezionarla.");

        objectButtonGroup.add(parametriRadioButton);
        parametriRadioButton.setText("Parametri");
        parametriRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parametriRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(variabiliRadioButton);
        variabiliRadioButton.setText("Variabili");
        variabiliRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variabiliRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(exceptionRadioButton);
        exceptionRadioButton.setText("Exception");
        exceptionRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exceptionRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(procChiamateRadioButton);
        procChiamateRadioButton.setText("Procedure richiamate");
        procChiamateRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procChiamateRadioButtonActionPerformed(evt);
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
            .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                    .addComponent(istr1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istr2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istr3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(parametriRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(variabiliRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(exceptionRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(procChiamateRadioButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr1)
                .addGap(0, 0, 0)
                .addComponent(istr2)
                .addGap(0, 0, 0)
                .addComponent(istr3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parametriRadioButton)
                    .addComponent(variabiliRadioButton)
                    .addComponent(exceptionRadioButton)
                    .addComponent(procChiamateRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //ricava la Procedura in base al nome inserito
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        PreparedStatement pstmt;
        ResultSet rs;
        String query = "SELECT nomeProcedura AS nome_procedura, bloccoCodice AS Blocco_Codice, tipo, tipoRitorno, schema, proprietario FROM procedura ";
        
        if(!procTextField.getText().equals("")){
            query += " WHERE nomeProcedura LIKE ?";
        }

        try{
            pstmt = Database.getDefaultConnection().prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!procTextField.getText().equals("")){
                pstmt.setString(1, procTextField.getText()+"%");
            }
            rs = pstmt.executeQuery();

            if(rs.last()){
                rs.beforeFirst();

                proceduraTableModel = new TableModel(rs);
                proceduraTableModel.setEditable(false);
                proceduraTableModel.setNumColumn();

                while(rs.next()) {
                    int riga = proceduraTableModel.getRowCount();
                    proceduraTableModel.setRowCount(proceduraTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < proceduraTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        proceduraTableModel.setValueAt(valore, riga, c);
                    }
                }

                procTrovata = SI;
                proceduraTable.setModel(proceduraTableModel);
            }else{
                JOptionPane.showMessageDialog(this, "Nessuna Procedura/Funzione trovata !", "Ricerca vuota", JOptionPane.WARNING_MESSAGE);
                proceduraTable.setModel(new DefaultTableModel());
                objectTable.setModel(new DefaultTableModel());
                procTrovata = NO;
                IDproc = -1;
            }

            pstmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void parametriRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parametriRadioButtonActionPerformed
        mostraOggetti("SELECT nomeParam AS Nome_parametro, tipo, tipologiaPar AS tipologia_parametro FROM parametri WHERE ID_proc = ");
    }//GEN-LAST:event_parametriRadioButtonActionPerformed

    private void variabiliRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variabiliRadioButtonActionPerformed
        mostraOggetti("SELECT nomeVariabile AS Nome_Variabile, tipo FROM variabili WHERE ID_proc = ");
    }//GEN-LAST:event_variabiliRadioButtonActionPerformed

    private void exceptionRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exceptionRadioButtonActionPerformed
        mostraOggetti("SELECT nomeEccezione AS Exception, bloccoCodice AS Blocco_Codice FROM eccezioni WHERE id_proc = ");
    }//GEN-LAST:event_exceptionRadioButtonActionPerformed

    private void procChiamateRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procChiamateRadioButtonActionPerformed
        mostraOggetti("SELECT Procedura_chiamata FROM procedureChiamate WHERE ID_chiamante = ");
    }//GEN-LAST:event_procChiamateRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton exceptionRadioButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup objectButtonGroup;
    private javax.swing.JTable objectTable;
    private javax.swing.JRadioButton parametriRadioButton;
    private javax.swing.JRadioButton procChiamateRadioButton;
    private javax.swing.JTextField procTextField;
    private javax.swing.JTable proceduraTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JRadioButton variabiliRadioButton;
    // End of variables declaration//GEN-END:variables
}
