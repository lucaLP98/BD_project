/**
 * file : Database.java
 */
package progettopastoreluca;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Classe principale di connessione al database.
 * 
 * @author Luca Pastore N86002599
 * @version 2019
 */
public class Database { 
    
    static public String host = "localhost"; //Indirizzo del server Oracle.
    static public String servizio = "xe"; //Nome del servizio.
    static public int porta = 1521; //Porta utilizzata per la connessione.
    static public String user = ""; //Nome utente per la connessione.
    static public String password = ""; //Password corrispondente all'utente specificato.
    static public String schema = "LAB18"; //Nome dello schema contenente le tabelle/viste/procedure cui si vuole accedere; coincide di solito con il nome utente.
   
    static private OracleDataSource ods; //Oggetto DataSource utilizzato nella connessione al DB
    static private Connection defaultConnection; //Variabile che contiene la connessione attiva, se esiste

    /**
     * Crea una nuova istanza della classe Database
     */
    public Database() {
    
    }
   
    /**
     * Restituisce una nuova connessione al DB.
     *
     * @return Connessione al DB secondo i parametri attualmente impostati
     * @throws java.sql.SQLException in caso di problemi di connessione
     */
    static public Connection connetti() throws SQLException {
        ods = new OracleDataSource();
      
        ods.setDriverType("thin");
        ods.setServerName(host);
        ods.setPortNumber(porta);
        ods.setUser(user);
        ods.setPassword(password);
        ods.setDatabaseName(servizio);
      
        return ods.getConnection();
    }
   
    /**
     * Imposta una connessione specificata in input come default.
     *
     * @param c Connessione al DB
     */
    static public void setDefaultConnection(Connection c) {
        defaultConnection = c;
    }
    
    /**
     * Restituisce la connessione di default al DB.
     *
     * @return Connessione di default (cioè quella attiva o una nuova
     * ottenuta in base ai parametri di connessione attualmente impostati)
     * @throws SQLException In caso di problemi di connessione
     */
    static public Connection getDefaultConnection() throws SQLException {
        if(defaultConnection == null || defaultConnection.isClosed()) 
            defaultConnection = connetti(); //se non esiste già, crea una nuova connessione al database
        //altrimenti restituisce la connessione gia instaurata, riciclando la conessione
        
      return defaultConnection;
    } 
}
