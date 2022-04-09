/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pl.polsl.polybiussquare2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.math.Decryption;
import pl.polsl.math.PolybiusException;

/**
 * FXML Controller class
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class DecryptionnController implements Initializable {

    private Decryption decryption;
    @FXML
    private TextField givenWord;

    /**
     * When button is pressed the decryption starts
     */
    @FXML
    private Button decryptButton;

    /**
     * When the button is pressed is return to menu view
     */
    @FXML
    private Button backButton;
    @FXML
    private Label resultDecryption;

    /**
     * Initializes the controller class.
     *
     * @param url url
     * @param rb resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        decryption = new Decryption();
        initData(App.listCode);
    }

    /**
     * Method which is execuden on action buttonDecrypt
     *
     * @param event event
     */
    @FXML
    private void decryptWord(ActionEvent event) {
        String decodedWord;
        List<String> newArgs = new ArrayList<>();
        List<Integer> givenWordIntegerArray = new ArrayList<>();
        String word = givenWord.getText();
        Stream<String> givenWordArray = Stream.of(word.split(" "));
        givenWordArray.forEach(letterCode -> newArgs.add(letterCode));
        boolean matches = true;
        int wordLenght = newArgs.size();
        for (int i = 0; i < wordLenght; i++) {
            if (!newArgs.get(i).matches("\\d\\d")) {
                matches = false;
            }
        }
        if (matches) {
            newArgs.stream()
                    .mapToInt(arg -> Integer.parseInt(arg))
                    .forEach(arg -> givenWordIntegerArray.add(arg));
            try {
                decodedWord = decryption.decryption(givenWordIntegerArray);
                resultDecryption.setText("Decrypted word:   " + decodedWord);
            } catch (PolybiusException exception) {
                new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.OK).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Worng format of data. Letter code is not two-digit or is not a number.", ButtonType.OK).show();
        }
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
            decryption.polybiusSquare.add(new ArrayList<>());
            decryption.polybiusSquare.get(i).addAll(list.get(i));
        }
    }

}
