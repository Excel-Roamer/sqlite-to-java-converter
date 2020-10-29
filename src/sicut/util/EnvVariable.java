package sicut.util;

public class EnvVariable {
    
    public static String APPDATADirectory(){
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("APPDATA");
        }
        else {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "\\Library\\Application Support";
        }
        return workingDirectory;
    }
    
}
