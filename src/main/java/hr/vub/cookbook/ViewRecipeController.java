package hr.vub.cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ViewRecipeController implements Initializable {

    @FXML private Label recipeNameLabel;
    @FXML private ListView<String> ingredientsList;
    @FXML private ListView<String> stepsList;
    @FXML private Button backButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    public static Recipe selectedRecipe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (selectedRecipe != null) {
            displayRecipeData();
        }
    }

    private void displayRecipeData() {
        recipeNameLabel.setText(selectedRecipe.getName());

        ObservableList<String> ingredientStrings = FXCollections.observableArrayList();
        for (Ingredient ingredient : selectedRecipe.getIngredients()) {
            String ingredientStr = ingredient.getQuantity() + " " +
                    ingredient.getMass() + " " +
                    ingredient.getUnit() + " " +
                    ingredient.getIngredientName();
            ingredientStrings.add(ingredientStr);
        }

        ObservableList<String> stepStrings = FXCollections.observableArrayList();
        int stepNumber = 1;
        for (Step step : selectedRecipe.getSteps()) {
            stepStrings.add(stepNumber + ". " + step.getStepDescription());
            stepNumber++;
        }

        ingredientsList.setItems(ingredientStrings);
        stepsList.setItems(stepStrings);
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("recipe");
    }

    @FXML
    private void handleEdit() {
        AddRecipeController.setEditMode(selectedRecipe);
        SceneManager.switchTo("add-recipe");
    }

    @FXML
    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText(null);
        alert.setContentText("Jeste li sigurni da želite obrisati recept '" + selectedRecipe.getName() + "'?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            RecipeRepository recipeRepository = new RecipeRepositoryImpl(connection);

            try {
                recipeRepository.deleteById(selectedRecipe.getId());

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Uspjeh");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Recept je uspješno obrisan!");
                successAlert.showAndWait();

                SceneManager.switchTo("recipe");
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Greška");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Greška pri brisanju recepta: " + e.getMessage());
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public static void setRecipeData(Recipe recipe) {
        selectedRecipe = recipe;
    }
}