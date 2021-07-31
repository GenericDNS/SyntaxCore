package com.syntax.bukkit.modules.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

public class SkullChanger {
    public static Class<?> skullMetaClass;
    public static Class<?> tileEntityClass;
    public static Class<?> blockPositionClass;

    public SkullChanger() {
    }

    public static ItemStack getSkull(Player player) {
        return getSkull(getSignature(player), getValue(player), 1);
    }

    public static ItemStack getSkull(String signature, String value) {
        return getSkull(signature, value, 1);
    }

    public static ItemStack getSkullURL(String url, String displayname, Integer amount, String... lore) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, amount, (short)3);
        if (url == null) {
            return head;
        } else {
            SkullMeta headMeta = (SkullMeta)head.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;

            try {
                profileField = headMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(headMeta, profile);
            } catch (Exception var10) {
                var10.printStackTrace();
            }

            headMeta.setDisplayName(displayname);
            headMeta.setLore(Arrays.asList(lore));
            head.setItemMeta(headMeta);
            return head;
        }
    }

    public static ItemStack getSkull(String signature, String value, int amount) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();

        try {
            Field profileField = skullMetaClass.getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, getProfile(signature, value));
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        skull.setItemMeta(meta);
        return skull;
    }

    public static boolean setBlock(Location loc, String signature, String value) {
        return setBlock(loc.getBlock(), signature, value);
    }

    public static boolean setBlock(Block block, String signature, String value) {
        if (block.getType() != Material.SKULL && block.getType() != Material.SKULL_ITEM) {
            block.setType(Material.SKULL);
        }

        try {
            Object nmsWorld = block.getWorld().getClass().getMethod("getHandle").invoke(block.getWorld());
            Object tileEntity = null;
            Method getTileEntity = nmsWorld.getClass().getMethod("getTileEntity", blockPositionClass);
            tileEntity = tileEntityClass.cast(getTileEntity.invoke(nmsWorld, getBlockPositionFor(block.getX(), block.getY(), block.getZ())));
            tileEntityClass.getMethod("setGameProfile", GameProfile.class).invoke(tileEntity, getProfile(signature, value));
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static String getSignature(Player p) {
        try {
            return getSignature((GameProfile)p.getClass().getMethod("getProfile").invoke(p));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getSignature(GameProfile gp) {
        Property textures = (Property)gp.getProperties().get("textures").iterator().next();
        return textures.getSignature();
    }

    public static String getValue(Player p) {
        try {
            return getValue((GameProfile)p.getClass().getMethod("getProfile").invoke(p));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getValue(GameProfile gp) {
        Property textures = (Property)gp.getProperties().get("textures").iterator().next();
        return textures.getValue();
    }

    private static GameProfile getProfile(String signature, String value) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
        Property property = new Property("textures", value, signature);
        profile.getProperties().put("textures", property);
        return profile;
    }

    private static Object getBlockPositionFor(int x, int y, int z) {
        Object blockPosition = null;

        try {
            Constructor<?> cons = blockPositionClass.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            blockPosition = cons.newInstance(x, y, z);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return blockPosition;
    }

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try {
            skullMetaClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftMetaSkull");
            tileEntityClass = Class.forName("net.minecraft.server." + version + ".TileEntitySkull");
            blockPositionClass = Class.forName("net.minecraft.server." + version + ".BlockPosition");
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
        }

    }
}
