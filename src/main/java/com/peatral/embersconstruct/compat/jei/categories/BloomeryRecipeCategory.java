package com.peatral.embersconstruct.compat.jei.categories;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.client.gui.GuiBloomery;
import com.peatral.embersconstruct.compat.jei.JEICompat;
import com.peatral.embersconstruct.compat.jei.RecipeCategories;
import com.peatral.embersconstruct.compat.jei.wrapper.BloomeryRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class BloomeryRecipeCategory implements IRecipeCategory<BloomeryRecipeWrapper> {
    private final IDrawable background;
    private final String name;

    protected static final ResourceLocation TEXTURES = GuiBloomery.BLOOMERY_GUI_TEXTURES;

    protected static final int inputA = 0;
    protected static final int inputB = 1;
    protected static final int fuel = 2;
    protected static final int output = 3;

    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    protected final IDrawableAnimated animatedArrow;


    public BloomeryRecipeCategory(IGuiHelper helper) {
        staticFlame = helper.createDrawable(TEXTURES, 176, 0, 14, 14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic staticArrow = helper.createDrawable(TEXTURES, 176, 14, 8, 22);
        animatedArrow = helper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.TOP, false);

        background = helper.createDrawable(TEXTURES, 47, 16, 76, 54);
        name = JEICompat.translateToLocal("gui.jei.category.bloomery");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        animatedFlame.draw(minecraft, 5, 10);
        animatedArrow.draw(minecraft, 66, 22);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getModName() {
        return EmbersConstruct.MOD_NAME;
    }

    @Override
    public String getUid() {
        return RecipeCategories.BLOOMERY;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BloomeryRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(inputA, true, 31, 0);
        stacks.init(inputB, true, 53, 0);
        stacks.init(output, false, 42, 32);
        stacks.set(ingredients);
    }
}
