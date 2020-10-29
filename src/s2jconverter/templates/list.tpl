public ArrayList<${cap_name}> list() {
        HashMap<String, ArrayList> res = super.select();
        ArrayList<${cap_name}> l = new ArrayList();
        for ( int i = 0, n = res.get(res.keySet().iterator().next()).size(); i < n; i++ ) {
            l.add( new ${cap_name}( ${db_fields_param} ) );
        }
        return l;
    }