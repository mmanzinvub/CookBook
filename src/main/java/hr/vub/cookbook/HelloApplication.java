package hr.vub.cookbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Starting CookBook application...");

        List<Recipe> generatedData = GeneratedData.getGeneratedRecipes();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/vub/cookbook/login-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 640.0, 640.0);

        stage.setTitle("CookBook");
        stage.setScene(scene);
        stage.show();

        SceneManager.setStage(stage);
        SceneManager.registerScene("login-screen", "/hr/vub/cookbook/login-screen.fxml");
        SceneManager.registerScene("recipe", "/hr/vub/cookbook/recipe.fxml");
        SceneManager.registerScene("add-recipe", "/hr/vub/cookbook/add-recipe.fxml");
        SceneManager.registerScene("view-recipe", "/hr/vub/cookbook/view-recipe.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}