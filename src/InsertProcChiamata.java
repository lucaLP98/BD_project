/*
 * file : InsertProcChiamata.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Finestra di dialogo richiamata nel pannello InsertProcedura, 
 * utilizzata per popolare la tabella chiamataProc, cioè per indicare
 * quali procedure/funzioni sono richiamate da una procedura/funzione 
 * passata in input
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class InsertProcChiamata extends javax.swing.JDialog {
    
    private int id_procChiamante;
    private String schemaChiamante;
    private InsertProcedura proc;
    private InsertParametri par;
    
    private PreparedStatement pstmt = null;
    private final String queryIns = "INSERT INTO chiamataProc(chiamante, chiamata) VALUES(?, ?)";
    private int righeInserite = 0;
    
    
    /**
     * Creates new form InsertProcChiamata
     * @param parent
     */
    public InsertProcChiamata(InsertProcedura p) {
        this.setSize(400, 300);
        initComponents();
        this.setVisible(true);
        
        p.enabledAnnullaButton(false);
        proc = p;
        schemaChiamante = p.getSchema();
        id_procChiamante = p.getIDprocedura();
        chiamanteTextField.setText(p.getNomeProc());
        chiamanteTextField.setEnabled(false);
        riempiComboBox();
    }

    //Metodo che mostra a video l'errore generatosi durante l'inserimento di un elemento nel database
    private void mostraErrore(SQLException e) {
        String msg;
        
        msg = "Codice Errore: " + e.getErrorCode() + "\n";
        msg += "Messaggio: " + e.getMessage() + "\n";
        msg += "SQLState: " + e.getSQLState() + "\n";

        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    private void riempiComboBox(){
        Statement stmt;
        ResultSet rst;
        String query = "SELECT P.nomeProcedura FROM procedura P WHERE P.schema = '" + schemaChiamante + "'";
        
        chiamataComboBox.removeAllItems();
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                chiamataComboBox.addItem(rst.getString(1));
            }
            
            chiamataComboBox.setSelectedIndex(-1);
            
            stmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }
    
    private int ottieniID(String proc){
        int id = -1;
        Statement stmt;
        ResultSet rst;
        String query = "SELECT ID_procedura FROM procedura WHERE nomeProcedura = '" + (String)chiamataComboBox.getSelectedItem() + "' AND schema = '" + schemaChiamante + "'";
        
        try{
            stmt = Database.getDefaultConnection().createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                id = rst.getInt(1);
                this.chiamataComboBox.removeItem(chiamataComboBox.getSelectedItem() );
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

        jLabel5 = new javax.swing.JLabel();
        istr1 = new javax.swing.JLabel();
        istr2 = new javax.swing.JLabel();
        istr3 = new javax.swing.JLabel();
        chiamanteLabel = new javax.swing.JLabel();
        chiamanteTextField = new javax.swing.JTextField();
        chiamataLabel = new javax.swing.JLabel();
        chiamataComboBox = new javax.swing.JComboBox<>();
        campoObb = new javax.swing.JLabel();
        insertButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Inserimento Procedure/Funzioni richiamate");

        istr1.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr1.setText("Inserire le procedure o funzioni chiamate all'interno");

        istr2.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr2.setText("della procedura/funzione appena inserita.");

        istr3.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        istr3.setText("Premere Fine per terminare l'inserimento.");

        chiamanteLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chiamanteLabel.setText("Procedura Chiamante");

        chiamanteTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chiamataLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        chiamataLabel.setText("Procedura Chiamata  *");

        chiamataComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        campoObb.setText("*  Campo Obbligatorio");

        insertButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        insertButton.setText("Inserisci");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });

        endButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        endButton.setText("Fine");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nel campo \"procedura Chiamata\" andranno visualizzate solo le ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("procedure/funzioni appartenenti allo stesso schema.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(istr1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(istr3)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chiamataLabel)
                                        .addComponent(chiamanteLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chiamanteTextField)
                                        .addComponent(chiamataComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(istr2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(campoObb)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(insertButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(istr1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istr3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chiamanteLabel)
                    .addComponent(chiamanteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chiamataLabel)
                    .addComponent(chiamataComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(campoObb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(endButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(insertButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Ora verrà aperta una finestra di dialogo per l'inserimento\ndei Parametri della procedura appena inserita.\nPremere Fine se la procedura non ha parametri.", "Fine inserimento procedure richiamate", JOptionPane.INFORMATION_MESSAGE); 
        par = new InsertParametri(proc);
        dispose();
    }//GEN-LAST:event_endButtonActionPerformed

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        try{
            pstmt = Database.getDefaultConnection().prepareStatement(queryIns);              
                
            pstmt.setInt(1, id_procChiamante);
            pstmt.setInt(2, ottieniID((String)chiamataComboBox.getSelectedItem()));
            
            righeInserite = pstmt.executeUpdate();
            
            if(righeInserite != 0){
                JOptionPane.showMessageDialog(this, "La procedura/funzione " + (String)chiamataComboBox.getSelectedItem() + " è stata aggiunta alle chiamate !", "Inserimento effettuato", JOptionPane.INFORMATION_MESSAGE); 
               chiamataComboBox.setSelectedIndex(-1);
            } 
            
            if(pstmt != null) pstmt.close();
        }catch(SQLException e) {
            mostraErrore(e);
        }
    }//GEN-LAST:event_insertButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel campoObb;
    private javax.swing.JLabel chiamanteLabel;
    private javax.swing.JTextField chiamanteTextField;
    private javax.swing.JComboBox<String> chiamataComboBox;
    private javax.swing.JLabel chiamataLabel;
    private javax.swing.JButton endButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JLabel istr1;
    private javax.swing.JLabel istr2;
    private javax.swing.JLabel istr3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}