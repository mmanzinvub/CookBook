package hr.vub.cookbook;

public class Step extends Food {
    private int recipeId;

    public Step(int id, String stepDescription) {
        super("Korak " + id, stepDescription);
        this.id = id;
    }

    public Step(String stepDescription) {
        super("Korak", stepDescription);
    }

    @Override
    public String getFoodType() {
        return "Step";
    }

    @Override
    public int getPreparationTime() {
        return 5;
    }

    @Override
    public String getDifficulty() {
        if (description.toLowerCase().contains("kuha") ||
                description.toLowerCase().contains("pec")) return "Srednje";
        if (description.toLowerCase().contains("prž")) return "Teško";
        return "Lagano";
    }

    @Override
    public boolean requiresCooking() {
        return description.toLowerCase().contains("kuha") ||
                description.toLowerCase().contains("pec") ||
                description.toLowerCase().contains("prž");
    }

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getStepDescription() {
        return description;
    }
    public void setStepDescription(String stepDescription) {
        this.description = stepDescription;
    }
}