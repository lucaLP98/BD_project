/*
 * file : SearchViewSeqAssert.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette di effettuare la ricerca di Viste o Sequenze o Asserzioni
 * in base alla scelta fatta dalla oggComboBox del pannello Search
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class SearchViewSeqAssert extends javax.swing.JPanel {

    private TableModel elemTableModel; //modello tabella visualizzazione Elemento cercato
    
    public static final int VISTA = 1;
    public static final int SEQ = 2;
    public static final int ASSERT = 3;
    
    private String query; //query utilizzata per cercare l'elemento cercato
    private String queryWhere; //utilizzata per indicare uno specifico elemento cercato
    private String oggetto;
    
    /**
     * Creates new form SearchViewSeqAssert
     */
    public SearchViewSeqAssert(int mod) {
        this.setSize(770, 418);
        initComponents();
        
        switch(mod){
            case VISTA:
                query = "SELECT nomeVista AS Nome_Vista, query, schema, Proprietario FROM Vista ";
                queryWhere = " WHERE nomeVista = ?";
                oggetto = "Vista";
            break;
            
            case SEQ:
                query = "SELECT nomeSeq AS Nome_Sequenza, Incremento, ValIniziale AS Valore_Iniziale, valMax AS Valore_Massimo, Cycle, schema, Proprietario FROM sequenza ";
                queryWhere = "WHERE nomeSeq = ?";
                oggetto = "Sequenza";
            break;
            
            case ASSERT:
                query = "SELECT nomeAsserzione AS Nome_Assertion, clausolaNotExists AS NOT_EXISTS, schema, proprietario FROM asserzione ";
                queryWhere = " WHERE nomeAsserzione = ?";
                oggetto = "Asserzione";
            break;
        }
        
        elemTable.setModel(new DefaultTableModel());
    }
    
    //Metodo che mostra a video l'errore generatosi durante l'operazione SQL
    public void mostraErrore(SQLException e) {
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

        searchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        elemTextField = new javax.swing.JTextField();
        istr = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        elemTable = new javax.swing.JTable();

        searchLabel.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchLabel.setText("Inserire il nome dell'elemento da cercare :");

        searchButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        searchButton.setText("Cerca");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        elemTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchLabel)
                .addGap(18, 18, 18)
                .addComponent(elemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(elemTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        istr.setText("Di seguito verranno mostrate le informazioni dell'elemento cercato.");

        elemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(elemTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(istr)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(istr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        PreparedStatement pstmt;
        ResultSet rs;
        
        if(!elemTextField.getText().equals("")){
            query += queryWhere;
        }
            
        try{
            pstmt = Database.getDefaultConnection().prepareStatement(query,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!elemTextField.getText().equals("")){
                pstmt.setString(1, elemTextField.getText());
            }
            rs = pstmt.executeQuery();

            if(rs.last()){
                rs.beforeFirst();

                elemTableModel = new TableModel(rs);
                elemTableModel.setEditable(false);
                elemTableModel.setNumColumn();

                while(rs.next()) {
                    int riga = elemTableModel.getRowCount();
                    elemTableModel.setRowCount(elemTableModel.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < elemTableModel.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        elemTableModel.setValueAt(valore, riga, c);
                    }
                }
                    
                elemTable.setModel(elemTableModel);
            }else{
                JOptionPane.showMessageDialog(this, "Nessuna " + oggetto + " trovata !", "Ricerca vuota", JOptionPane.WARNING_MESSAGE);
                elemTable.setModel(new DefaultTableModel());
            }

            pstmt.close();
        }catch(SQLException e){
            mostraErrore(e);
        }
    }//GEN-LAST:event_searchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable elemTable;
    private javax.swing.JTextField elemTextField;
    private javax.swing.JLabel istr;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    // End of variables declaration//GEN-END:variables
}
