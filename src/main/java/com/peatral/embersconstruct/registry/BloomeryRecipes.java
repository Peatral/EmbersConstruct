package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstructItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;
import java.util.List;

public class BloomeryRecipes {
    private static final BloomeryRecipes SMELTING_BASE = new BloomeryRecipes();

    private final List<Recipe> recipeList = new ArrayList<>();

    public static BloomeryRecipes instance() {
        return SMELTING_BASE;
    }

    private BloomeryRecipes() {
    }

    public List<Recipe> getRecipeList() {
        return this.recipeList;
    }

    public void addRecipe(Ingredient a, Ingredient b, ItemStack output) {
        addRecipe(new Recipe(a, b, output));
    }

    public void addRecipe(Recipe recipe) {
        recipeList.add(recipe);
    }

    public class Recipe {

        private Ingredient inputA;
        private Ingredient inputB;
        private ItemStack output;

        public Recipe(Ingredient inputA, Ingredient inputB, ItemStack output) {
            this.inputA = inputA;
            this.inputB = inputB;
            this.output = output;
        }

        public Ingredient getInputA() {
            return inputA;
        }
        public Ingredient getInputB() {
            return inputB;
        }

        public ItemStack getOutput() {
            return output;
        }

        public boolean matches(ItemStack input) {
            return this.inputA.apply(input);
        }
    }

    public static void main() {
        instance().addRecipe(
                OreIngredient.fromStacks(new ItemStack(Items.COAL, 1, 1)),
                OreIngredient.fromStacks(new ItemStack(Item.getItemFromBlock(Blocks.IRON_ORE))),
                new ItemStack(EmbersConstructItems.WroughtIronIngot));
    }
}
