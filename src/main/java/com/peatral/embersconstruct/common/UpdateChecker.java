package com.peatral.embersconstruct.common;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class UpdateChecker {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (EmbersConstructConfig.general.showNewVersionMessage) {
            EmbersConstruct.logger.info("Checking now the version to send the chat-message...");
            ForgeVersion.CheckResult result = ForgeVersion.getResult(Loader.instance().activeModContainer());
            if (result != null && result.status == ForgeVersion.Status.OUTDATED) {
                event.player.sendMessage(
                        ForgeHooks.newChatWithLinks(ChatFormatting.DARK_AQUA + I18n.format("embersconstruct.outdated", result.target.toString()))
                                .setStyle(new Style()
                                        .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url))
                                        .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(I18n.format("embersconstruct.outdated.hover"))))));
            }
        } else {
            EmbersConstruct.logger.info("The Update-Checker Chat-Message is disabled...");
        }
    }
}
