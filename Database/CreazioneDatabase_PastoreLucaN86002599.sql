CREATE TABLE ruolo(
	NomeRuolo	VARCHAR2(50) NOT NULL,

	CONSTRAINT pk_ruolo PRIMARY KEY(NomeRuolo)
);

/

CREATE TABLE utente(
	UserName	VARCHAR2(50),
	Nome		VARCHAR2(50)	NOT NULL,
	Cognome		VARCHAR2(50)	NOT NULL,
	Password	VARCHAR2(50)	NOT NULL,
	Ruolo		VARCHAR2(50)	NOT NULL,
	
	CONSTRAINT pk_utente PRIMARY KEY(UserName),
	CONSTRAINT fk_utente FOREIGN KEY(Ruolo) REFERENCES ruolo(NomeRuolo)
);

/

CREATE TABLE privSistema(
	NomePrivSist	VARCHAR2(16) NOT NULL,

	CONSTRAINT pk_privSis PRIMARY KEY(NomePrivSist),
	CONSTRAINT sys_priv CHECK (NomePrivSist = 'CREATE SESSION' OR NomePrivSist = 'CONNECT' OR 
    	NomePrivSist = 'CREATE TABLE' OR NomePrivSist = 'CREATE VIEW' OR NomePrivSist = 'CREATE SEQUENCE' OR 
    	NomePrivSist = 'DROP TABLE' OR NomePrivSist = 'DROP VIEW' OR NomePrivSist = 'DROP SEQUENCE' OR 
    	NomePrivSist = 'ALL PRIVILEGIES' OR  NomePrivSist = 'CREATE SCHEMA'  OR  NomePrivSist = 'CREATE DOMAIN' OR  
		NomePrivSist = 'CREATE ASSERTION'  OR  NomePrivSist = 'CREATE TRIGGER'  OR  NomePrivSist = 'CREATE PROCEDURE')
);

/

CREATE TABLE privOggetto(
	NomePrivOgg	VARCHAR2(6) NOT NULL,

	CONSTRAINT pk_privOgg PRIMARY KEY (NomePrivOgg),
	CONSTRAINT "OGG_PRIV" CHECK ( NomePrivOgg = 'ALTER' OR NomePrivOgg = 'SELECT' OR NomePrivOgg = 'DELETE' OR 
		NomePrivOgg = 'INSERT' OR NomePrivOgg = 'UPDATE' )
);

/

CREATE TABLE compRuoloSistema(
	ID_ruolo	VARCHAR2(50)	NOT NULL,
	ID_privSis	VARCHAR2(15) 	NOT NULL,

	CONSTRAINT fk_rs1 FOREIGN KEY(ID_ruolo) REFERENCES ruolo (NomeRuolo) ON DELETE CASCADE,
	CONSTRAINT fk_rs2 FOREIGN KEY(ID_privSis) REFERENCES privSistema(NomePrivSist) ON DELETE CASCADE
);

/

CREATE TABLE compRuoloOggetto(
	ID_ruolo	VARCHAR2(50)	NOT NULL,
	ID_privOgg	VARCHAR2(6)		NOT NULL,

	CONSTRAINT fk_ro1 FOREIGN KEY(ID_ruolo) REFERENCES ruolo (NomeRuolo) ON DELETE CASCADE,
	CONSTRAINT fk_ro2 FOREIGN KEY(ID_privOgg) REFERENCES privOggetto(NomePrivOgg) ON DELETE CASCADE
);

/

CREATE TABLE schema1(
	NomeSchema		VARCHAR2(50),
	Proprietario	VARCHAR2(50) NOT NULL,

	CONSTRAINT pk_schema PRIMARY KEY (NomeSchema),
	CONSTRAINT fk_schema FOREIGN KEY(Proprietario) REFERENCES utente(UserName) 	
);

/

CREATE TABLE tabella(
	ID_tabella		INTEGER,
	NomeTabella		VARCHAR2(50)			NOT NULL,
	DataCreazione	DATE					NOT NULL,
	UltimaModifica	DATE					NOT NULL,
	Proprietario 	VARCHAR2(50) 			NOT NULL,
	Schema			VARCHAR2(50)  			NOT NULL,

	CONSTRAINT pk_tabella PRIMARY KEY (ID_tabella),
	CONSTRAINT fk1_tab FOREIGN KEY (Proprietario) REFERENCES utente(userName),
	CONSTRAINT fk2_tab FOREIGN KEY (Schema) REFERENCES schema1 (NomeSchema) ON DELETE CASCADE,
	CONSTRAINT V3_4 UNIQUE(NomeTabella, Schema) 
);

/

