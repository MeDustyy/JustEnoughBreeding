package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarineIguanaIntegration {

    final String MOD = "marineiguana"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();

    public MarineIguanaIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addEggLayingAnimal("marineiguana", CommonStrings.SEAGRASS, "marineiguana:marineiguana_egg", 1, 4);

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<String> animalEggResult = builder
                        .comment("Egg that " + animal + " lays after breeding")
                        .define(animal + "eggResult", resultEggs.get(animal));
                ForgeConfigSpec.ConfigValue<Integer> animalMinEggAmount = builder
                        .comment("Min amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMinAmount", eggsAmountMin.get(animal), 1, 64);
                ForgeConfigSpec.ConfigValue<Integer> animalMaxEggAmount = builder
                        .comment("Max amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMaxAmount", eggsAmountMax.get(animal), 1, 64);
                CommonConstants.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
            }
            if(breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }
            builder.pop();
        }

        builder.pop(2);

    }
    private void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    private void addEggLayingAnimal(String name, String ingredient, String resultEgg, int eggAmountMin, int eggAmountMax) {
        addAnimal(name, ingredient);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, eggAmountMin);
        eggsAmountMax.put(name, eggAmountMax);
    }

}