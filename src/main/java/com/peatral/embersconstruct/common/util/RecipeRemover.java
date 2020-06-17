package com.peatral.embersconstruct.common.util;

import com.google.common.collect.Lists;
import com.peatral.embersconstruct.common.EmbersConstructConfig;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.lang.reflect.Field;

public class RecipeRemover {

    public static void main() {
        removeTinkerRecipes();
    }

    public static void removeTinkerRecipes() {
        Class clazz = TinkerRegistry.class;
        EmbersConstructConfig.TCRecipeSettings tSettings = EmbersConstructConfig.tinkersConstructSettings;

        if (tSettings.removeMelting) clearStaticList(clazz, "meltingRegistry");
        if (tSettings.removeTableCasting) clearStaticList(clazz, "tableCastRegistry");
        if (tSettings.removeBasinCasting) clearStaticList(clazz, "basinCastRegistry");
        if (tSettings.removeAlloying) clearStaticList(clazz, "alloyRegistry");
    }

    public static void clearStaticList(Class clazz, String fieldname){
        setField(clazz, fieldname, null, Lists.newLinkedList());
    }

    public static void setField(Class clazz, String fieldname, Object obj, Object newVal) {
        try {
            Field field = clazz.getDeclaredField(fieldname);
            field.setAccessible(true);
            field.set(obj, newVal);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
