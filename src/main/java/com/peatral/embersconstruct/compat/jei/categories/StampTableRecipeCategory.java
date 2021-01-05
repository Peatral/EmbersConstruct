package com.peatral.embersconstruct.compat.jei.categories;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.client.gui.GuiStampTable;
import com.peatral.embersconstruct.compat.jei.JEICompat;
import com.peatral.embersconstruct.compat.jei.RecipeCategories;
import com.peatral.embersconstruct.compat.jei.wrapper.StampTableRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class StampTableRecipeCategory implements IRecipeCategory<StampTableRecipeWrapper> {
    private final IDrawable background;
    private final String name;

    protected static final ResourceLocation TEXTURES = GuiStampTable.BACKGROUND;
    protected static final ResourceLocation TEXTURES_2 = new ResourceLocation(EmbersConstruct.MODID, "textures/gui/gui.png");

    protected static final int input = 0;
    protected static final int output = 1;


    public StampTableRecipeCategory(IGuiHelper helper) {
        //background = helper.createDrawable(TEXTURES, 44, 16, 86, 54);
        //background = new AlphaDrawable(TEXTURES_2, 40, 6, 32, 32, 0, 18 * 4 + 5, 72, 72);

        background = helper.createBlankDrawable(163, 31 + 74 + 2*18);
        name = JEICompat.translateToLocal("gui.jei.category.stamptable");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.renderEngine.bindTexture(TEXTURES_2);
        GL11.glEnable(3042);
        Gui.drawModalRectWithCustomSizedTexture(0, 31-4, 0, 0, 163, 74, 256, 256);
        GL11.glDisable(3042);
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
        return RecipeCategories.STAMP_TABLE;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, StampTableRecipeWrapper recipeWrapper, IIngredients ingredients) {
        /*IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input, true, 3, 18);
        stacks.init(output, false, 61, 18);
        stacks.set(ingredients);*/
        recipeLayout.getIngredientsGroup(ItemStack.class).init(0, true, 81 - 9, 4);
        recipeLayout.getIngredientsGroup(ItemStack.class).set(0, ingredients.getInputs(ItemStack.class).get(0));

        int slot = 0;
        int row = 9;
        for (List<ItemStack> stacks : ingredients.getOutputs(ItemStack.class)) {
            recipeLayout.getItemStacks().init(slot + 1, true, (slot % row) * 18, (slot / row) * 18 + 32 - 4);
            recipeLayout.getItemStacks().set(slot + 1, stacks);
            slot++;
        }
    }

}
