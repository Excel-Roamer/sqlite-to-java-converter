
package sicut.ui;

import icofont.IcoMoon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sicut.ui.MessageBox.BUTTON_TYPE;

/**
 * FXML Controller class
 *
 * @author Sicut
 */
public final class MessageBoxController {
    @FXML Label msg;
    @FXML Label msgIcon;
    @FXML Label msgTitle;
    @FXML Button ok, cancel, yes, no, close;
    
    private BUTTON_TYPE clicked;
    private Stage container;
    
    public MessageBoxController() {
    }
    
    public void initialize() {
        msgIcon.setFont((new IcoMoon(64)).getFont());
        close.setFont((new IcoMoon(10)).getFont());
        close.setText(IcoMoon.cancel);
        setType(MessageBox.TYPE.NONE);
        ok.setOnAction(e -> {
            clicked = BUTTON_TYPE.OK;
            container.close();
        });
        cancel.setOnAction(e -> {
            clicked = BUTTON_TYPE.CANCEL;
            container.close();
        });
        yes.setOnAction(e -> {
            clicked = BUTTON_TYPE.YES;
            container.close();
        });
        no.setOnAction(e -> {
            clicked = BUTTON_TYPE.NO;
            container.close();
        });
        close.setOnAction(e -> {
            clicked = null;
            container.close();
        });
    }
    
    public void setType(MessageBox.TYPE tp) {
        msgIcon.setVisible(true);
        switch ( tp ) {
            case CONFIRMATION:
                msgIcon.setText(IcoMoon.question);
                msgIcon.setStyle("-fx-text-fill:#ffc107;");
                msgTitle.setStyle("-fx-text-fill:#000; -fx-background-color:#ffc107;");
                ok.setVisible(false);
                cancel.setVisible(false);
                yes.setVisible(true);
                no.setVisible(true);
                break;
            case INFORMATION:
                msgIcon.setText(IcoMoon.information);
                msgIcon.setStyle("-fx-text-fill:#87CEEB;");
                msgTitle.setStyle("-fx-text-fill:#000; -fx-background-color:#87CEEB;");
                ok.setVisible(true);
                cancel.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
                break;
            case WARNING:
                msgIcon.setText(IcoMoon.warning);
                msgIcon.setStyle("-fx-text-fill:#ffc107;");
                msgTitle.setStyle("-fx-text-fill:#000; -fx-background-color:#ffc107;");
                ok.setVisible(true);
                cancel.setVisible(true);
                yes.setVisible(false);
                no.setVisible(false);
                break;
            case ERROR:
                msgIcon.setText(IcoMoon.cancel_circle);
                msgIcon.setStyle("-fx-text-fill:#f44336;");
                msgTitle.setStyle("-fx-text-fill:#fff; -fx-background-color:#f44336;");
                ok.setVisible(true);
                cancel.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
                break;
            case NOTIFICATION:
                msgIcon.setText(IcoMoon.notification);
                msgIcon.setStyle("-fx-text-fill:#2196F3;");
                msgTitle.setStyle("-fx-text-fill:#fff; -fx-background-color:#2196F3;");
                ok.setVisible(true);
                cancel.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
                break;
            case NONE:
                msgIcon.setVisible(false);
                msgTitle.setStyle("-fx-text-fill:#fff; -fx-background-color:#616161;");
                ok.setVisible(true);
                cancel.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
                break;
            case SUCCESS:
                msgIcon.setText(IcoMoon.tick);
                msgIcon.setStyle("-fx-text-fill:#8bc34a;");
                msgTitle.setStyle("-fx-text-fill:#fff; -fx-background-color:#8bc34a;");
                ok.setVisible(true);
                cancel.setVisible(false);
                yes.setVisible(false);
                no.setVisible(false);
                break;
        }
    }
    
    public BUTTON_TYPE show() {
        container = (Stage) ok.getScene().getWindow();
        container.showAndWait();
        return clicked;
    }
    
    public void setTitle(String title) {
        this.msgTitle.setText(title);
    }
    
    public void setMessage(String message) {
        msg.setText(message);
    }
    
    public void setInfos(String title, String message, MessageBox.TYPE tp) {
        setTitle(title);
        setMessage(message);
        setType(tp);
    }
}
