package hr.vub.cookbook;

public abstract class Food {
    protected int id;
    protected String name;
    protected String description;

    public Food(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract String getFoodType();
    public abstract int getPreparationTime();
    public abstract String getDifficulty();
    public abstract boolean requiresCooking();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
