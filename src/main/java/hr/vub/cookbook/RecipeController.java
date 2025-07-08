package hr.vub.cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class RecipeController implements Initializable {
    @FXML private TableView<Recipe> recipesTable;
    @FXML private TableColumn<Recipe, Integer> recipeIdColumn;
    @FXML private TableColumn<Recipe, String> recipeNameColumn;
    @FXML private Button viewRecipeButton;
    @FXML private Button addNewRecipeButton;
    @FXML private Button exportButton;

    private RecipeRepository recipeRepository;
    private ObservableList<Recipe> recipesList;
    private Connection connection = null;

    private static RecipeController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        loadData();
    }

    private void loadData() {
        recipeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        recipeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        recipesList = FXCollections.observableArrayList();
        recipesTable.setItems(recipesList);

        DatabaseConnection connectNow = new DatabaseConnection();
        connection = connectNow.getConnection();

        recipeRepository = new RecipeRepositoryImpl(connection);

        refreshData();
    }

    private void refreshData() {
        recipesList.clear();
        try {
            List<Recipe> recipes = recipeRepository.findAll();
            recipesList.addAll(recipes);
            recipesTable.setItems(recipesList);
            System.out.println("Loaded " + recipes.size() + " recipes from database");
        } catch (Exception e) {
            System.err.println("Error loading recipes from database:");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Greška pri učitavanju recepata iz baze: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleViewRecipe() {
        Recipe selected = recipesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ViewRecipeController.setRecipeData(selected);
            SceneManager.switchTo("view-recipe");
        } else {
            showAlert("Molimo odaberite recept za pregled!");
        }
    }

    @FXML
    private void handleAddNewRecipe() {
        AddRecipeController.clearEditMode();
        SceneManager.switchTo("add-recipe");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upozorenje");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void refreshRecipeList() {
        if (instance != null) {
            instance.refreshData();
            System.out.println("Recipe list refreshed");
        } else {
            System.out.println("Recipe list refresh requested but instance is null");
        }
    }

    @FXML
    private void handleExport() {
        DataManager.exportRecipesToTxt(recipesList)
                .thenRun(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Export");
                    alert.setContentText("Recepti su uspješno eksportirani u recipes.txt!");
                    alert.showAndWait();
                });
    }
}