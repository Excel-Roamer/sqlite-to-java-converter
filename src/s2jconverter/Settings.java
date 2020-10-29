package s2jconverter;

import icofont.IcoMoon;
import java.util.ResourceBundle;
import sicut.db.Preferences;
import sicut.util.EnvVariable;

public class Settings {
    public static final String DB_FOLDER_PATH = EnvVariable.APPDATADirectory() + "\\.S2JConverter\\";
    //public static final String DB_NAME = "data--" + ;
    public static final String PREF_DB_NAME = "preferences.s2jc";
    public static final String I18N_DB_NAME = "properties.s2jc";
    public static final String DB_PATH = DB_FOLDER_PATH + "temp\\";
    public static final String PREF_DB_PATH = DB_FOLDER_PATH + PREF_DB_NAME;
    public static final String I18N_DB_PATH = DB_FOLDER_PATH + I18N_DB_NAME;
    
    public static ResourceBundle I18N_BUNDLE;
    public static Preferences PREF_BUNDLE;
    
    public static IcoMoon icoFont = new IcoMoon();
}
