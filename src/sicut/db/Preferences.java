package sicut.db;

import s2jconverter.Settings;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import sicut.sqlite.Table;

public class Preferences {
    
    HashMap<String, String> bundle;
    Table t;
    
    public Preferences() {
        bundle = new HashMap();
        t = new Table();
        t.setTableName("preferences");
        t.setDatabase(Settings.PREF_DB_PATH);
        HashMap<String, ArrayList> res;
        res = t.select();
        for ( int i = 0, n = res.get("id").size(); i < n; i++ )
            bundle.put(res.get("libelle").get(i).toString(), res.get("value").get(i).toString());
    }
    
    public String get(String key) {        
        return bundle.get(key);
    }
    
    public void update(String key, String value) {
        HashMap<String, Object> fv_pairs = new HashMap();
        fv_pairs.put("value", value);
        t.setFv_pairs(fv_pairs);
        Platform.runLater(() -> {
            if ( t.update2("libelle = '" + key + "'") )
            bundle.put(key, value);
        });
        
    }
    
}
