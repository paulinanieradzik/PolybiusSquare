package pl.polsl.polybiussquare2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class MainPageController implements Initializable {

    /**
     *Pressed this button swich view to decrypt
     */
    @FXML
    private Button decryptionButton;

    /**
     *Pressed this button swich view to encrypt
     */
    @FXML
    private Button encryptionButton;
    @FXML
    private Button buttonDisplay;

    /**
     * Initializes the controller class.
     * @param url url
     * @param rb  resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Switch view to decrypt
     * @param event event
     * @throws IOException throw when is an incorrect input
     */
    @FXML
    private void switchToDecryption(ActionEvent event) throws IOException {
        App.setRoot("squareView");
    }

    /**
     * Swich view to encrypt
     * @param event event
     * @throws IOException throw when is an incorrect input
     */
    @FXML
    private void switchToEncryption(ActionEvent event) throws IOException {
        App.setRoot("squareEncodingView");
    }

    @FXML
    private void displaySquare(ActionEvent event) throws IOException {
        App.setRoot("squareView");
    }

}
