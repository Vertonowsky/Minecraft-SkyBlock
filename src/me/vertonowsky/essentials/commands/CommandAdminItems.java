package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.Holograms;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandAdminItems implements CommandExecutor {

	public static ItemStack moneyChestItem;
	public static ItemStack mobSpawnerTool;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("adminitems")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getName());
				if (p.hasPermission("vert.*")) {
					pUUID.giveItem(moneyChestItem, false);
					pUUID.setMoneyChests(pUUID.getMoneyChests() + 3);
					Holograms.reloadMoneyChests(p);
					pUUID.giveItem(mobSpawnerTool, false);
					return true;
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				
			} else {
				sender.sendMessage("§c§lBłąd: §7Ta komenda nie jest dostepna dla konsoli!");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("spawners")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.*")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			}

			if (Main.spawnersWork) {
				Main.spawnersWork = false;
				sender.sendMessage("§a§lSukces: §7Wyłączono spawnery potworów.");
				return true;
			} else if (!Main.spawnersWork) {
				Main.spawnersWork = true;
				sender.sendMessage("§a§lSukces: §7Włączono spawnery potworów.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("moneychest")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 0) {
						User pUUID = User.get(p.getUniqueId());
						pUUID.setMoneyChests(pUUID.getMoneyChests() + 3);
						p.sendMessage("§a§lSukces: §7Dodano §f3 §fklucze do §6Złotej skrzyni§7.");
						return true;
					}

					if (args.length >= 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							User tUUID = User.get(t.getUniqueId());
							tUUID.setMoneyChests(tUUID.getMoneyChests() + 3);
							t.sendMessage("§6§lInfo: §7Otrzymano §f3 §fklucze do §6Złotej skrzyni§7 od §f" + p.getName() + "§7.");
							if (p != t) {
								p.sendMessage("§a§lSukces: §7Dodano §f3 §fklucze do §6Złotej skrzyni§7 do  §f" + t.getName() + "§7.");
							}
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/moneychest <gracz>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;

				}
			} else {
				sender.sendMessage("§c§lBlad: §7Ta komenda nie jest dostepna dla konsoli.");
				return true;
			}
		}
		return false;
	}


	public static void loadChests() {
		moneyChestItem = new ItemStack(Material.CHEST, 1);
		ItemMeta meta = moneyChestItem.getItemMeta();
		meta.setDisplayName("§7✧ §6Złota skrzynia §7✧");
		List<String> lore = new ArrayList<>();
		lore.add("§7[Skrzynia]");
		meta.setLore(lore);
		moneyChestItem.setItemMeta(meta);

		mobSpawnerTool = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta meta2 = mobSpawnerTool.getItemMeta();
		meta2.setDisplayName("§7✧ §aSpawner mobów §7✧");
		List<String> lore2 = new ArrayList<>();
		lore2.add("§7[Narzędzie]");
		meta2.setLore(lore2);
		mobSpawnerTool.setItemMeta(meta2);

		CommandDaily.dailyChestItem = new ItemStack(Material.CHEST, 1);
		ItemMeta meta3 = CommandDaily.dailyChestItem.getItemMeta();
		meta3.setDisplayName("§7>> §aDzienny zestaw §7<<");
		List<String> lore3 = new ArrayList<>();
		lore3.add("§7[Skrzynia]");
		lore3.add("");
		lore3.add("§8> §7Postaw aby otworzyć!");
		meta3.setLore(lore3);
		CommandDaily.dailyChestItem.setItemMeta(meta3);
	}
}
