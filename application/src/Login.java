/*
 * file : Login.java
 */
package progettopastoreluca;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class Login extends javax.swing.JDialog {

    public static final boolean BUTTON_ACCEDI = true;
    public static final boolean BUTTON_ESCI = false;
    private boolean bottonePremuto; 
    private final AvanzateLogin a = new AvanzateLogin(this,"Settaggi avanzati - Connessione Remota"); 
    
    /**
     * Creates new form Login
     * @param parent Frame chiamante
     * @param modal
     */
    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setSize(418, 350);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginLabel = new javax.swing.JLabel();
        inputPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        pswLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        avanzateCheckBox = new javax.swing.JCheckBox();
        istrLabel1 = new javax.swing.JLabel();
        istrLabel2 = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        esciButton = new javax.swing.JButton();
        accediButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login - Gestione metadati DB Relazionali");

        loginLabel.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        loginLabel.setForeground(new java.awt.Color(204, 0, 0));
        loginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loginLabel.setText("LOGIN");

        usernameLabel.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(0, 51, 255));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        usernameLabel.setText("Nome Utente");

        usernameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pswLabel.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        pswLabel.setForeground(new java.awt.Color(0, 51, 255));
        pswLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pswLabel.setText("Password");

        passwordField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        avanzateCheckBox.setText("Ulteriori settaggi");
        avanzateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avanzateCheckBoxActionPerformed(evt);
            }
        });

        istrLabel1.setText("Seleziona per la connessione in remoto");

        istrLabel2.setText("La connessione di default è in locale");

        esciButton.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        esciButton.setText("ESCI");
        esciButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                esciButtonActionPerformed(evt);
            }
        });

        accediButton.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        accediButton.setText("ACCEDI");
        accediButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accediButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accediButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(esciButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(esciButton, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(accediButton, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pswLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(avanzateCheckBox)))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(usernameTextField)
                        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(istrLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(istrLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTextField))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pswLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordField))
                .addGap(32, 32, 32)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(istrLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(istrLabel2))
                    .addComponent(avanzateCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(loginLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public boolean getBottonePremuto(){
        return bottonePremuto;
    }
    
    private void esciButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_esciButtonActionPerformed
        this.bottonePremuto = BUTTON_ESCI;
        dispose();
    }//GEN-LAST:event_esciButtonActionPerformed

    private void accediButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accediButtonActionPerformed
        Database.user = usernameTextField.getText();
        Database.password = new String(passwordField.getPassword());
        
        try{
            Database.setDefaultConnection(Database.connetti());
            bottonePremuto = BUTTON_ACCEDI;
            dispose();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Connessione fallita (credenziali errate!)", "Errore di connessione", JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_accediButtonActionPerformed

    private void avanzateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avanzateCheckBoxActionPerformed
        if(avanzateCheckBox.isSelected()){
        //    AvanzateLogin a = new AvanzateLogin(this,"Settaggi avanzati - Connessione Remota");
            a.setVisible(true);
        }
    }//GEN-LAST:event_avanzateCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accediButton;
    private javax.swing.JCheckBox avanzateCheckBox;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton esciButton;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel istrLabel1;
    private javax.swing.JLabel istrLabel2;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel pswLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
