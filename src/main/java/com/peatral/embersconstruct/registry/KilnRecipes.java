package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.item.ItemStamp;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import soot.Registry;
import teamroots.embers.RegistryManager;

import java.util.ArrayList;
import java.util.List;

public class KilnRecipes {
    private static final KilnRecipes SMELTING_BASE = new KilnRecipes();

    private final List<Recipe> recipeList = new ArrayList<>();

    public static KilnRecipes instance() {
        return SMELTING_BASE;
    }

    private KilnRecipes() {
    }

    public void addRecipe(Item input, ItemStack output) {
        addRecipe(new ItemStack(input), output);
    }

    public void addRecipe(ItemStack input, ItemStack output) {
        addRecipe(Ingredient.fromStacks(input), output);
    }

    public void addRecipe(Ingredient input, ItemStack output) {
        this.recipeList.add(new Recipe(input, output));
    }

    public ItemStack getResult(ItemStack input) {
        for (Recipe recipe : this.recipeList) {
            if (recipe.matches(input)) {
                return recipe.getOutput();
            }
        }

        return ItemStack.EMPTY;
    }

    public List<Recipe> getRecipeList() {
        return this.recipeList;
    }

    public class Recipe {

        private Ingredient input;
        private ItemStack output;

        public Recipe(Ingredient input, ItemStack output) {
            this.input = input;
            this.output = output;
        }

        public Ingredient getInput() {
            return input;
        }

        public ItemStack getOutput() {
            return output;
        }

        public boolean matches(ItemStack input) {
            return this.input.apply(input);
        }


    }

    public static void main() {
        for (Stamp stamp : RegistryStamps.registry.getValuesCollection()) {
            instance().addRecipe(new IngredientNBT(((ItemStamp) EmbersConstructItems.StampRaw).fromStamp(stamp)){}, ((ItemStamp) EmbersConstructItems.Stamp).fromStamp(stamp));
        }

        //Bar, flat, plate, gear
        instance().addRecipe(RegistryManager.stamp_bar_raw, new ItemStack((RegistryManager.stamp_bar)));
        instance().addRecipe(RegistryManager.stamp_flat_raw, new ItemStack((RegistryManager.stamp_flat)));
        instance().addRecipe(RegistryManager.stamp_plate_raw, new ItemStack((RegistryManager.stamp_plate)));
        instance().addRecipe(RegistryManager.stamp_gear_raw, new ItemStack((RegistryManager.stamp_gear)));
    }
}
