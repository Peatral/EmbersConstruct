package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstructItems;
import com.peatral.embersconstruct.common.item.ItemStamp;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.embers.RegistryManager;

@Mod.EventBusSubscriber
public class RegistryCrafting {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (Stamp stamp : RegistryStamps.registry.getValuesCollection()) {
            ItemStack output = ((ItemStamp)EmbersConstructItems.StampRaw).fromStamp(stamp);
            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.add(Ingredient.fromStacks(new ItemStack(RegistryManager.plate_caminite_raw)));
            ingredients.add(new IngredientNBT(new ItemStack(stamp.getItem())){});
            if (ingredients.size() >= 2)
                event.getRegistry().register(
                        new ShapelessRecipes("", output, ingredients).setRegistryName(stamp.getName())
                );
        }
    }
}
