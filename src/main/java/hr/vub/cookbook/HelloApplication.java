package hr.vub.cookbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Starting CookBook application...");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/vub/cookbook/login-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 640.0, 640.0);

        stage.setTitle("CookBook - Prijava");
        stage.setScene(scene);
        stage.show();

        SceneManager.setStage(stage);
        SceneManager.registerScene("hello-view", "/hr/vub/cookbook/hello-view.fxml");
        SceneManager.registerScene("login-screen", "/hr/vub/cookbook/login-screen.fxml");
        SceneManager.registerScene("recipeTable", "/hr/vub/cookbook/recipe-table.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}