CREATE TABLE asserzione(
	ID_asserzione		INTEGER,
	NomeAsserzione		VARCHAR2(50)	NOT NULL,
	ClausolaNotExists	VARCHAR2(4000)	NOT NULL,
	Schema				VARCHAR2(50)	NOT NULL,
	Proprietario		VARCHAR2(50)	NOT NULL,

	CONSTRAINT pk_assert PRIMARY KEY(ID_asserzione),
	CONSTRAINT fk1_assert FOREIGN KEY(Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT fk2_assert FOREIGN KEY(Proprietario) REFERENCES utente(UserName),
	CONSTRAINT V13 UNIQUE (NomeAsserzione, Schema)	
);

/

CREATE TABLE dominio(
	ID_dominio		INTEGER,
	NomeDominio		VARCHAR2(50) 	NOT NULL,
	Tipo			VARCHAR2(10) 	NOT NULL,
	Proprietario 	VARCHAR2(50)	NOT NULL,
	Schema			VARCHAR2(50)   	NOT NULL,

	CONSTRAINT pk_dominio PRIMARY KEY (ID_dominio),	
	CONSTRAINT fk1_dominio FOREIGN KEY(Proprietario) REFERENCES utente(userName),
	CONSTRAINT fk2_dominio FOREIGN KEY(Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT tipoDatoDominio CHECK (Tipo = 'NUMBER' OR Tipo = 'INTEGER' OR Tipo = 'FLOAT' OR 
		Tipo = 'DOUBLE' OR Tipo = 'REAL' OR Tipo = 'CHAR' OR Tipo = 'VARCHAR' OR Tipo = 'BIT' OR 
		Tipo = 'BLOB' OR Tipo = 'DATE' OR Tipo = 'TIMESTAMP' OR Tipo = 'INTERVAL'),
	CONSTRAINT V16 UNIQUE(NomeDominio, Schema)
);

/

CREATE TABLE colonna(
	ID_colonna		INTEGER,
	NomeColonna		VARCHAR2(50)	NOT NULL,
	Tipo			VARCHAR2(15),
	LunghezzaDati	INTEGER,
	Nullo			CHAR(2)			NOT NULL,
	ValDefault		VARCHAR2(50),
	Tabella			INTEGER			NOT NULL,
	ID_dominio		INTEGER,

	CONSTRAINT pk_colonna PRIMARY KEY (ID_colonna),
	CONSTRAINT fk1_colonna FOREIGN KEY (Tabella) REFERENCES tabella(ID_tabella) ON DELETE CASCADE,	
	CONSTRAINT fk2_colonna FOREIGN KEY (ID_dominio) REFERENCES dominio (ID_dominio) ON DELETE CASCADE,
	CONSTRAINT tipoDatoColonna CHECK (Tipo = 'NUMBER' OR Tipo = 'INTEGER' OR Tipo = 'FLOAT' OR 
    	Tipo = 'DOUBLE' OR Tipo = 'REAL' OR Tipo = 'CHAR' OR Tipo = 'VARCHAR' OR Tipo = 'BIT' OR Tipo = 'BLOB' OR 
    	Tipo = 'DATE' OR Tipo = 'TIMESTAMP' OR Tipo = 'INTERVAL'),
	CONSTRAINT boolNull CHECK ( Nullo = 'SI' OR Nullo = 'NO'), 
	CONSTRAINT V4_1 CHECK ( (Tipo IS NULL AND ID_dominio IS NOT NULL) OR (Tipo IS NOT NULL AND ID_dominio IS NULL) ),
	CONSTRAINT V4_2 UNIQUE(NomeColonna, Tabella),
	CONSTRAINT V4_4 CHECK ((LunghezzaDati IS NOT NULL AND (Tipo = 'CHAR' OR Tipo = 'VARCHAR')) OR 
		(LunghezzaDati IS NULL AND (Tipo <> 'CHAR' AND Tipo <> 'VARCHAR')) )
);

/

CREATE TABLE vincolo(    
	ID_vincolo		INTEGER,
	NomeVincolo		VARCHAR2(50)	NOT NULL,
	Tipo			VARCHAR2(11)	NOT NULL,
	CondCheck		VARCHAR2(500),
	Stato 			VARCHAR2(12)  	DEFAULT 'ABILITATO'  NOT NULL,
	Tabella			INTEGER			NOT NULL,
	Proprietario	VARCHAR2(50)	NOT NULL,

	CONSTRAINT pk_vincolo PRIMARY KEY (ID_vincolo),
	CONSTRAINT fk1_vincolo FOREIGN KEY(Tabella) REFERENCES tabella(ID_tabella) ON DELETE CASCADE,
	CONSTRAINT fk2_vincolo FOREIGN KEY(Proprietario) REFERENCES utente(UserName),					
	CONSTRAINT tipoVincolo CHECK (Tipo = 'CHECK' OR Tipo = 'PRIMARY KEY' OR Tipo = 'FOREIGN KEY' OR Tipo = 'UNIQUE'),
	CONSTRAINT statoVincolo CHECK(Stato ='ABILITATO' OR Stato='DISABILITATO'),
	CONSTRAINT V5_2 CHECK ( (Tipo <> 'CHECK' AND CondCheck IS NULL) OR (Tipo = 'CHECK' AND CondCheck IS NOT NULL) ),
	CONSTRAINT V5_5 UNIQUE (NomeVincolo, Tabella) 
);

/

CREATE TABLE vincoloColonna(
	ID_colonna	INTEGER NOT NULL,
	ID_vincolo	INTEGER NOT NULL,

	CONSTRAINT fk1_vincoloColonna FOREIGN KEY(ID_colonna) REFERENCES colonna(ID_colonna) ON DELETE CASCADE,
	CONSTRAINT fk2_vincoloColonna FOREIGN KEY(ID_vincolo) REFERENCES vincolo(ID_vincolo) ON DELETE CASCADE				
);

/

CREATE TABLE chiaveEsterna(
	ID_fk		INTEGER,
	RegCanc		VARCHAR2(9)	NOT NULL,
	ID_vincolo 	INTEGER		NOT NULL,
	ID_tabRef	INTEGER		NOT NULL,

	CONSTRAINT pk_chiaveEsterna PRIMARY KEY (ID_fk),
	CONSTRAINT fk1_chiaveEsterna FOREIGN KEY (ID_vincolo) REFERENCES vincolo (ID_vincolo) ON DELETE CASCADE,
	CONSTRAINT fk2_chiaveEsterna FOREIGN KEY (ID_tabRef) REFERENCES tabella (ID_tabella) ON DELETE CASCADE,
	CONSTRAINT cancMode CHECK(RegCanc = 'NO ACTION' OR RegCanc ='CASCADE')
);

/

CREATE TABLE colonne_FK(
	ID_colonnaRef	INTEGER	NOT NULL,
	ID_FKcons		INTEGER	NOT NULL,

	CONSTRAINT fk1_colonneFK FOREIGN KEY (ID_colonnaRef) REFERENCES colonna (ID_colonna) ON DELETE CASCADE,
	CONSTRAINT fk2_Fkcons FOREIGN KEY (ID_Fkcons) REFERENCES chiaveEsterna (ID_fk) ON DELETE CASCADE
);

/

CREATE TABLE sequenza(
	ID_sequenza		INTEGER,
	NomeSeq			VARCHAR2(50)			NOT NULL,
	Incremento		INTEGER		DEFAULT 1	NOT NULL,
	ValIniziale		INTEGER		DEFAULT 1	NOT NULL,
	ValMAX			INTEGER,
	Cycle			CHAR(2)		DEFAULT 'NO',
	Proprietario	VARCHAR2(50) 			NOT NULL,
	Schema			VARCHAR2(50)			NOT NULL,

	CONSTRAINT pk_sequenza PRIMARY KEY (ID_sequenza),
	CONSTRAINT fk1_sequenza FOREIGN KEY (Proprietario) REFERENCES utente (UserName),
	CONSTRAINT fk2_sequenza FOREIGN KEY (Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT SNseq CHECK ( Cycle = 'SI' OR Cycle = 'NO' ), 
	CONSTRAINT V15 UNIQUE(NomeSeq, Schema) 
);

/

CREATE TABLE vista(
	ID_view			INTEGER,
	NomeVista		VARCHAR2(50)	NOT NULL,
	Query			VARCHAR2(4000)	NOT NULL,
	Schema 			VARCHAR2(50)	NOT NULL,
	Proprietario	VARCHAR2(50)	NOT NULL,
	
	CONSTRAINT pk_vista PRIMARY KEY (ID_VIEW),
	CONSTRAINT fk1_vista FOREIGN KEY (Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT fk2_vista FOREIGN KEY (Proprietario) REFERENCES utente(UserName),
	CONSTRAINT V14 UNIQUE(NomeVista, Schema)
);

/

CREATE TABLE procedura(
	ID_procedura	INTEGER,
	NomeProcedura	VARCHAR2(50)	NOT NULL,
	BloccoCodice	VARCHAR2(4000)	NOT NULL,
	Tipo 			VARCHAR2(9)		NOT NULL,
	TipoRitorno		VARCHAR2(15),
	Schema 			VARCHAR2(50)	NOT NULL,
	Proprietario	VARCHAR2(50)	NOT NULL,

	CONSTRAINT pk_procedura PRIMARY KEY (ID_procedura),
	CONSTRAINT fk_procedura FOREIGN KEY (Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT tipoProc CHECK (Tipo = 'PROCEDURA' OR Tipo = 'FUNZIONE'),
	CONSTRAINT typeReturn CHECK (TipoRitorno = 'NUMBER' OR TipoRitorno = 'INTEGER' OR TipoRitorno = 'FLOAT' OR TipoRitorno = 'DOUBLE' OR 
		TipoRitorno = 'REAL' OR TipoRitorno = 'CHAR' OR TipoRitorno = 'VARCHAR' OR TipoRitorno = 'BIT' OR TipoRitorno = 'DATE'),
	CONSTRAINT V10_1 CHECK ( (TipoRitorno IS NULL AND Tipo <> 'FUNZIONE') OR (TipoRitorno IS NOT NULL AND Tipo = 'FUNZIONE') ),
	CONSTRAINT V10_3 UNIQUE(NomeProcedura, Schema)
);

/

CREATE TABLE chiamataProc(
	Chiamante	INTEGER	NOT NULL,
	Chiamata	INTEGER	NOT NULL,

	CONSTRAINT fk1_chiamataProc FOREIGN KEY(Chiamante) REFERENCES procedura(ID_procedura) ON DELETE CASCADE,
	CONSTRAINT fk2_chiamataProc FOREIGN KEY(Chiamata) REFERENCES procedura(ID_procedura) ON DELETE CASCADE
);

/

CREATE TABLE parametri(
	ID_parametro	INTEGER,
	NomeParam		VARCHAR2(50)	NOT NULL,
	Tipo			VARCHAR2(15)	NOT NULL,
	TipologiaPar	VARCHAR2(6)		NOT NULL,
	ID_proc			INTEGER			NOT NULL,

	CONSTRAINT pk_parametri	PRIMARY KEY (ID_parametro),
	CONSTRAINT fk_parametri FOREIGN KEY (ID_proc) REFERENCES procedura (ID_procedura) ON DELETE CASCADE,
	CONSTRAINT typePar CHECK(TipologiaPar ='INPUT' OR TipologiaPar = 'OUTPUT'),
	CONSTRAINT dataParType CHECK (Tipo = 'NUMBER' OR Tipo = 'INTEGER' OR Tipo = 'FLOAT' OR Tipo = 'DOUBLE' 
		OR Tipo = 'REAL' OR Tipo = 'CHAR' OR Tipo = 'VARCHAR' OR Tipo = 'BIT' OR Tipo = 'DATE'),
	CONSTRAINT V17 UNIQUE(NomeParam, ID_proc)
);

/

CREATE TABLE trigger1(
	ID_trigger		INTEGER,
	NomeTrigger		VARCHAR2(50)	NOT NULL,
	Tempo			VARCHAR2(10)	NOT NULL,
	ForEachRow		CHAR(2)			NOT NULL,
	CondWhen		VARCHAR2(100),
	Causa			VARCHAR2(6)		NOT NULL,
	BloccoCodice	VARCHAR2(4000)	NOT NULL,
	Proprietario 	VARCHAR2(50)	NOT NULL,
	Schema			VARCHAR2(50)	NOT NULL,
	OggettoTAB		INTEGER,
	OggettoVIEW		INTEGER,

	CONSTRAINT pk_trigger PRIMARY KEY (ID_trigger),
	CONSTRAINT fk1_trigger FOREIGN KEY (Proprietario) REFERENCES utente(UserName),
	CONSTRAINT fk2_trigger FOREIGN KEY (Schema) REFERENCES schema1(NomeSchema) ON DELETE CASCADE,
	CONSTRAINT ogg_tab FOREIGN KEY (OggettoTAB) REFERENCES tabella(ID_tabella) ON DELETE CASCADE,
	CONSTRAINT ogg_view FOREIGN KEY (OggettoVIEW) REFERENCES vista(ID_view) ON DELETE CASCADE,
	CONSTRAINT typeTrigger CHECK (Tempo = 'AFTER' OR Tempo = 'BEFORE' OR Tempo = 'INSTEAD OF'),
	CONSTRAINT causeTrigger CHECK (Causa = 'INSERT' OR Causa = 'DELETE' OR Causa = 'UPDATE' OR Causa = 'CREATE'
		OR Causa = 'ALTER' OR Causa = 'DROP'),
	CONSTRAINT eachRow CHECK (ForEachRow = 'SI' OR ForEachRow = 'NO'),
	CONSTRAINT V7_1 CHECK ( (OggettoTAB IS NULL AND OggettoVIEW IS NOT NULL) OR (OggettoTAB IS NOT NULL AND OggettoVIEW IS NULL) ),
	CONSTRAINT V7_2 CHECK ( (Tempo = 'INSTEAD OF' AND OggettoVIEW IS NOT NULL) OR (Tempo <> 'INSTEAD OF' AND OggettoVIEW IS NULL) ),
	CONSTRAINT V7_5 UNIQUE(NomeTrigger, Schema)  
);

/

CREATE TABLE variabili(
	ID_var			INTEGER,
	NomeVariabile	VARCHAR2(50)	NOT NULL,
	Tipo			VARCHAR2(15)	NOT NULL,
	ID_trigger		INTEGER,
	ID_proc			INTEGER,

	CONSTRAINT pk_var PRIMARY KEY (ID_var),
	CONSTRAINT fk1_var FOREIGN KEY (ID_trigger) REFERENCES trigger1 (ID_trigger) ON DELETE CASCADE,
	CONSTRAINT fk2_var FOREIGN KEY (ID_proc) REFERENCES procedura (ID_procedura) ON DELETE CASCADE,
	CONSTRAINT varType CHECK (Tipo = 'NUMBER' OR Tipo = 'INTEGER' OR Tipo = 'FLOAT' OR Tipo = 'DOUBLE' OR 
	Tipo = 'REAL' OR Tipo = 'CHAR' OR Tipo = 'VARCHAR' OR Tipo = 'BIT' OR Tipo = 'DATE'),
	CONSTRAINT V8_1 CHECK ( (ID_trigger IS NULL AND ID_proc IS NOT NULL) OR (ID_trigger IS NOT NULL AND ID_proc IS NULL) )
);

/

CREATE TABLE eccezioni(
	ID_eccezione	INTEGER,
	NomeEccezione	VARCHAR2(50)	NOT NULL,
	bloccoCodice	VARCHAR2(4000)	NOT NULL,
	ID_trigger	INTEGER,
	ID_proc		INTEGER,

	CONSTRAINT pk_exception PRIMARY KEY (ID_eccezione),
	CONSTRAINT fk1_exception FOREIGN KEY (ID_trigger) REFERENCES trigger1 (ID_trigger) ON DELETE CASCADE,
	CONSTRAINT fk2_exception FOREIGN KEY (ID_proc) REFERENCES procedura (ID_procedura) ON DELETE CASCADE,
	CONSTRAINT V9_1 CHECK ( (ID_trigger IS NULL AND ID_proc IS NOT NULL) OR (ID_trigger IS NOT NULL AND ID_proc IS NULL) )
);

/

CREATE TABLE valore(
	ValoreNome	VARCHAR2(50)  NOT NULL,
	ID_dominio	INTEGER	  NOT NULL,

	CONSTRAINT fk_valore FOREIGN KEY(ID_dominio) REFERENCES dominio(ID_dominio) ON DELETE CASCADE
);

/

create or replace TRIGGER V1asserzione
BEFORE INSERT ON asserzione
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE ASSERTION' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20010, 'ERRORE !! L''utente non ha i permessi necessari per creare un''asserzione');
END;

/

create or replace TRIGGER V1dominio
BEFORE INSERT ON dominio
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE DOMAIN' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20008, 'ERRORE !! L''utente non ha i permessi necessari per creare un dominio');
END;

/

create or replace TRIGGER V1procedura
BEFORE INSERT ON procedura
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE PROCEDURE' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20013, 'ERRORE !! L''utente non ha i permessi necessari per creare una procedura.');
END;

/

create or replace TRIGGER V1schema
BEFORE INSERT ON schema1
FOR EACH ROW
DECLARE
    temp INTEGER;
    
    permessoNegato EXCEPTION;
BEGIN
    
    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE SCHEMA' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;
    
    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;
    
    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20006, 'ERRORE !! L''utente non ha i permessi necessari per creare uno schema');
END;

/

create or replace TRIGGER V1sequenza
BEFORE INSERT ON sequenza
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE SEQUENCE' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20009, 'ERRORE !! L''utente non ha i permessi necessari per creare una sequenza');
END;

/

create or replace TRIGGER V1tabella
BEFORE INSERT ON tabella
FOR EACH ROW
DECLARE
    temp INTEGER;
    
    permessoNegato EXCEPTION;
BEGIN
    
    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE TABLE'  OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;
    
    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;
    
    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20007, 'ERRORE !! L''utente non ha i permessi necessari per creare una tabella');
END;

/

create or replace TRIGGER V1trigger
BEFORE INSERT ON trigger1
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE TRIGGER' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20012, 'ERRORE !! L''utente non ha i permessi necessari per creare un trigger.');
END;

/

create or replace TRIGGER V1vista
BEFORE INSERT ON vista
FOR EACH ROW
DECLARE
    temp INTEGER;

    permessoNegato EXCEPTION;
BEGIN

    SELECT COUNT(*) INTO temp
    FROM utente U JOIN CompRuoloSistema C ON U.ruolo = C.ID_ruolo
    WHERE (C.ID_privSis = 'CREATE VIEW' OR C.ID_privSis = 'ALL PRIVILEGIES') AND U.UserName = :NEW.proprietario;

    IF temp = 0 THEN 
        RAISE permessoNegato;
    END IF;

    EXCEPTION WHEN permessoNegato THEN
        RAISE_APPLICATION_ERROR(-20011, 'ERRORE !! L''utente non ha i permessi necessari per creare una vista');
END;

/

create or replace TRIGGER V4_3
BEFORE INSERT ON colonna
FOR EACH ROW
WHEN (NEW.ID_dominio IS NOT NULL)
DECLARE
    schemaTab   schema1.NomeSchema%TYPE;
    schemaDom   schema1.NomeSchema%TYPE;
    erroreSchemiDiversi EXCEPTION;
BEGIN
    SELECT T.schema INTO schemaTab
    FROM tabella T 
    WHERE T.ID_tabella = :NEW.tabella;

    SELECT D.schema INTO schemaDom
    FROM dominio D
    WHERE D.ID_dominio = :NEW.ID_dominio;
    
    IF schemaTab <> schemaDom THEN
        RAISE erroreSchemiDiversi;
    END IF;
    
    EXCEPTION WHEN erroreSchemiDiversi THEN
        RAISE_APPLICATION_ERROR(-20016, 'ERRORE !! La colonna e il dominio appartengono a due schemi differenti.');
END;

/

create or replace TRIGGER V5_1
BEFORE INSERT ON ChiaveEsterna
FOR EACH ROW
DECLARE
    tipoVincolo vincolo.Tipo%TYPE;
    erroreFK EXCEPTION;
BEGIN 
    SELECT V.Tipo INTO tipoVincolo
    FROM vincolo V
    WHERE V.ID_vincolo = :NEW.ID_vincolo;

    IF tipoVincolo <> 'FOREIGN KEY' THEN
        RAISE erroreFK;
    END IF;

    EXCEPTION WHEN erroreFK THEN
        RAISE_APPLICATION_ERROR(-20004, 'ERRORE !! Il vincolo associato non � una FOREIGN KEY.');
END;

/

create or replace TRIGGER V5_3
BEFORE INSERT ON VincoloColonna
FOR EACH ROW
DECLARE 
    cont INTEGER;
    colonna INTEGER := NULL;
    tab1 INTEGER;
    tab2 INTEGER;
    erroreVincolo EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont
    FROM VincoloColonna V
    WHERE V.ID_vincolo = :NEW.ID_vincolo;

    IF cont > 0 THEN
        SELECT V.ID_colonna INTO colonna
        FROM VincoloColonna V
        WHERE ROWNUM = 1 AND V.ID_vincolo = :NEW.ID_vincolo;
    
        SELECT C.tabella INTO tab1
        FROM colonna C
        WHERE C.ID_colonna = colonna;

        SELECT C.tabella INTO tab2
        FROM colonna C
        WHERE C.ID_colonna = :NEW.ID_colonna;

        IF tab1 <> tab2 THEN
            RAISE erroreVincolo;    
        END IF;
    END IF;

    EXCEPTION
        WHEN erroreVincolo THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRRORE !!! Vincolo interelazionale non esprimibile dentro una tabella.');
END;

/

create or replace TRIGGER V5_4
BEFORE INSERT ON vincolo
FOR EACH ROW 
DECLARE
    tmp INTEGER;
    schemaV schema1.NomeSchema%TYPE;

    vincoloEsistente EXCEPTION;
BEGIN
    SELECT T.schema INTO schemaV
    FROM tabella T
    WHERE T.ID_tabella = :NEW.tabella;

    SELECT COUNT(*) INTO tmp
    FROM tabella T JOIN vincolo V ON T.ID_tabella = V.tabella
    WHERE T.schema = schemaV AND V.NomeVincolo = :NEW.NomeVincolo;

    IF tmp > 0 THEN
        RAISE vincoloEsistente;
    END IF;

    EXCEPTION WHEN vincoloEsistente THEN
        RAISE_APPLICATION_ERROR(-20015, 'ERRORE !! Nome gi� utilizzato per un altro vincolo nello schema.');
END;

/

create or replace trigger V5_6
BEFORE INSERT ON vincolo
FOR EACH ROW 
WHEN (NEW.tipo = 'PRIMARY KEY')
DECLARE
    cont INTEGER := 0;
    doublePK EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont
    FROM vincolo V 
    WHERE V.tabella = :NEW.tabella AND V.tipo = 'PRIMARY KEY';

    IF cont > 0 THEN 
        RAISE doublePK;
    END IF;

    EXCEPTION WHEN doublePK THEN
        RAISE_APPLICATION_ERROR(-20022, 'ERRORE !! La tabella selezionata ha gia una chiave primaria...');
END;

/

create or replace TRIGGER V6_2
BEFORE INSERT ON colonne_FK
FOR EACH ROW
DECLARE
    contFK INTEGER;
    contV INTEGER;
    tipoRef colonna.Tipo%TYPE;

    incongruenza_dati EXCEPTION;
BEGIN
    SELECT C.tipo INTO tipoRef
    FROM colonna C 
    WHERE C.ID_colonna = :NEW.ID_colonnaRef;

    SELECT COUNT(*) INTO contV
    FROM (chiaveEsterna CE JOIN vincoloColonna V ON CE.ID_vincolo = V.ID_vincolo) JOIN colonna C ON C.ID_colonna = V.ID_colonna
    WHERE C.tipo = tipoRef;

    SELECT COUNT(*) INTO contFK
    FROM (chiaveEsterna CE JOIN colonne_FK V ON CE.ID_fk = V.ID_fkcons) JOIN colonna C ON C.ID_colonna = V.ID_colonnaRef
    WHERE C.tipo = tipoRef;

    IF contFK+1 > contV THEN
        RAISE incongruenza_dati;
    END IF;

    EXCEPTION WHEN incongruenza_dati THEN
        RAISE_APPLICATION_ERROR(-20014, 'ERRORE !! I tipi di dato delle colonne messe in associazioni non combaciano');
END;

/

create or replace TRIGGER V6_3
BEFORE INSERT ON ChiaveEsterna
FOR EACH ROW
DECLARE
    schem1 schema1.NomeSchema%TYPE;
    schem2 schema1.NomeSchema%TYPE;
    erroreSchemi EXCEPTION;
BEGIN
    SELECT T.schema INTO schem1
    FROM tabella T 
    WHERE T.ID_tabella = :NEW.ID_tabRef; 

    SELECT T.schema INTO schem2
    FROM vincolo V JOIN tabella T ON V.tabella = T.ID_tabella
    WHERE V.ID_vincolo = :NEW.ID_vincolo;

    IF schem1 <> schem2 THEN
       RAISE erroreSchemi;
    END IF;

    EXCEPTION WHEN erroreSchemi THEN
        RAISE_APPLICATION_ERROR(-20005, 'ERRORE !! Il vincolo di FOREIGN KEY è invalido perchè le tabelle appartengono a schemi differenti.'); 
       
END;

/

create or replace TRIGGER V7_3
BEFORE INSERT ON trigger1
FOR EACH ROW
WHEN (NEW.oggettoVIEW IS NULL)
DECLARE
    schemaTab schema1.NomeSchema%TYPE;
    erroreSchemi EXCEPTION;
BEGIN
    SELECT T.schema INTO schemaTAB
    FROM tabella T 
    WHERE T.ID_tabella = :NEW.oggettoTAB;

    IF schemaTAB <> :NEW.schema THEN
        RAISE erroreSchemi;
    END IF;

    EXCEPTION
        WHEN erroreSchemi THEN
            RAISE_APPLICATION_ERROR(-20002, 'ERRORE !! Il trigger e la tabella appartengono a schemi differenti.');
END;

/

create or replace TRIGGER V7_4
BEFORE INSERT ON trigger1
FOR EACH ROW
WHEN (NEW.oggettoTAB IS NULL)
DECLARE
    schemaView schema1.NomeSchema%TYPE;
    erroreSchemi2 EXCEPTION;
BEGIN
    SELECT V.schema INTO schemaView
    FROM vista V 
    WHERE V.ID_view = :NEW.oggettoVIEW;

    IF schemaView <> :NEW.schema THEN
        RAISE erroreSchemi2;
    END IF;

    EXCEPTION
        WHEN erroreSchemi2 THEN
            RAISE_APPLICATION_ERROR(-20003, 'ERRORE !! Il trigger e la vista appartengono a schemi differenti.');
END;

/

create or replace trigger v8_3_1
BEFORE INSERT ON variabili
FOR EACH ROW
WHEN (NEW.ID_trigger IS NULL)
DECLARE 
    cont INTEGER := 0;
    erroreUnicitaVar EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont 
    FROM variabili v
    WHERE V.nomeVariabile = :NEW.nomeVariabile AND V.id_proc = :NEW.id_proc;
    
    IF cont > 0 THEN
        RAISE erroreUnicitavar;
    END IF;
    
    EXCEPTION WHEN erroreUnicitaVar THEN
         RAISE_APPLICATION_ERROR(-20018, 'ERRORE !! I nomi delle variabili non possono essere uguali nella stessa procedura.');
END;

/

create or replace trigger v8_3_2
BEFORE INSERT ON variabili
FOR EACH ROW
WHEN (NEW.ID_proc IS NULL)
DECLARE 
    cont INTEGER := 0;
    erroreUnicitaVar2 EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont 
    FROM variabili v
    WHERE V.nomeVariabile = :NEW.nomeVariabile AND V.id_trigger = :NEW.id_trigger;

    IF cont > 0 THEN
        RAISE erroreUnicitavar2;
    END IF;

    EXCEPTION WHEN erroreUnicitaVar2 THEN
         RAISE_APPLICATION_ERROR(-20019, 'ERRORE !! I nomi delle variabili non possono essere uguali nello stesso trigger.');
END;

/

create or replace trigger v9_2_1
BEFORE INSERT ON eccezioni
FOR EACH ROW
WHEN (NEW.ID_trigger IS NULL)
DECLARE 
    cont INTEGER := 0;
    erroreUnicitaExc EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont 
    FROM eccezioni E
    WHERE E.nomeEccezione = :NEW.nomeEccezione AND E.id_proc = :NEW.id_proc;

    IF cont > 0 THEN
        RAISE erroreUnicitaExc;
    END IF;

    EXCEPTION WHEN erroreUnicitaExc THEN
         RAISE_APPLICATION_ERROR(-20020, 'ERRORE !! I nomi delle Exception non possono essere uguali nella stessa procedura.');
END;

/

create or replace trigger v9_2_2
BEFORE INSERT ON eccezioni
FOR EACH ROW
WHEN (NEW.ID_proc IS NULL)
DECLARE 
    cont INTEGER := 0;
    erroreUnicitaExc2 EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO cont 
    FROM eccezioni E
    WHERE E.nomeEccezione = :NEW.nomeEccezione AND E.id_trigger = :NEW.id_trigger;

    IF cont > 0 THEN
        RAISE erroreUnicitaExc2;
    END IF;

    EXCEPTION WHEN erroreUnicitaExc2 THEN
         RAISE_APPLICATION_ERROR(-20021, 'ERRORE !! I nomi delle Exception non possono essere uguali nello stesso trigger.');
END;

/

create or replace TRIGGER v10_4
BEFORE INSERT ON chiamataProc
FOR EACH ROW
DECLARE
    schemaChiamante schema1.nomeSchema%TYPE;
    schemaChiamata schema1.nomeSchema%TYPE;
    erroreSchemiDiversiProc EXCEPTION;
BEGIN
    SELECT P.schema INTO schemaChiamata
    FROM procedura P
    WHERE :NEW.chiamata = P.ID_procedura;

    SELECT P.schema INTO schemaChiamante
    FROM procedura P 
    WHERE :NEW.chiamante = P.ID_procedura;

    IF schemaChiamante <> schemaChiamata
        THEN RAISE erroreSchemiDiversiProc;
    END IF;

    EXCEPTION WHEN erroreSchemiDiversiProc THEN
        RAISE_APPLICATION_ERROR(-20017, 'ERRORE !! La procedura chiamante e quella richiamata appartengono a due schemi differenti.');
END;

/

create SEQUENCE assertionSEQ
INCREMENT BY 1
START WITH 1;

/


create SEQUENCE fkSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE colonnaSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE dominioSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE eccezioniSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE parSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE procSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE sequenzaSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE tabSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE triggerSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE varSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE viewSEQ
INCREMENT BY 1
START WITH 1;

/

create SEQUENCE vincoloSEQ
INCREMENT BY 1
START WITH 1;

/

create or replace TRIGGER IDassert
BEFORE INSERT ON asserzione
FOR EACH ROW
WHEN (NEW.ID_asserzione IS NULL)
BEGIN 
    :NEW.ID_asserzione := assertionSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDchiaveesterna
BEFORE INSERT ON chiaveesterna
FOR EACH ROW
WHEN (NEW.ID_fk IS NULL)
BEGIN 
    :NEW.ID_fk := fkSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDdominio
BEFORE INSERT ON dominio
FOR EACH ROW
WHEN (NEW.ID_dominio IS NULL)
BEGIN 
    :NEW.ID_dominio := dominioSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDeccezione
BEFORE INSERT ON eccezioni
FOR EACH ROW
WHEN (NEW.ID_eccezione IS NULL)
BEGIN 
    :NEW.ID_eccezione := eccezioniSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDparametro
BEFORE INSERT ON parametri
FOR EACH ROW
WHEN (NEW.ID_parametro IS NULL)
BEGIN 
    :NEW.ID_parametro := parSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDprocedura
BEFORE INSERT ON procedura
FOR EACH ROW
WHEN (NEW.ID_procedura IS NULL)
BEGIN 
    :NEW.ID_procedura := procSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDsequenza
BEFORE INSERT ON sequenza
FOR EACH ROW
WHEN (NEW.ID_sequenza IS NULL)
BEGIN 
    :NEW.ID_sequenza := sequenzaSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDtabella
BEFORE INSERT ON tabella
FOR EACH ROW
WHEN (NEW.ID_tabella IS NULL)
BEGIN 
    :NEW.ID_tabella := tabSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDtrigger
BEFORE INSERT ON trigger1
FOR EACH ROW
WHEN (NEW.ID_trigger IS NULL)
BEGIN 
    :NEW.ID_trigger := triggerSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDvariabile
BEFORE INSERT ON variabili
FOR EACH ROW
WHEN (NEW.ID_var IS NULL)
BEGIN 
    :NEW.ID_var := varSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDvincolo
BEFORE INSERT ON vincolo
FOR EACH ROW
WHEN (NEW.ID_vincolo IS NULL)
BEGIN 
    :NEW.ID_vincolo := vincoloSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDvista
BEFORE INSERT ON vista
FOR EACH ROW
WHEN (NEW.ID_view IS NULL)
BEGIN 
    :NEW.ID_view := viewSEQ.NEXTVAL;
END;

/

create or replace TRIGGER IDcolonna
BEFORE INSERT ON colonna
FOR EACH ROW
WHEN (NEW.ID_colonna IS NULL)
BEGIN 
    :NEW.ID_colonna := colonnaSEQ.NEXTVAL;
END;

/

create or replace trigger insDataCreazioneTab
BEFORE INSERT ON tabella
FOR EACH ROW
BEGIN
    :NEW.dataCreazione := SYSDATE;
    :NEW.ultimaModifica := SYSDATE;
END;

/

create or replace TRIGGER up_ruolo
AFTER UPDATE OF nomeRuolo ON ruolo
FOR EACH ROW
BEGIN
    UPDATE utente U 
    SET U.ruolo = :NEW.nomeRuolo
    WHERE U.ruolo = :OLD.nomeRuolo;

    UPDATE compRuoloOggetto RO 
    SET RO.ID_ruolo = :NEW.nomeRuolo
    WHERE RO.id_ruolo = :OLD.nomeRuolo;

    UPDATE compRuoloSistema RS 
    SET RS.ID_ruolo = :NEW.nomeRuolo
    WHERE RS.id_ruolo = :OLD.nomeRuolo;
END;

/

CREATE TRIGGER up_utente
AFTER UPDATE OF username ON utente
FOR EACH ROW
BEGIN
    UPDATE vista A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE vincolo A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE trigger1 A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE tabella A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE sequenza A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE schema1 A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE procedura A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE dominio A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
    
    UPDATE asserzione A 
    SET A.proprietario = :NEW.username
    WHERE A.proprietario = :OLD.username;
END;

/

create or replace TRIGGER up_schema
AFTER UPDATE OF nomeSchema ON schema1
FOR EACH ROW
BEGIN
    UPDATE vista A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE trigger1 A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE tabella A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE sequenza A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE procedura A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE dominio A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;

    UPDATE asserzione A 
    SET A.schema = :NEW.nomeSchema
    WHERE A.schema = :OLD.nomeSchema;
END;

/

create or replace TRIGGER ultimoAggiornamentoTab_1
AFTER INSERT OR UPDATE ON colonna
FOR EACH ROW
BEGIN
    UPDATE tabella T
    SET T.ultimaModifica = SYSDATE
    WHERE T.ID_tabella = :NEW.tabella;
END;

/

create or replace TRIGGER ultimoAggiornamentoTab_2
BEFORE UPDATE ON tabella
FOR EACH ROW
BEGIN
    :NEW.ultimaModifica := SYSDATE;
END;

/

create or replace view infoTrigger as 
(SELECT TR.nomeTrigger as nomeTrigger, TR.tempo as tempo, TR.forEachRow as forEachRow, 
	TR.CondWhen as condizioneWhen, TR.causa as causa, TR.bloccoCodice as bloccoCodice, 
	TR.schema as schema, TR.proprietario as proprietario, T.nomeTabella as oggetto,
	T.ID_tabella AS ID
 FROM trigger1 TR join tabella T on TR.oggettoTab = T.ID_tabella)
UNION
(SELECT TR.nomeTrigger as nomeTrigger, TR.tempo as tempo, TR.forEachRow as forEachRow, 
	TR.CondWhen as condizioneWhen, TR.causa as causa, TR.bloccoCodice as bloccoCodice, 
	TR.schema as schema, TR.proprietario as proprietario, V.nomeVista as oggetto,
	V.ID_view AS ID
 FROM trigger1 TR join vista V on TR.oggettoView = V.ID_view);

/

create or replace view infoVincolo as (
    SELECT V.nomeVincolo AS NomeVincolo, V.tipo AS TipoVincolo, V.condCheck AS CondizioneCheck, 
    V.stato AS Stato, T.nomeTabella AS Tabella, V.Proprietario AS Proprietario, T.schema as Schema,
	T.ID_tabella AS ID
    FROM vincolo V JOIN tabella T ON V.tabella = T.ID_tabella
); 


/

CREATE OR REPLACE VIEW infoColonna AS(
    SELECT C.nomeColonna AS Nome_Colonna, 
    NVL(C.tipo, (SELECT D.nomeDominio FROM dominio D WHERE D.ID_dominio = C.ID_dominio)) AS Tipo_Dato,
    C.lunghezzaDati AS Lunghezza_Dati, C.nullo as Annullabile, C.valDefault AS Valore_Default, 
    T.nomeTabella AS Tabella, T.ID_tabella AS ID
    FROM colonna C JOIN tabella T ON C.Tabella = T.ID_tabella
);

/

CREATE OR REPLACE VIEW ProcedureChiamate AS(
    SELECT C.chiamante AS ID_chiamante, P.nomeProcedura AS procedura_Chiamata
    FROM chiamataProc C JOIN Procedura P ON C.chiamata = P.ID_Procedura
);

/

CREATE OR REPLACE VIEW infoColonneVincolo AS(
    (SELECT C.nomeColonna, (select T.nomeTabella from tabella T where C.tabella = T.ID_tabella) as Tabella, V.id_vincolo
     FROM (Colonna C JOIN vincoloColonna VC ON C.id_colonna = VC.id_colonna) JOIN vincolo V ON V.id_vincolo = VC.id_vincolo
    )   
    UNION
    (SELECT C.nomeColonna, (select T.nomeTabella from tabella T where C.tabella = T.ID_tabella) as Tabella, V.id_vincolo
     FROM ((vincolo V JOIN chiaveEsterna CE ON V.id_vincolo = CE.id_vincolo) JOIN colonne_fk CFK ON CE.id_fk = CFK.id_fkCons) JOIN colonna C ON C.id_colonna = CFK.id_colonnaRef
    )
);