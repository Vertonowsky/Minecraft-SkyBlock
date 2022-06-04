package me.vertonowsky.essentials.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import me.vertonowsky.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerListPingEvent implements Listener {

    @EventHandler
    public void onServerListPing(org.bukkit.event.server.ServerListPingEvent e) {
        e.setMotd("          §7◇ ◇ ◆ §2§m§l>§a§m§l>§f§m§l>  §b  Prestigemc  §f§m§l  <§a§m§l<§2§m§l<§7 ◆ ◇ ◇       " +
                "§8§l>>§c§lCreative tymczasowo niedostępny! §8[§c1.12-1.15§8]");
    }


    public static void changePlayerCount() {
        final List<WrappedGameProfile> names = new ArrayList<WrappedGameProfile>();
        names.add(new WrappedGameProfile("1", "§8§m------------§8[ §bPrestigemc §8]§m------------"));
        names.add(new WrappedGameProfile("2", ""));
        names.add(new WrappedGameProfile("3", "§8§l➤§6Wersja§8: §8[§c1.12-1.15§8]"));
        names.add(new WrappedGameProfile("4", "§8§l➤§6WWW§8:     §7www.prestigemc.pl"));
        names.add(new WrappedGameProfile("5", ""));
        names.add(new WrappedGameProfile("6", "      §aDziekujemy, ze jestesmy na Twojej"));
        names.add(new WrappedGameProfile("7", "                  §aliscie serwerow!"));
        names.add(new WrappedGameProfile("8", ""));
        names.add(new WrappedGameProfile("9", "§8§m------------§8[ §bPrestigemc §8]§m------------"));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInst(), ListenerPriority.NORMAL,
                Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getServerPings().read(0).setPlayers(names);
            }
        });
    }

}
