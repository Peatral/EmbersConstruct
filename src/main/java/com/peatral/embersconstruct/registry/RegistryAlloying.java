package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import teamroots.embers.recipe.FluidMixingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.ArrayList;
import java.util.List;

public class RegistryAlloying {
    public static void main() {
        registerTinkerRecipes();
    }

    private static List<AlloyRecipe> alloyRegistry;

    public static void registerTinkerRecipes() {

        registerAlloys();

        int c = 0;
        for (AlloyRecipe rec : alloyRegistry) {
            boolean flag = true;
            for (FluidStack s : rec.getFluids()) {
                if (s.amount > 16) flag = false;
            }
            if (flag) {
                RecipeRegistry.mixingRecipes.add(new FluidMixingRecipe(rec.getFluids().toArray(new FluidStack[]{}), rec.getResult()));
                c++;
            }
        }
        EmbersConstruct.logger.info("Registered " + c + " alloying recipes from Tinkers'.");
    }

    private static void registerAlloys() {
        alloyRegistry = TinkerRegistry.getAlloys();
        if (alloyRegistry.size() > 0) return;


        //If smeltery is disabled, only way to add alloys back in is the manual one
        alloyRegistry = new ArrayList<>();


        // 1 bucket lava + 1 bucket water = 2 ingots = 1 block obsidian
        // 1000 + 1000 = 288
        // 125 + 125 = 36
        if(Config.obsidianAlloy) {
            if (FluidRegistry.isFluidRegistered("obsidian")) {
                registerAlloy(FluidRegistry.getFluidStack("obsidian", 36),
                        new FluidStack(FluidRegistry.WATER, 125),
                        new FluidStack(FluidRegistry.LAVA, 125));
            }
        }

        // 1 bucket water + 4 seared ingot + 4 mud bricks = 1 block hardened clay
        // 1000 + 288 + 576 = 576
        // 250 + 72 + 144 = 144
        if (
                FluidRegistry.isFluidRegistered("clay") &&
                FluidRegistry.isFluidRegistered("searedStone") &&
                FluidRegistry.isFluidRegistered("dirt")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("clay", 144),
                    new FluidStack(FluidRegistry.WATER, 250),
                    FluidRegistry.getFluidStack("searedStone", 72),
                    FluidRegistry.getFluidStack("dirt", 144));
        }

        // 1 iron ingot + 1 purple slime ball + seared stone in molten form = 1 knightslime ingot
        // 144 + 250 + 288 = 144
        if (
                FluidRegistry.isFluidRegistered("knightslime") &&
                FluidRegistry.isFluidRegistered("iron") &&
                FluidRegistry.isFluidRegistered("purpleSlime") &&
                FluidRegistry.isFluidRegistered("searedStone")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("knightslime", 72),
                    FluidRegistry.getFluidStack("iron", 72),
                    FluidRegistry.getFluidStack("purpleSlime", 125),
                    FluidRegistry.getFluidStack("searedStone", 144));
        }

        // i iron ingot + 1 blood... unit thingie + 1/3 gem = 1 pigiron
        // 144 + 99 + 222 = 144
        if (
                FluidRegistry.isFluidRegistered("pigIron") &&
                FluidRegistry.isFluidRegistered("iron") &&
                FluidRegistry.isFluidRegistered("blood") &&
                FluidRegistry.isFluidRegistered("clay")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("pigIron", 144),
                    FluidRegistry.getFluidStack("iron", 144),
                    FluidRegistry.getFluidStack("blood", 40),
                    FluidRegistry.getFluidStack("clay", 72));
        }

        // 1 ingot cobalt + 1 ingot ardite = 1 ingot manyullyn!
        // 144 + 144 = 144
        if (
                FluidRegistry.isFluidRegistered("manyullyn") &&
                FluidRegistry.isFluidRegistered("cobalt") &&
                FluidRegistry.isFluidRegistered("ardite")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("manyullyn", 2),
                    FluidRegistry.getFluidStack("cobalt", 2),
                    FluidRegistry.getFluidStack("ardite", 2));
        }

        // 3 ingots copper + 1 ingot tin = 4 ingots bronze
        if (
                FluidRegistry.isFluidRegistered("bronze") &&
                FluidRegistry.isFluidRegistered("copper") &&
                FluidRegistry.isFluidRegistered("tin")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("bronze", 4),
                    FluidRegistry.getFluidStack("copper", 3),
                    FluidRegistry.getFluidStack("tin", 1));
        }

        // 1 ingot gold + 1 ingot silver = 2 ingots electrum
        if (
                FluidRegistry.isFluidRegistered("electrum") &&
                FluidRegistry.isFluidRegistered("gold") &&
                FluidRegistry.isFluidRegistered("silver")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("electrum", 2),
                    FluidRegistry.getFluidStack("gold", 1),
                    FluidRegistry.getFluidStack("silver", 1));
        }

        // 1 ingot copper + 3 ingots aluminium = 4 ingots alubrass
        if (
                FluidRegistry.isFluidRegistered("alubrass") &&
                        FluidRegistry.isFluidRegistered("copper") &&
                        FluidRegistry.isFluidRegistered("aluminum")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("alubrass", 4),
                    FluidRegistry.getFluidStack("copper", 1),
                    FluidRegistry.getFluidStack("aluminum", 3));
        }

        // 2 ingots copper + 1 ingot zinc = 3 ingots brass
        if (
                FluidRegistry.isFluidRegistered("brass") &&
                        FluidRegistry.isFluidRegistered("copper") &&
                        FluidRegistry.isFluidRegistered("zinc")
        ) {
            registerAlloy(FluidRegistry.getFluidStack("brass", 3),
                    FluidRegistry.getFluidStack("copper", 2),
                    FluidRegistry.getFluidStack("zinc", 1));
        }
    }

    private static void registerAlloy(FluidStack result, FluidStack... inputs) {
        if(result.amount < 1) {
            EmbersConstruct.logger.error("Alloy Recipe: Resulting alloy %s has to have an amount (%d)", result.getLocalizedName(), result.amount);
            return;
        }
        if(inputs.length < 2) {
            EmbersConstruct.logger.error("Alloy Recipe: Alloy for %s must consist of at least 2 liquids", result.getLocalizedName());
            return;
        }

        registerAlloy(new AlloyRecipe(result, inputs));
    }

    private static void registerAlloy(AlloyRecipe recipe) {
        alloyRegistry.add(recipe);
    }
}
