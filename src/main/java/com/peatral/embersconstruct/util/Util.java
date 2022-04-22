package com.peatral.embersconstruct.util;

import com.peatral.embersconstruct.EmbersConstructMaterials;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;

public class Util {
    public static Fluid getFluidFromString(String s) {
        if (FluidRegistry.isFluidRegistered(s)) return FluidRegistry.getFluid(s);
        return null;
    }

    public static Fluid getFluidFromMaterial(Material material) {
        String identifier = material.identifier;
        if (identifier.contains(".")) {
            identifier = identifier.split("\\.")[1];
        }
        Fluid fluid;
        String[] names = new String[]{
                identifier,
                identifier + "fluid",
                identifier + "_fluid",
                "fluid" + identifier,
                "fluid_" + identifier,
                identifier + "liquid",
                identifier + "_liquid",
                "liquid" + identifier,
                "liquid_" + identifier
        };
        for (String s : names) {
            fluid = getFluidFromString(s);
            if (fluid != null) return fluid;
        }
        if (material == EmbersConstructMaterials.wroughtiron)
            return TinkerFluids.iron;
        return null;
    }

    public static String getOreDictFromFluid(String name) {
        return name.toLowerCase()
                .replace("_fluid", "")
                .replace("fluid_", "")
                .replace("fluid","")
                .replace("_liquid", "")
                .replace("liquid_", "")
                .replace("liquid","");
    }
}
