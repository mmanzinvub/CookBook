package hr.vub.cookbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepository {
    private Connection connection;

    public RecipeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Recipe recipe = new Recipe(id, name);

                loadIngredientsForRecipe(recipe);
                loadStepsForRecipe(recipe);

                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public Recipe findById(int id) {
        String query = "SELECT * FROM recipes WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Recipe recipe = new Recipe(id, name);

                loadIngredientsForRecipe(recipe);
                loadStepsForRecipe(recipe);

                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Recipe recipe) {
        String query = "INSERT INTO recipes (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                int recipeId = keys.getInt(1);
                recipe.setId(recipeId);

                saveIngredients(recipeId, recipe.getIngredients());

                saveSteps(recipeId, recipe.getSteps());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Recipe recipe) {
        String query = "UPDATE recipes SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setInt(2, recipe.getId());
            preparedStatement.executeUpdate();

            deleteIngredientsByRecipeId(recipe.getId());
            deleteStepsByRecipeId(recipe.getId());

            saveIngredients(recipe.getId(), recipe.getIngredients());
            saveSteps(recipe.getId(), recipe.getSteps());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM recipes WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Recipe with ID=" + id + " deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadIngredientsForRecipe(Recipe recipe) {
        String query = "SELECT * FROM ingredients WHERE recipe_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipe.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String quantity = resultSet.getString("quantity");
                String mass = resultSet.getString("mass");
                String unit = resultSet.getString("unit");
                String ingredientName = resultSet.getString("ingredient_name");

                Ingredient ingredient = new Ingredient(id, quantity, mass, unit, ingredientName);
                recipe.getIngredients().add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStepsForRecipe(Recipe recipe) {
        String query = "SELECT * FROM steps WHERE recipe_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipe.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String stepDescription = resultSet.getString("step_description");

                Step step = new Step(id, stepDescription);
                recipe.getSteps().add(step);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveIngredients(int recipeId, List<Ingredient> ingredients) {
        String query = "INSERT INTO ingredients (recipe_id, quantity, mass, unit, ingredient_name) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Ingredient ingredient : ingredients) {
                preparedStatement.setInt(1, recipeId);
                preparedStatement.setString(2, ingredient.getQuantity());
                preparedStatement.setString(3, ingredient.getMass());
                preparedStatement.setString(4, ingredient.getUnit());
                preparedStatement.setString(5, ingredient.getIngredientName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSteps(int recipeId, List<Step> steps) {
        String query = "INSERT INTO steps (recipe_id, step_description) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Step step : steps) {
                preparedStatement.setInt(1, recipeId);
                preparedStatement.setString(2, step.getStepDescription());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteIngredientsByRecipeId(int recipeId) {
        String query = "DELETE FROM ingredients WHERE recipe_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStepsByRecipeId(int recipeId) {
        String query = "DELETE FROM steps WHERE recipe_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, recipeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}