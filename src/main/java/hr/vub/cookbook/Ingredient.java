package hr.vub.cookbook;

public class Ingredient extends Food {
    private int recipeId;
    private String quantity;
    private String mass;
    private String unit;

    public Ingredient(int id, String quantity, String mass, String unit, String ingredientName) {
        super(ingredientName, "Sastojak: " + ingredientName);
        this.id = id;
        this.quantity = quantity;
        this.mass = mass;
        this.unit = unit;
    }

    public Ingredient(String quantity, String mass, String unit, String ingredientName) {
        super(ingredientName, "Sastojak: " + ingredientName);
        this.quantity = quantity;
        this.mass = mass;
        this.unit = unit;
    }

    @Override
    public String getFoodType() {
        return "Ingredient";
    }

    @Override
    public int getPreparationTime() {
        return 0;
    }

    @Override
    public String getDifficulty() {
        return "Easy";
    }

    @Override
    public boolean requiresCooking() {
        return false;
    }

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMass() {
        return mass;
    }
    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIngredientName() {
        return name;
    }
    public void setIngredientName(String ingredientName) {
        this.name = ingredientName;
    }
}