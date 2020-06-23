package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstructConfig;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.util.IngredientNonMeta;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import teamroots.embers.RegistryManager;

import java.util.ArrayList;
import java.util.List;

public class StampTableRecipes {
    private static final StampTableRecipes CRAFTING_BASE = new StampTableRecipes();

    private final List<StampTableRecipes.Recipe> recipeList = new ArrayList<>();

    public static StampTableRecipes instance() {
        return CRAFTING_BASE;
    }

    private StampTableRecipes() {
    }

    public void addRecipe(Item input, ItemStack output, boolean requiresBlank) {
        addRecipe(new ItemStack(input), output, requiresBlank);
    }

    public void addRecipe(ItemStack input, ItemStack output, boolean requiresBlank) {
        addRecipe(Ingredient.fromStacks(input), output, requiresBlank);
    }

    public void addRecipe(Ingredient input, ItemStack output, boolean requiresBlank) {
        this.recipeList.add(new StampTableRecipes.Recipe(input, output, requiresBlank));
    }

    public ItemStack getResult(ItemStack input) {
        for (StampTableRecipes.Recipe recipe : this.recipeList) {
            if (recipe.matches(input)) {
                return recipe.getOutput();
            }
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getResultWithStamp(ItemStack input, Stamp stamp) {
        ItemStack stack = getResult(input).copy();
        if (stack != ItemStack.EMPTY) return Stamp.putStamp(stack, stamp);
        return stack;
    }

    public List<StampTableRecipes.Recipe> getRecipeList() {
        return this.recipeList;
    }

    public class Recipe {

        private Ingredient input;
        private ItemStack output;
        private boolean requiresBlank;

        public Recipe(Ingredient input, ItemStack output, boolean requiresBlank) {
            this.input = input;
            this.output = output;
            this.requiresBlank = requiresBlank;
        }

        public Ingredient getInput() {
            return input;
        }

        public ItemStack getOutput() {
            return output;
        }

        public boolean matches(ItemStack input) {
            if (!requiresBlank) return this.input.apply(input);
            else return this.input.apply(input) && Stamp.getStampFromStack(input) == null;
        }
    }

    public static void main() {
        boolean requiresBlank = EmbersConstructConfig.embersConstructSettings.stampTableNeedBlank;
        instance().addRecipe(new IngredientNonMeta(new ItemStack(EmbersConstructItems.StampRaw, OreDictionary.WILDCARD_VALUE)), new ItemStack(EmbersConstructItems.StampRaw), requiresBlank);
        instance().addRecipe(new IngredientNonMeta(new ItemStack(EmbersConstructItems.Stamp, OreDictionary.WILDCARD_VALUE)), new ItemStack(EmbersConstructItems.Stamp), requiresBlank);
        instance().addRecipe(RegistryManager.stamp_flat_raw, new ItemStack(EmbersConstructItems.StampRaw), requiresBlank);
    }
}
