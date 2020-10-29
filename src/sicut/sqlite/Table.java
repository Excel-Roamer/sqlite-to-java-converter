package sicut.sqlite;

import java.util.HashMap;

public class Table {

    protected Connector con;
    protected HashMap<String, Object> fv_pairs; // fields -> values
    protected String tableName, idLib;
    protected String translation = "i18n, language l, translate t";
    protected String tableJoins = "idLibelle = i18n.idLibelle and i18n.idLibelle = t.idLibelle and t.idLang = l.idLang and l.abbrev like '%lang%'";

    public Table() {
        con = new Connector();
        fv_pairs = new HashMap();
    };

    public String getDatabase() {
        return con.getDatabase();
    };

    public void setDatabase(String fileName) {
        con.setDatabase(fileName);
    };

    public Connector getConnector() {
        return con;
    };

    public boolean delete(int idValue) {
        boolean result;
        con.connect();
        result = con.delete(tableName, idLib, idValue);
        con.close();
        return result;
    };

    public boolean delete(String idLib, int idValue) {
        boolean result;
        con.connect();
        result = con.delete(tableName, idLib, idValue);
        con.close();
        return result;
    };

    public boolean delete2(String whereClause) {
        boolean result;
        con.connect();
        result = con.delete(tableName, whereClause);
        con.close();
        return result;
    };

    public boolean insert() {
        boolean result;
        con.connect();
        result = con.insert(tableName, fv_pairs);
        con.close();
        return result;
    };

    public boolean update(int idValue) {
        boolean result;
        con.connect();
        result = con.update(tableName, idLib, idValue, fv_pairs);
        con.close();
        return result;
    };

    public boolean update(String idLib, int idValue) {
        boolean result;
        con.connect();
        result = con.update(tableName, idLib, idValue, fv_pairs);
        con.close();
        return result;
    };
    
    public boolean update2(String whereClause) {
        boolean result;
        con.connect();
        result = con.update(tableName, fv_pairs, whereClause);
        con.close();
        return result;
    };
    
    public boolean update(String whereClause, boolean[] flags) {
        boolean result;
        con.connect();
        result = con.update(tableName, fv_pairs, whereClause, flags);
        con.close();
        return result;
    };

    public HashMap select() {
        HashMap hm;
        con.connect();
        hm = con.select(tableName);
        con.close();
        return hm;
    };

    public HashMap select(String whereClause) {
        HashMap hm;
        con.connect();
        hm = con.select(tableName, whereClause);
        con.close();
        return hm;
    };

    public HashMap select(String[] fields, String whereClause) {
        HashMap hm;
        con.connect();
        hm = con.select(tableName, fields, whereClause);
        con.close();
        return hm;
    };

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTableJoins() {
        return tableJoins;
    }

    public void setTableJoins(String tableJoins) {
        this.tableJoins = tableJoins;
    }

    public void setTableJoinsLanguage(String language) {
        tableJoins = tableJoins.replace("%lang%", language);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    // when you want just to get translated libelles
    public void useTranslateJoins() {
        tableName = translation;
        tableJoins = tableJoins.replace("idLibelle = i18n.idLibelle and ", "");
    }

    public void setFv_pairs(HashMap<String, Object> fv_pairs) {
        this.fv_pairs = fv_pairs;
    }
    

}
