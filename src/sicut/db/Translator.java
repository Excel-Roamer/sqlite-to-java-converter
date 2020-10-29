package sicut.db;

import s2jconverter.Settings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import sicut.sqlite.Table;

/**
 *
 * @author Sicut
 */
public class Translator extends ResourceBundle {
    
    HashMap<String, String> bundle;
    
    public Translator() {
        super();
        bundle = new HashMap();
    }
    
    @Override
    protected Object handleGetObject(String key) {        
        return bundle.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        HashMap<String, ArrayList> res;
        Table t = new Table();
        String[] fields = {"i18n.libelle lib", "synonym syn"};
        t.useTranslateJoins();
        t.setTableJoinsLanguage(Settings.PREF_BUNDLE.get("LANGUAGE"));
        t.setDatabase(Settings.I18N_DB_PATH);
        res = t.select(fields, t.getTableJoins());
        for ( int i = 0, n = res.get("lib").size(); i < n; i++ )
            bundle.put(res.get("lib").get(i).toString(), res.get("syn").get(i).toString());
        return Collections.enumeration(bundle.keySet());
    }
    
}
