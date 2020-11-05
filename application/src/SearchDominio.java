/*
 * file : SearchDomain.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette di effettuare la ricerca di un elemento Dominio nel database
 * e di visualizzare i Valori che lo compongono e le Colonne che lo utilizzano
 * 
 * @author Luca Pastore
 * @version 2019
 */
public class SearchDominio extends javax.swing.JPanel {

    private TableModel dominioTableModel; //modello tabella visualizzazione Dominio cercato
    private TableModel objectTableModel; //modello tabella visualizzazione Valori e Colonne legati al Dominio cercato
    
    private int domTrovato; //indicherà se la ricrca del Dominio ha prodotto risultati o meno
    private final int NO = 0;
    private final int SI = 1;
    
    private int IDdominio = -1; //conterrà l'ID del Dominio cercato
    
    /**
     * Creates new form SearchDominio
     */
    public SearchDominio() {
        this.setSize(770,418);
        initComponents();
        
        domTrovato = NO;
        dominioTable.setModel(new DefaultTableModel());
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
    
    //ricava l'ID del Dominio selezionato nella JTable dominioTable
    private void ricavaID(){
        Statement stmt;
        ResultSet rst;
        String query = "";
        
        if(dominioTable.getRowCount() == 1){
            /**
             * se è stata trovata solo una riga che rispetta i criteri di ricerca, allora viene ricavato l'ID 
             * del Dominio relativo all'unica riga trovata, senza prima dover selezionare una riga 
             */
            query = "SELECT id_dominio FROM Dominio WHERE nomeDominio = '" + dominioTable.getValueAt(0, 0).toString() + "' AND schema = '" + dominioTable.getValueAt(0, 3).toString() + "'";
        }else if(dominioTable.getSelectedRow() != -1){
            /**
             * se è stata torvata più di una riga che rispetta i criteri di ricerca, allora prima di poter ricavare l'ID di un Dominio
             * l'utente dovrà selezionare una riga dalla dominioTable
             */
            query = "SELECT id_dominio FROM Dominio WHERE nomeDominio = '" + dominioTable.getValueAt(dominioTable.getSelectedRow(), 0).toString() + "' AND schema = '" + dominioTable.getValueAt(dominioTable.getSelectedRow(), 3).toString() + "'";
        }else{
            JOptionPane.showMessageDialog(this, "Errore: selezionare prima una riga", "Errore", JOptionPane.ERROR_MESSAGE);
            IDdominio = -1;
        }
        
        //ricava l'ID del Dominio in base alla query costruita nell'if precedente
        if(!query.equals("")){
            
            try{
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
                
                while(rst.next()){
                    IDdominio = rst.getInt(1);
                }
                
                stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }   
        }
    }
    
    //in base al RadioButton selezionato e alla riga selezionata dalla dominioTable, recupera le righe dalla tabella corrispondente e le mostra nell'objectTable
    private void mostraOggetti(String query){
        PreparedStatement pstmt;
        ResultSet rs;
        
        ricavaID(); //ricavo l'ID del Dominio della quale ci insteressa visualizzare gli oggetto associati
        
        if(domTrovato == SI && IDdominio != -1){ 
            try{
                pstmt = Database.getDefaultConnection().prepareStatement(query + " WHERE ID_dominio = " + IDdominio);
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
        searchDomLabel = new javax.swing.JLabel();
        dominioTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dominioTable = new javax.swing.JTable();
        istr1 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        istr3 = new javax.swing.JLabel();
        valoriRadioButton = new javax.swing.JRadioButton();
        colonneRadioButton = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        objectTable = new javax.swing.JTable();

        searchDomLabel.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchDomLabel.setText("Inserire il nome del Dominio da cercare : ");

        dominioTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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
                .addComponent(searchDomLabel)
                .addGap(18, 18, 18)
                .addComponent(dominioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPanelLayout.createSequentialGroup()
                        .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchDomLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dominioTextField))
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dominioTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(dominioTable);

        istr1.setText("Di seguito verranno mostrate le informazioni del Dominio cercato; selezionare un bottone per visualizzare le relative informazioni.");

        istr2.setText("Se la ricerca ha restituito più risultati, selezionare una riga per visualizzare le relative informazioni; ");

        istr3.setText("se ha restituito una sola riga, non è neccessario selezionarla.");

        objectButtonGroup.add(valoriRadioButton);
        valoriRadioButton.setText("Valori");
        valoriRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valoriRadioButtonActionPerformed(evt);
            }
        });

        objectButtonGroup.add(colonneRadioButton);
        colonneRadioButton.setText("Colonne che utilizzano il Dominio");
        colonneRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colonneRadioButtonActionPerformed(evt);
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
            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istr1)
                            .addComponent(istr2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(valoriRadioButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colonneRadioButton))
                                .addComponent(istr3, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(valoriRadioButton)
                    .addComponent(colonneRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //esegue l'istruzione SQL che cerca le tabelle che rispettano i criteri di ricerca
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        PreparedStatement pstmt;
        ResultSet rs;
        String query = "SELECT nomeDominio, tipo , proprietario, schema FROM Dominio "; 

        if(!dominioTextField.getText().equals("")){
            query += " WHERE nomeDominio LIKE ?";
        }
        
        try{
            pstmt = Database.getDefaultConnection().prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!dominioTextField.getText().equals("")){
                pstmt.setString(1, dominioTextField.getText()+"%");
            }
            rs = pstmt.executeQuery();

            if(rs.last()){
                rs.beforeFirst();

                dominioTableModel = new TableModel(rs);
                dominioTableModel.setEditable(false);
                dominioTableModel.setNumColumn();

                while(rs.next()) {
                    int riga = dominioTableModel.getRowCount();
                    dominioTableModel.setRowCount(dominioTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < dominioTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        dominioTableModel.setValueAt(valore, riga, c);
                    }
                }

                domTrovato = SI;
                dominioTable.setModel(dominioTableModel);
            }else{
                JOptionPane.showMessageDialog(this, "Nessun Dominio trovato !", "Ricerca vuota", JOptionPane.WARNING_MESSAGE);
                dominioTable.setModel(new DefaultTableModel());
                objectTable.setModel(new DefaultTableModel());
                domTrovato = NO;
                IDdominio = -1;
            }

            pstmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    //mostra tutti i valori appartenenti al Dominio cercato
    private void valoriRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valoriRadioButtonActionPerformed
        mostraOggetti("SELECT valoreNome AS Valore FROM valore ");
    }//GEN-LAST:event_valoriRadioButtonActionPerformed

    //mostra tutte le colonne che utilizzano come tipo il Dominio cercato
    private void colonneRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colonneRadioButtonActionPerformed
        mostraOggetti("SELECT C.nomeColonna AS Colonna, C.nullo AS Annullabile, C.valdefault AS Valore_Default, (SELECT nomeTabella FROM tabella WHERE ID_tabella = C.tabella) AS Tabella FROM Colonna C ");
    }//GEN-LAST:event_colonneRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton colonneRadioButton;
    private javax.swing.JTable dominioTable;
    private javax.swing.JTextField dominioTextField;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.ButtonGroup objectButtonGroup;
    private javax.swing.JTable objectTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchDomLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JRadioButton valoriRadioButton;
    // End of variables declaration//GEN-END:variables
}
