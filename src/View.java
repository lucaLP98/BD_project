/*
 * file : View.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello utilizzato per visualizzare un'intera tabella del Database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class View extends javax.swing.JPanel {
    private ResultSet rs;
    private PreparedStatement stmt;
    private String query = "SELECT * FROM ";
    
    private TableModel model;
    
    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        viewTable.setModel(new DefaultTableModel());
    }

    //Metodo che mostra a video l'errore generatosi durante la visualizzazione di una tabella
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

        selectPanel = new javax.swing.JPanel();
        tabLabel = new javax.swing.JLabel();
        tabComboBox = new javax.swing.JComboBox<>();
        viewTabButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewTable = new javax.swing.JTable();

        tabLabel.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        tabLabel.setText("Selezionare la Tabella da visualizzare : ");

        tabComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asserzione", "ChiamataProc", "ChiaveEsterna", "Colonna", "Colonne_FK", "CompRuoloOggetto", "CompRuoloSistema", "Dominio", "Eccezioni", "Parametri", "PrivOggetto", "PrivSistema", "Procedura", "Ruolo", "Schema1", "Sequenza", "Tabella", "Trigger1", "Utente", "Valore", "Variabili", "Vincolo", "VincoloColonna", "Vista" }));
        tabComboBox.setSelectedIndex(-1);

        viewTabButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        viewTabButton.setText("Visualizza Tabella");
        viewTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTabButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout selectPanelLayout = new javax.swing.GroupLayout(selectPanel);
        selectPanel.setLayout(selectPanelLayout);
        selectPanelLayout.setHorizontalGroup(
            selectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(tabComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(viewTabButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        selectPanelLayout.setVerticalGroup(
            selectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(selectPanelLayout.createSequentialGroup()
                .addGroup(selectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(selectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabComboBox)
                            .addComponent(viewTabButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(selectPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        viewTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(viewTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
        
    //visualizzo la tabella scelta nella tabComboBox nella JTable viewTable
    private void viewTabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTabButtonActionPerformed
        if(tabComboBox.getSelectedItem() != null){
            try{
                stmt = Database.getDefaultConnection().prepareStatement(query + tabComboBox.getSelectedItem().toString());
                rs = stmt.executeQuery();
                
                model = new TableModel(rs);
                model.setEditable(false);
                model.setNumColumn();
                
                while(rs.next()) {
                    int riga = model.getRowCount(); 
                    model.setRowCount(model.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < model.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        model.setValueAt(valore, riga, c);
                    }
                }
                
                viewTable.setModel(model);
                
                if(stmt != null) stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Selezionare la tabella da visualizzare", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_viewTabButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel selectPanel;
    private javax.swing.JComboBox<String> tabComboBox;
    private javax.swing.JLabel tabLabel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JButton viewTabButton;
    private javax.swing.JTable viewTable;
    // End of variables declaration//GEN-END:variables
}