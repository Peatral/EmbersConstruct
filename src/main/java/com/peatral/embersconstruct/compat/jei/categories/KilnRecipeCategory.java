package com.peatral.embersconstruct.compat.jei.categories;

import com.peatral.embersconstruct.client.gui.GuiKiln;
import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.compat.jei.JEICompat;
import com.peatral.embersconstruct.compat.jei.RecipeCategories;
import com.peatral.embersconstruct.compat.jei.wrapper.KilnRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class KilnRecipeCategory implements IRecipeCategory<KilnRecipeWrapper> {
    private final IDrawable background;
    private final String name;

    protected static final ResourceLocation TEXTURES = GuiKiln.KILN_GUI_TEXTURES;

    protected static final int input = 0;
    protected static final int fuel = 1;
    protected static final int output = 2;

    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    protected final IDrawableAnimated animatedArrow;

    public KilnRecipeCategory(IGuiHelper helper) {
        staticFlame = helper.createDrawable(TEXTURES, 176, 0, 14, 14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic staticArrow = helper.createDrawable(TEXTURES, 176, 14, 24, 17);
        animatedArrow = helper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);

        background = helper.createDrawable(TEXTURES, 55, 16, 82, 54);
        name = JEICompat.translateToLocal("gui.jei.category.kiln");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        animatedFlame.draw(minecraft, 1, 20);
        animatedArrow.draw(minecraft, 24, 18);
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
        return RecipeCategories.KILN;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, KilnRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input, true, 0, 0);
        stacks.init(output, false, 60, 18);
        stacks.set(ingredients);
    }
}
