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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.math.SquereFX;

/**
 * FXML Controller class
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class SquareEncodingController implements Initializable {

    @FXML
    private TableView<SquereFX> tableCode;
    @FXML
    private TableColumn<SquereFX, String> firstColumn;
    @FXML
    private TableColumn<SquereFX, String> secondColumn;
    @FXML
    private TableColumn<SquereFX, String> thirdColumn;
    @FXML
    private TableColumn<SquereFX, String> fourthColumn;
    @FXML
    private TableColumn<SquereFX, String> fifthColumn;
    @FXML
    private TableColumn<SquereFX, String> sixthColumn;

    /**
     *Go to next step - decryption
     */
    @FXML
    private Button buttonNext;

    /**
     * Initializes the controller class.
     * @param url url
     * @param rb resource
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("First"));
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("Second"));
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("Third"));
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("Fourth"));
        fifthColumn.setCellValueFactory(new PropertyValueFactory<>("Fifth"));
        sixthColumn.setCellValueFactory(new PropertyValueFactory<>("Sixth"));
        tableCode.setItems(squere);
    }
    /**
     * Inicjalize List
     */
    private ObservableList<SquereFX> squere = FXCollections.observableArrayList(
            new SquereFX("1", "A", "B", "C", "D", "E"),
            new SquereFX("2", "F", "G", "H", "I", "K"),
            new SquereFX("3", "L", "M", "N", "O", "P"),
            new SquereFX("4", "Q", "R", "S", "T", "U"),
            new SquereFX("5", "V", "W", "X", "Y", "Z")
    );

    /**
     * Go to next step - encryption
     * @param event enent
     * @throws IOException throw when is an incorrect input
     */
    @FXML
    private void goToNextStep(ActionEvent event) throws IOException {
        SquereFX item;
        List<List<Character>> square = new ArrayList<>();
        for (int i = 0; i < tableCode.getItems().size(); i++) {
            item = tableCode.getItems().get(i);
            square.add(new ArrayList<>());
            char secondToChar[] = item.getSecond().toCharArray();
            square.get(i).add(secondToChar[0]);
            char thirdToChar[] = item.getThird().toCharArray();
            square.get(i).add(thirdToChar[0]);
            char fourthToChar[] = item.getFourth().toCharArray();
            square.get(i).add(fourthToChar[0]);
            char fifthToChar[] = item.getFifth().toCharArray();
            square.get(i).add(fifthToChar[0]);
            char sixthToChar[] = item.getSixth().toCharArray();
            square.get(i).add(sixthToChar[0]);
        }
        App.setRoot("encryptionView", square);
    }
}
