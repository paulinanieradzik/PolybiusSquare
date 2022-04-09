package pl.polsl.polybiussquare2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static List<List<Character>> listCode = new ArrayList<>(5);

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainPageView"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Polybius Square");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    static void setRoot(String fxml, List<List<Character>> list) throws IOException {
        for(int i=0; i<list.size(); i++)
        {
            listCode.add(new ArrayList());
            listCode.get(i).addAll(list.get(i));
            
        }
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}