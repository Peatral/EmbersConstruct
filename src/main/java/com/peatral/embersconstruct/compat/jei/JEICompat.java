package com.peatral.embersconstruct.compat.jei;

import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.client.gui.GuiBloomery;
import com.peatral.embersconstruct.client.gui.GuiStampTable;
import com.peatral.embersconstruct.compat.jei.categories.BloomeryRecipeCategory;
import com.peatral.embersconstruct.compat.jei.categories.StampTableRecipeCategory;
import com.peatral.embersconstruct.compat.jei.wrapper.BloomeryRecipeWrapper;
import com.peatral.embersconstruct.compat.jei.wrapper.StampTableRecipeWrapper;
import com.peatral.embersconstruct.inventory.ContainerBloomery;
import com.peatral.embersconstruct.inventory.ContainerKiln;
import com.peatral.embersconstruct.client.gui.GuiKiln;
import com.peatral.embersconstruct.EmbersConstructBlocks;
import com.peatral.embersconstruct.compat.jei.categories.KilnRecipeCategory;
import com.peatral.embersconstruct.compat.jei.wrapper.KilnRecipeWrapper;
import com.peatral.embersconstruct.inventory.ContainerStampTable;
import com.peatral.embersconstruct.registry.BloomeryRecipes;
import com.peatral.embersconstruct.registry.KilnRecipes;
import com.peatral.embersconstruct.registry.StampTableRecipes;
import com.peatral.embersconstruct.util.IngredientNonMeta;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import java.util.Collections;
import java.util.IllegalFormatException;

@JEIPlugin
public class JEICompat implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(EmbersConstructItems.Stamp, EmbersConstructItems.StampRaw);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        final IGuiHelper gui = helpers.getGuiHelper();

        registry.addRecipeCategories(
                new KilnRecipeCategory(gui),
                new BloomeryRecipeCategory(gui)//,
                //new StampTableRecipeCategory(gui)
        );
    }

    @Override
    public void register(IModRegistry registry) {
        final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

        // Assign Recipe Handler
        registry.handleRecipes(KilnRecipes.Recipe.class, KilnRecipeWrapper::new, RecipeCategories.KILN);
        registry.handleRecipes(BloomeryRecipes.Recipe.class, BloomeryRecipeWrapper::new, RecipeCategories.BLOOMERY);
        //registry.handleRecipes(StampTableRecipes.Recipe.class, StampTableRecipeWrapper::new, RecipeCategories.STAMP_TABLE);

        // Add Recipes
        registry.addRecipes(KilnRecipes.instance().getRecipeList(), RecipeCategories.KILN);
        registry.addRecipes(BloomeryRecipes.instance().getRecipeList(), RecipeCategories.BLOOMERY);
        //registry.addRecipes(StampTableRecipes.instance().getRecipeList(), RecipeCategories.STAMP_TABLE);

        // Add Gui Click Area
        registry.addRecipeClickArea(GuiKiln.class, 78, 32, 28, 23, RecipeCategories.KILN, VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeClickArea(GuiBloomery.class, 111, 34, 11, 29, RecipeCategories.BLOOMERY, VanillaRecipeCategoryUid.FUEL);
        //registry.addRecipeClickArea(GuiStampTable.class, 71, 34, 24, 17, RecipeCategories.STAMP_TABLE);

        // Add Recipe Catalyst
        registry.addRecipeCatalyst(new ItemStack(EmbersConstructBlocks.Kiln), RecipeCategories.KILN, VanillaRecipeCategoryUid.FUEL);
        registry.addRecipeCatalyst(new ItemStack(EmbersConstructBlocks.Bloomery), RecipeCategories.BLOOMERY, VanillaRecipeCategoryUid.FUEL);
        //registry.addRecipeCatalyst(new ItemStack(EmbersConstructBlocks.StampTable), RecipeCategories.STAMP_TABLE);

        // Add Transfer Handler
        recipeTransfer.addRecipeTransferHandler(ContainerKiln.class, RecipeCategories.KILN, 0, 1, 3, 36);
        recipeTransfer.addRecipeTransferHandler(ContainerBloomery.class, RecipeCategories.BLOOMERY, 0, 2, 4, 36);
        //recipeTransfer.addRecipeTransferHandler(ContainerStampTable.class, RecipeCategories.STAMP_TABLE, 0, 1, 2, 36);
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
