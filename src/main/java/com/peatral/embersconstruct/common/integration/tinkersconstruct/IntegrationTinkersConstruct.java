package com.peatral.embersconstruct.common.integration.tinkersconstruct;

import com.peatral.embersconstruct.common.integration.Integration;
import com.peatral.embersconstruct.common.util.Stamp;
import net.minecraft.init.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class IntegrationTinkersConstruct extends Integration {

    public static List<Stamp> stamps = new ArrayList<>();

    static {
        stamps.add(initStamp("arrow_head", TinkerTools.arrowHead));
        stamps.add(initStamp("arrow_shaft", TinkerTools.arrowShaft));
        stamps.add(initStamp("axe_head", TinkerTools.axeHead));
        stamps.add(initStamp("binding", TinkerTools.binding));
        stamps.add(initStamp("bow_limb", TinkerTools.bowLimb));
        stamps.add(initStamp("bow_string", TinkerTools.bowString));
        stamps.add(initStamp("broad_axe_head", TinkerTools.broadAxeHead));
        stamps.add(initStamp("cross_guard", TinkerTools.crossGuard));
        stamps.add(initStamp("emerald", Items.EMERALD, 666, FluidRegistry.getFluid("emerald"))); //GEM!!!!
        stamps.add(initStamp("excavator_head", TinkerTools.excavatorHead));
        stamps.add(initStamp("fletching", TinkerTools.fletching));
        stamps.add(initStamp("hammer_head", TinkerTools.hammerHead));
        stamps.add(initStamp("hand_guard", TinkerTools.handGuard));
        stamps.add(initStamp("kama_head", TinkerTools.kamaHead));
        stamps.add(initStamp("knife_blade", TinkerTools.knifeBlade));
        stamps.add(initStamp("large_plate", TinkerTools.largePlate));
        stamps.add(initStamp("large_sword_blade", TinkerTools.largeSwordBlade));
        stamps.add(initStamp("pan_head", TinkerTools.panHead));
        stamps.add(initStamp("pick_head", TinkerTools.pickHead));
        //stamps.add(initStamp("polishing_kit", ));
        stamps.add(initStamp("scythe_head", TinkerTools.scytheHead));
        stamps.add(initStamp("shard", TinkerTools.shard));
        stamps.add(initStamp("sharpening_kit", TinkerTools.sharpeningKit));
        stamps.add(initStamp("shovel_head", TinkerTools.shovelHead));
        stamps.add(initStamp("sign_head", TinkerTools.signHead));
        stamps.add(initStamp("sword_blade", TinkerTools.swordBlade));
        stamps.add(initStamp("tool_rod", TinkerTools.toolRod));
        stamps.add(initStamp("tough_binding", TinkerTools.toughBinding));
        stamps.add(initStamp("tough_tool_rod", TinkerTools.toughToolRod));
        stamps.add(initStamp("wide_guard", TinkerTools.wideGuard));
    }

    //Not optional to provide basic support (because tinkers is the base mod)!!!
    @SubscribeEvent
    public static void registerStamps(RegistryEvent<Stamp> event) {
        registerAll(event, stamps);
    }

}
