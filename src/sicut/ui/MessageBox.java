
package sicut.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Sicut
 */
public class MessageBox {
    
    public enum BUTTONS { OK, YES_NO, OK_CANCEL };
    public enum BUTTON_TYPE { OK, YES, NO, CANCEL };
    public enum TYPE { CONFIRMATION, INFORMATION, NOTIFICATION, WARNING, ERROR, NONE, SUCCESS };
    private final MessageBoxController controller;
    private final RB resB;
    private final Stage container;
    
    public MessageBox(Window parent) {
        container = new Stage();
        resB = new RB();
        FXMLLoader fl = new FXMLLoader(getClass().getResource("messageBox.fxml"), resB);
        try {
            Parent p = fl.load();
            Scene s = new Scene(p);
            container.initOwner(parent);
            container.initModality(Modality.WINDOW_MODAL);
            container.initStyle(StageStyle.UNDECORATED);
            container.setResizable(false);
            container.setScene(s);
        } catch (IOException ex) { 
            System.out.println("Erroe: " + ex);
        }
        controller = fl.getController();
    }
    
    public MessageBox(Window parent, String title, String message) {
        this(parent);
        controller.setTitle(title);
        controller.setMessage(message);
    }
    
    public MessageBox(Window parent, String title, String message, TYPE tp) {
        this(parent);
        controller.setInfos(title, message, tp);
    }
    
    public void setLanguage(String lang) {
        
        resB.setLanguage(lang);
    }
    
    public void setType(TYPE tp) {
        controller.setType(tp);
    }
    
    public void setTitle(String title) {
        controller.setTitle(title);
    }
    
    public void setMessage(String message) {
        controller.setMessage(message);
    }
    
    public BUTTON_TYPE show() {
        return controller.show();
    }
    
}
