INSERT INTO ruolo VALUES('amministratore');

/

INSERT INTO ruolo VALUES('ospite');

/

INSERT INTO privSistema VALUES('ALL PRIVILEGIES');

/

INSERT INTO privSistema VALUES('CONNECT');

/

INSERT INTO privOggetto VALUES('SELECT');

/

INSERT INTO compRuoloSistema VALUES('amministratore', 'ALL PRIVILEGIES');

/

INSERT INTO compRuoloSistema VALUES('ospite', 'CONNECT');

/

INSERT INTO compRuoloOggetto VALUES('ospite', 'SELECT');

/

INSERT INTO utente VALUES('lucapastore', 'Luca', 'Pastore', '12345', 'amministratore');

/

INSERT INTO utente VALUES('andreaRossi', 'Andrea', 'Rossi', 'abcde', 'ospite');

/

INSERT INTO utente VALUES('federicaBianchi', 'Federica', 'Bianchi', '67890', 'ospite');

/

INSERT INTO schema1 VALUES('biblioteca', 'lucapastore');

/

INSERT INTO dominio(id_dominio, nomeDominio, Tipo, Proprietario, schema) VALUES(1, 'tipoProvenienza', 'VARCHAR', 'lucapastore', 'biblioteca');

/

INSERT INTO valore VALUES('ACQUISTO', 1);

/

INSERT INTO valore VALUES('DONAZIONE', 1);

/

