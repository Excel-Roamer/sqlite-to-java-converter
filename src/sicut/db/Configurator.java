package sicut.db;

import s2jconverter.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import sicut.sqlite.Connector;

public final class Configurator {
    private final Connector con;
    
    public Configurator() {
        con = new Connector(Settings.PREF_DB_PATH);
    }
    
    public Configurator(Connector con) {
        this.con = con;
    }
    
    public void loadPreferences() {
        File db = new File(Settings.PREF_DB_PATH);
        if ( !db.exists() ) {
            (new File(Settings.DB_FOLDER_PATH)).mkdir();
            createDB("/s2jconverter/db/source/preferences.sql");
        }
        db = new File(Settings.I18N_DB_PATH);
        if ( !db.exists() ) {
            con.setDatabase(Settings.I18N_DB_PATH);
            createDB("/s2jconverter/db/source/i18n.sql");
        }
    }
    
    public void prepare() {
    }
    
    private void createDB(String src) {
        String crQuery = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResource(src).openStream()));
            if ( !con.connect() )   return;
            String line;
            con.prpeareBatch();
            while ((line = br.readLine()) != null) {
                if ( line.startsWith("--"))
                    continue;
                crQuery += line;
                if ( line.contains(";") ) {
                    con.batch(crQuery);
                    crQuery = "";
                }
            }
            con.runBatch();
            con.close();
        } 
        catch (IOException ex) {
        }
    }
}
