package com.peatral.embersconstruct.integration.tinkersconstruct;

import com.peatral.embersconstruct.modules.EmbersConstructModule;
import com.peatral.embersconstruct.util.OreDictValues;
import com.peatral.embersconstruct.util.Stamp;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.tools.TinkerTools;

@Pulse(id = IntegrationTinkersConstruct.PulseId,
        modsRequired = IntegrationTinkersConstruct.modid,
        defaultEnable = true)
public class IntegrationTinkersConstruct extends EmbersConstructModule {

    public static final String modid = "tconstruct";
    public static final String PulseId = modid + "Integration";

    @SubscribeEvent
    public void registerStamps(RegistryEvent.Register<Stamp> event) {
        registerAll(event,
                initStamp("arrow_head", TinkerTools.arrowHead),
                initStamp("arrow_shaft", TinkerTools.arrowShaft),
                initStamp("axe_head", TinkerTools.axeHead),
                initStamp("binding", TinkerTools.binding),
                initStamp("bow_limb", TinkerTools.bowLimb),
                initStamp("bow_string", TinkerTools.bowString),
                initStamp("broad_axe_head", TinkerTools.broadAxeHead),
                initStamp("cross_guard", TinkerTools.crossGuard),
                initStamp("gem", OreDictValues.GEM),
                initStamp("excavator_head", TinkerTools.excavatorHead),
                initStamp("fletching", TinkerTools.fletching),
                initStamp("hammer_head", TinkerTools.hammerHead),
                initStamp("hand_guard", TinkerTools.handGuard),
                initStamp("kama_head", TinkerTools.kamaHead),
                initStamp("knife_blade", TinkerTools.knifeBlade),
                initStamp("large_plate", TinkerTools.largePlate),
                initStamp("large_sword_blade", TinkerTools.largeSwordBlade),
                initStamp("pan_head", TinkerTools.panHead),
                initStamp("pick_head", TinkerTools.pickHead),
                //initStamp("polishing_kit", ),
                initStamp("scythe_head", TinkerTools.scytheHead),
                initStamp("shard", TinkerTools.shard),
                initStamp("sharpening_kit", TinkerTools.sharpeningKit),
                initStamp("shovel_head", TinkerTools.shovelHead),
                initStamp("sign_head", TinkerTools.signHead),
                initStamp("sword_blade", TinkerTools.swordBlade),
                initStamp("tool_rod", TinkerTools.toolRod),
                initStamp("tough_binding", TinkerTools.toughBinding),
                initStamp("tough_tool_rod", TinkerTools.toughToolRod),
                initStamp("wide_guard", TinkerTools.wideGuard));
    }

}