INSERT INTO valore VALUES('LASCITO', 1);

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(1, 'BIBLIOTECA', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(2, 'DESCRITTORE', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(3, 'DESCRIBIBL', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(4, 'COPIEFISICHE', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(5, 'DESCRCOPIE', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(6, 'DIGITALE', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(7, 'DEPOSITO', 'lucapastore', 'biblioteca');

/

INSERT INTO tabella(id_tabella, nomeTabella, Proprietario, schema) VALUES(8, 'CARTACEO', 'lucapastore', 'biblioteca');

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(1, 'codice', 'VARCHAR', 30, 'NO', null, 1, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(2, 'indirizzo', 'VARCHAR', 50, 'NO', null, 1, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(3, 'nome', 'VARCHAR', 20, 'NO', null, 1, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(4, 'homePage', 'VARCHAR', 50, 'NO', null, 1, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(5, 'ISBN', 'INTEGER', null, 'NO', null, 2, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(6, 'formato', 'VARCHAR', 20, 'NO', null, 2, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(7, 'testo', 'VARCHAR', 100, 'NO', null, 2, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(8, 'ISBN_var', 'INTEGER', null, 'NO', null, 2, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(9, 'formato_var', 'VARCHAR', 20, 'NO', null, 2, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(10, 'codice_bib', 'VARCHAR', 30, 'NO', null, 3, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(11, 'ISBN', 'INTEGER', null, 'NO', null, 3, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(12, 'formato', 'VARCHAR', 20, 'NO', null, 3, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(13, 'CODinventario', 'VARCHAR', 20, 'NO', null, 4, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(14, 'collezione', 'VARCHAR', 50, 'NO', null, 4, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(15, 'data_acquisizione', 'DATE', null, 'SI', null, 4, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(16, 'data_acquisto', 'DATE', null, 'SI', null, 4, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(17, 'provenienza', null, null, 'NO', null, 4, 1);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(18, 'codice_bib', 'VARCHAR', 30, 'NO', null, 4, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(19, 'ISBN', 'INTEGER', null, 'NO', null, 5, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(20, 'formato', 'VARCHAR', 20, 'NO', null, 5, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(21, 'codInventario', 'VARCHAR', 20, 'NO', null, 5, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(22, 'path', 'VARCHAR', 30, 'NO', null, 6, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(23, 'accesso', 'VARCHAR', 30, 'NO', null, 6, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(24, 'dim', 'VARCHAR', 20, 'NO', null, 6, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(25, 'formato', 'VARCHAR', 10, 'NO', null, 6, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(26, 'codInventario', 'VARCHAR', 20, 'NO', null, 6, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(27, 'cod_deposito', 'VARCHAR', 20, 'NO', null, 7, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(28, 'indirizzo', 'VARCHAR', 50, 'NO', null, 7, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(29, 'stanza', 'VARCHAR', 10, 'NO', null, 7, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(30, 'accesso', 'VARCHAR', 20, 'NO', null, 7, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(31, 'codice_bib', 'VARCHAR', 30, 'NO', null, 7, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(32, 'codiceBarre', 'INTEGER', null, 'NO', null, 8, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(33, 'collocazione', 'VARCHAR', 30, 'NO', null, 8, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(34, 'stato', 'VARCHAR', 20, 'NO', null, 8, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(35, 'codInventario', 'VARCHAR', 20, 'NO', null, 8, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(36, 'cod_deposito', 'VARCHAR', 20, 'NO', null, 8, null);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(1, 'pk_biblioteca', 'PRIMARY KEY', null, 'ABILITATO', 1, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(1, 1);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(2, 'pk_descrittore', 'PRIMARY KEY', null, 'ABILITATO', 2, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(5, 2);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(6, 2);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(3, 'fk_descrittore', 'FOREIGN KEY', null, 'ABILITATO', 2, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(8, 3);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(9, 3);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(1, 'NO ACTION', 3, 2);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(5, 1);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(6, 1);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(4, 'fk_bibl', 'FOREIGN KEY', null, 'ABILITATO', 3, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(10, 4);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(2, 'NO ACTION', 4, 3);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(1, 2);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(5, 'fk_descrittore2', 'FOREIGN KEY', null, 'ABILITATO', 3, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(11, 5);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(12, 5);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(3, 'NO ACTION', 5, 3);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(5, 3);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(6, 3);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(6, 'pk_copieFisiche', 'PRIMARY KEY', null, 'ABILITATO', 4, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(13, 6);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(7, 'vincolo_acquisto', 'CHECK', 'provenienza = ACQUISTO OR data_acquisto IS NULL', 'ABILITATO', 4, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(17,7);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(16,7);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(8, 'vincolo_acquisizione', 'CHECK', 'provenienza <> ACQUISTO OR data_acquisizione IS NULL', 'ABILITATO', 4, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(17,8);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(15,8);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(9, 'fk_copieFisiche', 'FOREIGN KEY', null, 'ABILITATO', 4, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(18,9);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(4, 'NO ACTION', 9, 1);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(1, 4);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(10, 'fk1_descrCopie', 'FOREIGN KEY', null, 'ABILITATO', 5, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(19,10);

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(20,10);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(5, 'NO ACTION', 10, 2);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(5, 5);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(6, 5);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(11, 'fk2_descrCopie', 'FOREIGN KEY', null, 'ABILITATO', 5, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(21,11);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(6, 'NO ACTION', 11, 4);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(13, 6);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(12, 'pk_digitale', 'PRIMARY KEY', null, 'ABILITATO', 6, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(22,12);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(13, 'fk_digitale', 'FOREIGN KEY', null, 'ABILITATO', 6, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(26,13);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(7, 'NO ACTION', 13, 4);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(13, 7);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(14, 'pk_deposito', 'PRIMARY KEY', null, 'ABILITATO', 7, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(27,14);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(15, 'fk_deposito', 'FOREIGN KEY', null, 'ABILITATO', 7, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(31,15);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(8, 'NO ACTION', 15, 1);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(1, 8);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(16, 'pk_cartaceo', 'PRIMARY KEY', null, 'ABILITATO', 8, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(32,16);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(17, 'fk1_cartaceo', 'FOREIGN KEY', null, 'ABILITATO', 8, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(35,17);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(9, 'CASCADE', 17, 4);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(13, 9);

/

INSERT INTO vincolo(ID_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(18, 'fk2_cartaceo', 'FOREIGN KEY', null, 'ABILITATO', 8, 'lucapastore');

/

INSERT INTO vincoloColonna(ID_colonna, ID_vincolo) VALUES(36,18);

/

INSERT INTO chiaveEsterna(ID_fk, regCanc, ID_vincolo, ID_tabRef) VALUES(10, 'CASCADE', 18, 7);

/

INSERT INTO colonne_FK(ID_colonnaRef, ID_fkCons) VALUES(27, 10);

/

INSERT INTO vista VALUES (1, 'descrittoreCopie', 'SELECT .....', 'biblioteca', 'lucapastore');

/

INSERT INTO procedura VALUES(1, 'calcolaISBN', '/*calcolo ISBN*/', 'FUNZIONE', 'VARCHAR', 'biblioteca', 'lucapastore');

/

INSERT INTO variabili VALUES(1, 'ISBN', 'VARCHAR', null, 1);

/

INSERT INTO variabili VALUES(2, 'cont', 'INTEGER', null, 1);

/

INSERT INTO variabili VALUES(3, 'msg', 'VARCHAR', null, 1);

/

INSERT INTO eccezioni VALUES(1, 'erroreCalcoloISBN', 'RAISE_APPLICATION_ERROR(-20001, msg);', null, 1);

/

INSERT INTO parametri VALUES(1, 'formato', 'VARCHAR', 'INPUT', 1);

/

INSERT INTO trigger1 VALUES(1, 'setISBN', 'BEFORE', 'SI', null, 'INSERT', '/*richiama procedura calcolaISBN*/', 'lucapastore', 'biblioteca', 2, null);

/

INSERT INTO schema1 VALUES('formula1', 'lucapastore');

/

INSERT INTO dominio VALUES(2, 'puntiGara', 'INTEGER', 'lucapastore', 'formula1');

/

INSERT INTO valore VALUES(25, 2);

/

INSERT INTO valore VALUES(18, 2);

/

INSERT INTO valore VALUES(15, 2);

/

INSERT INTO valore VALUES(12, 2);

/

INSERT INTO valore VALUES(10, 2);

/

INSERT INTO valore VALUES(8, 2);

/

INSERT INTO valore VALUES(6, 2);

/

INSERT INTO valore VALUES(4, 2);

/

INSERT INTO valore VALUES(2, 2);

/

INSERT INTO valore VALUES(1, 2);

/

INSERT INTO tabella(ID_tabella, nomeTabella, proprietario, schema) VALUES(9, 'pilota', 'lucapastore', 'formula1');

/

INSERT INTO tabella(ID_tabella, nomeTabella, proprietario, schema) VALUES(10, 'scuderia', 'lucapastore', 'formula1');

/

INSERT INTO tabella(ID_tabella, nomeTabella, proprietario, schema) VALUES(11, 'gara', 'lucapastore', 'formula1');

/

INSERT INTO tabella(ID_tabella, nomeTabella, proprietario, schema) VALUES(12, 'griglia', 'lucapastore', 'formula1');

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(37, 'numero', 'INTEGER', null, 'NO', null, 9, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(38, 'nomePilota', 'VARCHAR', 30, 'NO', null, 9, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(39, 'cognomePilota', 'VARCHAR', 30, 'NO', null, 9, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(40, 'scuderia', 'INTEGER', null, 'NO', null, 9, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(41, 'id_scuderia', 'INTEGER', null, 'NO', null, 10, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(42, 'nomeScuderia', 'VARCHAR', 30, 'NO', null, 10, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(43, 'paeseScuderia', 'VARCHAR', 50, 'NO', null, 10, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(44, 'id_gara', 'INTEGER', null, 'NO', null, 11, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(45, 'paeseGara', 'VARCHAR', 50, 'NO', null, 11, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(46, 'pilota', 'INTEGER', null, 'NO', null, 12, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(47, 'ID_gara', 'INTEGER', null, 'NO', null, 12, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(48, 'posizione', 'INTEGER', null, 'NO', null, 12, null);

/

INSERT INTO colonna(id_colonna, nomeColonna, tipo, lunghezzaDati, nullo, valDefault, tabella, ID_dominio) VALUES(49, 'puntiOttenuti', null, null, 'NO', null, 12, 2);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(19, 'pk_pilota', 'PRIMARY KEY', null, 'ABILITATO', 9, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(37, 19);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(20, 'fk_pilota', 'FOREIGN KEY', null, 'ABILITATO', 9, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(40, 20);

/

INSERT INTO chiaveEsterna(id_fk, regCanc, id_vincolo, id_tabRef) VALUES(11, 'CASCADE', 20, 10);

/

INSERT INTO colonne_FK(ID_colonnaRef, id_fkCons) VALUES(41, 11);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(21, 'pk_scuderia', 'PRIMARY KEY', null, 'ABILITATO', 10, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(41, 21);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(22, 'pk_gara', 'PRIMARY KEY', null, 'ABILITATO', 11, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(44, 22);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(23, 'fk1_griglia', 'FOREIGN KEY', null, 'ABILITATO', 12, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(46, 23);

/

INSERT INTO chiaveEsterna(id_fk, regCanc, id_vincolo, id_tabRef) VALUES(12, 'CASCADE', 23, 9);

/

INSERT INTO colonne_FK(ID_colonnaRef, id_fkCons) VALUES(37, 12);

/

INSERT INTO vincolo(id_vincolo, nomeVincolo, tipo, condCheck, stato, tabella, proprietario) VALUES(24, 'fk2_griglia', 'FOREIGN KEY', null, 'ABILITATO', 12, 'lucapastore');

/

INSERT INTO vincoloColonna(id_colonna, id_vincolo) VALUES(47, 24);

/

INSERT INTO chiaveEsterna(id_fk, regCanc, id_vincolo, id_tabRef) VALUES(13, 'CASCADE', 24, 11);

/

INSERT INTO colonne_FK(ID_colonnaRef, id_fkCons) VALUES(44, 13);

/

INSERT INTO sequenza VALUES(1, 'IDscuderia', 1, 1, 30, 'NO', 'lucapastore', 'formula1');

/

INSERT INTO sequenza VALUES(2, 'IDgara', 1, 1, 25, 'NO', 'lucapastore', 'formula1');

/

INSERT INTO trigger1 VALUES(2, 'setIdScuderia', 'BEFORE', 'SI', 'NEW.ID_scud IS NULL', 'INSERT', ':NEW.id_scud = IDscuderia.NextVal;', 'lucapastore', 'formula1', 10, null);

/

INSERT INTO trigger1 VALUES(3, 'setIdGara', 'BEFORE', 'SI', 'NEW.ID_gara IS NULL', 'INSERT', ':NEW.id_gara = IDgara.NextVal;', 'lucapastore', 'formula1', 11, null);

/

INSERT INTO vista VALUES(2, 'ClassificaPiloti', 'SELECT...', 'formula1', 'lucapastore');

/

COMMIT;