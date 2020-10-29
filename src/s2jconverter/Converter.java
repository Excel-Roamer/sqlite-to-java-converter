/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2jconverter;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sicut.db.Configurator;
import sicut.db.Preferences;
import sicut.db.Translator;

/**
 *
 * @author Sicut
 */
public class Converter extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        String lang;
        Parent root;
        Scene scene;
        FXMLLoader fl;
        
        (new Configurator()).loadPreferences();
        Settings.I18N_BUNDLE = new Translator();
        Settings.PREF_BUNDLE = new Preferences();
        fl = new FXMLLoader(getClass().getResource("/s2jconverter/Main.fxml"), Settings.I18N_BUNDLE);
        lang = Settings.PREF_BUNDLE.get("LANGUAGE");
        Locale.setDefault(new Locale(lang));
        root = fl.load();
        scene = new Scene(root);
        scene.setNodeOrientation( ( lang.equals("AR") ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT ) );
        stage.setScene(scene);
        stage.setTitle(Settings.I18N_BUNDLE.getString("APP_TITLE"));
        stage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("icons/LOGO128.png")),
                new Image(getClass().getResourceAsStream("icons/LOGO64.png")),
                new Image(getClass().getResourceAsStream("icons/LOGO48.png")),
                new Image(getClass().getResourceAsStream("icons/LOGO32.png")),
                new Image(getClass().getResourceAsStream("icons/LOGO16.png"))
        );
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
