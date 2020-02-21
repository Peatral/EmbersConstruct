package com.peatral.embersconstruct.common.registry;

import c4.conarm.lib.armor.ArmorPart;
import com.peatral.embersconstruct.common.EmbersConstruct;
import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.integration.conarm.lib.EnumStampsConarm;
import com.peatral.embersconstruct.common.lib.EnumStamps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import teamroots.embers.recipe.ItemStampingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.Collection;

public class RegistryStamping {

    public static void main() {
        registerTinkerRecipes();
    }

    public static void registerTinkerRecipes() {
        int c = 0;
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            if (FluidRegistry.isFluidRegistered(material.identifier)) {
                for (int i = 0; i < EnumStamps.values().length; i++) {
                    EnumStamps stamp = EnumStamps.values()[i];
                    Ingredient stampIng = Ingredient.fromStacks(new ItemStack(EmbersConstructItems.Stamp, 1, i));
                    if (stamp.getPart() instanceof ToolPart) {

                        ItemStack result = ((ToolPart) stamp.getPart()).getItemstackWithMaterial(material);
                        FluidStack fluid = FluidRegistry.getFluidStack(material.identifier, ((ToolPart) stamp.getPart()).getCost());

                        RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(Ingredient.EMPTY, fluid, stampIng, result));
                        c++;
                    }
                }
            }
        }
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes for Tinkers'.");
    }

    @Optional.Method(modid="conarm")
    public static void registerConarmRecipes() {
        int c = 0;
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Material material : materials) {
            if (material.isCastable()) {
                for (int i = 0; i < EnumStampsConarm.values().length; i++) {
                    EnumStampsConarm stamp = EnumStampsConarm.values()[i];
                    Ingredient stampIng = Ingredient.fromStacks(new ItemStack(EmbersConstructItems.StampConarm, 1, i));
                    if (stamp.getPart() instanceof ArmorPart) {

                        ItemStack result = ((ArmorPart) stamp.getPart()).getItemstackWithMaterial(material);
                        FluidStack fluid = new FluidStack(material.getFluid(), ((ArmorPart) stamp.getPart()).getCost());

                        RecipeRegistry.stampingRecipes.add(new ItemStampingRecipe(Ingredient.EMPTY, fluid, stampIng, result));
                        c++;
                    }
                }
            }
        }
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes for Construct's Armory.");
    }
}
