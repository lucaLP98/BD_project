/**
 * file: InsertUSer.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Pannello che permette l'aggiunta dell'elemento utente nel database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertUSer extends javax.swing.JPanel {

    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO utente(username, nome, cognome, password, ruolo) VALUES(?,?,?,?,?)";
    private int righeInserite = 0;
    
    /**
     * Creates new form InsertUSer
     */
    public InsertUSer() {
        this.setSize(770, 423);
        initComponents();
      //  riempiComboBox();
    }
    
    //svuota tutti i campi di input del pannello
    public void pulisciInput(){
        cognomeTextField.setText("");
        nomeTextField.setText("");
        pswTextField1.setText("");
        pswTextField2.setText("");
        usernameTextField.setText("");
        ruoloComboBox.setSelectedIndex(-1);
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento utente nel database database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    //metodo che restituisce true se almeno uno dei campi di input è vuoto, false se sono tutti non vuoti
    private boolean campiVuoti(){
        boolean tmp;
        
        tmp = nomeTextField.getText().equals("") || cognomeTextField.getText().equals("") || 
              pswTextField1.getPassword().equals("") || pswTextField2.getPassword().equals("") || 
              usernameTextField.getText().equals("") || ruoloComboBox.getSelectedIndex() == -1;
        
        return tmp;
    }
    
    //metodo che inserisce nella ruoloComboBox i ruoli presenti nel database 
    public void riempiComboBox(){
        Statement stmt;
        ResultSet ruoli;
        String query = "SELECT R.nomeRuolo FROM ruolo R";            
        
        ruoloComboBox.removeAllItems();
        try{
            stmt = Database.getDefaultConnection().createStatement();
            ruoli = stmt.executeQuery(query);
            
            while(ruoli.next()){
                ruoloComboBox.addItem((String)ruoli.getString(1));
            }           
            
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }finally{
            ruoloComboBox.setSelectedIndex(-1);
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

        jLabel1 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        nomeLabel = new javax.swing.JLabel();
        nomeTextField = new javax.swing.JTextField();
        cognomeLabel = new javax.swing.JLabel();
        cognomeTextField = new javax.swing.JTextField();
        pswLabel1 = new javax.swing.JLabel();
        pswTextField1 = new javax.swing.JPasswordField();
        pswLabel2 = new javax.swing.JLabel();
        pswTextField2 = new javax.swing.JPasswordField();
        ruoloLabel = new javax.swing.JLabel();
        ruoloComboBox = new javax.swing.JComboBox<>();
        insertUserButton = new javax.swing.JButton();
        annullaButton = new javax.swing.JButton();
        campoObbLabel = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jLabel1.setText("Inserire qui i dati dell' utente che si desidera aggiungere al Database.");

        usernameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameLabel.setText("Username  *");

        usernameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        nomeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeLabel.setText("Nome  *");

        nomeTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cognomeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cognomeLabel.setText("Cognome  *");

        cognomeTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pswLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswLabel1.setText("Password  *");

        pswTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pswLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswLabel2.setText("Ripeti Password  *");

        pswTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ruoloLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ruoloLabel.setText("Ruolo  *");

        ruoloComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        insertUserButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertUserButton.setText("Inserisci Utente");
        insertUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertUserButtonActionPerformed(evt);
            }
        });

        annullaButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        annullaButton.setText("Annulla");
        annullaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaButtonActionPerformed(evt);
            }
        });

        campoObbLabel.setText("* Campo Obbligatorio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)
                                .addComponent(nomeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cognomeLabel)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pswLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pswLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(ruoloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(92, 92, 92)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pswTextField1)
                                    .addComponent(cognomeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(pswTextField2)
                                    .addComponent(ruoloComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(insertUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(annullaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoObbLabel)))
                .addContainerGap(265, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nomeTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cognomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cognomeTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pswLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pswTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pswLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pswTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruoloLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ruoloComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertUserButton)
                    .addComponent(annullaButton))
                .addGap(18, 18, 18)
                .addComponent(campoObbLabel)
                .addGap(38, 38, 38))
        );
    }// </editor-fold>//GEN-END:initComponents

    //metodo utilizzato per annullare l'inserimento di un utente
    private void annullaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaButtonActionPerformed
        pulisciInput();
    }//GEN-LAST:event_annullaButtonActionPerformed

    //inserisce un elemento utente nel database
    private void insertUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertUserButtonActionPerformed
        String psw1 = new String(pswTextField1.getPassword());
        String psw2 = new String(pswTextField2.getPassword());
        
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento dell' utente\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else if(psw1.equals(psw2)){
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);            
            
                pstmt.setString(1, usernameTextField.getText());
                pstmt.setString(2, nomeTextField.getText());
                pstmt.setString(3, cognomeTextField.getText());
                pstmt.setString(4, psw1);
                pstmt.setString(5, (String)ruoloComboBox.getSelectedItem());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){ 
                    JOptionPane.showMessageDialog(this, "L' utente " + usernameTextField.getText() + " è stato inserito correttamente", "Inserimento utente effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    pulisciInput(); 
                }
                
                if(pstmt != null) pstmt.close();
            }else{    
                JOptionPane.showMessageDialog(this, "Le password inserite non combaciano", "Attenzione", JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException e) {
            mostraErrore(e);
        } 
    }//GEN-LAST:event_insertUserButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaButton;
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.JLabel cognomeLabel;
    private javax.swing.JTextField cognomeTextField;
    private javax.swing.JButton insertUserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JLabel pswLabel1;
    private javax.swing.JLabel pswLabel2;
    private javax.swing.JPasswordField pswTextField1;
    private javax.swing.JPasswordField pswTextField2;
    private javax.swing.JComboBox<String> ruoloComboBox;
    private javax.swing.JLabel ruoloLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
