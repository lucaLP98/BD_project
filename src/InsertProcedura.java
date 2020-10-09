/*
 * file : InsertProcedura.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello utilizzato per l'inserimentio di una procedura/funzione e delle relative
 * variabili, eccezioni e parametri nel database.
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertProcedura extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO procedura(nomeProcedura, bloccoCodice, tipo, tipoRitorno, schema, proprietario) VALUES(?, ?, ?, ?, ?, ?)";
    private int righeInserite = 0;
    
    private InsertProcChiamata iPC; //JDialog richiamata al momento dell'inserimento delle procedure richiamate 
    
    /**
     * Creates new form InsertProcedura
     */
    public InsertProcedura() {
        this.setSize(770, 423);
        initComponents();
        tipoRitComboBox.setEnabled(false);
    }

    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo che riempe la proprProcComboBox con gli usernsme degli utenti che hanno i permessi necessari per la creazione di una procedura/funzione
    private void riempiProprProcComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT U.username FROM utente U JOIN compRuoloSistema C ON U.ruolo = C.ID_ruolo WHERE C.ID_privSis = 'ALL PRIVILEGIES' OR C.ID_privSis = 'CREATE PROCEDURE'";
        
        proprProcComboBox.removeAllItems();
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                proprProcComboBox.addItem(rst.getString(1));
            }
            
            proprProcComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //metodo utilizzato per riempire la schemaProcComboBox con gli schemi presenti nel database
    private void riempiSchemaProcComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT nomeSchema FROM schema1";
        
        schemaProcComboBox.removeAllItems();
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                schemaProcComboBox.addItem(rst.getString(1));
            }
            
            schemaProcComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //utilizzata per riempire le 2 combo box con gli schemi e gli utenti attualmente presenti nel database
    public void riempiComboBox(){
        riempiProprProcComboBox();
        riempiSchemaProcComboBox();
    }
    
    //utilizzato per azzerare i campi di input del pannello
    public void pulisciInput(){
        tipoRitComboBox.setSelectedIndex(-1);
        tipoButtonGroup.clearSelection();
        schemaProcComboBox.setSelectedIndex(-1);
        proprProcComboBox.setSelectedIndex(-1);
        nomeProcTextField.setText("");
        codeTextArea.setText("");
    }
    
    private boolean campiVuoti(){
        return nomeProcTextField.getText().equals("") || codeTextArea.getText().equals("") || 
               proprProcComboBox.getSelectedIndex() == -1 || schemaProcComboBox.getSelectedIndex() == -1 ||
               (procRadioButton.isSelected() == false && funRadioButton.isSelected() == false && tipoRitComboBox.getSelectedIndex() == -1);
    }
    
    //utilizzato per ricavare l'ID dell'elemento procedura appena inserito
    public int getIDprocedura(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT P.ID_procedura FROM procedura P WHERE P.NomeProcedura = '" + nomeProcTextField.getText() + "' AND P.schema = '" + (String)schemaProcComboBox.getSelectedItem()+"'";
        int ID = -1;
        
        try{
            if(!nomeProcTextField.getText().equals("") && schemaProcComboBox.getSelectedIndex() != -1){
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
    
    //restituisce lo schema a cui appartiene la procedura ricavandolo da schemaProcComboBox
    public String getSchema(){
        return (String)schemaProcComboBox.getSelectedItem();
    }
    
    //restituisce il nome della procedura ricavandolo dalla nomeProcTextField
    public String getNomeProc(){
        return nomeProcTextField.getText();
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

        tipoButtonGroup = new javax.swing.ButtonGroup();
        istruzioni1 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        istruzioni3 = new javax.swing.JLabel();
        nomeLabel = new javax.swing.JLabel();
        nomeProcTextField = new javax.swing.JTextField();
        tipoLabel = new javax.swing.JLabel();
        procRadioButton = new javax.swing.JRadioButton();
        funRadioButton = new javax.swing.JRadioButton();
        tipoRitornoLabel = new javax.swing.JLabel();
        tipoRitComboBox = new javax.swing.JComboBox<>();
        codeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        codeTextArea = new javax.swing.JTextArea();
        schemaProcLabel = new javax.swing.JLabel();
        schemaProcComboBox = new javax.swing.JComboBox<>();
        proprProcLabel = new javax.swing.JLabel();
        proprProcComboBox = new javax.swing.JComboBox<>();
        campiObbl = new javax.swing.JLabel();
        insertButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        istruzioni4 = new javax.swing.JLabel();
        istruzioni5 = new javax.swing.JLabel();
        istruzioni6 = new javax.swing.JLabel();

        istruzioni1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni1.setText("Di seguito verranno inseriti i dati per aggiungere una Procedurra o una Funzione nel Database.");

        istruzioni2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni2.setText("Il campo \"Tipo di Ritorno\" va riempito solo se il campo \"Tipo\" è uguale a 'Funzione'. Una volta effettuato");

        istruzioni3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni3.setText("l'inserimento, si verra reindirizzati all'inserimento delle eventuali variabili e degli eventuali parametri.");

        nomeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nomeLabel.setText("Nome Procedura  *");

        nomeProcTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tipoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoLabel.setText("Tipo *");

        tipoButtonGroup.add(procRadioButton);
        procRadioButton.setText("PROCEDURA");
        procRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procRadioButtonActionPerformed(evt);
            }
        });

        tipoButtonGroup.add(funRadioButton);
        funRadioButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        funRadioButton.setText("FUNZIONE");
        funRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funRadioButtonActionPerformed(evt);
            }
        });

        tipoRitornoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoRitornoLabel.setText("Tipo di ritorno");

        tipoRitComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tipoRitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NUMBER", "INTEGER", "FLOAT", "DOUBLE", "REAL", "CHAR", "VARCHAR", "BIT", "DATE" }));
        tipoRitComboBox.setSelectedIndex(-1);

        codeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        codeLabel.setText("Blocco di Codice  *");

        codeTextArea.setColumns(20);
        codeTextArea.setRows(5);
        jScrollPane1.setViewportView(codeTextArea);

        schemaProcLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        schemaProcLabel.setText("Schema  *");

        schemaProcComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        proprProcLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        proprProcLabel.setText("Proprietario  *");

        proprProcComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campiObbl.setText("*  Campo Obbligatorio");

        insertButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertButton.setText("Inserisci Procedura");
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

        istruzioni4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni4.setText("Nella casella \"Proprietario\" verranno mostrati solo gli utenti con i permessi necessari alla creazione.");

        istruzioni5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni5.setText("Dopo aver inserito variabili e parametri, verrà richiesto di inserire le procedure/funzioni richiamate ");

        istruzioni6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        istruzioni6.setText("all'interno di quella appena inserito.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(schemaProcLabel)
                                    .addComponent(proprProcLabel))
                                .addGap(135, 135, 135)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proprProcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(schemaProcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(96, 96, 96)
                                        .addComponent(campiObbl))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomeLabel)
                                    .addComponent(tipoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tipoRitornoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(105, 105, 105)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(procRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(funRadioButton))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tipoRitComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nomeProcTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(codeLabel)
                                .addGap(109, 109, 109)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(istruzioni1)
                            .addComponent(istruzioni2)
                            .addComponent(istruzioni3)
                            .addComponent(istruzioni4)
                            .addComponent(istruzioni5)
                            .addComponent(istruzioni6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(insertButton)
                        .addGap(18, 18, 18)
                        .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(istruzioni1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni2)
                .addGap(4, 4, 4)
                .addComponent(istruzioni3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nomeLabel)
                    .addComponent(nomeProcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoLabel)
                    .addComponent(procRadioButton)
                    .addComponent(funRadioButton))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoRitornoLabel)
                    .addComponent(tipoRitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codeLabel)
                        .addGap(0, 68, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proprProcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proprProcLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(schemaProcLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(schemaProcComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campiObbl)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    //annulla l'inserimento di una procedura
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    private void funRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funRadioButtonActionPerformed
        tipoRitComboBox.setEnabled(true);
    }//GEN-LAST:event_funRadioButtonActionPerformed

    private void procRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procRadioButtonActionPerformed
        tipoRitComboBox.setEnabled(false);
        tipoRitComboBox.setSelectedIndex(-1);
    }//GEN-LAST:event_procRadioButtonActionPerformed

    //inserisce una procedura e indirizza all'inserimento delle variabili, dei
    //parametri e delle altre procedure/funzioni ricchiamate all'interno
    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento della procedura\funzione\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);
                pstmt.setString(1, nomeProcTextField.getText());
                pstmt.setString(2, codeTextArea.getText());
                if(procRadioButton.isSelected()){
                    pstmt.setString(3, procRadioButton.getText());
                    pstmt.setString(4, null);
                }else{
                    pstmt.setString(3, funRadioButton.getText());
                    pstmt.setString(4, (String)tipoRitComboBox.getSelectedItem());
                }
                pstmt.setString(5, (String)schemaProcComboBox.getSelectedItem());
                pstmt.setString(6, (String)proprProcComboBox.getSelectedItem());                
                
                righeInserite = pstmt.executeUpdate();
                
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "La procedura/funzione " + nomeProcTextField.getText() + " è stata inserita correttamente !", "Inserimento Procedura effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    iPC = new InsertProcChiamata(this);
                } 
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campiObbl;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JTextArea codeTextArea;
    private javax.swing.JRadioButton funRadioButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni3;
    private javax.swing.JLabel istruzioni4;
    private javax.swing.JLabel istruzioni5;
    private javax.swing.JLabel istruzioni6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JTextField nomeProcTextField;
    private javax.swing.JRadioButton procRadioButton;
    private javax.swing.JComboBox<String> proprProcComboBox;
    private javax.swing.JLabel proprProcLabel;
    private javax.swing.JComboBox<String> schemaProcComboBox;
    private javax.swing.JLabel schemaProcLabel;
    private javax.swing.ButtonGroup tipoButtonGroup;
    private javax.swing.JLabel tipoLabel;
    private javax.swing.JComboBox<String> tipoRitComboBox;
    private javax.swing.JLabel tipoRitornoLabel;
    // End of variables declaration//GEN-END:variables
}
