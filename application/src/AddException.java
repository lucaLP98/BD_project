/*
 * file : AddExceptiom.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette di aggiungere un'eccezione ad un Trigger o ad una Procedura/Funzione
 * 
 * @author Luca Pastore N86002599
 */
public class AddException extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private String queryIns;
    private int righeInserite = 0;
    
    public static final int TRIGGER = 1;
    public static final int PROCEDURA = 2;
    private int modalita;
    
    /**
     * Creates new form AddException
     */
    public AddException(int mod) {
       this.setSize(770, 418);
        initComponents();
        
        switch(mod){
            case TRIGGER:
                modalita = TRIGGER;
                
                procLabel.setVisible(false);
                proceduraComboBox.setVisible(false);
                triggerLabel.setVisible(true);
                triggerComboBox.setVisible(true);
                
                queryIns = "INSERT INTO eccezioni(nomeEccezione, bloccoCodice, ID_trigger) VALUES(?, ?, ?)";
            break;
            
            case PROCEDURA:
                modalita = PROCEDURA;
                
                procLabel.setVisible(true);
                proceduraComboBox.setVisible(true);
                triggerLabel.setVisible(false);
                triggerComboBox.setVisible(false);
                
                queryIns = "INSERT INTO eccezioni(nomeEccezione, bloccoCodice, ID_proc) VALUES(?, ?, ?)";
            break;    
        }
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento Eccezione nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //riempe la proceduraComboBox con le Procedure e Fuznioni presenti nel database
    public void riempiProceduraComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT P.schema, P.nomeProcedura FROM Procedura P";
        
        proceduraComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le Procedure nella comboBox saranno mostrate secondo il modello: nomeSchema.nomeProcedura
                //in quanto Procedure appartenenti a Schemi diversi possono avere lo stesso nome
                proceduraComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            proceduraComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //riempe la triggerComboBox con i Trigger presenti nel database
    public void riempiTriggerComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT T.schema, T.nomeTrigger FROM trigger1 T";
        
        triggerComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //I Trigger nella comboBox saranno mostrate secondo il modello: nomeSchema.nomeTrigger
                //in quanto Trigger appartenenti a Schemi diversi possono avere lo stesso nome
                triggerComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            triggerComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //ricava il nome della Procedura o Trigger selezionati nelle relative ComboBox
    private String ricavaNome(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(i+1);
        
        return schema;
    }
    
    //ricava il nome dell Schema a cui appartiene la Procedura o Trigger selezionati nelle relative ComboBox
    private String ricavaSchema(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(0, i);
        
        return schema;
    }
    
    //ricava la chiave primaria ID della Procedura o del Trigger selezionati nelle relative ComboBox
    private int ricavaID(String itemSelected){
        String ogg = ricavaNome(itemSelected);
        String schema = ricavaSchema(itemSelected);
        int id = -1;
        
        Statement stmt; 
        ResultSet rst;
    
        try{
            stmt = Database.getDefaultConnection().createStatement();
            
            if(modalita == TRIGGER)
                rst = stmt.executeQuery("SELECT T.id_trigger FROM trigger1 T WHERE T.nomeTrigger = '" + ogg + "' AND T.schema = '" + schema + "'");
            else
                rst = stmt.executeQuery("SELECT P.ID_procedura FROM Procedura P WHERE P.nomeProcedura = '" + ogg + "' AND P.schema = '" + schema + "'");
            
            while(rst.next()){
                id = rst.getInt(1);
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return id;
    }
    
    //controlla se i campi di input sono stati riempiti tutti
    private boolean campiVuoti(){
        return nomeExcTextField.getText().equals("") || bloccoCodTextArea.getText().equals("");
    }
    
    //azzera campi di input
    public void pulisciInput(){
        nomeExcTextField.setText("");
        bloccoCodTextArea.setText("");
        if(modalita == TRIGGER) triggerComboBox.setSelectedIndex(-1); 
        else proceduraComboBox.setSelectedIndex(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        istr1 = new javax.swing.JLabel();
        triggerLabel = new javax.swing.JLabel();
        procLabel = new javax.swing.JLabel();
        nomeExcLabel = new javax.swing.JLabel();
        nomeExcTextField = new javax.swing.JTextField();
        bloccoCodLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bloccoCodTextArea = new javax.swing.JTextArea();
        addExcButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
        campoObb = new javax.swing.JLabel();
        triggerComboBox = new javax.swing.JComboBox<>();
        proceduraComboBox = new javax.swing.JComboBox<>();

        istr1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr1.setText("Di sequito verranno inseriti i dati relativi all' Exception  che si vuole aggiungere al Trigger o Procedura");

        triggerLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        triggerLabel.setText("Trigger  ");

        procLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        procLabel.setText("Procedura");

        nomeExcLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeExcLabel.setText("Nome Exception  *");

        nomeExcTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        bloccoCodLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bloccoCodLabel.setText("Blocco Codice  *");

        bloccoCodTextArea.setColumns(20);
        bloccoCodTextArea.setRows(5);
        jScrollPane1.setViewportView(bloccoCodTextArea);

        addExcButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        addExcButton.setText("Aggiungi Exception");
        addExcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExcButtonActionPerformed(evt);
            }
        });

        endButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        endButton.setText("Annulla");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        campoObb.setText("*  Campo Obbligatorio");

        triggerComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        proceduraComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(procLabel)
                            .addComponent(istr1)
                            .addComponent(campoObb))
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(196, 196, 196)
                                .addComponent(addExcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(endButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomeExcLabel)
                                    .addComponent(bloccoCodLabel)
                                    .addComponent(triggerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(proceduraComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 215, Short.MAX_VALUE)
                                        .addComponent(nomeExcTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(triggerComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(istr1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(triggerLabel)
                    .addComponent(triggerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(procLabel)
                    .addComponent(proceduraComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeExcLabel)
                        .addGap(18, 18, 18)
                        .addComponent(bloccoCodLabel)
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endButton)
                            .addComponent(addExcButton))
                        .addGap(18, 18, 18)
                        .addComponent(campoObb))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeExcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addExcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExcButtonActionPerformed
        try{
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento di un' eccezione\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);

                pstmt.setString(1, nomeExcTextField.getText());
                pstmt.setString(2, bloccoCodTextArea.getText());
                
                if(modalita == TRIGGER)
                    pstmt.setInt(3, ricavaID(triggerComboBox.getSelectedItem().toString()));
                else
                    pstmt.setInt(3, ricavaID(proceduraComboBox.getSelectedItem().toString()));

                righeInserite = pstmt.executeUpdate();
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "L' exception " + nomeExcTextField.getText() + " è stata aggiunta", "Inserimento effettuato", JOptionPane.INFORMATION_MESSAGE);
                    pulisciInput();
                }
                
                pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_addExcButtonActionPerformed

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_endButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addExcButton;
    private javax.swing.JLabel bloccoCodLabel;
    private javax.swing.JTextArea bloccoCodTextArea;
    private javax.swing.JLabel campoObb;
    private javax.swing.JButton endButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nomeExcLabel;
    private javax.swing.JTextField nomeExcTextField;
    private javax.swing.JLabel procLabel;
    private javax.swing.JComboBox<String> proceduraComboBox;
    private javax.swing.JComboBox<String> triggerComboBox;
    private javax.swing.JLabel triggerLabel;
    // End of variables declaration//GEN-END:variables
}
