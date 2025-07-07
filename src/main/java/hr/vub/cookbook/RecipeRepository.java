package hr.vub.cookbook;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findAll();
    Recipe findById(int id);
    void save(Recipe recipe);
    void deleteById(int id);
    void update(Recipe recipe);
}