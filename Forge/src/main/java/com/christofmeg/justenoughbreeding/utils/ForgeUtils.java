package com.christofmeg.justenoughbreeding.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeUtils {

    public static String getEdibleMeatItemNames(boolean includRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item != null) {
                FoodProperties foodProperties = item.getFoodProperties();
                if(includRottenFlesh) {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat()) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
                else {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat() && item != Items.ROTTEN_FLESH) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
            }
        }

        return String.join(", ", edibleMeatItemNames);
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

}
