package me.vertonowsky.api.skin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Collection;

public class Skin {



    public static void changeSkin(CraftPlayer cp, String nameFromPlayer) {
        GameProfile skingp = cp.getProfile();

        try {
            if (UUIDFetcher.getUUID(nameFromPlayer) != null) {
                skingp = GameProfileBuilder.fetch(UUIDFetcher.getUUID(nameFromPlayer));
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Collection<Property> props = skingp.getProperties().get("textures");
        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures", props);
        //cp.setHealth(0);
        cp.spigot().respawn();
        /*PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(cp.getEntityId());
        sendPacket(destroy);
        PacketPlayOutPlayerInfo tabremove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
        sendPacket(tabremove);

        new BukkitRunnable() {

            @Override
            public void run() {
                PacketPlayOutPlayerInfo tabremove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
                sendPacket(tabremove);

                PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!(all.getName().equals(cp.getName()))) {
                        ((CraftPlayer)all).getHandle().playerConnection.sendPacket(spawn);
                    }
                }
            }

        }.runTaskLater(Main.getInst(), 2);

         */

    }



    private static void sendPacket(@SuppressWarnings("rawtypes") Packet packet) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)all).getHandle().playerConnection.sendPacket(packet);
        }
    }


}
