package com.peatral.embersconstruct.compat.jei;

import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.client.gui.GuiBloomery;
import com.peatral.embersconstruct.compat.jei.categories.BloomeryRecipeCategory;
import com.peatral.embersconstruct.compat.jei.wrapper.BloomeryRecipeWrapper;
import com.peatral.embersconstruct.inventory.ContainerBloomery;
import com.peatral.embersconstruct.inventory.ContainerKiln;
import com.peatral.embersconstruct.client.gui.GuiKiln;
import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.compat.jei.categories.KilnRecipeCategory;
import com.peatral.embersconstruct.compat.jei.wrapper.KilnRecipeWrapper;
import com.peatral.embersconstruct.registry.BloomeryRecipes;
import com.peatral.embersconstruct.registry.KilnRecipes;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import java.util.IllegalFormatException;

@JEIPlugin
public class JEICompat implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        StampSubtypeInterpreter stampSubtypeInterpreter = new StampSubtypeInterpreter();
        subtypeRegistry.registerSubtypeInterpreter(EmbersConstructItems.Stamp, stampSubtypeInterpreter);
        subtypeRegistry.registerSubtypeInterpreter(EmbersConstructItems.StampRaw, stampSubtypeInterpreter);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        final IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(new KilnRecipeCategory(gui));
        registry.addRecipeCategories(new BloomeryRecipeCategory(gui));
    }

    @Override
    public void register(IModRegistry registry) {
        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        // Assign Recipe Handler
        registry.handleRecipes(KilnRecipes.Recipe.class, KilnRecipeWrapper::new, RecipeCategories.KILN);
        registry.handleRecipes(BloomeryRecipes.Recipe.class, BloomeryRecipeWrapper::new, RecipeCategories.BLOOMERY);

        // Add Recipes
        registry.addRecipes(KilnRecipes.instance().getRecipeList(), RecipeCategories.KILN);
        registry.addRecipes(BloomeryRecipes.instance().getRecipeList(), RecipeCategories.BLOOMERY);

        // Add Gui Click Area
        registry.addRecipeClickArea(GuiKiln.class, 78, 32, 28, 23, RecipeCategories.KILN, VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeClickArea(GuiBloomery.class, 111, 34, 11, 29, RecipeCategories.BLOOMERY, VanillaRecipeCategoryUid.FUEL);

        // Add Recipe Catalyst
        registry.addRecipeCatalyst(new ItemStack(EmbersConstructBlocks.Kiln), RecipeCategories.KILN, VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeCatalyst(new ItemStack(EmbersConstructBlocks.Bloomery), RecipeCategories.BLOOMERY, VanillaRecipeCategoryUid.FUEL);

        // Add Transfer Handler
        recipeTransfer.addRecipeTransferHandler(ContainerKiln.class, RecipeCategories.KILN, 0, 1, 3, 36);
        recipeTransfer.addRecipeTransferHandler(ContainerBloomery.class, RecipeCategories.BLOOMERY, 0, 2, 0, 36);

    }

    public static String translateToLocal(String key) {
        if (I18n.canTranslate(key)) return I18n.translateToLocal(key);
        return I18n.translateToFallback(key);
    }

    public static String translateToLocalFormatted(String key, Object... format) {
        String s = translateToLocal(key);
        try {
            return String.format(s, format);
        } catch (IllegalFormatException e) {
            return "Format error: " + s;
        }
    }

}
