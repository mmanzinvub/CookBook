package hr.vub.cookbook;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DataManager {
    private static final String RECIPES_FILE = "recipes.txt";

    public static CompletableFuture<Void> exportRecipesToTxt(List<Recipe> recipes) {
        return CompletableFuture.runAsync(() -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(RECIPES_FILE))) {
                writer.println("=== COOKBOOK RECEPTI ===");
                writer.println("Datum: " + new Date());
                writer.println("Ukupno recepata: " + recipes.size());
                writer.println("========================");
                writer.println();

                for (Recipe recipe : recipes) {
                    writer.println("RECEPT: " + recipe.getName());
                    writer.println("Tip: " + recipe.getFoodType());
                    writer.println("Težina: " + recipe.getDifficulty());
                    writer.println("Vrijeme: " + recipe.getPreparationTime() + " min");
                    writer.println("Sastojci:");

                    for (Ingredient ingredient : recipe.getIngredients()) {
                        writer.println("    " + ingredient.getQuantity() + " " +
                                ingredient.getMass() + " " + ingredient.getUnit() +
                                " " + ingredient.getIngredientName());
                    }

                    writer.println("Koraci:");
                    int stepNum = 1;
                    for (Step step : recipe.getSteps()) {
                        writer.println("    " + stepNum + ". " + step.getStepDescription());
                        stepNum++;
                    }
                    writer.println("-------------------");
                    writer.println();
                }

                System.out.println("Export u " + RECIPES_FILE + " je gotov");

            } catch (IOException e) {
                System.err.println("Greška pri exportu: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
