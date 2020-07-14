package com.gamesense.client.module.modules.misc;

import com.gamesense.api.event.GameSenseEvent;
import com.gamesense.api.event.events.GuiScreenDisplayedEvent;
import com.gamesense.api.settings.Setting;
import com.gamesense.client.GameSenseMod;
import com.gamesense.client.command.Command;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.Misc);
    }

    Setting.b coords;

    public void setup(){
        coords = registerB("Coords", false);
    }

    @EventHandler
    private Listener<GuiScreenDisplayedEvent> listener = new Listener<>(event -> {
        if(event.getScreen() instanceof GuiGameOver) {
            if(coords.getValBoolean())
                Command.sendClientMessage(String.format("You died at x%d y%d z%d", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
            if(mc.player != null)
                mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    });

    public void onEnable(){
        GameSenseMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        GameSenseMod.EVENT_BUS.unsubscribe(this);
    }
}