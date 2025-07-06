package hr.vub.cookbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Recipe {
    private int id;
    private String name;
    private ObservableList<Ingredient> ingredients;
    private ObservableList<Step> steps;

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
        this.ingredients = FXCollections.observableArrayList();
        this.steps = FXCollections.observableArrayList();
    }

    public Recipe(String name) {
        this.name = name;
        this.ingredients = FXCollections.observableArrayList();
        this.steps = FXCollections.observableArrayList();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ObservableList<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(ObservableList<Ingredient> ingredients) { this.ingredients = ingredients; }

    public ObservableList<Step> getSteps() { return steps; }
    public void setSteps(ObservableList<Step> steps) { this.steps = steps; }
}