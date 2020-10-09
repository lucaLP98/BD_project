/**
 * file: InsertColonna.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette l'aggiunta dell'elemento colonna nel database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertColonna extends javax.swing.JPanel {

    private boolean onlyItemCB = false;
    private InsertTabella tabella;
    private insColTab dialog;
    
    private PreparedStatement pstmt = null;
    private int righeInserite = 0;
    private String queryIns = "INSERT INTO colonna(nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, id_dominio) VALUES(?, ?, ?, ?, ?, ?, ?)";
    
    /**
     * Creates new form InsertColonna
     */
    public InsertColonna() {
        this.setSize(770, 423);
        initComponents();
        dominioComboBox.setEnabled(false);
        lungDatiSpinner.setEnabled(false);
    }
    
    //costruttore utilizzato in InsertTabella per aggiungere colonne a tabella appena inserita
    public InsertColonna(InsertTabella tab) {
        this();
        this.tabella = tab;
        annullaButton.setEnabled(false);
        dialog = new insColTab(tab);
        setOnlyItemComboBox(tab.getSchemaTab()+"."+tab.getNomeTab());    
        riempiDominioComboBox(tab.getSchemaTab());
    }
    
    //metodo utilizzato inserire nella VomboBox un'unica stringa (utilizzata in InsertTabella)
    private void setOnlyItemComboBox(String tabel){
        tabColComboBox.removeAllItems();
        tabColComboBox.addItem(tabel);
        tabColComboBox.setSelectedIndex(0);
        tabColComboBox.setEnabled(false);
        
        onlyItemCB = true;
    }
    
    //metodo che svuota le caselle di testo e reimposta i radioButton e le ComboBox
    public void pulisciInput(){
        defaultValTextField.setText("");
        nomeColonnaTextField.setText("");
        dominioComboBox.setSelectedIndex(-1);
        dominioComboBox.removeAllItems();
        tipoColComboBox.setSelectedIndex(-1);
        lungDatiSpinner.setValue(1);
        nulloColbuttonGroup.clearSelection();
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //riempe la tabColComboBox con le tabelle presenti nel database
    public void riempiTabColComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT T.schema, T.nomeTabella FROM tabella T";
        
        tabColComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le tabelle nella comboBox saranno mostrate secondo il modello: nomeSchema.nomeTabella
                //in quanto tabelle appartenenti a schemi diversi possono avere lo stesso nome
                tabColComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            tabColComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //ricava il nome della tabella selezionata nella tabColComboBox
    private String ricavaNomeTab(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(i+1);
        
        return schema;
    }
    
    //ricava il nome dello schema a cui appartiene la tabella scelta
    private String ricavaSchemaTab(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(0, i);
        
        return schema;
    }
    
    //ricava la chiave primaria id_tabella della tabella scelta nella tabColComboBox
    private int ricavaIDtab(String itemSelected){
        String tab = ricavaNomeTab(itemSelected);
        String schema = ricavaSchemaTab(itemSelected);
        int id = -1;
        
        Statement stmt; 
        ResultSet rst;
        String query = "SELECT T.ID_tabella FROM tabella T WHERE T.nomeTabella = '" + tab + "' AND T.schema = '" + schema + "'";
    
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                id = rst.getInt(1);
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return id;
    }
    
    //riempe la dominioComboBox con i domini appartenenti allo schema scelto
    private void riempiDominioComboBox(String schema){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT D.nomeDominio FROM dominio D WHERE D.schema = '" + schema + "'";
        
        dominioComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                dominioComboBox.addItem(rst.getString(1));
            }
            
            dominioComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //restituisce la chiave primaria id_dominio del dominio scelto nella dominioComboBox
    private int getIDdominio(String dom, String schema){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT D.ID_dominio FROM dominio D WHERE D.schema = '" + schema + "' AND D.nomeDominio = '" + dom + "'";
        int id = -1;
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                id = rst.getInt(1);
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return id;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nulloColbuttonGroup = new javax.swing.ButtonGroup();
        istruzioni1 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        istruzioni3 = new javax.swing.JLabel();
        nomeColonnaLabel = new javax.swing.JLabel();
        nomeColonnaTextField = new javax.swing.JTextField();
        tipoColLabel = new javax.swing.JLabel();
        tipoColComboBox = new javax.swing.JComboBox<>();
        lungDatiColLabel = new javax.swing.JLabel();
        lungDatiSpinner = new javax.swing.JSpinner();
        nulloColLabel = new javax.swing.JLabel();
        siRadioButton = new javax.swing.JRadioButton();
        noRadioButton = new javax.swing.JRadioButton();
        defaultValColLabel = new javax.swing.JLabel();
        defaultValTextField = new javax.swing.JTextField();
        tabColLabel = new javax.swing.JLabel();
        tabColComboBox = new javax.swing.JComboBox<>();
        dominioColLabel = new javax.swing.JLabel();
        dominioComboBox = new javax.swing.JComboBox<>();
        campoObLabel = new javax.swing.JLabel();
        insertColButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        istruzioni4 = new javax.swing.JLabel();

        nulloColbuttonGroup.add(siRadioButton);
        nulloColbuttonGroup.add(noRadioButton);

        istruzioni1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni1.setText("Inserire i dati relativi alla colonna che si desidera aggiungere al Database;");

        istruzioni2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni2.setText("Se la tabella ha come tipo un dominio definito dall' utente, selezionare \"DOMINIO\" nel campo \"Tipo\" ");

        istruzioni3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni3.setText("e selezionare il dominio dall'apposito menu a tendina.");

        nomeColonnaLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeColonnaLabel.setText("Nome Colonna *");

        nomeColonnaTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tipoColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tipoColLabel.setText("Tipo");

        tipoColComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoColComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NUMBER", "INTEGER", "FLOAT", "DOUBLE", "REAL", "CHAR", "VARCHAR", "BIT", "BLOB", "DATE", "TIMESTAMP", "INTERVAL", "DOMINIO" }));
        tipoColComboBox.setSelectedIndex(-1);
        tipoColComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoColComboBoxActionPerformed(evt);
            }
        });

        lungDatiColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lungDatiColLabel.setText("Lunghezza Dati");

        lungDatiSpinner.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lungDatiSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4000, 1));

        nulloColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nulloColLabel.setText("Nullo *");

        siRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        siRadioButton.setText("SI");

        noRadioButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        noRadioButton.setText("NO");

        defaultValColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        defaultValColLabel.setText("Valore di Default");

        defaultValTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tabColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabColLabel.setText("Tabella *");

        tabColComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabColComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabColComboBoxActionPerformed(evt);
            }
        });

        dominioColLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dominioColLabel.setText("Dominio");

        dominioComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dominioComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dominioComboBoxActionPerformed(evt);
            }
        });

        campoObLabel.setText("* Campo Obligatorio");

        insertColButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertColButton.setText("Inserisci Colonna");
        insertColButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertColButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        istruzioni4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni4.setText("Il campo Lunghezza Dati va compilato solo per i tipi VARCHAR e CHAR.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istruzioni2, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istruzioni1)
                            .addComponent(istruzioni3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tabColLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nomeColonnaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tipoColLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lungDatiColLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nulloColLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(defaultValColLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(dominioColLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(99, 99, 99)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(siRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(noRadioButton))
                                    .addComponent(nomeColonnaTextField)
                                    .addComponent(tipoColComboBox, 0, 220, Short.MAX_VALUE)
                                    .addComponent(lungDatiSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(defaultValTextField)
                                    .addComponent(tabColComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dominioComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoObLabel)
                                .addGap(102, 102, 102)
                                .addComponent(insertColButton, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(istruzioni4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(istruzioni1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(istruzioni2)
                .addGap(4, 4, 4)
                .addComponent(istruzioni3)
                .addGap(4, 4, 4)
                .addComponent(istruzioni4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeColonnaLabel)
                    .addComponent(nomeColonnaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoColLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoColComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lungDatiColLabel)
                    .addComponent(lungDatiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(siRadioButton)
                    .addComponent(noRadioButton)
                    .addComponent(nulloColLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultValColLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(defaultValTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabColLabel)
                    .addComponent(tabColComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dominioColLabel)
                    .addComponent(dominioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertColButton)
                    .addComponent(annullaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoObLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * metodo utilizzato per implementare i vincoli V4_1, V4_4 del database:
     * essi prevedono che il campo lunghezza dati sia abilitato solo nel caso in cui il campo tipo sia impostato su 
     * CHAR o VARCHAR e che il campo dominio sia abiliatto solo se nel campo tipo viene selezionato DOMINIO
    */
    private void tipoColComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoColComboBoxActionPerformed
        if(tipoColComboBox.getSelectedItem() != "DOMINIO")
            dominioComboBox.setEnabled(false);
        else
            dominioComboBox.setEnabled(true);
        
        if(tipoColComboBox.getSelectedItem() == "CHAR" || tipoColComboBox.getSelectedItem() == "VARCHAR")
            lungDatiSpinner.setEnabled(true);
        else
            lungDatiSpinner.setEnabled(false);
    }//GEN-LAST:event_tipoColComboBoxActionPerformed

    //controlla che siano stati riempiti tutti i campi di input obbligatori
    private boolean campiVuoti(){
        return nomeColonnaTextField.getText().equals("") || tipoColComboBox.getSelectedIndex() == -1 ||
               tabColComboBox.getSelectedIndex() == -1 || (siRadioButton.isSelected() == false && noRadioButton.isSelected() == false);
    }
    
    //inserisce un elemento colonna all'interno del database
    private void insertColButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertColButtonActionPerformed
        try{
            if(campiVuoti()){
                 JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento di una colonna\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);
                
                //imposto nome colonna
                pstmt.setString(1, nomeColonnaTextField.getText());
                
                //imposto tipo dati della colonna
                if("DOMINIO".equals((String)tipoColComboBox.getSelectedItem()))
                    pstmt.setString(2, null);
                else
                    pstmt.setString(2, (String)tipoColComboBox.getSelectedItem());
                
                //imposto lunghezza dati colonna se tipo = char o tipo = varchar
                if("CHAR".equals((String)tipoColComboBox.getSelectedItem()) || "VARCHAR".equals((String)tipoColComboBox.getSelectedItem()) )
                    pstmt.setInt(3, (int)lungDatiSpinner.getValue());
                else
                    pstmt.setString(3, null);
                
                //imposto se la colonna può essere nulla o meno
                if(siRadioButton.isSelected())
                    pstmt.setString(4, "SI");
                else
                    pstmt.setString(4, "NO");
                
                //imposto valore di default
                if(defaultValTextField.getText().equals(""))
                    pstmt.setString(5, null);
                else
                    pstmt.setString(5, defaultValTextField.getText());
                
                //imposto id tabella a cui appartiene la colonna
                if(onlyItemCB)
                    pstmt.setInt(6, tabella.getIdTab());
                else    
                    pstmt.setInt(6, ricavaIDtab((String)tabColComboBox.getSelectedItem()));
                
                //imposto id dominio a cui è associato la colonna se tipo = dominio
                if(("DOMINIO".equals((String)tipoColComboBox.getSelectedItem()) ) && onlyItemCB){
                    pstmt.setInt(7, getIDdominio((String)dominioComboBox.getSelectedItem(), tabella.getSchemaTab()));
                }else if("DOMINIO".equals((String)tipoColComboBox.getSelectedItem()) ){
                    pstmt.setInt(7, getIDdominio((String)dominioComboBox.getSelectedItem(), ricavaSchemaTab((String)tabColComboBox.getSelectedItem())));
                }else{
                    pstmt.setString(7, null);
                }
                
                //inserisco la colonna nel database
                righeInserite = pstmt.executeUpdate();
                
                //controllo se l'inserimento è andato a buon fine
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "La colonna " + nomeColonnaTextField.getText() + " è stata aggiunta", "Inserimento effettuato", JOptionPane.INFORMATION_MESSAGE);
                    pulisciInput();
                    if(onlyItemCB){
                        dialog.setVisible(true);
                    }
                }
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertColButtonActionPerformed

    //metodo che annulla l'inserimento di una colonna
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    //inibisce la scelta del dominio se non è stata scelta prima la tabella a cui appartiene la colonna
    //(in modo tale da recuperare lo schema da cui andare a prendere i domini)
    private void dominioComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dominioComboBoxActionPerformed
        if(tabColComboBox.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Prima di selezionare un dominio, rimepire il campo Tabella", "Attenzione", JOptionPane.WARNING_MESSAGE);
            tabColComboBox.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_dominioComboBoxActionPerformed

    //riempe la dominioComboBox con i domini appartenenti allo schema a cui appartiene la tabella
    private void tabColComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabColComboBoxActionPerformed
        if(tabColComboBox.getSelectedIndex() != -1 && !onlyItemCB)
            riempiDominioComboBox(ricavaSchemaTab((String)tabColComboBox.getSelectedItem()));
    }//GEN-LAST:event_tabColComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObLabel;
    private javax.swing.JLabel defaultValColLabel;
    private javax.swing.JTextField defaultValTextField;
    private javax.swing.JLabel dominioColLabel;
    private javax.swing.JComboBox<String> dominioComboBox;
    private javax.swing.JButton insertColButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni3;
    private javax.swing.JLabel istruzioni4;
    private javax.swing.JLabel lungDatiColLabel;
    private javax.swing.JSpinner lungDatiSpinner;
    private javax.swing.JRadioButton noRadioButton;
    private javax.swing.JLabel nomeColonnaLabel;
    private javax.swing.JTextField nomeColonnaTextField;
    private javax.swing.JLabel nulloColLabel;
    private javax.swing.ButtonGroup nulloColbuttonGroup;
    private javax.swing.JRadioButton siRadioButton;
    private javax.swing.JComboBox<String> tabColComboBox;
    private javax.swing.JLabel tabColLabel;
    private javax.swing.JComboBox<String> tipoColComboBox;
    private javax.swing.JLabel tipoColLabel;
    // End of variables declaration//GEN-END:variables
}
