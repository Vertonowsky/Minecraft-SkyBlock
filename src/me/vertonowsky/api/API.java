package me.vertonowsky.api;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;


public class API {

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}


	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isShort(String s) {
		try {
			Short.parseShort(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}



	public static void playBroadcastSound(Sound sound) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getLocation().getWorld().playSound(p.getLocation(), sound, 1, 1);
		}
	}

	public static void sendBroadcastMessage(String message) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(message);
		}
	}



	public static String getRankColor(String rank) {
		String toReturn = "§a";
		if (rank.equals("VIP")) toReturn = "§6";
		if (rank.equals("SuperVIP")) toReturn = "§6";
		if (rank.equals("KidMod")) toReturn = "§3";
		if (rank.equals("Moderator")) toReturn = "§9";
		if (rank.equals("Admin")) toReturn = "§4";

		return toReturn;
	}






	public static void sendJsonMessage(Player p, String message, String hoverMessage, ClickEvent.Action action, String command) {
		Player player = p.getPlayer();
		TextComponent jsonText = new TextComponent();
		jsonText.setText(message);
		jsonText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
		jsonText.setClickEvent(new ClickEvent(action, command));
		player.spigot().sendMessage(jsonText);
	}

	/**
	 * sendJsonMessage(Player, "�7Gracz Vertonowsky wszed� do gry!",
	 * "�7Kliknij!", RUN_COMMAND, "/say fuck me")
	 */










	public static void sendTitle(Player p, String Title, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Title + "\"}"), fadeIn, stay, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void sendSubTitle(Player p, String SubTitle, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "" + "\"}"), fadeIn, stay, fadeOut);
		PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + SubTitle + "\"}"), fadeIn, stay, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
	}

	public static void sendTitleWithSubTitle(Player p, String Title, String SubTitle, int fadeIn, int stay, int fadeOut) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Title + "\"}"), fadeIn, stay, fadeOut);
		PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + SubTitle + "\"}"), fadeIn, stay, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
	}




	public static void sendActionBar(Player p, String text) {
		PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), ChatMessageType.GAME_INFO);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}




	public static void sendHeaderFooter(Player p, String header, String footer) {
		IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try
		{
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, tabHeader);
			headerField.setAccessible(false);
			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, tabFooter);
			footerField.setAccessible(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
