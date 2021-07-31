package com.syntax.bukkit.modules.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LabyMod {
    public LabyMod() {
    }

    public void sendCurrentPlayingGamemode(Player player, boolean visible, String gamemodeName) {
        JsonObject object = new JsonObject();
        object.addProperty("show_gamemode", visible);
        object.addProperty("gamemode_name", gamemodeName);
        LabyModPlugin.getInstance().sendServerMessage(player, "server_gamemode", object);
    }

    public void forceSticker(Player receiver, UUID npcUUID, short stickerId) {
        JsonArray array = new JsonArray();
        JsonObject forcedSticker = new JsonObject();
        forcedSticker.addProperty("uuid", npcUUID.toString());
        forcedSticker.addProperty("sticker_id", stickerId);
        array.add(forcedSticker);
        LabyModPlugin.getInstance().sendServerMessage(receiver, "sticker_api", array);
    }

    public void setSubtitle(Player receiver, UUID subtitlePlayer, String value) {
        JsonArray array = new JsonArray();
        JsonObject subtitle = new JsonObject();
        subtitle.addProperty("uuid", subtitlePlayer.toString());
        subtitle.addProperty("size", 0.8D);
        if (value != null) {
            subtitle.addProperty("value", value);
        }

        array.add(subtitle);
        LabyModPlugin.getInstance().sendServerMessage(receiver, "account_subtitle", array);
    }

    public void sendServerBanner(Player player, String url) {
        JsonObject object = new JsonObject();
        object.addProperty("url", url);
        LabyModPlugin.getInstance().sendServerMessage(player, "server_banner", object);
    }
}
