/*
 * file : AddProcRichiamata.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette di aggiungere ad una Procedura gia esistente nel database
 * una chiamata ad un'altra Procedura o Fuznione
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class AddProcRichiamata extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO chiamataProc(chiamante, chiamata) VALUES(?, ?)";
    private int righeInserite = 0;
    
    /**
     * Creates new form AddProcRichiamata
     */
    public AddProcRichiamata() {
        this.setSize(770,418);
        initComponents();
    }
    
    //restituisce true se c'è almeno un campo di input che è stato rimasto vuoto
    private boolean campiVuoti(){
        return richiamataComboBox.getSelectedIndex() == -1 || chiamanteComboBox.getSelectedIndex() == -1;
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'aggiunta di una chiamata a Procedura
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    private void pulisciInput(){
        chiamanteComboBox.setSelectedIndex(-1);
        richiamataComboBox.setSelectedIndex(-1);
    }
    
    //riempe la chiamanteComboBox con le Procedure e Fuznioni presenti nel database
    public void riempiChiamanteComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT P.schema, P.nomeProcedura FROM Procedura P";
        
        chiamanteComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le Procedure nella comboBox saranno mostrate secondo il modello: nomeSchema.nomeProcedura
                //in quanto Procedure appartenenti a Schemi diversi possono avere lo stesso nome
                chiamanteComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            chiamanteComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //riempe la richiamataComboBox con le Procedure e Fuznioni presenti nel database che appartengono allo stesso Schema a cui appartiene la Procedura chiamante 
    private void riempiRichiamataComboBox(String chiamante){
        Statement stmt;
        ResultSet rst;
        String schema = ricavaSchemaProc(chiamanteComboBox.getSelectedItem().toString());
        int idChiamante = ricavaIDproc(chiamanteComboBox.getSelectedItem().toString());
        String query  = "SELECT P.schema, P.nomeProcedura FROM Procedura P WHERE P.schema = '" + schema + "' AND P.id_procedura NOT IN (SELECT A.chiamata FROM chiamataProc A WHERE A.chiamante = " + idChiamante +" )";
        
        richiamataComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();     
            
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                //le Procedure nella comboBox saranno mostrate secondo il modello: nomeSchema.nomeProcedura
                //in quanto Procedure appartenenti a Schemi diversi possono avere lo stesso nome
                richiamataComboBox.addItem(rst.getString(1)+"."+rst.getString(2));
            }
            richiamataComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    //ricava il nome della Procedura selezionato nella procComboBox
    private String ricavaNomeProc(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(i+1);
        
        return schema;
    }
    
    //ricava il nome dello schema a cui appartiene la procedura scelto
    private String ricavaSchemaProc(String itemSelected){
        int i = 0;
        String schema;
        
        while(itemSelected.charAt(i) != '.'){
            i++;
        }
        
        schema = itemSelected.substring(0, i);
        
        return schema;
    }
    
    //ricava la chiave primaria id_procedura della Procedura scelta nella procComboBox
    private int ricavaIDproc(String itemSelected){
        String proc = ricavaNomeProc(itemSelected);
        String schema = ricavaSchemaProc(itemSelected);
        int id = -1;
        
        Statement stmt; 
        ResultSet rst;
        String query = "SELECT P.ID_procedura FROM Procedura P WHERE P.nomeProcedura = '" + proc + "' AND P.schema = '" + schema + "'";
    
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

        istr1 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        chiamanteLabel = new javax.swing.JLabel();
        chiamanteComboBox = new javax.swing.JComboBox<>();
        richiamataLabel = new javax.swing.JLabel();
        richiamataComboBox = new javax.swing.JComboBox<>();
        campoObbl = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        istr1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr1.setText("Pannello permette di aggiungere ad una Procedura o Funzione una chiamta ");

        istr2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr2.setText("ad un'altra Procedura o Funzione");

        chiamanteLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chiamanteLabel.setText("Procedura Chiamante  * :");

        chiamanteComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chiamanteComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiamanteComboBoxActionPerformed(evt);
            }
        });

        richiamataLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        richiamataLabel.setText("Procedura Richiamanta  * :");

        richiamataComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        campoObbl.setText("*  Campo Obbligatorio");

        addButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        addButton.setText("Aggiungi Chiamata");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("In \"Procedura Richiamata\" verranno mostrate solo le Procedure/Funzioni appartenenti allo stesso Schema a cui appartiene la Procedura/Funzione");

        jLabel2.setText("selezionata nel campo \"Procedura Chiamante\".");

        jLabel3.setText("Inoltre, in \"Procedura Richiamata\" vengono mostrate solo le Procedure/Funzioni che non sono state gia richiamate dalla Procedura/Funzione");

        jLabel4.setText("selezionata nel campo \"Procedura Chiamante\".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(istr1)
                    .addComponent(istr2)
                    .addComponent(campoObbl)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(addButton)
                            .addGap(48, 48, 48)
                            .addComponent(annullaButton))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chiamanteLabel)
                                .addComponent(richiamataLabel))
                            .addGap(69, 69, 69)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(richiamataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chiamanteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(istr1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chiamanteLabel)
                    .addComponent(chiamanteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(richiamataLabel)
                    .addComponent(richiamataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton)
                    .addComponent(annullaButton))
                .addGap(30, 30, 30)
                .addComponent(campoObbl)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //dopo aver selezionato la procedura chiamante, riempo la seconda ComboBox con le procedure che 
    //appartengono allo stesso schema a cui appartiene la procedura chiamante selezionata
    private void chiamanteComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chiamanteComboBoxActionPerformed
        if(chiamanteComboBox.getSelectedIndex() != -1){
            riempiRichiamataComboBox(chiamanteComboBox.getSelectedItem().toString());
        }
    }//GEN-LAST:event_chiamanteComboBoxActionPerformed

    //annullo l'inserimento di una chiamata Procedurale azzerando i campi di input
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    //effettuo l'inserimento della chiamata a Procedura andando a popolare la tabella ChiamataProc
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        try{
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento di una chiamata ad una Procedura riempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);

                pstmt.setInt(1, ricavaIDproc(chiamanteComboBox.getSelectedItem().toString()));
                pstmt.setInt(2, ricavaIDproc(richiamataComboBox.getSelectedItem().toString()));

                righeInserite = pstmt.executeUpdate();

                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "La chiamata alla Proceduraa " + richiamataComboBox.getSelectedItem().toString() + " è stato aggiunto alla procedura " + chiamanteComboBox.getSelectedItem().toString() + " !", "Inserimento effettuato", JOptionPane.INFORMATION_MESSAGE);
                    pulisciInput();  
                }
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbl;
    private javax.swing.JComboBox<String> chiamanteComboBox;
    private javax.swing.JLabel chiamanteLabel;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> richiamataComboBox;
    private javax.swing.JLabel richiamataLabel;
    // End of variables declaration//GEN-END:variables
}
