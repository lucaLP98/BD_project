
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Finestra di dialogo che permette l'inserimento di valori all'interno di un dominio
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertValueToDom extends javax.swing.JDialog {
    
    private InsertDominio dominio;
    
    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO valore(ID_dominio, valoreNome) VALUES(?, ?)";
    private int righeInserite = 0;
    
    /**
     * Creates new form InsertValueToDom
     * @param dom
     */
    public InsertValueToDom(InsertDominio dom) {
        initComponents();
        this.pack();
        this.setVisible(true);
        
        dom.enabledAnnullaButton(false);
        this.dominio = dom;
        this.endButton.setEnabled(false);
        this.domTextField.setText(dom.getNomeDominio());
        this.domTextField.setEnabled(false);
    }
    
    //metodo che controlla se i campi di input sono stati riempiti o ne rimane almeno uno vuoto
    private boolean campiVuoti(){
        boolean tmp;
        
        tmp = domTextField.getText().equals("") || valoreTextField.getText().equals("");
        
        return tmp;
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento schema nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
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
        istruzioni3 = new javax.swing.JLabel();
        istruzioni2 = new javax.swing.JLabel();
        domLabel = new javax.swing.JLabel();
        valoreLabel = new javax.swing.JLabel();
        valoreTextField = new javax.swing.JTextField();
        domTextField = new javax.swing.JTextField();
        campoObbLabel = new javax.swing.JLabel();
        insertValueButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Inserimento Valori nel Dominio");
        setResizable(false);

        istruzioni1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        istruzioni1.setText("Inserire i valori che appartengono al dominio ");

        istruzioni3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni3.setText("Premere Fine per smettere di inserire i valori nel dominio.");

        istruzioni2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istruzioni2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        istruzioni2.setText("nella casella di testo sottostante.");

        domLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        domLabel.setText("Dominio  *");

        valoreLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        valoreLabel.setText("Valore  *");

        valoreTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        domTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        campoObbLabel.setText("*  Campo Obblogatorio");

        insertValueButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertValueButton.setText("Inserisci valore nel Dominio");
        insertValueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertValueButtonActionPerformed(evt);
            }
        });

        endButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        endButton.setText("Fine");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(istruzioni1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(istruzioni3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoObbLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(domLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(valoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(domTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(valoreTextField))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(insertValueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(endButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(istruzioni2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(istruzioni1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(domLabel)
                    .addComponent(domTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valoreLabel)
                    .addComponent(valoreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(campoObbLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertValueButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //inserisce un elemento valore legato ad un particolare dominio nel database
    private void insertValueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertValueButtonActionPerformed
        //inserisci valori in dominio
        try{ 
            if(campiVuoti()){
                JOptionPane.showMessageDialog(this, "Prima di effettuare l'inserimento del valore a " + dominio.getNomeDominio() +"\nriempire tutti i campi obbligatori !", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }else{
                pstmt = Database.getDefaultConnection().prepareStatement(queryIns);            
            
                pstmt.setInt(1, dominio.getIDdominio());
                pstmt.setString(2, (String)valoreTextField.getText());
                righeInserite = pstmt.executeUpdate();
            
                if(righeInserite != 0){
                    JOptionPane.showMessageDialog(this, "Valore inserito correttamente !", "Inserimento valore effettuato", JOptionPane.INFORMATION_MESSAGE); 
                    this.endButton.setEnabled(true);
                    this.valoreTextField.setText("");
                } 
                
                if(pstmt != null) pstmt.close();
            }
        }catch(SQLException e) {
            mostraErrore(e);
        }     
    }//GEN-LAST:event_insertValueButtonActionPerformed

    //termina l'inserimento dei vlori all'interno del dominio
    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        dominio.pulisciInput();
        dominio.enabledAnnullaButton(true);
        dispose();
    }//GEN-LAST:event_endButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel campoObbLabel;
    private javax.swing.JLabel domLabel;
    private javax.swing.JTextField domTextField;
    private javax.swing.JButton endButton;
    private javax.swing.JButton insertValueButton;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JLabel istruzioni3;
    private javax.swing.JLabel valoreLabel;
    private javax.swing.JTextField valoreTextField;
    // End of variables declaration//GEN-END:variables
}
