package sicut.ui;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RB extends ResourceBundle {
    
    HashMap<String, String> bundle;
    
    public RB() {
        this("EN");
    }
    
    public RB(String lang) {
        bundle = new HashMap();
        setLanguage(lang);
    }
    
    
    public final void setLanguage(String lang) {
        switch (lang) {
            case "AR":
                bundle.put("cancel", "إلغاء");
                bundle.put("ok", "مرحبا");
                bundle.put("yes", "نعم");
                bundle.put("no", "لا");
                break;
            case "FR":
                bundle.put("cancel", "Annuler");
                bundle.put("ok", "Ok");
                bundle.put("yes", "Oui");
                bundle.put("no", "Non");
                break;
            case "EN":
                bundle.put("cancel", "Cancel");
                bundle.put("ok", "Ok");
                bundle.put("yes", "Yes");
                bundle.put("no", "No");
                break;
        }
    }

    @Override
    protected Object handleGetObject(String key) {
        return bundle.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(bundle.keySet());
    }
    
}
