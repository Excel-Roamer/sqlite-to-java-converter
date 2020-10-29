    public int next${cap_pk}() {
        HashMap<String, ArrayList> res = super.select(new String[] {"max(${pk}) as ${pk}"}, "1");
        return res.get("${pk}").isEmpty() ? 0 : (int) res.get("${pk}").get(0) + 1;
    }