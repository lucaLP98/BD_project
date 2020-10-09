/*
 * file : AddVariabile.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette di aggiungere Variabili a Trigger o Procedure
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class AddVariabile extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private String queryIns;
    private int righeInserite = 0;
    
    public static final int TRIGGER = 1;
    public static final int PROCEDURA = 2;
    private int modalita;
    
    /**
     * Creates new form AddVariabile
     * 
     * @param mod indica l'oggetto a cui si sta aggiungendo una Variabile: mod = 1 -> Trigger, mod = 2 -> Procedura
     */
    public AddVariabile(int mod) {
        this.setSize(770, 418);
        initComponents();
        
        switch(mod){
            case TRIGGER:
                modalita = TRIGGER;
                
                procLabel.setVisible(false);
                proceduraComboBox.setVisible(false);
                triggerLabel.setVisible(true);
                triggerComboBox.setVisible(true);
                
                queryIns = "INSERT INTO variabili(nomeVariabile, tipo, ID_trigger) VALUES(?, ?, ?)";
            break;
            
            case PROCEDURA:
                modalita = PROCEDURA;
                
                procLabel.setVisible(true);
                proceduraComboBox.setVisible(true);
                triggerLabel.setVisible(false);
                triggerComboBox.setVisible(false);
                
                queryIns = "INSERT INTO variabili(nomeVariabile, tipo, ID_proc) VALUES(?, ?, ?)";
            break;    
        }
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento Vatiabile nel database
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
        return nomeVarTextField.getText().equals("") || tipoVarComboBox.getSelectedIndex() == -1;
    }
    
    //azzera campi di input
    public void pulisciInput(){
        nomeVarTextField.setText("");
        tipoVarComboBox.setSelectedIndex(-1);
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
        nomeVarLabel = new javax.swing.JLabel();
        nomeVarTextField = new javax.swing.JTextField();
        tipoVarComboBox = new javax.swing.JComboBox<>();
        tipoLabel = new javax.swing.JLabel();
        campoObbligatorio = new javax.swing.JLabel();
        addVarButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        triggerComboBox = new javax.swing.JComboBox<>();
        proceduraComboBox = new javax.swing.JComboBox<>();

        istr1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr1.setText("Di sequito verranno inseriti i dati relativi alla variabile da aggiungere ad un Trigger o Procedura ");

        triggerLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        triggerLabel.setText("Trigger  ");

        procLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        procLabel.setText("Procedura");

        nomeVarLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeVarLabel.setText("Nome Variabile  *");

        nomeVarTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tipoVarComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoVarComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NUMBER", "INTEGER", "FLOAT", "DOUBLE", "REAL", "CHAR", "VARCHAR", "BIT", "DATE" }));
        tipoVarComboBox.setSelectedIndex(-1);

        tipoLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tipoLabel.setText("Tipo Variabile *");

        campoObbligatorio.setText("*  Campo Obbligatorio");

        addVarButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        addVarButton.setText("Aggiungi Variabile");
        addVarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVarButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

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
                        .addComponent(campoObbligatorio)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istr1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(triggerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(procLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(proceduraComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tipoLabel)
                                    .addComponent(nomeVarLabel))
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nomeVarTextField)
                                    .addComponent(tipoVarComboBox, 0, 200, Short.MAX_VALUE)
                                    .addComponent(triggerComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(335, 335, 335))))
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(addVarButton)
                .addGap(68, 68, 68)
                .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(istr1)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(triggerLabel)
                    .addComponent(triggerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(procLabel)
                    .addComponent(proceduraComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeVarLabel)
                        .addGap(18, 18, 18)
                        .addComponent(tipoLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nomeVarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tipoVarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(annullaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(addVarButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(campoObbligatorio)
                .addGap(48, 48, 48))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addVarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVarButtonActionPerformed
        try{
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento di una variabile\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);

                pstmt.setString(1, nomeVarTextField.getText());
                pstmt.setString(2, (String)tipoVarComboBox.getSelectedItem());
                if(modalita == TRIGGER)
                    pstmt.setInt(3, ricavaID(triggerComboBox.getSelectedItem().toString()));
                else
                    pstmt.setInt(3, ricavaID(proceduraComboBox.getSelectedItem().toString()));

                righeInserite = pstmt.executeUpdate();
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "La variabile " + nomeVarTextField.getText() + " è stata aggiunta", "Inserimento effettuato", JOptionPane.INFORMATION_MESSAGE);
                    pulisciInput();
                }
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_addVarButtonActionPerformed

    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addVarButton;
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbligatorio;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel nomeVarLabel;
    private javax.swing.JTextField nomeVarTextField;
    private javax.swing.JLabel procLabel;
    private javax.swing.JComboBox<String> proceduraComboBox;
    private javax.swing.JLabel tipoLabel;
    private javax.swing.JComboBox<String> tipoVarComboBox;
    private javax.swing.JComboBox<String> triggerComboBox;
    private javax.swing.JLabel triggerLabel;
    // End of variables declaration//GEN-END:variables
}
