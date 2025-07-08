package hr.vub.cookbook;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratedData {

    public static List<Recipe> getGeneratedRecipes() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe(1, "Gulaš"),
                new Recipe(2, "Kokice"),
                new Recipe(3, "Juha"),
                new Recipe(4, "Pizza slavonska"),
                new Recipe(5, "Čaj"),
                new Recipe(6, "Palačinke")
        );

        List<Recipe> filteredRecipes = recipes.stream()
                .filter(r -> r.getName().length() > 4)
                .collect(Collectors.toList());
        System.out.println("Recepti koji imaju vise od 4 slova: " + filteredRecipes.size());

        List<String> recipeNames = recipes.stream()
                .map(Recipe::getName)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Sortirani nazivi: " + recipeNames);

        List<Recipe> recipesStartingWithP = recipes.stream()
                .filter(r -> r.getName().startsWith("P"))
                .collect(Collectors.toList());
        System.out.println("Recepti sa slovom P: " + recipesStartingWithP.size());

        List<String> firstThreeNames = recipes.stream()
                .limit(3)
                .map(Recipe::getName)
                .collect(Collectors.toList());
        System.out.println("Prva 3 recepta: " + firstThreeNames);

        return filteredRecipes;
    }
}