/**
 * file: Insert.java
 */
package progettopastoreluca;

import javax.swing.*;

/**
 * Pannello che permette la scelta dell'oggetto da inserire nel database
 * una volta selezionata una scelta, nel sottopannello insertPanel verrà inserito 
 * un nuovo pannello di volta in volta che permette di inserire l'oggetto scelto
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class Insert extends javax.swing.JPanel {
    private final InsertUSer iU = new InsertUSer(); //crea oggetto InsertUSer per inserimento elemento utente nel database
    private final InsertRuolo iR = new InsertRuolo(); //crea oggetto InsertRuolo per inserimento elemento ruolo nel database
    private final InsertPrivilegio iP = new InsertPrivilegio(); //crea oggetto InsertPrivilegio per inserimento elemento privilegio nel database
    private final InsertSchema iS = new InsertSchema(); //crea oggetto InsertSchema per inserimento elemento schema nel database
    private final InsertTabella iT = new InsertTabella(); //crea oggetto InsertTabella per inserimento elemento tabella nel database
    private final InsertColonna iC = new InsertColonna(); //crea oggetto InsertColonna per inserimento elemento colonna nel database
    private final InsertDominio iD = new InsertDominio(); //crea oggetto InsertDominio per inserimento elemento dominio nel database
    private final InsertSequenza iSeq = new InsertSequenza(); //crea oggetto InsertSequenza per inserimento elemento sequenza nel database
    private final InsertVista iV = new InsertVista(); //crea oggetto InsertVista per inserimento elemento vista nel database
    private final InsertAssertion iA = new InsertAssertion(); //crea oggetto InsertAssertion per inserimento elemento asserzione nel database
    private final InsertTrigger iTr = new InsertTrigger(); //crea oggetto InsertTrigger per inserimento elemento trigger nel database
    private final InsertProcedura iPr = new InsertProcedura(); //crea oggetto InsertProcedura per inserimento elemento procedura/funzione nel database
    private final InsertVincolo iVinc = new InsertVincolo(); //crea oggetto InsertVincolo per inserimento elemento Vincolo nel database
    private final AddValueToDom addVal = new AddValueToDom(); //crea oggetto AddValueToDom per inserimento elemento Valore nel database
    private final AddParamToProc addPar = new AddParamToProc(); //crea oggetto AddParamToProc per inserimento elemento Parametro nel database
    private final AddVariabile addVarTr = new AddVariabile(AddVariabile.TRIGGER); //crea oggetto AddVariabile per aggiungere elemento Variabile a Trigger
    private final AddVariabile addVarPr = new AddVariabile(AddVariabile.PROCEDURA); //crea oggetto AddVariabile per aggiungere elemento Variabile a Procedura
    private final AddException addExcTr = new AddException(AddException.TRIGGER); //crea oggetto AddException per aggiungere elemento Eccezione a Trigger
    private final AddException addExcPr = new AddException(AddException.PROCEDURA); //crea oggetto AddException per aggiungere elemento Eccezione a Procedura
    private final AddProcRichiamata addProcRic = new AddProcRichiamata(); //crea oggetto AddProcRichiamata per aggiungere elemento Eccezione a Procedura
    
    /**
     * Crea un nuovo oggetto Insert
     */
    public Insert() {
        this.setSize(770, 510);
        initComponents();
        oggComboBox.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        insertButtonGroup = new javax.swing.ButtonGroup();
        insertLabel = new javax.swing.JLabel();
        insertOptionPanel = new javax.swing.JPanel();
        userRadioButton = new javax.swing.JRadioButton();
        ruoloRadioButton = new javax.swing.JRadioButton();
        privRadioButton = new javax.swing.JRadioButton();
        schemaRadioButton = new javax.swing.JRadioButton();
        oggRadioButton = new javax.swing.JRadioButton();
        oggComboBox = new javax.swing.JComboBox<>();
        insertPanel = new javax.swing.JPanel();

        insertButtonGroup.add(userRadioButton);
        insertButtonGroup.add(ruoloRadioButton);
        insertButtonGroup.add(privRadioButton);
        insertButtonGroup.add(schemaRadioButton);
        insertButtonGroup.add(oggRadioButton);

        insertLabel.setFont(new java.awt.Font("Impact", 0, 30)); // NOI18N
        insertLabel.setForeground(new java.awt.Color(0, 51, 255));
        insertLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        insertLabel.setText("SELEZIONARE L' ELEMENTO CHE SI DESIDERA INSERIRE");
        insertLabel.setToolTipText("");

        userRadioButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        userRadioButton.setText("Utente");
        userRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userRadioButtonActionPerformed(evt);
            }
        });

        ruoloRadioButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        ruoloRadioButton.setText("Ruolo");
        ruoloRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruoloRadioButtonActionPerformed(evt);
            }
        });

        privRadioButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        privRadioButton.setText("Privilegio");
        privRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                privRadioButtonActionPerformed(evt);
            }
        });

        schemaRadioButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        schemaRadioButton.setText("Schema");
        schemaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schemaRadioButtonActionPerformed(evt);
            }
        });

        oggRadioButton.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        oggRadioButton.setText("Oggetto DB");
        oggRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oggRadioButtonActionPerformed(evt);
            }
        });

        oggComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oggComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tabella", "Colonna", "Dominio", "Vincolo", "Asserzione", "Sequenza", "Trigger", "Procedura/Funzione", "Vista", "Valore a Dominio", "Parametro a Procedura", "Variabile a Procedura", "Variabile a Trigger", "Eccezione a Procedura", "Eccezione a Trigger", "Procedura richiamata" }));
        oggComboBox.setSelectedIndex(-1);
        oggComboBox.setEnabled(false);
        oggComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oggComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout insertOptionPanelLayout = new javax.swing.GroupLayout(insertOptionPanel);
        insertOptionPanel.setLayout(insertOptionPanelLayout);
        insertOptionPanelLayout.setHorizontalGroup(
            insertOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertOptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userRadioButton)
                .addGap(18, 18, 18)
                .addComponent(ruoloRadioButton)
                .addGap(18, 18, 18)
                .addComponent(privRadioButton)
                .addGap(18, 18, 18)
                .addComponent(schemaRadioButton)
                .addGap(18, 18, 18)
                .addComponent(oggRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(oggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        insertOptionPanelLayout.setVerticalGroup(
            insertOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(insertOptionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(insertOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(insertOptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userRadioButton)
                        .addComponent(ruoloRadioButton)
                        .addComponent(privRadioButton)
                        .addComponent(schemaRadioButton)
                        .addComponent(oggRadioButton))
                    .addComponent(oggComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout insertPanelLayout = new javax.swing.GroupLayout(insertPanel);
        insertPanel.setLayout(insertPanelLayout);
        insertPanelLayout.setHorizontalGroup(
            insertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        insertPanelLayout.setVerticalGroup(
            insertPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(insertLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
            .addComponent(insertPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(insertOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(insertLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(insertOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(insertPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //Metodo che rimuove tutte le componnti nel pannello insertPanel ed aggiunge ad esso il JPanel p, passato come parametro    
    private void viewPanel(JPanel p){
        insertPanel.removeAll();
        insertPanel.add(p);
        repaintPanel();
    }
    
    //metodo utilizzato per ridiseganre il pannello insertPanel quando viene effettuata una nuova scelta
    private void repaintPanel(){
        insertPanel.revalidate();
        insertPanel.repaint();
    }
    
    //metodo che inserisce il pannello iU nel insertPanel alla selezione del RadioButton utente
    private void userRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userRadioButtonActionPerformed
        viewPanel(iU);
        iU.riempiComboBox();
        oggComboBox.setEnabled(false);
    }//GEN-LAST:event_userRadioButtonActionPerformed

    //metodo che inserisce il pannello iR nel insertPanel alla selezione del RadioButton Ruolo
    private void ruoloRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruoloRadioButtonActionPerformed
        viewPanel(iR);
        oggComboBox.setEnabled(false);
    }//GEN-LAST:event_ruoloRadioButtonActionPerformed

    //metodo che inserisce il pannello iP nel insertPanel alla selezione del RadioButton privilegio
    private void privRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privRadioButtonActionPerformed
        viewPanel(iP);
        oggComboBox.setEnabled(false);
    }//GEN-LAST:event_privRadioButtonActionPerformed

    //metodo che inserisce il pannello iS nel insertPanel alla selezione del RadioButton schema
    private void schemaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schemaRadioButtonActionPerformed
        viewPanel(iS);
        iS.riempiComboBox();
        oggComboBox.setEnabled(false);
    }//GEN-LAST:event_schemaRadioButtonActionPerformed

    //metodo che abilita la ComboBox "oggComboBox" che permette la scelta dell'oggetto del DB da inserire alla selezione del RadioButton oggetto DB
    private void oggRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oggRadioButtonActionPerformed
        insertPanel.removeAll();
        repaintPanel();
        oggComboBox.setEnabled(true);
    }//GEN-LAST:event_oggRadioButtonActionPerformed

    //metodo che, in base alla scelta della ComboBox, aggiunge il pannello del corrispondete elemento da inserire nel DB all'interno del pannello insertPanel
    private void oggComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oggComboBoxActionPerformed
        String scelta = (String)oggComboBox.getSelectedItem();
        
        switch(scelta){
            //inserisce nel insertPanel il pannello iT che permette l'aggiunta di una tabella all'interno del database
            case "Tabella":
                viewPanel(iT);
                iT.riempiComboBox();
            break;
            
            //inserisce nel insertPanel il pannello iC che permette l'aggiunta di una colonna all'interno del database
            case "Colonna":
                viewPanel(iC);
                iC.riempiTabColComboBox();
            break;
            
            //inserisce nel insertPanel il pannello iVinc che permette l'aggiunta di un vincolo all'interno del database
            case "Vincolo":
                viewPanel(iVinc);
                iVinc.riempiComboBox();
            break;
            
            //inserisce nel insertPanel il pannello iD che permette l'aggiunta di un dominio all'interno del database
            case "Dominio":
                viewPanel(iD);
                iD.riempiComboBox();
            break;
            
            //inserisce nel insertPanel il pannello iV che permette l'aggiunta di una vista all'interno del database
            case "Vista":
                viewPanel(iV);
                iV.riempiComboBox();
            break; 
            
            //inserisce nel insertPanel il pannello iSeq che permette l'aggiunta di una sequenza all'interno del database
            case "Sequenza":
                viewPanel(iSeq);
                iSeq.riempiComboBox();
            break;    
            
            //inserisce nel insertPanel il pannello iA che permette l'aggiunta di un' asserzione all'interno del database
            case "Asserzione":
                viewPanel(iA);
                iA.riempiComboBox();
            break; 
            
            //inserisce nel insertPanel il pannello iTr che permette l'aggiunta di un trigger all'interno del database
            case "Trigger":
                viewPanel(iTr);
                iTr.riempiComboBox();
            break;
            
            //inserisce nel insertPanel il pannello iPr che permette l'aggiunta di una procedura/funzione all'interno del database
            case "Procedura/Funzione":
                viewPanel(iPr);
                iPr.riempiComboBox();
            break;
            
            //inserisce nel insertPanel il pannello addVal che permette l'aggiunta di un Valore ad un Dominio
            case "Valore a Dominio":
                viewPanel(addVal);
                addVal.riempiDominioComboBox();
            break;   
            
            //inserisce nel insertPanel il pannello addPar che permette l'aggiunta di un Parametro ad una Procedura / Funzione
            case "Parametro a Procedura":
                viewPanel(addPar);
                addPar.riempiProcComboBox();
            break; 
            
            //inserisce nel insertPanel il pannello addVarPr che permette l'aggiunta di una Variabile ad una Procedura / Funzione
            case "Variabile a Procedura":
                viewPanel(addVarPr);
                addVarPr.riempiProceduraComboBox();
            break; 
            
            //inserisce nel insertPanel il pannello addVarTr che permette l'aggiunta di una Variabile ad un Trigger
            case "Variabile a Trigger":
                viewPanel(addVarTr);
                addVarTr.riempiTriggerComboBox();
            break;
            
            //inserisce nel insertPanel il pannello addExcTr che permette l'aggiunta di una Eccezione ad un Trigger
            case "Eccezione a Trigger":
                viewPanel(addExcTr);
                addExcTr.riempiTriggerComboBox();
            break;
            
            //inserisce nel insertPanel il pannello addExcPr che permette l'aggiunta di una Eccezione ad una Procedura o Funzione
            case "Eccezione a Procedura":
                viewPanel(addExcPr);
                addExcPr.riempiProceduraComboBox();
            break;
            
            //inserisce nel insertPanel il pannello addProcRic che permette l'aggiunta di una chiamata a Procedura fatta da un'aòtra Procedura
            case "Procedura richiamata":
                viewPanel(addProcRic);
                addProcRic.riempiChiamanteComboBox();
            break;
        }
        
        repaintPanel();
    }//GEN-LAST:event_oggComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup insertButtonGroup;
    private javax.swing.JLabel insertLabel;
    private javax.swing.JPanel insertOptionPanel;
    private javax.swing.JPanel insertPanel;
    private javax.swing.JComboBox<String> oggComboBox;
    private javax.swing.JRadioButton oggRadioButton;
    private javax.swing.JRadioButton privRadioButton;
    private javax.swing.JRadioButton ruoloRadioButton;
    private javax.swing.JRadioButton schemaRadioButton;
    private javax.swing.JRadioButton userRadioButton;
    // End of variables declaration//GEN-END:variables
}
