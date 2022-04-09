package pl.polsl.polybiussquare2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.math.Encryption;
import pl.polsl.math.PolybiusException;

/**
 * FXML Controller class
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class EncryptionController implements Initializable {

    private Encryption encryption;
    @FXML
    private TextField givenWord;

    /**
     * When button is pressed the encryption starts
     */
    @FXML
    private Button encryptButton;
    @FXML
    private Label resultEncryption;

    /**
     * When the button is pressed is return to menu view
     */
    @FXML
    private Button buttonBack;

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        encryption = new Encryption();
        initData(App.listCode);
    }

    /**
     * Method which is execuden on action buttonEncryppt
     *
     * @param event event
     */
    @FXML
    private void encryptWord(ActionEvent event) {
        String result = "";
        try {
            List<Integer> encryptedWord = encryption.encryption(givenWord.getText());
            for (int letterCode : encryptedWord) {
                result += " " + letterCode;
            }
        } catch (PolybiusException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
        }
        resultEncryption.setText("Encrypted word: " + result);
    }

    /**
     * Method which is execuded after press buttonBack
     *
     * @param event event
     * @throws IOException throw when is an incorrect input
     */
    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("mainPageView");
    }

    /**
     * Method inicialize list of list with letteers codes
     *
     * @param list list
     */
    public void initData(List<List<Character>> list) {
        for (int i = 0; i < list.size(); i++) {
            encryption.polybiusSquare.add(new ArrayList<>());
            encryption.polybiusSquare.get(i).addAll(list.get(i));
        }
    }
}
