package hr.vub.cookbook;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static Stage mainStage;
    private static final Map<String, String> sceneMap = new HashMap<>();

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void registerScene(String name, String fxmlPath) {
        sceneMap.put(name, fxmlPath);
    }

    public static void switchTo(String name) {
        String fxmlPath = sceneMap.get(name);
        if (fxmlPath == null) {
            System.err.println("Scena s imenom '" + name + "' nije registrirana!");
            return;
        } else {
            System.out.println("Scena je registrirana " + fxmlPath);
        }

        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(fxmlPath));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}