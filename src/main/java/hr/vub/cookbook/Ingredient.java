package hr.vub.cookbook;

public class Ingredient {
    private int id;
    private int recipeId;
    private String quantity;
    private String mass;
    private String unit;
    private String ingredientName;

    public Ingredient(int id, String quantity, String mass, String unit, String ingredientName) {
        this.id = id;
        this.quantity = quantity;
        this.mass = mass;
        this.unit = unit;
        this.ingredientName = ingredientName;
    }

    public Ingredient(String quantity, String mass, String unit, String ingredientName) {
        this.quantity = quantity;
        this.mass = mass;
        this.unit = unit;
        this.ingredientName = ingredientName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getMass() { return mass; }
    public void setMass(String mass) { this.mass = mass; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getIngredientName() { return ingredientName; }
    public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }
}