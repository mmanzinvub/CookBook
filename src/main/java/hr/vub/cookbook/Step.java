package hr.vub.cookbook;

public class Step {
    private int id;
    private int recipeId;
    private String stepDescription;

    public Step(int id, String stepDescription) {
        this.id = id;
        this.stepDescription = stepDescription;
    }

    public Step(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public String getStepDescription() { return stepDescription; }
    public void setStepDescription(String stepDescription) { this.stepDescription = stepDescription; }
}