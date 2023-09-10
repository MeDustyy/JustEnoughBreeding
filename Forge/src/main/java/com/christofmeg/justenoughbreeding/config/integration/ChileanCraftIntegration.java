package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChileanCraftIntegration {

    final String MOD = "chileancraft"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();

    public ChileanCraftIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimal("codorniz", CommonStrings.SEMILLA);
        addTamableOnly("chinchilla", CommonStrings.CARROT);
        addTamableOnly("quiltro", CommonStrings.PINO);

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                .comment("Ingredients required for " + animal + " breeding")
                .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }
            builder.pop();
        }

        for (String tamable : tamableOnly) {
            if(tamingIngredients.get(tamable) != null && tamingChance.get(tamable) != null) {
                ForgeConfigSpec.ConfigValue<String> animalTamingIngredients = builder.push(tamable)
                        .comment("Ingredients required for " + tamable + " taming")
                        .define(tamable + "TamingIngredients", tamingIngredients.get(tamable));
                ForgeConfigSpec.ConfigValue<Integer> animalTamingChance = builder.defineInRange(tamable + "TamingChance", tamingChance.get(tamable), 0, 100);
                CommonConstants.tamingIngredientConfigs.put(MOD + "_" + tamable, animalTamingIngredients);
                CommonConstants.tamingChanceConfigs.put(MOD + "_" + tamable, animalTamingChance);
            }
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(tamable + "SpawnEgg", MOD + ":" + tamable + "_spawn_egg");
            CommonConstants.spawnEggConfigs.put(MOD + "_" + tamable, animalSpawnEgg);
            builder.pop();
        }
        builder.pop(2);
    }

    private void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    private void addTamableOnly(String name, String tamingIngredient) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, 33);
    }

}
