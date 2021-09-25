package com.peatral.embersconstruct.client.render;

import com.peatral.embersconstruct.item.IMetaItem;
import com.peatral.embersconstruct.item.ITagItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EmbersConstructRenderer {
    public static void registerItemRender(String domain, Item item) {
        if (item instanceof IMetaItem) {
            IMetaItem metaItem = (IMetaItem) item;
            for (int i = 0; i < metaItem.getVariants(); i++) {
                if (metaItem.getTexture(i) == null) {
                    continue;
                }

                ModelResourceLocation loc = new ModelResourceLocation(new ResourceLocation(domain, metaItem.getTexture(i)), "inventory");
                ModelLoader.setCustomModelResourceLocation(item, i, loc);
                ModelBakery.registerItemVariants(item, new ResourceLocation(domain, metaItem.getTexture(i)));
            }

            return;
        } else if (item instanceof ITagItem) {
            ITagItem tagItem = (ITagItem) item;

            ModelLoader.setCustomMeshDefinition(item, stack -> new ModelResourceLocation(new ResourceLocation(domain, tagItem.getTexture(stack)), "inventory"));

            for (ItemStack stack : tagItem.getVariants()) {
                if (tagItem.getTexture(stack) == null) {
                    continue;
                }
                ModelBakery.registerItemVariants(item, new ModelResourceLocation(new ResourceLocation(domain, tagItem.getTexture(stack)), "inventory"));
            }
            ModelBakery.registerItemVariants(item, new ModelResourceLocation(new ResourceLocation(domain, tagItem.getTexture(new ItemStack(item))), "inventory"));

            return;
        } else if (item instanceof ItemBlock) {

        }
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
