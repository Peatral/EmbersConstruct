package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import teamroots.embers.recipe.ItemStampingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.Collection;

public class RegistryStamping {

    private static int c = 0;

    public static void main() {
        registerTinkerRecipes();
    }

    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (int i = 0; i < RegistryStamps.values().size(); i++) {
            Stamp stamp = RegistryStamps.values().get(i);
            if (stamp.usesCustomFluid()) {
                registerBasic(new ItemStack(stamp.getItem()), new FluidStack(stamp.getFluid(), stamp.getCost()), i);
            } else {
                for (Material material : materials) {
                    if (FluidRegistry.isFluidRegistered(material.identifier)) {
                        ItemStack result = null;
                        if (stamp.getItem() instanceof IToolPart) {
                            IToolPart part = (IToolPart) stamp.getItem();
                            if (part.canUseMaterial(material)) result = part.getItemstackWithMaterial(material);
                        } else {
                            result = new ItemStack(stamp.getItem());
                        }
                        if (result != null) {
                            registerBasic(result, FluidRegistry.getFluidStack(material.identifier, stamp.getCost()), i);
                        }
                    }
                }
            }
        }
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes for Tinkers'.");
    }

    public static void registerBasic(ItemStack result, FluidStack fluid, int meta) {
        Ingredient stampIng = Ingredient.fromStacks(new ItemStack(EmbersConstructItems.Stamp, 1, meta));
        RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(Ingredient.EMPTY, fluid, stampIng, result));
        c++;
    }
}
