/*
 * file : Update.java
 */
package progettopastoreluca;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Pannello che permette di modificare determinati dati presenti nel database
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class Update extends javax.swing.JPanel {
    private TableModel model;
    
    private String chiaviString[]; //array che conterrà le chiavi primarie numeriche per permettere l'aggiornamento delle stesse
    
    //query per aggiornamento Tabella
    private final String queryUpTab = "UPDATE tabella SET nomeTabella = ?, proprietario = ?, schema = ? WHERE id_tabella = ?";
   
    //query per aggiornamento Procedura
    private final String queryUpProc = "UPDATE procedura SET nomeProcedura = ?, bloccoCodice = ?, tipo = ?, tipoRitorno = ?, schema = ?, proprietario = ? WHERE id_procedura = ?";
    
    //query per aggiornamento Parametro Procedura
    private final String queryUpPar = "UPDATE parametri SET nomeParam = ?, tipo = ?, tipologiaPar = ? WHERE id_parametro = ?";
    
    //query per aggiornamento Dominio
    private final String queryUpDom = "UPDATE dominio SET nomeDominio = ?, tipo = ?, proprietario = ?, schema = ? WHERE id_dominio = ?";
    
    //query per aggiornamento Vista
    private final String queryUpVista = "UPDATE vista SET nomeVista = ?, query = ?, schema = ?, proprietario = ? WHERE id_view = ?";
    
    //query per aggiornamento Assert
    private final String queryUpAssert = "UPDATE asserzione SET nomeAsserzione = ?, clausolaNotExists = ?, schema = ?, proprietario = ? WHERE id_asserzione = ?";
    
    //query per aggiornamento Sequenza
    private final String queryUpSeq = "UPDATE sequenza SET nomeSeq = ?, incremento = ?, valIniziale = ?, valMax = ?, cycle = ?, schema = ?, proprietario = ? WHERE id_sequenza = ?";
    
    //query per aggiornamento Ruolo
    private final String queryUpRuolo = "UPDATE ruolo SET nomeRuolo = ? WHERE nomeRuolo = ?";
    
    //query per aggiornamento Schema
    private final String queryUpSchema = "UPDATE schema1 SET nomeSchema = ?, proprietario = ? WHERE nomeSchema = ?";
    
    //query per aggiornamento Colonna
    private final String queryUpCol = "UPDATE colonna SET nomeColonna = ?, tipo = ?, lunghezzaDati = ?, nullo = ?, valDefault = ?, ID_dominio = ? WHERE id_colonna = ?";
   
    //query per aggiornamento Utente
    private final String queryUpUtente = "UPDATE utente SET username = ?, nome = ?, cognome = ?, password = ?, ruolo = ? WHERE username = ?";
    
    //query per aggiornamento Trigger
    private final String queryUpTrigger = "UPDATE trigger1 SET nomeTrigger = ?, tempo = ?, forEachRow = ?, condWhen = ?, causa = ?, bloccoCodice = ?, proprietario = ?, schema = ?, oggettoTab = ?, oggettoView = ? WHERE id_trigger = ?";
    
    //query per aggiornare una Variabile di una Procedura o di un Trigger
    private final String queryUpVar = "UPDATE variabili SET nomeVariabile = ?, tipo = ? WHERE id_var = ?";
    
    //query per aggiornare una Exception di una Procedura o di un Trigger
    private final String queryUpExc = "UPDATE eccezioni SET nomeEccezione = ?, bloccoCodice = ? WHERE id_eccezione = ?";
    
    //query per aggiornare un Valore di un dominio
    private final String queryUpVal = "UPDATE valore SET valoreNome = ? WHERE valoreNome = ? AND id_dominio = ?";
    
    //query per aggiornare un Vincolo 
    private final String queryUpVin = "UPDATE vincolo SET nomeVincolo = ?, condCheck = ?, stato = ?, proprietario = ? WHERE id_vincolo = ?";
    
    /**
     * Creates new form Update
     */
    public Update() {
        initComponents();
        updateTable.setModel(new DefaultTableModel());
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
    * metodo utilizzato in modifica Colonne
    * permette di ricavare l'id del dominio che si sta associando alla Colonna
    * in base al nome inserito e allo schema di appartenenza (cioè lo stesso 
    * della tabella a cui appartiene la Colonna)
    * questo perche il campo dominio nella tabella Colonna è un campo numerico
    * in cui va inserito l'ID chiave primaria del Dominio associato
    */
    private int ricavaIDdominioColonna(){
        int id = -1;
        Statement stmt;
        ResultSet rs;
        
        try{
            if(updateTable.getSelectedRow() != -1){
                stmt = Database.getDefaultConnection().createStatement();
                rs = stmt.executeQuery("SELECT D.id_dominio FROM dominio D WHERE D.nomeDominio = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 6) + "' AND D.schema = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 8) + "'");
            
                while(rs.next()){
                    id = rs.getInt(1);
                }
                
                stmt.close();
            }
        }catch(SQLException e){
            id = -1;
            mostraErrore(e);
        }
        
        return id;
    }
    
    /**
     * ricava l'ID chiaver primaria dell'oggetto dal trigger inserito.
     * Se l'oggetto è una vista, allora viene cercato l'ID della vista inserita nella tabella VISTA
     * Se l'oggetto è una TAbella, allora viene cercato l'ID della tabella inserita nella tabella TABELLA
     * 
     * @return id trovato
     */
    private int ricavaIdOggettoTrigger(){
        int id = -1;
        int oggetto;
        final int VISTA = 1;
        final int TABELLA = 2;
        Statement stmt;
        ResultSet rs;
        
        try{
            if(updateTable.getSelectedRow() != -1){
                stmt = Database.getDefaultConnection().createStatement();
                
                if(updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().equals("INSTEAD OF")){
                    rs = stmt.executeQuery("SELECT id_view FROM vista WHERE nomeVista = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 9).toString() + "' AND schema = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 8).toString() +"'");
                }else{
                    rs = stmt.executeQuery("SELECT id_tabella FROM tabella WHERE nomeTabella = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 9).toString() + "' AND schema = '" + updateTable.getValueAt(updateTable.getSelectedRow(), 8).toString() +"'");
                }
                
                while(rs.next()){
                    id = rs.getInt(1);
                }
                
                if(id == -1){
                    JOptionPane.showMessageDialog(this, "ERRORE nell'inserimento Oggetto del Trigger.\nAssicurati che l'OGGETTO inserto sia coerente con il TEMPO del Trigger\n\nRicorda:\nse TEMPO = INSTEAD OF allora l'OGGETTO deve essere una Vista\nse TEMPO = BEFORE oppure AFTER allora l'OGGETTO deve essere una Tabella", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                
                stmt.close();
            }
        }catch(SQLException e){
            id = -1;
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

        updatePanel = new javax.swing.JPanel();
        updateLabel = new javax.swing.JLabel();
        elemComboBox = new javax.swing.JComboBox<>();
        updateButton = new javax.swing.JButton();
        istruzioni1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        updateTable = new javax.swing.JTable();
        istruzioni2 = new javax.swing.JLabel();

        updateLabel.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        updateLabel.setText("Selezionare l'elemento che si vuole modificare : ");

        elemComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        elemComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Utente", "Ruolo", "Schema", "Tabella", "Colonna", "Vincolo", "Trigger", "Procedura", "Parametro procedura", "Variabile proc/trig", "Exception proc/trig", "Dominio", "Valore dominio", "Vista", "Asserzione", "Sequenza" }));
        elemComboBox.setSelectedIndex(-1);
        elemComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elemComboBoxActionPerformed(evt);
            }
        });

        updateButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        updateButton.setText("Modifica");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        istruzioni1.setText("Si puo modificare una sola riga per volta. Di seguito verranno mostrati solo i campi modificabili");

        javax.swing.GroupLayout updatePanelLayout = new javax.swing.GroupLayout(updatePanel);
        updatePanel.setLayout(updatePanelLayout);
        updatePanelLayout.setHorizontalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addComponent(updateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(elemComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(istruzioni1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        updatePanelLayout.setVerticalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(updateLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addComponent(elemComboBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(istruzioni1))
        );

        updateTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(updateTable);

        istruzioni2.setText("Il campo ID di ogni elemento non è modificabile: la sua modifica può portare ad errori nell'aggiornamento dei dati.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(istruzioni2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(updatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(istruzioni2)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //riempe la updateTable con gli elementi selezionati dalla elemComboBox
    private void elemComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elemComboBoxActionPerformed
        String querySelect = "";
        PreparedStatement stmt;
        ResultSet rs;
        
        int tipoChiave = -1;
        final int NUMERICA = 1;
        final int STRINGA = 2;
        
        if(elemComboBox.getSelectedItem() != null){
            switch(elemComboBox.getSelectedItem().toString()){     
                //la updateTable viene riempita con gli elementi Ruolo
                case "Ruolo" :
                    querySelect = "SELECT * FROM ruolo";
                    tipoChiave = STRINGA;
                break;
                
                //la updateTable viene riempita con gli elementi Utente
                case "Utente" :
                    querySelect = "SELECT * FROM utente";
                    tipoChiave = STRINGA;
                break;
                
                //la updateTable viene riempita con gli elementi Schema
                case "Schema" :
                    querySelect = "SELECT nomeSchema, proprietario FROM schema1";
                    tipoChiave = STRINGA;
                break;
                
                //la updateTable viene riempita con gli elementi Tabella
                case "Tabella" :
                    querySelect = "SELECT id_tabella, nomeTabella, proprietario, schema FROM tabella";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Colonna
                case "Colonna" :
                    querySelect = "SELECT C.id_colonna, C.nomeColonna, C.tipo, C.lunghezzaDati, C.nullo, C.valDefault, (SELECT D.nomeDominio FROM dominio D WHERE D.id_dominio = C.id_dominio) AS dominio,(SELECT T.nomeTabella FROM tabella T WHERE T.id_tabella = C.tabella) AS tabella, (SELECT T.schema FROM tabella T WHERE T.id_tabella = C.tabella) AS schema_tabella FROM colonna C";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Vincolo
                case "Vincolo" :
                    querySelect = "SELECT id_vincolo, nomeVincolo, tipo, condCheck, stato, proprietario FROM vincolo";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Trigger
                case "Trigger" :
                    querySelect = "SELECT T.id_trigger, T.nomeTrigger, T.tempo, T.forEachRow, T.condWhen, T.causa, T.bloccoCodice, T.proprietario, T.schema, NVL2(T.oggettoTab, (SELECT A.nomeTabella FROM tabella A WHERE A.id_tabella = T.oggettoTab), (SELECT A.nomeVista FROM vista A WHERE A.id_view = T.oggettoView)) AS Oggetto FROM trigger1 T";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Procedura
                case "Procedura" :
                    querySelect = "SELECT * FROM procedura";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Parametro
                case "Parametro procedura" :
                    querySelect = "SELECT id_parametro, nomeParam, tipo, tipologiaPar, (SELECT P.nomeProcedura FROM procedura P WHERE P.id_procedura = id_proc) AS procedura FROM parametri";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Variabile
                case "Variabile proc/trig" :
                    querySelect = "SELECT V.id_var, V.nomeVariabile, V.tipo, NVL2(V.id_trigger, (SELECT A.nomeTrigger FROM trigger1 A WHERE A.id_trigger = V.id_trigger), (SELECT A.nomeProcedura FROM procedura A WHERE A.id_procedura = V.id_proc)) AS appartiene_a FROM variabili V";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Eccezione
                case "Exception proc/trig" :
                    querySelect = "SELECT E.id_eccezione, E.nomeEccezione, E.bloccoCodice, NVL2(E.id_trigger, (SELECT A.nomeTrigger FROM trigger1 A WHERE A.id_trigger = E.id_trigger), (SELECT A.nomeProcedura FROM procedura A WHERE A.id_procedura = E.id_proc)) AS appartiene_a FROM eccezioni E";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Dominio
                case "Dominio" :
                    querySelect = "SELECT * FROM dominio";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Valore Dominio
                case "Valore dominio" :
                    querySelect = "SELECT * FROM valore";
                    tipoChiave = STRINGA;
                break;
                
                //la updateTable viene riempita con gli elementi Vista
                case "Vista" :
                    querySelect = "SELECT * FROM vista";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Asserzione
                case "Asserzione" :
                    querySelect = "SELECT * FROM asserzione";
                    tipoChiave = NUMERICA;
                break;
                
                //la updateTable viene riempita con gli elementi Sequenza
                case "Sequenza" :
                    querySelect = "SELECT * FROM sequenza";
                    tipoChiave = NUMERICA;
                break;
            }
            
            //riempimento della updateTable
            try{
                stmt = Database.getDefaultConnection().prepareStatement(querySelect);
                
                rs = stmt.executeQuery();
                
                model = new TableModel(rs);
                model.setEditable(true);
                model.setNumColumn();
                
                while(rs.next()) {
                    int riga = model.getRowCount(); 
                    model.setRowCount(model.getRowCount() + 1); //nuova riga
                    for(int c = 0; c < model.getColumnCount(); c++) {
                        Object valore = rs.getObject(c+1);
                        model.setValueAt(valore, riga, c);
                    }
                }
                
                updateTable.setModel(model);
                
                /**
                 * salvo le stringhe usate come chiave primaria da determinate tabelle
                 * (schema1, utente, ruolo, ...), in un array. L'indice a cui sono salvate le 
                 * stringhe nell'array corrisponde all'indice di riga in cui si trovano nella
                 * updateTable.
                 * 
                 * il salvataggio delle chiavi primarie di tipo stringhe viene fatto perche le 
                 * esse possono essere modificate dall'utente (a differenza degli ID). Tuttavia 
                 * serve il valore precedente della chiave per poter effettuare l'operazione UPDATE 
                 * nel database
                 */ 
                if(tipoChiave == STRINGA){
                    chiaviString = new String[updateTable.getRowCount()];
                    for(int i = 0; i < updateTable.getRowCount(); i++){
                        chiaviString[i] = new String(updateTable.getValueAt(i, 0).toString());
                    }
                }
                
                stmt.close();
            }catch(SQLException e){
                mostraErrore(e);
            }
        }
    }//GEN-LAST:event_elemComboBoxActionPerformed

    //aggiorno la riga selezionata in base al tipo di elemento scelto nella elemComboBox
    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        int righeModificate;
        PreparedStatement stmt = null;
        
        if(elemComboBox.getSelectedIndex() != -1 && updateTable.getSelectedRow() != -1){
            try{
                switch(elemComboBox.getSelectedItem().toString()){
                    //aggiorno una riga della tabella Tabella
                    case "Tabella" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpTab);
                        //nome della tabella
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //proprietario tabella
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        //schema tabella
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        
                        //ID della della riga che deve essere aggiornata
                        stmt.setInt(4, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Procedura
                    case "Procedura" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpProc);
                        //nome Procedura
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //blocco di codice
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        //tipologia della procedura (FUNZIONE, PROCEDURA)
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString().toUpperCase());
                        
                        //tipo di ritonro, non null solo nel caso la tipologia sia FUNZIONE
                        if(updateTable.getValueAt(updateTable.getSelectedRow(), 4) == null) 
                            stmt.setString(4, null);
                        else
                            stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString().toUpperCase());
                        
                        //schema Procedura
                        stmt.setString(5, updateTable.getValueAt(updateTable.getSelectedRow(), 5).toString());
                        //proprietario Procedura
                        stmt.setString(6, updateTable.getValueAt(updateTable.getSelectedRow(), 6).toString());
                        
                        //ID della riga che deve essere aggiornata
                        stmt.setInt(7, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Parametro procedura
                    case "Parametro procedura" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpPar);
                        
                        //nome parametro
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //tipologia parametro
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().toUpperCase());
                        //tipo di dato del parametro
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString().toUpperCase());
                        
                        //ID della riga di Parametri che devo aggiornare
                        stmt.setInt(4, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Dominio
                    case "Dominio" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpDom);
                        
                        //nome del Dominio
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //tipo di dato del Dominio
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().toUpperCase());
                        //proprietario del Dominio
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        //schema del Dominio
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString());
                        
                        //ID della riga di Dominio da aggiornare
                        stmt.setInt(5, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Vista
                    case "Vista" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpVista);
                        
                        //nome vista 
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //query vista
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        //schema della vista
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        //proprietario vista
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString());
                        
                        //ID della riga di Vista da aggiornare
                        stmt.setInt(5, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Asserzione
                    case "Asserzione" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpAssert);
                        
                        //nome dell'Asserzione
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //clausola Not Exists
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        //schema asserzione
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        //proprietario asserzione
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString());
                        
                        //ID della riga di Asserzione che deve essere aggiornata
                        stmt.setInt(5, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Sequenza
                    case "Sequenza" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpSeq);
                        
                        //nome della Sequenza
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        ///incremento sequenza
                        stmt.setInt(2, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString()));
                        //valore iniziale assunto
                        stmt.setInt(3, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString()));
                        //valore massimo assumibile
                        stmt.setInt(4, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString()));
                        //CYCLE (SI, NO)
                        stmt.setString(5, updateTable.getValueAt(updateTable.getSelectedRow(), 5).toString().toUpperCase());
                        //proprietario sequenza
                        stmt.setString(6, updateTable.getValueAt(updateTable.getSelectedRow(), 7).toString());
                        //schema sequenza
                        stmt.setString(7, updateTable.getValueAt(updateTable.getSelectedRow(), 6).toString());
                        
                        //ID della riga di Sequenza che deve essere aggiornata
                        stmt.setInt(8, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Ruolo
                    case "Ruolo" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpRuolo);
                        
                        //nome ruolo
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString());
                        
                        //chiave primaria della riga di Ruolo da modificare prima che venisse alterata
                        stmt.setString(2, chiaviString[updateTable.getSelectedRow()]);
                    break;
                    
                    //aggiorno una riga della tabella Schema1
                    case "Schema" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpSchema);
                        
                        //nome schema
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString());
                        //proprietario
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        
                        //ricavo il nome dello schema (chiave primaria) prima che venisse modificato
                        stmt.setString(3, chiaviString[updateTable.getSelectedRow()]);
                    break;
                    
                    //aggiorno una riga della tabella Colonna
                    case "Colonna" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpCol);
                        
                        //Nome Colonna
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        
                        //Tipo Colonna (null nel caso il tipo sia un Dominio definito dall'Utente)
                        if(updateTable.getValueAt(updateTable.getSelectedRow(), 2) == null)
                            stmt.setString(2, null);
                        else
                            stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().toUpperCase());
                        
                        //Lunghezza dati della Colonna (non null solo se il tipo è VARCHAR o CHAR)
                        if(updateTable.getValueAt(updateTable.getSelectedRow(), 3) == null || updateTable.getValueAt(updateTable.getSelectedRow(), 3).equals(""))
                            stmt.setString(3, null);
                        else
                            stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        
                        //campo Nullo colonna
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString().toUpperCase());
                        
                        //Valore di Default eventualemnte assunto dalla Colonna
                        if(updateTable.getValueAt(updateTable.getSelectedRow(), 5) == null)
                            stmt.setString(5, null);
                        else
                            stmt.setString(5, updateTable.getValueAt(updateTable.getSelectedRow(), 5).toString());
                        
                        //l'ID dell'eventuale Dominio associato alla Colonna
                        if(updateTable.getValueAt(updateTable.getSelectedRow(), 6) == null)
                            stmt.setString(6, null);
                        else
                            stmt.setInt(6, ricavaIDdominioColonna());
                        
                        //l'ID della colonna che si sta modificando
                        stmt.setInt(7, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Utente
                    case "Utente" :
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpUtente);
                        
                        //username utente
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString());
                        //nome utente
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //cognome utente
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        //password utente
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        //ruolo utente
                        stmt.setString(5, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString());
                        
                        //ricavo l'username dell'utente prima che venisse modificato
                        stmt.setString(6, chiaviString[updateTable.getSelectedRow()]);
                    break;
                    
                    //aggiorno una riga della tabella Trigger1
                    case "Trigger":
                        if(ricavaIdOggettoTrigger() != -1){
                            stmt = Database.getDefaultConnection().prepareStatement(queryUpTrigger);
                            
                            //nome Trigger
                            stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                            //tempo Trigger
                            stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().toUpperCase());
                            //campo For Each Row
                            stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString().toUpperCase());
                            
                            //condizione When del Trigger
                            if(updateTable.getValueAt(updateTable.getSelectedRow(), 4) == null)
                                stmt.setString(4, null);
                            else    
                                stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString());
                            
                            //causa Trigger
                            stmt.setString(5, updateTable.getValueAt(updateTable.getSelectedRow(), 5).toString().toUpperCase());
                            //blocco codice del Trigger
                            stmt.setString(6, updateTable.getValueAt(updateTable.getSelectedRow(), 6).toString());
                            //proprietario Trigger
                            stmt.setString(7, updateTable.getValueAt(updateTable.getSelectedRow(), 7).toString());
                            //schema Trigger
                            stmt.setString(8, updateTable.getValueAt(updateTable.getSelectedRow(), 8).toString());
                            
                            //oggetto del trigger
                            if(updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().equals("INSTEAD OF")){
                                stmt.setString(9, null);
                                stmt.setInt(10, ricavaIdOggettoTrigger());
                            }else{
                                stmt.setString(10, null);
                                stmt.setInt(9, ricavaIdOggettoTrigger());
                            }
                            
                            //l'ID del trigger che si sta modificando
                            stmt.setInt(11, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                        }
                    break;   
                    
                    //aggiorno una riga della tabella Variabili
                    case "Variabile proc/trig":
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpVar);
                        
                        //nome della Variabile
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //tipo della Variabile
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString().toUpperCase());
                        
                        //l'ID della Variabile che si sta modificando
                        stmt.setInt(3, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Eccezioni
                    case "Exception proc/trig":
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpExc);
                        
                        //nome della Exception
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //tipo della Exception
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 2).toString());
                        
                        //l'ID della Exception che si sta modificando
                        stmt.setInt(3, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;
                    
                    //aggiorno una riga della tabella Valore
                    case "Valore dominio":
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpVal);
                        
                        //nome del Valore
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString());
                        
                        //ricavo il nome del Valore prima che venisse modificato
                        stmt.setString(2, chiaviString[updateTable.getSelectedRow()]);
                        //l'ID del Dominio a cui appartiene il Valore che si sta modificando
                        stmt.setInt(3, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString()));
                    break;    
                    
                    //aggiorno una riga della tabella Vincolo
                    case "Vincolo":
                        stmt = Database.getDefaultConnection().prepareStatement(queryUpVin);
                        
                        //nome del Vincolo
                        stmt.setString(1, updateTable.getValueAt(updateTable.getSelectedRow(), 1).toString());
                        //condizione Check 
                        stmt.setString(2, updateTable.getValueAt(updateTable.getSelectedRow(), 3).toString());
                        //stato Vincolo
                        stmt.setString(3, updateTable.getValueAt(updateTable.getSelectedRow(), 4).toString().toUpperCase());
                        //proprietario Vincolo
                        stmt.setString(4, updateTable.getValueAt(updateTable.getSelectedRow(), 5).toString());
                        
                        //recupero l'ID del vincolo da modificare
                        stmt.setInt(5, Integer.parseInt(updateTable.getValueAt(updateTable.getSelectedRow(), 0).toString()));
                    break;    
                }
                
                //esecuzione dell' istruzione UPDATE costruita sopra
                if(stmt != null){
                    righeModificate = stmt.executeUpdate();
                
                    //modifica riuscita
                    if(righeModificate != 0){
                        JOptionPane.showMessageDialog(this, "Riga aggiornata correttamente !", "Aggiornamento Effettuato", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    stmt.close();
                }
                    
            }catch(SQLException e){
                mostraErrore(e);
            }
        }else{
            //non si può modificare una riga se non viene prima selezionata
            JOptionPane.showMessageDialog(this, "Attenzione, prima di effettuare una modifica,\nselezionare la riga da modificare.", "Attenzione", JOptionPane.WARNING_MESSAGE);            
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> elemComboBox;
    private javax.swing.JLabel istruzioni1;
    private javax.swing.JLabel istruzioni2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel updateLabel;
    private javax.swing.JPanel updatePanel;
    private javax.swing.JTable updateTable;
    // End of variables declaration//GEN-END:variables
}
