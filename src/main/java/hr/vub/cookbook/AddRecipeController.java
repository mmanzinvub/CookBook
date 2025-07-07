package hr.vub.cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AddRecipeController implements Initializable {

    @FXML private TextField recipeNameField;

    @FXML private TextField quantityField;
    @FXML private TextField massField;
    @FXML private TextField unitField;
    @FXML private TextField ingredientNameField;
    @FXML private Button addIngredientButton;
    @FXML private TableView<IngredientRow> ingredientsTable;
    @FXML private TableColumn<IngredientRow, Integer> idColumn;
    @FXML private TableColumn<IngredientRow, String> quantityColumn;
    @FXML private TableColumn<IngredientRow, String> massColumn;
    @FXML private TableColumn<IngredientRow, String> unitColumn;
    @FXML private TableColumn<IngredientRow, String> ingredientColumn;
    @FXML private Button removeIngredientButton;
    @FXML private Button saveRecipeButton;

    @FXML private TextArea instructionsTextArea;
    @FXML private Button addStepButton;
    @FXML private TableView<StepRow> stepsTable;
    @FXML private TableColumn<StepRow, Integer> stepIdColumn;
    @FXML private TableColumn<StepRow, String> stepColumn;
    @FXML private Button removeStepButton;

    private final ObservableList<IngredientRow> ingredientsList = FXCollections.observableArrayList();
    private final ObservableList<StepRow> stepsList = FXCollections.observableArrayList();
    private int ingredientIdCounter = 1;
    private int stepIdCounter = 1;
    private static boolean isEditMode = false;
    private static Recipe editRecipe = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        massColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredient"));

        stepIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stepColumn.setCellValueFactory(new PropertyValueFactory<>("step"));

        ingredientsTable.setItems(ingredientsList);
        stepsTable.setItems(stepsList);

        if (isEditMode && editRecipe != null) {
            populateFormForEdit();
        }
    }

    private void populateFormForEdit() {
        recipeNameField.setText(editRecipe.getName());

        ingredientsList.clear();
        stepsList.clear();

        ingredientIdCounter = 1;
        for (Ingredient ingredient : editRecipe.getIngredients()) {
            IngredientRow ingredientRow = new IngredientRow(
                    ingredientIdCounter++,
                    ingredient.getQuantity(),
                    ingredient.getMass(),
                    ingredient.getUnit(),
                    ingredient.getIngredientName()
            );
            ingredientsList.add(ingredientRow);
        }

        stepIdCounter = 1;
        for (Step step : editRecipe.getSteps()) {
            StepRow stepRow = new StepRow(stepIdCounter++, step.getStepDescription());
            stepsList.add(stepRow);
        }

        ingredientIdCounter = ingredientsList.size() + 1;
        stepIdCounter = stepsList.size() + 1;
    }

    @FXML
    private void handleAddIngredient() {
        String quantity = quantityField.getText().trim();
        String mass = massField.getText().trim();
        String unit = unitField.getText().trim();
        String ingredient = ingredientNameField.getText().trim();

        if (!quantity.isEmpty() && !mass.isEmpty() && !unit.isEmpty() && !ingredient.isEmpty()) {
            IngredientRow newIngredient = new IngredientRow(ingredientIdCounter++, quantity, mass, unit, ingredient);
            ingredientsList.add(newIngredient);

            quantityField.clear();
            massField.clear();
            unitField.clear();
            ingredientNameField.clear();
        }
    }

    @FXML
    private void handleRemoveIngredient() {
        IngredientRow selected = ingredientsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ingredientsList.remove(selected);
        }
    }

    @FXML
    private void handleAddStep() {
        String instruction = instructionsTextArea.getText().trim();
        if (!instruction.isEmpty()) {
            StepRow newStep = new StepRow(stepIdCounter++, instruction);
            stepsList.add(newStep);
            instructionsTextArea.clear();
        }
    }

    @FXML
    private void handleRemoveStep() {
        StepRow selected = stepsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            stepsList.remove(selected);
        }
    }

    @FXML
    private void handleSaveRecipe() {
        String recipeName = recipeNameField.getText().trim();

        if (!recipeName.isEmpty() && !ingredientsList.isEmpty() && !stepsList.isEmpty()) {
            Recipe recipe = new Recipe(recipeName);

            for (IngredientRow ingredientRow : ingredientsList) {
                Ingredient ingredient = new Ingredient(
                        ingredientRow.getQuantity(),
                        ingredientRow.getMass(),
                        ingredientRow.getUnit(),
                        ingredientRow.getIngredient()
                );
                recipe.getIngredients().add(ingredient);
            }

            for (StepRow stepRow : stepsList) {
                Step step = new Step(stepRow.getStep());
                recipe.getSteps().add(step);
            }

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connection = connectNow.getConnection();
            RecipeRepository recipeRepository = new RecipeRepositoryImpl(connection);

            try {
                if (isEditMode && editRecipe != null) {
                    recipe.setId(editRecipe.getId());
                    recipeRepository.update(recipe);
                } else {
                    recipeRepository.save(recipe);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspjeh");
                alert.setHeaderText(null);
                alert.setContentText("Recept '" + recipeName + "' je uspješno " + (isEditMode ? "ažuriran" : "dodan") + "!");
                alert.showAndWait();

                clearForm();
                clearEditMode();

                RecipeController.refreshRecipeList();

                SceneManager.switchTo("recipe");

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText(null);
                alert.setContentText("Greška pri spremanju recepta: " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Greška");
            alert.setHeaderText(null);
            alert.setContentText("Molimo unesite naziv recepta, sastojke i korake pripreme!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack() {
        clearEditMode();
        clearForm();
        SceneManager.switchTo("recipe");
    }

    private void clearForm() {
        recipeNameField.clear();
        ingredientsList.clear();
        stepsList.clear();
        ingredientIdCounter = 1;
        stepIdCounter = 1;
    }

    public static void setEditMode(Recipe recipe) {
        isEditMode = true;
        editRecipe = recipe;
    }

    public static void clearEditMode() {
        isEditMode = false;
        editRecipe = null;
    }

    public static class IngredientRow {
        private final int id;
        private final String quantity;
        private final String mass;
        private final String unit;
        private final String ingredient;

        public IngredientRow(int id, String quantity, String mass, String unit, String ingredient) {
            this.id = id;
            this.quantity = quantity;
            this.mass = mass;
            this.unit = unit;
            this.ingredient = ingredient;
        }

        public int getId() { return id; }
        public String getQuantity() { return quantity; }
        public String getMass() { return mass; }
        public String getUnit() { return unit; }
        public String getIngredient() { return ingredient; }
    }

    public static class StepRow {
        private final int id;
        private final String step;

        public StepRow(int id, String step) {
            this.id = id;
            this.step = step;
        }

        public int getId() { return id; }
        public String getStep() { return step; }
    }
}