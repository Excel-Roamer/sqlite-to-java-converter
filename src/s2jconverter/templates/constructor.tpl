public ${cap_name}(${pks_param}) {
        this();
        HashMap<String, ArrayList> res = super.select("${pks_select}");
        ${pks_assign}
        if ( res.get(res.keySet().iterator().next()).size() > 0 ) {
            ${db_assign}
        }
        else {
            ${fields_assign}
        }
    }