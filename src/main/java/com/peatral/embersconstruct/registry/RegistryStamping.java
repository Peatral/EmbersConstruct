package com.peatral.embersconstruct.registry;

import com.peatral.embersconstruct.EmbersConstruct;
import com.peatral.embersconstruct.EmbersConstructConfig;
import com.peatral.embersconstruct.EmbersConstructItems;
import com.peatral.embersconstruct.item.ItemStamp;
import com.peatral.embersconstruct.util.OreDictValues;
import com.peatral.embersconstruct.util.Stamp;
import com.peatral.embersconstruct.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.smeltery.ICast;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ranged.item.BoltCore;
import teamroots.embers.RegistryManager;
import teamroots.embers.recipe.ItemStampingRecipe;
import teamroots.embers.recipe.RecipeRegistry;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * This is where the recipe registration for the stamper happens.
 */
public class RegistryStamping {

    /** Tracks how many recipes were registered **/
    private static int c = 0;

    /**
     * The main "entry point" for stamper recipe registration
     */
    public static void main() {
        registerOreDictRecipes();
        registerTinkerRecipes();
        EmbersConstruct.logger.info("Registered " + c + " stamping recipes.");
    }

    /**
     * Registers recipes using the ore dictionary
     */
    public static void registerOreDictRecipes() {
        registerFromOreDict(OreDictValues.INGOT.getName(), new ItemStack(RegistryManager.stamp_bar), OreDictValues.INGOT.getValue());
        registerFromOreDict(OreDictValues.GEAR.getName(), new ItemStack(RegistryManager.stamp_gear), OreDictValues.GEAR.getValue());
        registerFromOreDict(OreDictValues.PLATE.getName(), new ItemStack(RegistryManager.stamp_plate), OreDictValues.PLATE.getValue());
        if (EmbersConstructConfig.embersConstructSettings.dustStamping) registerItemFromOreDict(OreDictValues.DUST.getName(), OreDictValues.INGOT.getName(), new ItemStack(RegistryManager.stamp_flat), 1);
    }

    /**
     * Registers recipes by copying the ones from tinkers
     */
    public static void registerTinkerRecipes() {
        Collection<Material> materials = TinkerRegistry.getAllMaterials();
        for (Stamp stamp : RegistryStamps.registry.getValuesCollection()) {
            // if ore dict recipe
            if (stamp.usesOreDictKey()) {
                Arrays.stream(OreDictionary.getOreNames())
                        .filter(oreDictName -> oreDictName.startsWith(stamp.getOreDictKey()))
                        .flatMap(name -> OreDictionary.getOres(name).stream())
                        .flatMap(stack -> FluidRegistry.getRegisteredFluids().keySet().stream().filter(fluidName -> Arrays.stream(OreDictionary.getOreIDs(stack))
                                .anyMatch(oreId -> OreDictionary.getOreName(oreId)
                                        .replace(stamp.getOreDictKey(), "")
                                        .toLowerCase()
                                        .equals(Util.getOreDictFromFluid(fluidName))))
                                .map(name -> new AbstractMap.SimpleEntry<>(name, stack)))
                        .forEach(map -> registerMeta(map.getValue(), FluidRegistry.getFluidStack(map.getKey(), stamp.getCost()), stamp));
            // generic non-oredict recipe
            } else {
                for (Material material : materials) {
                    Fluid fluid = Util.getFluidFromMaterial(material);
                    if (fluid != null) {
                        ItemStack result = null;
                        // set material of toolpart if possible
                        if (stamp.getItem() instanceof IToolPart) {
                            IToolPart part = (IToolPart) stamp.getItem();
                            if (part.canUseMaterial(material)) result = part.getItemstackWithMaterial(material);
                        } else {
                            result = new ItemStack(stamp.getItem());
                        }
                        if (result != null) {
                            registerMeta(result, FluidRegistry.getFluidStack(fluid.getName(), stamp.getCost()), stamp);
                        }
                    }
                }
            }
        }

        // Registering boltcore recipes
        for (Material shaftMat : materials) {
            // is this a valid material for bolts/arrows?
            if (TinkerTools.boltCore.canUseMaterial(shaftMat) && TinkerTools.arrowShaft.canUseMaterial(shaftMat)) {
                for (Material headMat : materials) {
                    Fluid fluid = Util.getFluidFromMaterial(headMat);
                    // has this material be used for the tip?
                    if (fluid != null && TinkerTools.boltCore.canUseMaterial(headMat)) {
                        ItemStack inp = TinkerTools.arrowShaft.getItemstackWithMaterial(shaftMat);
                        ItemStack res = BoltCore.getItemstackWithMaterials(shaftMat, headMat);
                        register(new IngredientNBT(inp){}, Ingredient.fromItems(RegistryManager.stamp_flat), res, new FluidStack(fluid, OreDictValues.INGOT.getValue() * 2));
                    }
                }
            }
        }

        for (ICastingRecipe iCastingRecipe : TinkerRegistry.getAllTableCastingRecipes()) {
            // only if "normal" recipe
            if (iCastingRecipe instanceof CastingRecipe) {
                CastingRecipe recipe = (CastingRecipe) iCastingRecipe;

                Ingredient stamp = Ingredient.fromItems(RegistryManager.stamp_flat);
                ItemStack result = recipe.getResult();
                FluidStack fluid = new FluidStack(recipe.getFluid(), recipe.getFluidAmount());

                // No cast here
                if (recipe.cast == null) {
                    register(Ingredient.EMPTY, stamp, result, fluid);
                    continue;
                }

                // No casts allowed here
                if (recipe.cast.getInputs().stream().anyMatch(stack -> stack.getItem() instanceof ICast) || recipe.getResult().getItem() instanceof ICast)
                    continue;

                // Don't consume cast (hopefully)
                if (!recipe.consumesCast()) {
                    register(new ItemStampingRecipe(Ingredient.fromStacks(recipe.cast.getInputs().toArray(new ItemStack[0])), fluid, stamp, result) {
                        @Override
                        public int getInputConsumed() {
                            return 0;
                        }
                    });
                    continue;
                }

                // ok normal recipe
                register(Ingredient.fromStacks(recipe.cast.getInputs().toArray(new ItemStack[0])), stamp, result, fluid);
            }
        }
    }

