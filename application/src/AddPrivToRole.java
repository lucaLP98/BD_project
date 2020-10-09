/**
 * file: AddPrivToRole.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Aggiunge un privilegio (gia esistente nel database) ad un ruolo
 * andando a popolare le tabelle compRuoloSistema e compRuoloOggetto
 * 
 * @author I Pastore
 * @version 2019
 */
public class AddPrivToRole extends javax.swing.JPanel {
    
    private PreparedStatement pstmt = null;
    private final String queryInsPsis = "INSERT INTO compRuoloSistema(ID_ruolo, ID_privSis) VALUES(?, ?)";
    private final String queryInsPogg = "INSERT INTO compRuoloOggetto(ID_ruolo, ID_privOgg) VALUES(?, ?)";
    private int righeInserite = 0;
    
    /**
     * Creates new form AddPrivToRole
     */
    public AddPrivToRole() {
        this.setSize(770, 281);
        initComponents();
        
        creaSelectRoleComboBox();
        selPrivLabel.setVisible(false);
        istrLabel.setVisible(false);
        privSisComboBox.setVisible(false);
        privOggComboBox.setVisible(false);
        addPrivButton.setVisible(false);
        annullaButton.setVisible(false);
        privSisRadioButton.setVisible(false);
        privOggRadioButton.setVisible(false);
        tipoPrivLabel.setVisible(false);
        istrLabel.setVisible(false);
    }
    
    //rende visibili o invisibili i pulsanti "Aggiungi Privilegio" e "Annulla"
    private void setVisibleButton(boolean t){
        selPrivLabel.setVisible(t);
        addPrivButton.setVisible(t);
        annullaButton.setVisible(t);
    }

    //svuota tutti i campi di input del pannello
    public void pulisciInput(){
        privOggComboBox.setSelectedIndex(-1);
        privOggComboBox.removeAllItems();
        privSisComboBox.setSelectedIndex(-1);
        privSisComboBox.removeAllItems();
    }
    
    //aggiunge gli elementi 'ruolo' presenti nel database alla tabella selectRoleComboBox
    public void creaSelectRoleComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT nomeRuolo FROM ruolo";
        
        selectRoleComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                selectRoleComboBox.addItem(rst.getString(1));
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }finally{
            selectRoleComboBox.setSelectedIndex(-1);
        }
    }
    
    //metodo che cerca se un determinato privilegio è gia stato inserito nel database e aggiunto ad un ruolo
    private boolean cercaPriv(String colonnaPriv, String tabella, String ruolo, String privilegio){
        Statement stmt;
        ResultSet ris;
        String query = "SELECT " + colonnaPriv + " FROM " + tabella + " WHERE ID_ruolo = '" + ruolo + "' AND " + colonnaPriv + " = '" + privilegio +"'";
        boolean trovato = true;
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            ris = stmt.executeQuery(query);
            
            if(ris.next())
                trovato = false;
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return trovato;
    }
    
    //metodo che inserisce un privilegio di sistema nella privSisComboBox se questo non è gia presente nel database
    private void addItemToPrivSisComboBox(String ruolo, String privilegio){
        if(cercaPriv("ID_privSis", "compRuoloSistema", ruolo, privilegio))
            privSisComboBox.addItem(privilegio);
    }
    
    //metodo che inserisce un privilegio d'oggetto nella privOggComboBox se questo non è gia presente nel database
    private void addItemToPrivOggComboBox(String ruolo, String privilegio){
        if(cercaPriv("ID_privOgg", "compRuoloOggetto", ruolo, privilegio))
            privOggComboBox.addItem(privilegio);
    }
    
    //popola la privSisComboBox
    private void creaPrivSisComboBox(String ruolo){
        Statement stmt;
        ResultSet ris;
        String query = "SELECT nomePrivSist FROM privSistema";
        
        privSisComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            ris = stmt.executeQuery(query);
            
            while(ris.next()){
                addItemToPrivSisComboBox(ruolo, ris.getString(1));
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //popola la privOggComboBox
    private void creaPrivOggComboBox(String ruolo){
        Statement stmt;
        ResultSet ris;
        String query = "SELECT nomePrivOgg FROM privOggetto";
        
        privOggComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            ris = stmt.executeQuery(query);
            
            while(ris.next()){
                addItemToPrivOggComboBox(ruolo, ris.getString(1));
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento privilegio nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo utilizzato per effettuare aggiungere nel database di un privilegio di sistema ad un ruolo
    private void insertPrivSis(){
        try{ 
            if(privSisComboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(this, "Prima di aggiungere il privilegio al ruolo\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryInsPsis);            
            
                pstmt.setString(1, (String)selectRoleComboBox.getSelectedItem());
                pstmt.setString(2, (String)privSisComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il privilegio " + (String)privSisComboBox.getSelectedItem() + " è stato aggiunto al ruolo " +(String)selectRoleComboBox.getSelectedItem() + " correttamente", "Aggiunta Privilegio a ruolo effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    privSisComboBox.removeItem(privSisComboBox.getSelectedItem());
                }   
                pulisciInput();
                
                if(pstmt != null) pstmt.close();
            }   
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }
    
    //metodo utilizzato per effettuare aggiungere nel database di un privilegio d'oggetto ad un ruolo
    private void insertPrivOgg(){
        try{ 
            if(privOggComboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(this, "Prima di aggiungere il privilegio al ruolo\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryInsPogg);            
            
                pstmt.setString(1, (String)selectRoleComboBox.getSelectedItem());
                pstmt.setString(2, (String)privOggComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il privilegio " + (String)privOggComboBox.getSelectedItem() + " è stato aggiunto al ruolo " +(String)selectRoleComboBox.getSelectedItem() + " correttamente", "Aggiunta Privilegio a ruolo effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    privOggComboBox.removeItem(privOggComboBox.getSelectedItem());
                }   
                pulisciInput();
                
                if(pstmt != null) pstmt.close();
            }   
        }catch(SQLException e) {
            mostraErrore(e);
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

        typePrivButtonGroup = new javax.swing.ButtonGroup();
        selectRoleLabel = new javax.swing.JLabel();
        selectRoleComboBox = new javax.swing.JComboBox<>();
        selPrivLabel = new javax.swing.JLabel();
        tipoPrivLabel = new javax.swing.JLabel();
        privSisRadioButton = new javax.swing.JRadioButton();
        privOggRadioButton = new javax.swing.JRadioButton();
        privOggComboBox = new javax.swing.JComboBox<>();
        privSisComboBox = new javax.swing.JComboBox<>();
        addPrivButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        campoObbLabel = new javax.swing.JLabel();
        istrLabel = new javax.swing.JLabel();

        typePrivButtonGroup.add(privSisRadioButton);
        typePrivButtonGroup.add(privOggRadioButton);

        selectRoleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selectRoleLabel.setText("Inserire il Ruolo a cui si vuole aggiungere un privilegio *:");

        selectRoleComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        selectRoleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectRoleComboBoxActionPerformed(evt);
            }
        });

        selPrivLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selPrivLabel.setText("Seleziona il Privilegio *:");

        tipoPrivLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tipoPrivLabel.setText("Inserire il tipo di privilegio che si vuole inserire *:");

        privSisRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        privSisRadioButton.setText("Privilegio di Sistema");
        privSisRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privSisRadioButtonActionPerformed(evt);
            }
        });

        privOggRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        privOggRadioButton.setText("Privilegio d' Oggetto");
        privOggRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privOggRadioButtonActionPerformed(evt);
            }
        });

        privOggComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        privSisComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        addPrivButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        addPrivButton.setText("Aggiungi Privilegio");
        addPrivButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPrivButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        campoObbLabel.setText("*  Campo obligatorio");

        istrLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        istrLabel.setText("N.B. Nel menu a tendina seguente verranno mostrati solo i privilegi gia presenti nel database che non appartengono al ruolo selezionato.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selPrivLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addPrivButton)
                                .addGap(18, 18, 18)
                                .addComponent(annullaButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(privSisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(privOggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(165, 165, 165))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectRoleLabel)
                                .addGap(50, 50, 50)
                                .addComponent(selectRoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoObbLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tipoPrivLabel)
                                .addGap(35, 35, 35)
                                .addComponent(privSisRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(privOggRadioButton))
                            .addComponent(istrLabel))
                        .addGap(0, 29, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectRoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectRoleLabel))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoPrivLabel)
                    .addComponent(privSisRadioButton)
                    .addComponent(privOggRadioButton))
                .addGap(17, 17, 17)
                .addComponent(istrLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selPrivLabel)
                    .addComponent(privSisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(privOggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPrivButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(campoObbLabel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
     selezionato il RadioButton "Privilegio di Sisistema", viengono resi visibili:
     la ComboBox per la selezione del privilegio di sistema da aggiungere al Ruolo
     e i pulsanti "Aggiungi privilegio" e "Annullare"
    */
    private void privSisRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privSisRadioButtonActionPerformed
        creaPrivSisComboBox((String)selectRoleComboBox.getSelectedItem());
        istrLabel.setVisible(true);
        privSisComboBox.setVisible(true);
        privOggComboBox.setVisible(false);
        setVisibleButton(true);
    }//GEN-LAST:event_privSisRadioButtonActionPerformed

    /*
     selezionato il RadioButton "Privilegio d' Oggetto", viengono resi visibili:
     la ComboBox per la selezione del privilegio d'oggetto da aggiungere al Ruolo
     e i pulsanti "Aggiungi privilegio" e "Annullare"
    */
    private void privOggRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privOggRadioButtonActionPerformed
        creaPrivOggComboBox((String)selectRoleComboBox.getSelectedItem());
        istrLabel.setVisible(true);
        privSisComboBox.setVisible(false);
        privOggComboBox.setVisible(true);
        setVisibleButton(true);
    }//GEN-LAST:event_privOggRadioButtonActionPerformed

    private void addPrivButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPrivButtonActionPerformed
        if(privSisRadioButton.isSelected()){
            insertPrivSis();
        }else{
            insertPrivOgg();
        }
    }//GEN-LAST:event_addPrivButtonActionPerformed

     //metodo utilizzato per annullare l'aggiunta del privilegio al ruolo
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
        istrLabel.setVisible(false);
        selPrivLabel.setVisible(false);
        privSisComboBox.setVisible(false);
        privOggComboBox.setVisible(false);
        setVisibleButton(false);
    }//GEN-LAST:event_annullaButtonActionPerformed

    private void selectRoleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectRoleComboBoxActionPerformed
        tipoPrivLabel.setVisible(true);
        privSisRadioButton.setVisible(true);
        privOggRadioButton.setVisible(true);
        istrLabel.setVisible(true);
        
        pulisciInput();
    }//GEN-LAST:event_selectRoleComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPrivButton;
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.JLabel istrLabel;
    private javax.swing.JComboBox<String> privOggComboBox;
    private javax.swing.JRadioButton privOggRadioButton;
    private javax.swing.JComboBox<String> privSisComboBox;
    private javax.swing.JRadioButton privSisRadioButton;
    private javax.swing.JLabel selPrivLabel;
    private javax.swing.JComboBox<String> selectRoleComboBox;
    private javax.swing.JLabel selectRoleLabel;
    private javax.swing.JLabel tipoPrivLabel;
    private javax.swing.ButtonGroup typePrivButtonGroup;
    // End of variables declaration//GEN-END:variables
}
