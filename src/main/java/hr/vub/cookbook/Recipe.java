package hr.vub.cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Recipe extends Food {
    private ObservableList<Ingredient> ingredients;
    private ObservableList<Step> steps;

    public Recipe(int id, String name) {
        super(name, "Recept za " + name);
        this.id = id;
        this.ingredients = FXCollections.observableArrayList();
        this.steps = FXCollections.observableArrayList();
    }

    public Recipe(String name) {
        super(name, "Recept za " + name);
        this.ingredients = FXCollections.observableArrayList();
        this.steps = FXCollections.observableArrayList();
    }

    @Override
    public String getFoodType() {
        return "Recept";
    }

    @Override
    public int getPreparationTime() {
        return steps.size() * 10;
    }

    @Override
    public String getDifficulty() {
        if (ingredients.size() <= 3) return "Lagano";
        if (ingredients.size() <= 6) return "Srednje";
        return "Teško";
    }

    @Override
    public boolean requiresCooking() {
        return steps.stream()
                .anyMatch(step -> step.getStepDescription().toLowerCase().contains("kuha") ||
                        step.getStepDescription().toLowerCase().contains("pec") ||
                        step.getStepDescription().toLowerCase().contains("prž"));
    }

    public ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(ObservableList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ObservableList<Step> getSteps() {
        return steps;
    }
    public void setSteps(ObservableList<Step> steps) {
        this.steps = steps;
    }
}