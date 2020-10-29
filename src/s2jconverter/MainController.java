/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2jconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import sicut.sqlite.Connector;
import sicut.ui.MessageBox;

/**
 * FXML Controller class
 *
 * @author Sicut
 */
public class MainController implements Initializable {

    @FXML private Button load, generate, export;
    @FXML private TextArea preview, output;
    private final FileChooser fc;
    private final DirectoryChooser dc;
    private String newDB;
    private final Engine eng;
    
    public MainController() {
        fc = new FileChooser();
        dc = new DirectoryChooser();
        eng = new Engine();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        load.setOnAction(evt -> {
            File ini_load_dir = new File(Settings.PREF_BUNDLE.get("LOAD_PATH"));
            File f;
            fc.setTitle("");
            if ( ini_load_dir.exists() && ini_load_dir.isDirectory() )
                fc.setInitialDirectory(ini_load_dir);
            fc.getExtensionFilters().clear();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sql files", "*.sql"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Everything", "*.*"));
            f = fc.showOpenDialog(load.getScene().getWindow());
            preview.clear();
            if ( f == null )
                return;
            if ( f.getParentFile() != ini_load_dir )
                Settings.PREF_BUNDLE.update("LOAD_PATH", f.getParent());
            if ( f.canRead() ) {
                try (BufferedReader br = new BufferedReader(new FileReader(f.getPath()))) {
                    String line;
                    while ( ( line = br.readLine() ) != null ) {
                        preview.appendText(line + "\n");
                    }
                } catch (IOException ex) {
                    
                }
            }
            else {
            
            }
        });
        
        generate.setOnAction(evt -> {
            output.clear();
            if ( preview.getText().isEmpty() )
                return;
            File db = new File(Settings.DB_PATH);
            if ( !db.exists() ) {
                db.mkdir();
            }
            newDB = Settings.DB_PATH + "data--" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss")) + ".s2jc";
            Connector con = new Connector(newDB);
            if ( !con.connect() )   return;
            String crQuery = "";
            String[] cnt = preview.getText().split("\\n");
            con.prpeareBatch();
            for( int i = 0, n = cnt.length; i < n; i++ ) {
                if ( cnt[i].startsWith("--"))
                    continue;
                crQuery += cnt[i];
                if ( cnt[i].contains(";") ) {
                    con.batch(crQuery);
                    crQuery = "";
                }
            }
            if ( con.runBatch() ) {
                eng.setCon(con);
                output.setText(eng.generate());
            }
            con.close();
        });
        
        export.setOnAction(evt -> {
            if ( output.getText().isEmpty() )
                return;
            File ini_dest = new File(Settings.PREF_BUNDLE.get("DEST_DIR"));
            dc.setTitle("");
            if ( ini_dest.exists() )
                dc.setInitialDirectory(ini_dest);
            File f = dc.showDialog(export.getScene().getWindow());
            if ( f == null )
                return;
            String dest = f.getAbsolutePath();
            Settings.PREF_BUNDLE.update("DEST_DIR", dest);
            dest += "\\" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss"));
            (new File(dest)).mkdir();
            eng.export(dest);
            try {
                (new File(newDB)).delete();
            } catch(SecurityException ex){ }
            MessageBox mb = new MessageBox(export.getScene().getWindow());
            mb.setLanguage(Settings.PREF_BUNDLE.get("LANGUAGE"));
            mb.setType(MessageBox.TYPE.SUCCESS);
            mb.setTitle(Settings.I18N_BUNDLE.getString("SUCCESS_TITLE"));
            mb.setMessage(Settings.I18N_BUNDLE.getString("SUCCESS_MSG"));
            mb.show();
        });
    }
    
}
