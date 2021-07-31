package com.syntax.bukkit.modules.entitymanager;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class EntityManager {
    static Entity entity;
    static CraftEntity craftentity;
    static net.minecraft.server.v1_8_R3.Entity entityS;
    static int scheduler;
    static Plugin plugin;
    static Player player = null;
    static float Speed;

    public EntityManager(Entity entity, Plugin plugin) {
        EntityManager.entity = entity;
        craftentity = (CraftEntity)entity;
        entityS = craftentity.getHandle();
        EntityManager.plugin = plugin;
    }

    public static Builder modify() {
        return new Builder();
    }

    public static final class Builder {
        public Builder() {
        }

        public Builder setDisplayName(String display) {
            EntityManager.entity.setCustomName(display);
            EntityManager.entity.setCustomNameVisible(true);
            return this;
        }

        public Builder setDisplayNameVisible(Boolean visible) {
            EntityManager.entity.setCustomNameVisible(visible);
            return this;
        }

        public Builder playEffect(EntityEffect entityeffect) {
            EntityManager.entity.playEffect(entityeffect);
            return this;
        }

        public Builder remove() {
            EntityManager.entity.remove();
            return this;
        }

        public Builder setPassenger(Entity passenger) {
            EntityManager.entity.setPassenger(passenger);
            return this;
        }

        public Builder setFireTicks(int ticks) {
            EntityManager.entity.setFireTicks(ticks);
            return this;
        }

        public Builder setLocation(Location location) {
            this.teleport(location);
            return this;
        }

        public Builder setYawPitch(float yaw, float pitch) {
            Location loc = EntityManager.entity.getLocation();
            loc.setYaw(yaw);
            loc.setPitch(pitch);
            this.teleport(loc);
            return this;
        }

        public Builder teleport(Location location) {
            EntityManager.entity.teleport(location);
            return this;
        }

        public Builder die() {
            EntityManager.entityS.die();
            return this;
        }

        public Builder setInvisible(boolean invisible) {
            EntityManager.entityS.setInvisible(invisible);
            return this;
        }

        public Builder noClip(boolean noClip) {
            EntityManager.entityS.noclip = noClip;
            return this;
        }

        public Builder setInvulnerable(boolean invulnerable) {
            try {
                Field invulnerableField = net.minecraft.server.v1_8_R3.Entity.class.getDeclaredField("invulnerable");
                invulnerableField.setAccessible(true);
                invulnerableField.setBoolean(EntityManager.entityS, invulnerable);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return this;
        }

        public Builder setNoAI(boolean noAI) {
            NBTTagCompound tag = new NBTTagCompound();
            EntityManager.entityS.c(tag);
            tag.setBoolean("NoAI", noAI);
            EntityLiving el = (EntityLiving)EntityManager.entityS;
            el.a(tag);
            return this;
        }

        public Builder setCanPickUpLoot(boolean canPickupLoot) {
            NBTTagCompound tag = new NBTTagCompound();
            EntityManager.entityS.c(tag);
            tag.setBoolean("CanPickUpLoot", canPickupLoot);
            EntityLiving el = (EntityLiving)EntityManager.entityS;
            el.a(tag);
            return this;
        }

        public Builder setHealth(float health) {
            NBTTagCompound tag = new NBTTagCompound();
            EntityManager.entityS.c(tag);
            tag.setFloat("HealF", health);
            EntityLiving el = (EntityLiving)EntityManager.entityS;
            el.a(tag);
            return this;
        }

        public Builder setCanDeSpawn(boolean candespawn) {
            candespawn = !candespawn;
            NBTTagCompound tag = new NBTTagCompound();
            EntityManager.entityS.c(tag);
            tag.setBoolean("PersistenceRequired", candespawn);
            EntityLiving el = (EntityLiving)EntityManager.entityS;
            el.a(tag);
            return this;
        }

        public Builder walkToLocation(Location location, float speed) {
            ((CraftCreature)EntityManager.entity).getHandle().getNavigation().a(location.getX(), location.getY(), location.getZ(), (double)speed);
            return this;
        }

        public Builder followPlayer(Player target, float speed) {
            EntityManager.player = target;
            EntityManager.Speed = speed;
            EntityManager.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityManager.plugin, new Runnable() {
                public void run() {
                    double distance = EntityManager.entity.getLocation().distance(EntityManager.player.getLocation());
                    if (distance < 11.0D) {
                        float speed = EntityManager.Speed;
                        if (distance < 3.0D) {
                            speed = 0.0F;
                        }

                        ((CraftCreature)EntityManager.entity).getHandle().getNavigation().a(EntityManager.player.getLocation().getX(), EntityManager.player.getLocation().getY(), EntityManager.player.getLocation().getZ(), (double)speed);
                    } else if (EntityManager.player.isOnGround()) {
                        EntityManager.entity.teleport(EntityManager.player);
                    }

                }
            }, 0L, 1L);
            return this;
        }

        public Builder stopFollowingPlayer() {
            Bukkit.getScheduler().cancelTask(EntityManager.scheduler);
            return this;
        }

        public Entity build() {
            return EntityManager.entity;
        }
    }
}
