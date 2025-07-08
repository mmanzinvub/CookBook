package hr.vub.cookbook;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratedData {

    public static List<Recipe> getGeneratedRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Recipe gulas = new Recipe(1, "Gulaš");
        gulas.getSteps().add(new Step("Dinstajte luk dok ne postane staklen"));
        gulas.getSteps().add(new Step("Kuhajte meso 30 minuta"));
        recipes.add(gulas);

        Recipe kokice = new Recipe(2, "Kokice");
        kokice.getSteps().add(new Step("Stavite paket kokica u mikrovalnu"));
        recipes.add(kokice);

        Recipe juha = new Recipe(3, "Juha");
        juha.getSteps().add(new Step("Kuhajte juhu 1 sat"));
        recipes.add(juha);

        Recipe pizza = new Recipe(4, "Pizza slavonska");
        pizza.getSteps().add(new Step("Pecite u pećnici 15 minuta"));
        recipes.add(pizza);

        Recipe caj = new Recipe(5, "Čaj");
        caj.getSteps().add(new Step("Zagrijte vodu"));
        caj.getSteps().add(new Step("Dodajte čaj"));
        recipes.add(caj);

        Recipe palacinke = new Recipe(6, "Palačinke");
        palacinke.getSteps().add(new Step("Pecite na tavi"));
        recipes.add(palacinke);

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

        List<Recipe> cookingRecipes = recipes.stream()
                .filter(r -> r.getSteps().stream()
                        .anyMatch(s -> s.getStepDescription().toLowerCase().contains("kuha")))
                .collect(Collectors.toList());
        System.out.println("Broj recepata koji zahtijevaju kuhanje: " + cookingRecipes.size());

        return filteredRecipes;
    }
}