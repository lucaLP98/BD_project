/*
 * file : InsertSequenza.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette l'aggiunta dell'elemento sequenza nel database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertSequenza extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO sequenza(NomeSeq, Incremento, ValIniziale, Cycle, Proprietario, Schema, ValMax) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private int righeInserite = 0;
    /**
     * Creates new form InsertSequenza
     */
    public InsertSequenza() {
        this.setSize(770, 423);
        initComponents();
    }

    //metodo che effettua l'azzeramento dei campi di input
    public void pulisciInput(){
        nomeSeqTextField.setText("");
        incrementoSpinner.setValue(1);
        valInizialeSpinner.setValue(1);
        valMaxSpinner.setValue(-1);
        noCycleRadioButton.setSelected(true);
        siCycleRadioButton.setSelected(false);
        schemaComboBox.setSelectedIndex(-1);
        proprComboBox.setSelectedIndex(-1);
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo che riempe la proprComboBox con gli usernsme degli utenti che hanno i permessi necessari alla creazione di una squenza
    private void riempiProprComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT U.username FROM utente U JOIN compRuoloSistema C ON U.ruolo = C.ID_ruolo WHERE C.ID_privSis = 'ALL PRIVILEGIES' OR C.ID_privSis = 'CREATE SEQUENCE'";
        
        proprComboBox.removeAllItems();
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                proprComboBox.addItem(rst.getString(1));
            }
            
            proprComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //metodo utilizzato per riempire la schemaComboBox con gli schemi presenti nel database
    private void riempiSchemaComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT nomeSchema FROM schema1";
        
        schemaComboBox.removeAllItems();
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                schemaComboBox.addItem(rst.getString(1));
            }
            
            schemaComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    public void riempiComboBox(){
        riempiProprComboBox();
        riempiSchemaComboBox();
    }
    
    private boolean campiVuoti(){
        return nomeSeqTextField.getText().equals("") || proprComboBox.getSelectedIndex() == -1 || schemaComboBox.getSelectedIndex() == -1;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cycleButtonGroup = new javax.swing.ButtonGroup();
        istruzioni1 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        istruzioni3 = new javax.swing.JLabel();
        nomeSeqLabel = new javax.swing.JLabel();
        incrementoLabel = new javax.swing.JLabel();
        valMaxLabel = new javax.swing.JLabel();
        valInizialeLabel = new javax.swing.JLabel();
        cycleLabel = new javax.swing.JLabel();
        proprLabel = new javax.swing.JLabel();
        schemaLabel = new javax.swing.JLabel();
        campoObbLabel = new javax.swing.JLabel();
        nomeSeqTextField = new javax.swing.JTextField();
        incrementoSpinner = new javax.swing.JSpinner();
        valInizialeSpinner = new javax.swing.JSpinner();
        valMaxSpinner = new javax.swing.JSpinner();
        siCycleRadioButton = new javax.swing.JRadioButton();
        noCycleRadioButton = new javax.swing.JRadioButton();
        proprComboBox = new javax.swing.JComboBox<>();
        schemaComboBox = new javax.swing.JComboBox<>();
        insertSeqButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();

        istruzioni1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni1.setText("Di seguito andranno inseriti tutti i dati utilizzati per la creazione di una sequenza.");

        istruzioni2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni2.setText("Nella casella Proprietario verranno mostrati solo gli utenti con i permessi necessari alla creazione");

        istruzioni3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni3.setText("di una Sequenza. Lasciare il campo Valore Massimo a -1 per non inserire un valore massimo alla sequenza");

        nomeSeqLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeSeqLabel.setText("Nome Sequenza  *");

        incrementoLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        incrementoLabel.setText("Incremento  *");

        valMaxLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        valMaxLabel.setText("Valore massimo");

        valInizialeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        valInizialeLabel.setText("Valore inziale  *");

        cycleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cycleLabel.setText("Abilita ciclo sequenza");

        proprLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        proprLabel.setText("Proprietario  *");

        schemaLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        schemaLabel.setText("Schema  *");

        campoObbLabel.setText("*  Campo obbligatorio");

        nomeSeqTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        incrementoSpinner.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        incrementoSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        valInizialeSpinner.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        valInizialeSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));

        valMaxSpinner.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        valMaxSpinner.setModel(new javax.swing.SpinnerNumberModel(-1, null, null, 1));
        valMaxSpinner.setToolTipText("");
        valMaxSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cycleButtonGroup.add(siCycleRadioButton);
        siCycleRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        siCycleRadioButton.setText("SI");

        cycleButtonGroup.add(noCycleRadioButton);
        noCycleRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        noCycleRadioButton.setSelected(true);
        noCycleRadioButton.setText("NO");

        proprComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        schemaComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        insertSeqButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertSeqButton.setText("Inserisci Sequenza");
        insertSeqButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertSeqButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(schemaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(proprLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nomeSeqLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(incrementoLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(valInizialeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(valMaxLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cycleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeSeqTextField)
                            .addComponent(incrementoSpinner)
                            .addComponent(valInizialeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(valMaxSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(siCycleRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(noCycleRadioButton))
                            .addComponent(proprComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(schemaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(campoObbLabel)
                        .addGap(84, 84, 84)
                        .addComponent(insertSeqButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(222, 222, 222))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istruzioni1)
                    .addComponent(istruzioni2)
                    .addComponent(istruzioni3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(istruzioni1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomeSeqLabel)
                    .addComponent(nomeSeqTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(incrementoLabel)
                    .addComponent(incrementoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valInizialeLabel)
                    .addComponent(valInizialeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valMaxLabel)
                    .addComponent(valMaxSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(siCycleRadioButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cycleLabel)
                        .addComponent(noCycleRadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proprLabel)
                    .addComponent(proprComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(schemaLabel)
                    .addComponent(schemaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(insertSeqButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campoObbLabel)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //inserimento sequenza nel database
    private void insertSeqButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertSeqButtonActionPerformed
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento della sequenza\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);
                
                pstmt.setString(1, nomeSeqTextField.getText());
                pstmt.setInt(2, (int)incrementoSpinner.getValue());
                pstmt.setInt(3, (int)valInizialeSpinner.getValue());
                
                if(siCycleRadioButton.isSelected()){ 
                    pstmt.setString(4, "SI");
                }else{              
                    pstmt.setString(4, "NO");
                }
                
                pstmt.setString(5, proprComboBox.getSelectedItem().toString());
                pstmt.setString(6, schemaComboBox.getSelectedItem().toString());
                
                if((int)valMaxSpinner.getValue() != -1 && ((int)valMaxSpinner.getValue() >= (int)valInizialeSpinner.getValue())){
                    pstmt.setInt(7, (int)valMaxSpinner.getValue());
                }else{
                    pstmt.setString(7, null);
                }
                
                righeInserite = pstmt.executeUpdate();
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "La sequenza " + nomeSeqTextField.getText() + " è stata inserita correttamente !", "Inserimento Sequenza effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    pulisciInput();
                } 
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertSeqButtonActionPerformed

    //annullamento inserimento sequenza
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.ButtonGroup cycleButtonGroup;
    private javax.swing.JLabel cycleLabel;
    private javax.swing.JLabel incrementoLabel;
    private javax.swing.JSpinner incrementoSpinner;
    private javax.swing.JButton insertSeqButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni3;
    private javax.swing.JRadioButton noCycleRadioButton;
    private javax.swing.JLabel nomeSeqLabel;
    private javax.swing.JTextField nomeSeqTextField;
    private javax.swing.JComboBox<String> proprComboBox;
    private javax.swing.JLabel proprLabel;
    private javax.swing.JComboBox<String> schemaComboBox;
    private javax.swing.JLabel schemaLabel;
    private javax.swing.JRadioButton siCycleRadioButton;
    private javax.swing.JLabel valInizialeLabel;
    private javax.swing.JSpinner valInizialeSpinner;
    private javax.swing.JLabel valMaxLabel;
    private javax.swing.JSpinner valMaxSpinner;
    // End of variables declaration//GEN-END:variables
}
