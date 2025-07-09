package hr.vub.cookbook;

import java.util.List;

public interface RecipeRepository {
    void save(Recipe recipe);
    List<Recipe> findAll();
    Recipe findById(int id);
    void update(Recipe recipe);
    void deleteById(int id);
}