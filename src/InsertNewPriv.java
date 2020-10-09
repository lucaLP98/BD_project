/**
 * file: InsertNewPriv.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette l'effettivo inserimento di un elemento privilegio (di sistema o di oggetto) nel database
 * 
 * @author Luca Pastore
 * @version 2019
 */
public class InsertNewPriv extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryInsPsis = "INSERT INTO privSistema(nomePrivSist) VALUES(?)";
    private final String queryInsPogg = "INSERT INTO privOggetto(nomePrivOgg) VALUES(?)";
    private int righeInserite = 0;
    
    /**
     * Creates new form InsertNewPriv
     */
    public InsertNewPriv() {
        this.setSize(770, 281);
        initComponents();
        
        creaPrivSisComboBox();
        creaPrivOggComboBox();
        istruzioniLabel1.setVisible(false);
        selPrivLabel.setVisible(false);
        privSisComboBox.setVisible(false);
        privOggComboBox.setVisible(false);
        insertPrivButton.setVisible(false);
        annullaButton.setVisible(false);
    }

    //metodo utilizzata per rendere visibili o invisibili i pulsanti "Inserisci Privilegio" e "Annulla"
    private void setVisibleButton(boolean t){
        insertPrivButton.setVisible(t);
        annullaButton.setVisible(t);
    }
    
    //svuota tutti i campi di input del pannello
    public void pulisciInput(){
        privOggComboBox.setSelectedIndex(-1);
        privSisComboBox.setSelectedIndex(-1);
        tipoPrivButtonGroup.clearSelection();
    }
    
    //metodo che cerca se un determinato privilegio è gia stato inserito nel database
    private boolean cercaPriv(String colonna, String tabella, String privilegio){
        Statement stmt;
        ResultSet ris;
        String query = "SELECT " + colonna + " FROM " + tabella + " WHERE " + colonna + " = '" + privilegio + "'";
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
    private void addItemToPrivSisComboBox(String privilegio){
        if(cercaPriv("nomePrivSist", "privSistema", privilegio))
            privSisComboBox.addItem(privilegio);
    }
    
    //metodo che inserisce un privilegio d'oggetto nella privOggComboBox se questo non è gia presente nel database
    private void addItemToPrivOggComboBox(String privilegio){
        if(cercaPriv("nomePrivOgg", "privOggetto", privilegio))
            privOggComboBox.addItem(privilegio);
    }
    
    //metodo che crea la privSisComboBox
    public void creaPrivSisComboBox(){
        privSisComboBox.removeAllItems();
        
        addItemToPrivSisComboBox("ALL PRIVILEGIES");
        addItemToPrivSisComboBox("CONNECT");
        addItemToPrivSisComboBox("CREATE SESSION");
        addItemToPrivSisComboBox("CREATE SCHEMA");
        addItemToPrivSisComboBox("CREATE TABLE");
        addItemToPrivSisComboBox("CREATE VIEW");
        addItemToPrivSisComboBox("CREATE SEQUENCE");
        addItemToPrivSisComboBox("CREATE DOMAIN");
        addItemToPrivSisComboBox("CREATE ASSERTION");
        addItemToPrivSisComboBox("CREATE TRIGGER");
        addItemToPrivSisComboBox("CREATE PROCEDURE");
        addItemToPrivSisComboBox("DROP TABLE");
        addItemToPrivSisComboBox("DROP VIEW");
        addItemToPrivSisComboBox("DROP SEQUENCE");
        
        privSisComboBox.setSelectedIndex(-1);
    }
    
    //metodo che crea la privSisComboBox
    public void creaPrivOggComboBox(){
        privOggComboBox.removeAllItems();
        
        addItemToPrivOggComboBox("INSERT");
        addItemToPrivOggComboBox("UPDATE");
        addItemToPrivOggComboBox("DELETE");
        addItemToPrivOggComboBox("ALTER");
        addItemToPrivOggComboBox("SELECT");
        
        privOggComboBox.setSelectedIndex(-1);
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento privilegio nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo utilizzato per effettuare inserimento nel database di un privilegio di sistema
    private void insertPrivSis(){
        try{ 
            if(privSisComboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del privilegio\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryInsPsis);            
            
                pstmt.setString(1, (String)privSisComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il privilegio " + (String)privSisComboBox.getSelectedItem() + " è stato inserito correttamente", "Inserimento Privilegio di Sistema effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    privSisComboBox.removeItem(privSisComboBox.getSelectedItem());
                    pulisciInput();
                }   
                
                if(pstmt != null) pstmt.close();
            }   
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }
    
    //metodo utilizzato per effettuare inserimento nel database di un privilegio d' oggetto
    private void insertPrivOgg(){
        try{ 
            if(privOggComboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del privilegio\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryInsPogg);            
            
                pstmt.setString(1, (String)privOggComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Il privilegio " + (String)privOggComboBox.getSelectedItem() + " è stato inserito correttamente", "Inserimento Privilegio d'Oggetto effettuato", JOptionPane.INFORMATION_MESSAGE); 
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

        tipoPrivButtonGroup = new javax.swing.ButtonGroup();
        tipoPrivLabel = new javax.swing.JLabel();
        privSisRadioButton = new javax.swing.JRadioButton();
        privOggRadioButton = new javax.swing.JRadioButton();
        selPrivLabel = new javax.swing.JLabel();
        privSisComboBox = new javax.swing.JComboBox<>();
        privOggComboBox = new javax.swing.JComboBox<>();
        insertPrivButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        campoObbLabel = new javax.swing.JLabel();
        istruzioniLabel1 = new javax.swing.JLabel();

        tipoPrivButtonGroup.add(privSisRadioButton);
        tipoPrivButtonGroup.add(privOggRadioButton);

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

        selPrivLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selPrivLabel.setText("Seleziona il Privilegio *:");

        privSisComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        privOggComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        insertPrivButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertPrivButton.setText("Inserisci Privilegio");
        insertPrivButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertPrivButtonActionPerformed(evt);
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

        istruzioniLabel1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        istruzioniLabel1.setText("N.B. Nel seguente menu a tendina verranno mostrati solo i privilegi che non sono ancora presenti nel database.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tipoPrivLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(privSisRadioButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selPrivLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(insertPrivButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(annullaButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(privSisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(privOggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addComponent(privOggRadioButton)
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(campoObbLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(istruzioniLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoPrivLabel)
                    .addComponent(privSisRadioButton)
                    .addComponent(privOggRadioButton))
                .addGap(21, 21, 21)
                .addComponent(istruzioniLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selPrivLabel)
                    .addComponent(privSisComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(privOggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertPrivButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(campoObbLabel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //metodo che permette l'aggiunta di un nuovo privilegio di sistema al database
    private void privSisRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privSisRadioButtonActionPerformed
        istruzioniLabel1.setVisible(true);
        selPrivLabel.setVisible(true);
        privSisComboBox.setVisible(true);
        privOggComboBox.setVisible(false);
        setVisibleButton(true);
    }//GEN-LAST:event_privSisRadioButtonActionPerformed

    //metodo che permette l'aggiunta di un nuovo privilegio d'oggetto al database
    private void privOggRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privOggRadioButtonActionPerformed
        istruzioniLabel1.setVisible(true);
        selPrivLabel.setVisible(true);
        privOggComboBox.setVisible(true);
        privSisComboBox.setVisible(false);    
        setVisibleButton(true);
    }//GEN-LAST:event_privOggRadioButtonActionPerformed

    private void insertPrivButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertPrivButtonActionPerformed
        // INSERIRE PRIVILEGIO NEL DATABASE
        if(privSisRadioButton.isSelected())
            insertPrivSis();
        else
            insertPrivOgg();
        
        pulisciInput();
    }//GEN-LAST:event_insertPrivButtonActionPerformed

     //metodo utilizzato per annullare l'inserimento del privilegio
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
        istruzioniLabel1.setVisible(false);
        selPrivLabel.setVisible(false);
        privSisComboBox.setVisible(false);
        privOggComboBox.setVisible(false);
        setVisibleButton(false);
    }//GEN-LAST:event_annullaButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.JButton insertPrivButton;
    private javax.swing.JLabel istruzioniLabel1;
    private javax.swing.JComboBox<String> privOggComboBox;
    private javax.swing.JRadioButton privOggRadioButton;
    private javax.swing.JComboBox<String> privSisComboBox;
    private javax.swing.JRadioButton privSisRadioButton;
    private javax.swing.JLabel selPrivLabel;
    private javax.swing.ButtonGroup tipoPrivButtonGroup;
    private javax.swing.JLabel tipoPrivLabel;
    // End of variables declaration//GEN-END:variables
}
