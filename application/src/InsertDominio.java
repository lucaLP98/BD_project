
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette l'aggiunta dell'elemento dominio nel database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertDominio extends javax.swing.JPanel {
    
    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO dominio(NomeDominio, Tipo, Proprietario, Schema) VALUES(?, ?, ?, ?)";
    private int righeInserite = 0;
    
    private InsertValueToDom iVD; //classe che permette l'inseriment di valori nel dominio
    private int ID_dom;
    
    /**
     * Creates new form InsertDominio
     */
    public InsertDominio() {
        this.setSize(770, 423);
        initComponents();
    }
    
    //metodo utilizzato per pulire i campi di input del pannello
    public void pulisciInput(){
        nomeDomTextField.setText("");
        proprDomComboBox.setSelectedIndex(-1);
        schemaDomComboBox.setSelectedIndex(-1);
        tipoDomComboBox.setSelectedIndex(-1);
    }
    
    //metodo che restituisce il tipo del dominio selezionato
    public String getDomType(){
        return (String)tipoDomComboBox.getSelectedItem();
    }

    //restituisce il nome del dominio
    public String getNomeDominio(){
        return nomeDomTextField.getText();
    }
    
    //restituisce lo schema del dominio
    public String getSchemaDominio(){
        return (String)schemaDomComboBox.getSelectedItem();
    }
    
    //metodo che ricava la chiave primaria ID_dominio dell'elemento dominio inserito
    private void ricavaIDdominio(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT D.ID_dominio FROM dominio D WHERE D.nomeDominio = '" + this.getNomeDominio() + "' AND D.schema = '" + this.getSchemaDominio() + "'";
        ID_dom = -1;
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                ID_dom = rst.getInt(1);
            }
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    public int getIDdominio(){
        return ID_dom;
    }
    
    //metodo che controlla se i campi di input sono stati riempiti o ne rimane almeno uno vuoto
    private boolean campiVuoti(){
        boolean tmp;
        
        tmp = nomeDomTextField.getText().equals("") || proprDomComboBox.getSelectedIndex() == -1 || schemaDomComboBox.getSelectedIndex() == -1 || tipoDomComboBox.getSelectedIndex() == -1;
        
        return tmp;
    }
    
    //metodo utilizzato per riempire la proprDomComboBox con gli username degli utenti che posseggono i permessi necessari alla creazione di un dominio
    private void riempiProprDomComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT U.username FROM utente U JOIN compRuoloSistema C ON U.ruolo = C.ID_ruolo WHERE C.ID_privSis = 'ALL PRIVILEGIES' OR C.ID_privSis = 'CREATE DOMAIN'";
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                proprDomComboBox.addItem(rst.getString(1));
            }
            
            proprDomComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //metodo utilizzato per riempire la schemaAssertComboBox con gli schemi presenti nel database
    private void riempiSchemaDomComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT nomeSchema FROM schema1";
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                schemaDomComboBox.addItem(rst.getString(1));
            }
            
            schemaDomComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //metodo richiamato nella JPanel Insert utilizzato per riempire le JComboBox presenti in questo JPanel
    //in modo che contengano sempre dati aggiurnati  ai cambiamenti che avvengono nel DB
    public void riempiComboBox(){
        proprDomComboBox.removeAllItems();
        schemaDomComboBox.removeAllItems();
        this.riempiProprDomComboBox();
        this.riempiSchemaDomComboBox();
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento schema nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
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

        istruzioni1 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        nomeDomLabel = new javax.swing.JLabel();
        nomeDomTextField = new javax.swing.JTextField();
        tipoDomLabel = new javax.swing.JLabel();
        tipoDomComboBox = new javax.swing.JComboBox<>();
        schemaLabel = new javax.swing.JLabel();
        schemaDomComboBox = new javax.swing.JComboBox<>();
        proprDomLabel = new javax.swing.JLabel();
        proprDomComboBox = new javax.swing.JComboBox<>();
        istruzioni3 = new javax.swing.JLabel();
        insertDomButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        campoObbLabel = new javax.swing.JLabel();
        istruzioni4 = new javax.swing.JLabel();

        istruzioni1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni1.setText("In questa sezione verranno inseriti i dati relativi al nuovo dominio che si ha intenzione di aggiungere al DB.");

        istruzioni2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni2.setText("Una volta aggiunto il dominio, verrà aperta una finestra di dialogo che consentirà l'aggiunta dei valori");

        nomeDomLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeDomLabel.setText("Nome Dominio  *");

        nomeDomTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tipoDomLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tipoDomLabel.setText("Tipo Dati del Dominio  *");

        tipoDomComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tipoDomComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NUMBER", "INTEGER", "FLOAT", "DOUBLE", "REAL", "CHAR", "VARCHAR", "BIT", "BLOB", "DATE", "TIMESTAMP", "INTERVAL" }));
        tipoDomComboBox.setSelectedIndex(-1);

        schemaLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        schemaLabel.setText("Schema  *");

        schemaDomComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        proprDomLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        proprDomLabel.setText("Proprietario  *");

        proprDomComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        istruzioni3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni3.setText("appartenenti ad esso.");

        insertDomButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertDomButton.setText("Inserisci Dominio e vai ad inserimento valori");
        insertDomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertDomButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        campoObbLabel.setText("*  Campo obbligatorio");

        istruzioni4.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni4.setText("In Proprietario verranno mostrati solo gli utenti con i permessi necessari alla creazione di un dominio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(schemaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tipoDomLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nomeDomLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(proprDomLabel))
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeDomTextField)
                            .addComponent(tipoDomComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(schemaDomComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(proprDomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoObbLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(insertDomButton)
                        .addGap(18, 18, 18)
                        .addComponent(annullaButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istruzioni1)
                    .addComponent(istruzioni2)
                    .addComponent(istruzioni3)
                    .addComponent(istruzioni4))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(istruzioni1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeDomLabel)
                    .addComponent(nomeDomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoDomLabel)
                    .addComponent(tipoDomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemaLabel)
                    .addComponent(schemaDomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proprDomLabel)
                    .addComponent(proprDomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(annullaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(insertDomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 23, Short.MAX_VALUE)
                .addComponent(campoObbLabel)
                .addGap(35, 35, 35))
        );
    }// </editor-fold>//GEN-END:initComponents

    //annulla l'inserimento di un oggetto dominio nel database
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    private void insertDomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertDomButtonActionPerformed
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del dominio\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);            
            
                pstmt.setString(1, nomeDomTextField.getText());
                pstmt.setString(2, (String)tipoDomComboBox.getSelectedItem());
                pstmt.setString(3, (String)proprDomComboBox.getSelectedItem());
                pstmt.setString(4, (String)schemaDomComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il Dominio " + nomeDomTextField.getText() + " è stata inserita correttamente !\nOra verra aperta una finestra di dialogo per l'inserimento dei valori\n\n N.B. Un dominio deve avere almeno un valore.", "Inserimento Dominio effettuato", JOptionPane.INFORMATION_MESSAGE);     
                    iVD = new InsertValueToDom(this);
                    ricavaIDdominio();
                } 
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertDomButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.JButton insertDomButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni3;
    private javax.swing.JLabel istruzioni4;
    private javax.swing.JLabel nomeDomLabel;
    private javax.swing.JTextField nomeDomTextField;
    private javax.swing.JComboBox<String> proprDomComboBox;
    private javax.swing.JLabel proprDomLabel;
    private javax.swing.JComboBox<String> schemaDomComboBox;
    private javax.swing.JLabel schemaLabel;
    private javax.swing.JComboBox<String> tipoDomComboBox;
    private javax.swing.JLabel tipoDomLabel;
    // End of variables declaration//GEN-END:variables
}
