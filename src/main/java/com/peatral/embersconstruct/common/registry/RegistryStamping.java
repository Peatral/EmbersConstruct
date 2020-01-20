package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.EnumStamps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import teamroots.embers.recipe.ItemStampingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.Collection;

public class RegistryStamping {
    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            if (material.isCastable()) {
                for (int i = 0; i < EnumStamps.values().length; i++) {
                    EnumStamps stamp = EnumStamps.values()[i];
                    Ingredient stampIng = Ingredient.fromStacks(new ItemStack(EmbersConstructItems.Stamp, 1, i));
                    if (stamp.getPart() instanceof ToolPart) {

                        ItemStack result = ((ToolPart) stamp.getPart()).getItemstackWithMaterial(material);
                        FluidStack fluid = new FluidStack(material.getFluid(), ((ToolPart) stamp.getPart()).getCost());

                        RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(Ingredient.EMPTY, fluid, stampIng, result));
                    }
                }
            }
        }
    }
}
