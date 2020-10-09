/**
 * file : InsertTrigger.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello utilizzato per l'inserimentio di Trigger e delle relative
 * variabili ed eccezioni nel database.
 * 
 * @author Luca Pastore
 * @version 2019
 */
public class InsertTrigger extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO trigger1(nomeTrigger, tempo, ForEachRow, condWhen, causa, bloccoCodice, proprietario, schema, oggettoTab, oggettoView) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private int righeInserite = 0;
    
    private InsertVariabili iVr;
    
    /**
     * Creates new form InsertTrigger
     */
    public InsertTrigger() {
        this.setSize(770,423);
        initComponents();
        
        tabellaComboBox.setEnabled(false);
        vistaComboBox.setEnabled(false);
        schemaTriggerComboBox.setEnabled(false);
    }

    //metodo utilizzato per azzerare i campi di input del pannello
    public void pulisciInput(){
        FERbuttonGroup.clearSelection();
        OGGbuttonGroup.clearSelection();
        causaComboBox.setSelectedIndex(-1);
        codTextArea.setText("");
        nomeTriggerTextField.setText("");
        proprTriggerComboBox.setSelectedIndex(-1);
        schemaTriggerComboBox.setSelectedIndex(-1);
        schemaTriggerComboBox.removeAllItems();
        tabellaComboBox.setSelectedIndex(-1);
        tempoComboBox.setSelectedIndex(-1);
        vistaComboBox.setSelectedIndex(-1);
        whenTextField.setText("");
    }
    
    private boolean campiVuoti(){
        return causaComboBox.getSelectedIndex() == -1 || codTextArea.getText().equals("") || nomeTriggerTextField.getText().equals("") ||
               proprTriggerComboBox.getSelectedIndex() == -1 || schemaTriggerComboBox.getSelectedIndex() == -1 ||
               tempoComboBox.getSelectedIndex() == -1 || (tabellaComboBox.getSelectedIndex()==-1 && vistaComboBox.getSelectedIndex()==-1); 
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo che riempe la proprTriggerComboBox con gli usernsme degli utenti che hanno i permessi necessari per la creazione di un trigger
    private void riempiProprTriggerComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT U.username FROM utente U JOIN compRuoloSistema C ON U.ruolo = C.ID_ruolo WHERE C.ID_privSis = 'ALL PRIVILEGIES' OR C.ID_privSis = 'CREATE TRIGGER'";
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                proprTriggerComboBox.addItem(rst.getString(1));
            }
            
            proprTriggerComboBox.setSelectedIndex(-1);
            
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
    
    //ricava il nome della tabella/vista scelta nella tabellaComboBox/vistaComboBox
    private String ricavaNome(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(i+1);
        
        return schema;
    }
    
    //ricava lo schema della tabell/vista scelta nella tabellaComboBox/vistacomboBox
    private String ricavaSchema(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(0, i);
        
        return schema;
    }
    
    //riempe la vistaComboBox con le viste presenti nel database
    private void riempiVistaComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT V.schema, V.nomeVista FROM vista V";
        
        vistaComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le viste saranno presentate nella vistaComboBox secondo il modello: nomeSchema.nomeVista
                vistaComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            vistaComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //riempe le 3 comboBox presenti nel pannello
    public void riempiComboBox(){
        riempiTabellaComboBox();
        riempiVistaComboBox();
        riempiProprTriggerComboBox();
    }
    
    //restituisce il nome del trigger ricavato da nomeTriggerTextField
    public String getNomeTrigger(){
        return nomeTriggerTextField.getText();
    }
    
    //restituisce lo schema di appartenenza del trigger ricavato da schemaTriggerTextField
    public String getSchematrigger(){
        return (String)schemaTriggerComboBox.getSelectedItem();
    }
    
    //utilizzato per ricavare l'ID dell'elemento trigger appena inserito
    public int getIDtrigger(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT T.ID_trigger FROM trigger1 T WHERE T.NomeTrigger = '" + nomeTriggerTextField.getText() + "' AND T.schema = '" + (String)schemaTriggerComboBox.getSelectedItem()+"'";
        int ID = -1;
        
        try{
            if(!nomeTriggerTextField.getText().equals("") && schemaTriggerComboBox.getSelectedIndex() != -1){
                stmt = Database.getDefaultConnection().createStatement();
                rst = stmt.executeQuery(query);
            
                while(rst.next()){
                    ID = rst.getInt(1);
                }
                
                stmt.close();
            }
        }catch(SQLException e){
            mostraErrore(e);
        }
        
        return ID;
    }
    
    //utilizzata per ricavare l'id di una vista o di una tabella
    private int ricavaID(String id_col, String tabella, String col_nome, String nome, String schema){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT " + id_col + " FROM " + tabella + " WHERE " + col_nome + " = '" + nome + "' AND schema = '" + schema + "'"  ;
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
    
    //metodo utilizzato per abilitare / disabilitare il tasto annulla del pannello durante gli inserimenti nelle Dialog richiamate
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

        FERbuttonGroup = new javax.swing.ButtonGroup();
        OGGbuttonGroup = new javax.swing.ButtonGroup();
        istruzioni1 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        nomeTriggerLabel = new javax.swing.JLabel();
        nomeTriggerTextField = new javax.swing.JTextField();
        tempoLabel = new javax.swing.JLabel();
        tempoComboBox = new javax.swing.JComboBox<>();
        forEachRowLabel = new javax.swing.JLabel();
        siRadioButton = new javax.swing.JRadioButton();
        noRadioButton = new javax.swing.JRadioButton();
        whenLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        codTextArea = new javax.swing.JTextArea();
        causaLabel = new javax.swing.JLabel();
        causaComboBox = new javax.swing.JComboBox<>();
        oggLabel = new javax.swing.JLabel();
        tabellaRadioButton = new javax.swing.JRadioButton();
        vistaRadioButton = new javax.swing.JRadioButton();
        tabellaComboBox = new javax.swing.JComboBox<>();
        vistaComboBox = new javax.swing.JComboBox<>();
        codLabel = new javax.swing.JLabel();
        whenTextField = new javax.swing.JTextField();
        proprtriggerLabel = new javax.swing.JLabel();
        proprTriggerComboBox = new javax.swing.JComboBox<>();
        schemaTriggerLabel = new javax.swing.JLabel();
        schemaTriggerComboBox = new javax.swing.JComboBox<>();
        campoObb = new javax.swing.JLabel();
        insertTriggerButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        istruzioni4 = new javax.swing.JLabel();

        istruzioni1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni1.setText("Di seguito verranno inseriti i dati del trigger che si desidera inserire.  Dopo aver effettuato l'inserimento");

        istruzioni2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni2.setText("verrà aperta una finestra di dialogo che permetterà di inserire le eventuali variabili usate nel trigger. ");

        nomeTriggerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nomeTriggerLabel.setText("Nome Trigger  *");

        nomeTriggerTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tempoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tempoLabel.setText("Tempo Trigger  *");

        tempoComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tempoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BEFORE", "AFTER", "INSTEAD OF" }));
        tempoComboBox.setSelectedIndex(-1);
        tempoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempoComboBoxActionPerformed(evt);
            }
        });

        forEachRowLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        forEachRowLabel.setText("For Each Row  *");

        FERbuttonGroup.add(siRadioButton);
        siRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        siRadioButton.setText("SI");

        FERbuttonGroup.add(noRadioButton);
        noRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        noRadioButton.setText("NO");

        whenLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        whenLabel.setText("Condizione When");

        codTextArea.setColumns(20);
        codTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        codTextArea.setRows(5);
        jScrollPane1.setViewportView(codTextArea);

        causaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        causaLabel.setText("Causa trigger  *");

        causaComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        causaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DELETE", "UPDATE", "INSERT", "CREATE", "DROP", "ALTER" }));
        causaComboBox.setSelectedIndex(-1);

        oggLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oggLabel.setText("Oggetto Trigger  *");

        OGGbuttonGroup.add(tabellaRadioButton);
        tabellaRadioButton.setText("Tabella");
        tabellaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabellaRadioButtonActionPerformed(evt);
            }
        });

        OGGbuttonGroup.add(vistaRadioButton);
        vistaRadioButton.setText("Vista");
        vistaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vistaRadioButtonActionPerformed(evt);
            }
        });

        tabellaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabellaComboBoxActionPerformed(evt);
            }
        });

        vistaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vistaComboBoxActionPerformed(evt);
            }
        });

        codLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        codLabel.setText("Blocco Codice  *");

        whenTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        proprtriggerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        proprtriggerLabel.setText("Proprietario  *");

        proprTriggerComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        schemaTriggerLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        schemaTriggerLabel.setText("Schema  *");

        schemaTriggerComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campoObb.setText("*   Campo Obbligatorio");

        insertTriggerButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertTriggerButton.setText("Inserisci Trigger");
        insertTriggerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTriggerButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        istruzioni4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        istruzioni4.setText("Nella casella Proprietario verranno mostrati solo gli utenti con i permessi necessari alla creazione.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istruzioni1)
                            .addComponent(istruzioni2))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(istruzioni4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(forEachRowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nomeTriggerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tempoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(whenLabel, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(oggLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(causaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(siRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(noRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                            .addComponent(tempoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nomeTriggerTextField)
                                            .addComponent(whenTextField)
                                            .addComponent(causaComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(proprtriggerLabel)
                                                    .addComponent(schemaTriggerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(proprTriggerComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(schemaTriggerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(campoObb)
                                                .addGap(78, 78, 78))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(tabellaComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(tabellaRadioButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(vistaComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(vistaRadioButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(codLabel)
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(insertTriggerButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(annullaButton))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 37, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(istruzioni1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(istruzioni2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(istruzioni4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomeTriggerLabel)
                                    .addComponent(nomeTriggerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tempoLabel)
                                    .addComponent(tempoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(proprtriggerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(schemaTriggerLabel))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(28, 28, 28)
                                    .addComponent(schemaTriggerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(proprTriggerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(forEachRowLabel)
                            .addComponent(siRadioButton)
                            .addComponent(noRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(whenLabel)
                            .addComponent(whenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(campoObb)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(causaLabel)
                    .addComponent(causaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oggLabel)
                    .addComponent(tabellaRadioButton)
                    .addComponent(vistaRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabellaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vistaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertTriggerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(annullaButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //inserimento di un elemento trigger nel database e reindirizzamento ad inserimento variabili
    private void insertTriggerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertTriggerButtonActionPerformed
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del trigger\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);
                
                pstmt.setString(1, nomeTriggerTextField.getText());
                pstmt.setString(2, (String)tempoComboBox.getSelectedItem());
                
                if(siRadioButton.isSelected())  pstmt.setString(3, "SI");
                else    pstmt.setString(3, "NO");
                
                if(!whenTextField.getText().equals(""))
                    pstmt.setString(4, whenTextField.getText());
                else
                    pstmt.setString(4, null);
                
                pstmt.setString(5, (String)causaComboBox.getSelectedItem());
                pstmt.setString(6, codTextArea.getText());
                pstmt.setString(7, (String)proprTriggerComboBox.getSelectedItem());
                pstmt.setString(8, (String)schemaTriggerComboBox.getSelectedItem());
                
                if(tabellaRadioButton.isSelected()){
                    pstmt.setInt(9, ricavaID("ID_tabella", "tabella", "nomeTabella", ricavaNome((String)tabellaComboBox.getSelectedItem()), (String)schemaTriggerComboBox.getSelectedItem()));
                    pstmt.setString(10, null);
                }else{
                    pstmt.setInt(10, ricavaID("ID_view", "vista", "nomeVista", ricavaNome((String)vistaComboBox.getSelectedItem()), (String)schemaTriggerComboBox.getSelectedItem()));
                    pstmt.setString(9, null);
                }
                
                righeInserite = pstmt.executeUpdate();
                
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il trigger " + nomeTriggerTextField.getText() + " è stata inserita correttamente !", "Inserimento Trigger effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    iVr = new InsertVariabili(this);
                } 
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertTriggerButtonActionPerformed

    //abbilita la tabellaComboBox per la scelta della tabella su cui agisce il trigger
    private void tabellaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabellaRadioButtonActionPerformed
        tabellaComboBox.setEnabled(true);
        vistaComboBox.setEnabled(false);
        tempoComboBox.setSelectedIndex(1);
    }//GEN-LAST:event_tabellaRadioButtonActionPerformed

    //abbilita la vistaComboBox per la scelta della vista su cui agisce il trigger
    private void vistaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vistaRadioButtonActionPerformed
        tabellaComboBox.setEnabled(false);
        vistaComboBox.setEnabled(true);
        tempoComboBox.setSelectedItem("INSTEAD OF");
    }//GEN-LAST:event_vistaRadioButtonActionPerformed

    //annulla l'inserimento del trigger
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    private void tempoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempoComboBoxActionPerformed
        if(tempoComboBox.getSelectedItem() == "INSTEAD OF"){
            vistaRadioButton.setEnabled(true);
            vistaRadioButton.setSelected(true);
            tabellaRadioButton.setEnabled(false);
            tabellaComboBox.setEnabled(false);
            vistaComboBox.setEnabled(true);
        }else{
            tabellaRadioButton.setEnabled(true);
            tabellaRadioButton.setSelected(true);
            vistaRadioButton.setEnabled(false);
            tabellaComboBox.setEnabled(true);
            vistaComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_tempoComboBoxActionPerformed

    private void tabellaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabellaComboBoxActionPerformed
        String schema;
        
        if(tabellaRadioButton.isSelected()){
            schema = ricavaSchema((String)tabellaComboBox.getSelectedItem());
            schemaTriggerComboBox.removeAllItems();
            schemaTriggerComboBox.addItem(schema);
            schemaTriggerComboBox.setSelectedIndex(0);
            schemaTriggerComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_tabellaComboBoxActionPerformed

    private void vistaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vistaComboBoxActionPerformed
        String schema;
        
        if(vistaRadioButton.isSelected()){
            schema = ricavaSchema((String)vistaComboBox.getSelectedItem());
            schemaTriggerComboBox.removeAllItems();
            schemaTriggerComboBox.addItem(schema);
            schemaTriggerComboBox.setSelectedIndex(0);
            schemaTriggerComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_vistaComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup FERbuttonGroup;
    private javax.swing.ButtonGroup OGGbuttonGroup;
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObb;
    private javax.swing.JComboBox<String> causaComboBox;
    private javax.swing.JLabel causaLabel;
    private javax.swing.JLabel codLabel;
    private javax.swing.JTextArea codTextArea;
    private javax.swing.JLabel forEachRowLabel;
    private javax.swing.JButton insertTriggerButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton noRadioButton;
    private javax.swing.JLabel nomeTriggerLabel;
    private javax.swing.JTextField nomeTriggerTextField;
    private javax.swing.JLabel oggLabel;
    private javax.swing.JComboBox<String> proprTriggerComboBox;
    private javax.swing.JLabel proprtriggerLabel;
    private javax.swing.JComboBox<String> schemaTriggerComboBox;
    private javax.swing.JLabel schemaTriggerLabel;
    private javax.swing.JRadioButton siRadioButton;
    private javax.swing.JComboBox<String> tabellaComboBox;
    private javax.swing.JRadioButton tabellaRadioButton;
    private javax.swing.JComboBox<String> tempoComboBox;
    private javax.swing.JLabel tempoLabel;
    private javax.swing.JComboBox<String> vistaComboBox;
    private javax.swing.JRadioButton vistaRadioButton;
    private javax.swing.JLabel whenLabel;
    private javax.swing.JTextField whenTextField;
    // End of variables declaration//GEN-END:variables
}