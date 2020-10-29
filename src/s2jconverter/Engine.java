/*
 * Copyright (C) 2020 Sicut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package s2jconverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import s2jconverter.templates.Template;
import sicut.sqlite.Connector;

/**
 *
 * @author Sicut
 */
public class Engine {
    
    private final String assign_tpl, assign_dummy_tpl, class_tpl, constructor_tpl, db_assign_tpl, get_set_tpl, list_tpl, pairs_put_tpl, variable_tpl, db_variable_tpl, next_pk_tpl, table_tpl, q_format_tpl, connector_tpl, header_tpl;
    private Connector con;
    private final Template tpl;
    private final HashMap<String, String> result;

    public Engine() {
        tpl = new Template();
        result = new HashMap();
        assign_tpl = tpl.getTplFile("assign.tpl");
        assign_dummy_tpl = tpl.getTplFile("assign_dummy.tpl");
        class_tpl = tpl.getTplFile("class.tpl");
        constructor_tpl = tpl.getTplFile("constructor.tpl");
        db_assign_tpl = tpl.getTplFile("db_assign.tpl");
        get_set_tpl = tpl.getTplFile("get_set.tpl");
        list_tpl = tpl.getTplFile("list.tpl");
        pairs_put_tpl = tpl.getTplFile("pairs_put.tpl");
        variable_tpl = tpl.getTplFile("variable.tpl");
        db_variable_tpl = tpl.getTplFile("db_variable.tpl");
        next_pk_tpl = tpl.getTplFile("next_pk.tpl");
        table_tpl = tpl.getTplFile("table.tpl");
        q_format_tpl = tpl.getTplFile("query_formatter.tpl");
        connector_tpl = tpl.getTplFile("connector.tpl");
        header_tpl = tpl.getTplFile("header.tpl");
    }

    public Engine(Connector con) {
        this();
        this.con = con;
    }

    public void setCon(Connector con) {
        this.con = con;
    }
    
    public String generate() {
        ArrayList<String> res = con.selectOneColumn("sqlite_master", "name", "type IN ('table','view')");
        result.clear();
        String output = "";
        return res.stream().map((s) -> header_tpl.replace("${name}", s) + generate(s) + "\n").reduce(output, String::concat);
    }
    
    public void export(String dest) {
        String dep = dest + "\\sicut";
        result.forEach((key, value) -> {
            try (PrintWriter pw = new PrintWriter(new FileWriter(dest + "\\" + key + ".java"))) {
                pw.print(value);
            }
            catch ( IOException ex ) {  }
        });
        (new File(dep)).mkdir();
        dep += "\\sqlite";
        (new File(dep)).mkdir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(dep + "\\Table.java"))) {
            pw.print(table_tpl);
        }
        catch ( IOException ex ) {  }
        try (PrintWriter pw = new PrintWriter(new FileWriter(dep + "\\QueryFormatter.java"))) {
            pw.print(q_format_tpl);
        }
        catch ( IOException ex ) {  }
        try (PrintWriter pw = new PrintWriter(new FileWriter(dep + "\\Connector.java"))) {
            pw.print(connector_tpl);
        }
        catch ( IOException ex ) {  }
    }
    
    private String generate(String table) {
        String cap_name = table.substring(0, 1).toUpperCase() + table.substring(1).toLowerCase();
        String field, cap_field, constructor, list, field_type, pks_param, pks_select, assign_pks, assign_db, assign, assign_fields, cast2str, castnot2str, variables, fields_param, db_fields_param, get_set, fv_pairs, next_pk;
        String cls = class_tpl.replace("${user}", Settings.PREF_BUNDLE.get("AUTHOR")).replace("${date}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd, HH:mm:ss")));//.replace("${name}", table).replace("${cap_name}", cap_name);
        constructor = constructor_tpl;
        list = list_tpl;
        HashMap<String, ArrayList> cols = con.execute("PRAGMA table_info(" + table + ")");
        pks_param = "";
        assign_pks = "";
        assign_db = "";
        assign_fields = "";
        pks_select = "1";
        variables = "";
        fields_param = "";
        assign = "";
        db_fields_param = "";
        get_set = "";
        fv_pairs = "";
        next_pk = "";
        for ( int i = 0, n = cols.get("name").size(); i < n; i++ ) {
            field = cols.get("name").get(i).toString();
            field_type = tpl.getVarType(cols.get("type").get(i).toString());
            variables += "\n" + variable_tpl.replace("${variable}", field).replace("${type}", field_type);
            fields_param += ", " + field_type + " " + field;
            assign += "\n" + assign_tpl.replace("${variable}", field);
            fv_pairs += "\n" + pairs_put_tpl.replace("${variable}", field);
            cap_field = field.substring(0, 1).toUpperCase() + field.substring(1).toLowerCase();
            get_set += "\n" + get_set_tpl.replace("${cap_variable}", cap_field).replace("${type}", field_type).replace("${variable}", field);
            if ( field_type.startsWith("S") ) {
                cast2str = ".toString()";
                castnot2str = "";
            }
            else {
                cast2str = "";
                castnot2str = "(" + field_type + ") ";
            }
            if ( !cols.get("pk").get(i).toString().equals("0") ) {
                pks_param += ", " + field_type + " " + field;
                pks_select += " and " + field + "='\" + " + field + " + \"'";
                assign_pks += "\n" + assign_tpl.replace("${variable}", field);
                next_pk += "\n" + next_pk_tpl.replace("${pk}", field).replace("${cap_pk}", cap_field);
            }
            else {
                assign_fields += "\n" + assign_dummy_tpl.replace("${variable}", field).replace("${value}", ( field_type.startsWith("S") ? "\"\"" : "-1" ));
                assign_db += "\n" + db_assign_tpl.replace("${variable}", field).replace("${cast}", castnot2str).replace("${str_cast}", cast2str);
            }
            db_fields_param += ", " + db_variable_tpl.replace("${variable}", field).replace("${cast}", castnot2str).replace("${str_cast}", cast2str);
        }
        if ( pks_param.isEmpty() ) {
            cls = cls.replace("${constructor}", "");
        }
        else {
            pks_param = pks_param.substring(2);
            cls = cls.replace("${constructor}", constructor.replace("${pks_param}", pks_param).replace("${pks_assign}", assign_pks).replace("${db_assign}", assign_db).replace("${fields_assign}", assign_fields));
        }
        cls = cls.replace("${list}", list).replace("${db_fields_param}", db_fields_param.substring(2));
        cls = cls.replace("${pks_select}", pks_select).replace("${cap_name}", cap_name).replace("${name}", table).replace("${variables}", variables).replace("${fields_param}", fields_param.substring(2));
        cls = cls.replace("${assign}", assign).replace("${get_set}", get_set).replace("${pairs_put}", fv_pairs).replace("${next_pks}", next_pk);
        result.put(cap_name, cls);
        return cls;
    }
    
}