    /**
     * Registers all recipes using the ore dictionary.
     *
     * @param key the fluid name of the ore
     * @param stamp the stamp to use
     * @param cost the fluid cost
     */
    public static void registerFromOreDict(String key, ItemStack stamp, int cost) {
        Map<String, Fluid> fluids = FluidRegistry.getRegisteredFluids();
        for (String fluidName : fluids.keySet()) {
            String oreName = Util.getOreDictFromFluid(fluidName);
            if (oreName.length() > 0) {
                for (ItemStack result : OreDictionary.getOres(key + Character.toString(oreName.charAt(0)).toUpperCase() + oreName.substring(1))) {
                    register(stamp, result, new FluidStack(fluids.get(fluidName), cost));
                }
            }
        }
    }

    public static void registerItemFromOreDict(String outputkey, String inputkey, ItemStack stamp, int amount) {
        for (String oreName : OreDictionary.getOreNames()) {
            if (oreName.startsWith(outputkey)) {
                String ore = oreName.replace(outputkey, "").toLowerCase();
                if (ore.length() > 0) {
                    for (ItemStack input : OreDictionary.getOres(inputkey + Character.toString(ore.charAt(0)).toUpperCase() + ore.substring(1))) {
                        if (OreDictionary.getOres(oreName).size() > 0) {
                            ItemStack result = OreDictionary.getOres(oreName).get(0);
                            result.setCount(amount);
                            register(Ingredient.fromStacks(input), new IngredientNBT(stamp) {
                            }, result, null);
                        }
                    }
                }
            }
        }
    }

    public static void registerMeta(ItemStack result, FluidStack fluid, Stamp stamp) {
        register(((ItemStamp) EmbersConstructItems.Stamp).fromStamp(stamp), result, fluid);
    }

    public static void register(ItemStack stamp, ItemStack result, FluidStack fluid) {
        register(Ingredient.EMPTY, new IngredientNBT(stamp){}, result, fluid);
    }

    /**
     * Registers a recipe using an input ingredient, a stamp, a resulting itemstack and a fluid stack.
     *
     * @param input the input
     * @param stamp the stamp
     * @param result the resulting stack
     * @param fluid the fluidstack
     */
    public static void register(Ingredient input, Ingredient stamp, ItemStack result, FluidStack fluid) {
        register(new ItemStampingRecipe(input, fluid, stamp, result));
    }

    /**
     * Registers an ItemStampingRecipe but checks beforehand if it overlaps with something else.
     *
     * @param recipe the recipe
     */
    public static void register(ItemStampingRecipe recipe) {
        if (RecipeRegistry.stampingRecipes.stream().anyMatch(test -> isRecipeTechnicallySame(test, recipe)))
            return;

        RecipeRegistry.stampingRecipes.add(recipe);
        c++;
    }

    /**
     * Checks if too recipes are technically the same. Includes item & stacksize for output, matching inputs and fluid type.
     * Doesn't check fluid amount so that certain double recipes don't get added.
     * (if output is the same and inputs are the same, there is no need to add a recipe only because the fluid amount is different)
     *
     * Alternatively if the result is different but the inputs match 100% they are also the same (we don't want any overlaps)
     *
     * @param a the first recipe
     * @param b the second recipe
     * @return true if they are technically the same
     */
    public static boolean isRecipeTechnicallySame(@NotNull ItemStampingRecipe a, @NotNull ItemStampingRecipe b) {
        return recipeSameOutput(a, b) && recipeSameInputs(a, b, false)
                || !recipeSameOutput(a, b) && recipeSameInputs(a, b, true);
    }

    public static boolean recipeSameOutput(@NotNull ItemStampingRecipe a, @NotNull ItemStampingRecipe b) {
        return a.result.isItemEqual(b.result) && a.result.getCount() == b.result.getCount();
    }

    public static boolean recipeSameInputs(@NotNull ItemStampingRecipe a, @NotNull ItemStampingRecipe b, boolean checkFluidAmount) {
        return a.input.getValidItemStacksPacked().equals(b.input.getValidItemStacksPacked()) &&
                (a.fluid == null && b.fluid == null
                        || a.fluid.getFluid().getName().equals(b.fluid.getFluid().getName()) &&
                        (a.fluid.amount == b.fluid.amount || !checkFluidAmount));
    }
}
