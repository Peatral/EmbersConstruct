package com.peatral.embersconstruct.common.registry;

import com.peatral.embersconstruct.common.EmbersConstructItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.embers.RegistryManager;

@Mod.EventBusSubscriber
public class RegistryCrafting {
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        for (int i = 0; i < RegistryStamps.values().size(); i++) {
            ItemStack output = new ItemStack(EmbersConstructItems.StampRaw, 1, i);
            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.add(Ingredient.fromStacks(new ItemStack(RegistryManager.plate_caminite_raw)));
            ingredients.add(Ingredient.fromStacks(new ItemStack(RegistryStamps.values().get(i).getItem())));
            if (ingredients.size() >= 2)
                event.getRegistry().register(
                        new ShapelessRecipes("", output, ingredients).setRegistryName(RegistryStamps.values().get(i).getName())
                );
        }
    }
}
