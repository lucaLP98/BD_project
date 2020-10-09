/*
 * file : InsertVincolo.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Jpanel utilizzato per l'inserimento di un elemento vincolo nel database
 * dopo aver inserito il vincolo verra aperta una JDialog per l'inserimento delle colonne
 * sulle quali opera il vincolo.
 * 
 * Se il vincolo è di tipo "FOREIGN KEY", dopo l'inserimento delle colonne verra 
 * aperto un nuovo JPanel per inserire i dati aggiuntivi relativi a tale vincolo,
 * seguito da un'ulteriore JDialog per l'inserimento delle colonne della seconda tabella
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertVincolo extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private PreparedStatement pstmt2 = null;
    private final String queryIns = "INSERT INTO vincolo(nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(?, ?, ?, ?, ?, ?)";
    private final String queryIns2 = "INSERT INTO chiaveEsterna(regCanc, ID_vincolo, ID_tabRef) VALUES(?, ?, ?)";
    private int righeInserite = 0;
    private int righeInserite2 = 0;
    
    private InsertColonneVincolo iCV;
    
    private int idTab;
    private int idTabRef;
    
    /**
     * Creates new form InsertVincolo
     */
    public InsertVincolo() {
        this.setSize(770,423);
        initComponents();
        
        checkTextArea.setEnabled(false);
        noActionRadioButton.setEnabled(false);
        cascadeRadioButton.setEnabled(false);
        tabRefComboBox.setEnabled(false);
    }
    
    //metodo che azzera i campi di input
    public void pulisciInput(){
        nomeVincoloTextField.setText("");
        tipoComboBox.setSelectedIndex(-1);
        checkTextArea.setText("");
        checkTextArea.setEditable(false);
        statoButtonGroup.clearSelection();
        tabellaComboBox.setSelectedIndex(-1);
        tabRefComboBox.removeAllItems();
        tabRefComboBox.setSelectedIndex(-1);
        proprComboBox.setSelectedIndex(-1);
        idTab = -1;
        idTabRef = -1;
    }
    
    //controlla se i campi di input obbligatori sono stati riempiti tutti
    private boolean campiVuoti(){
        return nomeVincoloTextField.getText().equals("") || tipoComboBox.getSelectedIndex()==-1 || 
               ("CHECK".equals((String)tipoComboBox.getSelectedItem()) && checkTextArea.getText().equals("")) ||
               (!abilitatoRadioButton.isSelected() && !disabilitatoRadioButton.isSelected()) ||
                tabellaComboBox.getSelectedIndex() == -1 || proprComboBox.getSelectedIndex() == -1;
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo che riempe la proprComboBox con gli usernsme degli utenti che hanno i permessi necessari per la creazione di un Vincolo
    private void riempiProprComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "(SELECT U.username FROM utente U JOIN compRuoloSistema C ON U.ruolo = C.ID_ruolo WHERE C.ID_privSis = 'ALL PRIVILEGIES' ) union(SELECT U.username FROM utente U JOIN compRuoloOggetto O ON U.ruolo = O.ID_ruolo WHERE O.ID_privOgg = 'CREATE TABLE' OR O.ID_privOgg = 'ALTER')";
        
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
    
    //riempe la tabellaComboBox con le tabelle presenti nel database
    private void riempiTabellaComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT T.schema, T.nomeTabella FROM tabella T";
        
        tabellaComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le tabelle saranno presentate nella tabellaComboBox secondo il modello: nomeSchema.nomeTabella
                tabellaComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            tabellaComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //riempe la tabRefComboBox con le tabelle presenti nel database
    private void riempiTabRefComboBox(){
        Statement stmt;
        ResultSet rst;
        String query;
        
        tabRefComboBox.removeAllItems();
        try{
            if(tabellaComboBox.getSelectedIndex() != -1){
                query = "SELECT T.schema, T.nomeTabella FROM tabella T WHERE T.schema = '" + ricavaSchema((String)tabellaComboBox.getSelectedItem()) + "'";
                
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
            
                while(rst.next()){
                    //le tabelle saranno presentate nella tabRefComboBox secondo il modello: nomeSchema.nomeTabella
                    tabRefComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
                }
                tabRefComboBox.setSelectedIndex(-1);
                
                stmt.close();
            }else{
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento della tabella coinvolta nell'associazione\nselezionare una tabella che contenga il vincolo !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //ricava il nome della tabella scelta nella tabellaComboBox
    private String ricavaNome(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(i+1);
        
        return schema;
    }
    
    //ricava lo schema della tabell scelta nella tabellaComboBox
    private String ricavaSchema(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(0, i);
        
        return schema;
    }
    
    //ricava l'id della tabella scelta nella tabellaComboBox / tabRefComboBox
    public int ricavaIDtab(String nomeTab, String schemaTab){
        String tab = nomeTab;
        String schema = schemaTab;
        int id = -1;
        
        Statement stmt; 
        ResultSet rst;
        String query;
    
        if(tabellaComboBox.getSelectedIndex() != -1){
            query = "SELECT T.ID_tabella FROM tabella T WHERE T.nomeTabella = '" + tab + "' AND T.schema = '" + schema + "'";
        
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
        }
        
        return id;
    }

    //riempe le 2 comboBox presenti nel pannello
    public void riempiComboBox(){
        riempiTabellaComboBox();
        riempiProprComboBox();
    }
    
    //restituisce il nome del vincolo attualmente inserito ricavandolo dalla nomeVincoloTextField
    public String getNomeVincolo(){
        String nome;
        
        if(nomeVincoloTextField.getText().equals(""))
            nome = "";
        else
            nome = nomeVincoloTextField.getText();
        
        return nome;
    }
    
    //restituisce l'id della tabella a cui appartiene il vincolo
    public int getIDtab(){
        int id = -1;
        
        if(tabellaComboBox.getSelectedIndex() != -1){
            id = idTab;
        }
        
        return id;
    }
    
    //restituisce l'id della seconda tabella coinvolta nel vincolo di foreign key
    public int getIDtabRef(){
        int id = -1;
        
        if(tabRefComboBox.getSelectedIndex() != -1 && "FOREIGN KEY".equals((String)tipoComboBox.getSelectedItem())){
            id = idTabRef;
        }
        
        return id;
    }
    
    public String getTipoVincolo(){
        return (String)tipoComboBox.getSelectedItem();
    }
    
    //utilizzato per ricavare l'ID dell'elemento vincolo appena inserito
    public int getIDvincolo(){
        Statement stmt;
        ResultSet rst;
        String query;
        int id = -1;
        
        try{
            if(!nomeVincoloTextField.getText().equals("") && tabellaComboBox.getSelectedIndex() != -1){
                query = "SELECT V.ID_vincolo FROM vincolo V WHERE V.NomeVincolo = '" + nomeVincoloTextField.getText() + "' AND V.tabella = '" + idTab +"'";
                
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
            
                while(rst.next()){
                    id = rst.getInt(1);
                }
                
                stmt.close();
            }
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return id;
    }
    
    public void enabledAnnullaButton(boolean t){
        annullaButton.setEnabled(t);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statoButtonGroup = new javax.swing.ButtonGroup();
        regCancButtonGroup = new javax.swing.ButtonGroup();
        istr1 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        istr3 = new javax.swing.JLabel();
        nomeVincoloLabel = new javax.swing.JLabel();
        nomeVincoloTextField = new javax.swing.JTextField();
        tipoLabel = new javax.swing.JLabel();
        tipoComboBox = new javax.swing.JComboBox<>();
        condCheckLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        checkTextArea = new javax.swing.JTextArea();
        istr4 = new javax.swing.JLabel();
        statoLabel = new javax.swing.JLabel();
        abilitatoRadioButton = new javax.swing.JRadioButton();
        disabilitatoRadioButton = new javax.swing.JRadioButton();
        proprietarioLabel = new javax.swing.JLabel();
        proprComboBox = new javax.swing.JComboBox<>();
        campoObbl = new javax.swing.JLabel();
        insertButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        tabellaLabel = new javax.swing.JLabel();
        tabellaComboBox = new javax.swing.JComboBox<>();
        regCancLabel = new javax.swing.JLabel();
        noActionRadioButton = new javax.swing.JRadioButton();
        cascadeRadioButton = new javax.swing.JRadioButton();
        tabRefLabel = new javax.swing.JLabel();
        tabRefComboBox = new javax.swing.JComboBox<>();
        istr5 = new javax.swing.JLabel();

        istr1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istr1.setText("Di seguito verranno inserite tutte le informazioni per l'inserimento di un vincolo nel database.");

        istr2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istr2.setText("Una volta inserito il vincolo verra chiesto su quali colonne della tabella selezionata lavore il vincolo .");

        istr3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istr3.setText("Nel campo \"Proprietario\" verranno mostrati solo gli utenti con i permessi necessari ad aggiungere un vincolo.");

        nomeVincoloLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nomeVincoloLabel.setText("Nome Vincolo  *");

        nomeVincoloTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tipoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoLabel.setText("Tipo Vincolo  *");

        tipoComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tipoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CHECK", "PRIMARY KEY", "UNIQUE", "FOREIGN KEY" }));
        tipoComboBox.setSelectedIndex(-1);
        tipoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoComboBoxActionPerformed(evt);
            }
        });

        condCheckLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        condCheckLabel.setText("Condizione Check");

        checkTextArea.setColumns(20);
        checkTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        checkTextArea.setRows(5);
        jScrollPane1.setViewportView(checkTextArea);

        istr4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istr4.setText("Il campo \"Condizione Check\" va riempito obbligatoriamente se \"Tipo Vincolo\" è uguale a CHECK.");

        statoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        statoLabel.setText("Stato  *");

        statoButtonGroup.add(abilitatoRadioButton);
        abilitatoRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        abilitatoRadioButton.setSelected(true);
        abilitatoRadioButton.setText("ABILITATO");
        abilitatoRadioButton.setActionCommand("");

        statoButtonGroup.add(disabilitatoRadioButton);
        disabilitatoRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        disabilitatoRadioButton.setText("DISABILITATO");

        proprietarioLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        proprietarioLabel.setText("Porprietario  *");

        proprComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campoObbl.setText("*  Campo Obbligatorio");

        insertButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertButton.setText("Inserisci Vincolo");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        tabellaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabellaLabel.setText("Tabella  *");

        tabellaComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabellaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabellaComboBoxActionPerformed(evt);
            }
        });

        regCancLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        regCancLabel.setText("Regola di Cancellazione");

        regCancButtonGroup.add(noActionRadioButton);
        noActionRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        noActionRadioButton.setText("NO ACTION");

        regCancButtonGroup.add(cascadeRadioButton);
        cascadeRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cascadeRadioButton.setText("CASCADE");

        tabRefLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabRefLabel.setText("Tabella Associata");

        tabRefComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabRefComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabRefComboBoxActionPerformed(evt);
            }
        });

        istr5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istr5.setText("Nel campo Tabella Associata va messa la tabella a cui fa riferimento un vincolo di tipo FOREIGN KEY.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istr1)
                            .addComponent(istr2)
                            .addComponent(istr3)
                            .addComponent(istr4)
                            .addComponent(tipoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoObbl)
                                .addGap(58, 58, 58)
                                .addComponent(insertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nomeVincoloLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(condCheckLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(statoLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proprietarioLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tabellaLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regCancLabel, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(79, 79, 79)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tabellaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(abilitatoRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(disabilitatoRadioButton))
                                    .addComponent(nomeVincoloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tipoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(noActionRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(cascadeRadioButton))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tabRefComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(proprComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(istr5))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabRefLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(istr1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomeVincoloLabel)
                    .addComponent(nomeVincoloTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tipoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(condCheckLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tipoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statoLabel)
                    .addComponent(abilitatoRadioButton)
                    .addComponent(disabilitatoRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabellaLabel)
                    .addComponent(tabellaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(proprietarioLabel)
                    .addComponent(proprComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(regCancLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(noActionRadioButton)
                        .addComponent(cascadeRadioButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabRefLabel)
                    .addComponent(tabRefComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(insertButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(annullaButton))
                    .addComponent(campoObbl))
                .addGap(0, 16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tipoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoComboBoxActionPerformed
        if(tipoComboBox.getSelectedItem() == null){
            checkTextArea.setEnabled(false);
            noActionRadioButton.setEnabled(false);
            cascadeRadioButton.setEnabled(false);
            tabRefComboBox.setEnabled(false);
        }else switch ((String)tipoComboBox.getSelectedItem()) {
            case "CHECK":
                checkTextArea.setEnabled(true);
                noActionRadioButton.setEnabled(false);
                cascadeRadioButton.setEnabled(false);
                tabRefComboBox.setEnabled(false);
                break;
            case "FOREIGN KEY":
                checkTextArea.setEnabled(false);
                noActionRadioButton.setEnabled(true);
                cascadeRadioButton.setEnabled(true);
                tabRefComboBox.setEnabled(true);
                break;
            default:
                checkTextArea.setEnabled(false);
                noActionRadioButton.setEnabled(false);
                cascadeRadioButton.setEnabled(false);
                tabRefComboBox.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_tipoComboBoxActionPerformed

    //annulla l'inserimento di un vincolo
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    //inserimento di un vincolo nel database
    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del vincolo\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);
                
                pstmt.setString(1, nomeVincoloTextField.getText());
                pstmt.setString(2, (String)tipoComboBox.getSelectedItem());
                
                if("CHECK".equals((String)tipoComboBox.getSelectedItem())){
                    pstmt.setString(3, checkTextArea.getText());
                }else{
                    pstmt.setString(3, null);
                }
                
                if(abilitatoRadioButton.isSelected()){
                    pstmt.setString(4, "ABILITATO");
                }else{
                    pstmt.setString(4, "DISABILITATO");
                }
                
                pstmt.setInt(5, idTab);
                pstmt.setString(6, (String)proprComboBox.getSelectedItem());
                
                righeInserite = pstmt.executeUpdate();
                if(righeInserite != 0){
                    if("FOREIGN KEY".equals((String)tipoComboBox.getSelectedItem()) && tabRefComboBox.getSelectedIndex() != -1){
                        pstmt2 = Database.getDefaultConnection().prepareStatement(queryIns2);
                            
                        if(noActionRadioButton.isSelected())
                            pstmt2.setString(1, "NO ACTION");
                        else
                            pstmt2.setString(1, "CASCADE");
                            
                        pstmt2.setInt(2, getIDvincolo());
                        pstmt2.setInt(3, idTabRef);
                        
                        righeInserite2 = pstmt2.executeUpdate();
                        if(righeInserite2 != 0){
                            JOptionPane.showMessageDialog(this, "Il vincolo di FOREIGN KEY: " + nomeVincoloTextField.getText() + " è stato inserito correttamente !\nOra verrà aperta una finestra di dialogo che permetterà\nl'inserimento delle colonne della prima tabella\nsulle quali agisce il vincolo inserito.", "Inserimento Trigger FOREIGN KEY effettuato", JOptionPane.INFORMATION_MESSAGE);
                            iCV = new InsertColonneVincolo(this);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "Il vincolo " + nomeVincoloTextField.getText() + " è stato inserito correttamente !\nOra verrà aperta una finestra di dialogo che permetterà\nl'inserimento delle colonne sulle quali agisce il vincolo inserito.", "Inserimento Trigger effettuato", JOptionPane.INFORMATION_MESSAGE);                         
                        iCV = new InsertColonneVincolo(this);
                    }
                    
                } 
                
                if(pstmt != null) pstmt.close();
                if(pstmt2 != null) pstmt2.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertButtonActionPerformed

    //ricava l'id della tabella attualmente scelta dalla tabellaComboBox
    private void tabellaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabellaComboBoxActionPerformed
        if(tabellaComboBox.getSelectedIndex() != -1 && tabellaComboBox.getSelectedItem() != null){
            idTab = ricavaIDtab(ricavaNome((String)tabellaComboBox.getSelectedItem()), ricavaSchema((String)tabellaComboBox.getSelectedItem()));
        
            if("FOREIGN KEY".equals((String)tipoComboBox.getSelectedItem())){
                riempiTabRefComboBox();
            }
        } 
    }//GEN-LAST:event_tabellaComboBoxActionPerformed

    //ricava l'id della tabella attualmente scelta dalla tabRefComboBox
    private void tabRefComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabRefComboBoxActionPerformed
        if(tabRefComboBox.getSelectedIndex() != -1 && tabRefComboBox.getSelectedItem() != null){
            idTabRef = ricavaIDtab(ricavaNome((String)tabRefComboBox.getSelectedItem()), ricavaSchema((String)tabRefComboBox.getSelectedItem()));
        }   
    }//GEN-LAST:event_tabRefComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton abilitatoRadioButton;
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbl;
    private javax.swing.JRadioButton cascadeRadioButton;
    private javax.swing.JTextArea checkTextArea;
    private javax.swing.JLabel condCheckLabel;
    private javax.swing.JRadioButton disabilitatoRadioButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JLabel istr4;
    private javax.swing.JLabel istr5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton noActionRadioButton;
    private javax.swing.JLabel nomeVincoloLabel;
    private javax.swing.JTextField nomeVincoloTextField;
    private javax.swing.JComboBox<String> proprComboBox;
    private javax.swing.JLabel proprietarioLabel;
    private javax.swing.ButtonGroup regCancButtonGroup;
    private javax.swing.JLabel regCancLabel;
    private javax.swing.ButtonGroup statoButtonGroup;
    private javax.swing.JLabel statoLabel;
    private javax.swing.JComboBox<String> tabRefComboBox;
    private javax.swing.JLabel tabRefLabel;
    private javax.swing.JComboBox<String> tabellaComboBox;
    private javax.swing.JLabel tabellaLabel;
    private javax.swing.JComboBox<String> tipoComboBox;
    private javax.swing.JLabel tipoLabel;
    // End of variables declaration//GEN-END:variables
}
