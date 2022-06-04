package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CommandHeal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("heal")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("vert.*")) {
					if (args.length == 0) {
						p.setHealth(20);
						p.setFoodLevel(20);
						p.setFireTicks(0);
						p.sendMessage("§a§lSukces: §7Zostałeś uleczony.");
						for (PotionEffect effect : p.getActivePotionEffects()) {
							p.removePotionEffect(effect.getType());
						}

						//OnInventoryClose.npc.camera(true, p);
						return true;
					}
					if (args.length >= 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							t.setHealth(20);
							t.setFoodLevel(20);
							t.setFireTicks(0);
							t.sendMessage("§a§lSukces: §7Zostałeś uleczony przez §f" + p.getName() + "§7.");
							if (p != t) {
								p.sendMessage("§a§lSukces: §7Uleczono gracza §f" + t.getName() + "§7.");
							}
							for (PotionEffect effect : t.getActivePotionEffects()) {
								t.removePotionEffect(effect.getType());
							}
							return true;

						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/heal <gracz>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
					
				}
				
			} if (sender instanceof ConsoleCommandSender) {
				if (args.length >= 1) {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player t = Bukkit.getPlayer(args[0]);
						t.setHealth(20);
						t.setFoodLevel(20);
						t.setFireTicks(0);
						t.sendMessage("§a§lSukces: §7Zostałeś uleczony przez §fadministratora§7.");
						sender.sendMessage("§6§lInfo: Uleczono gracza §b" + t.getName() + "§7.");
						for (PotionEffect effect : t.getActivePotionEffects()) {
							t.removePotionEffect(effect.getType());
						}
						return true;

					} else {
						sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
						return true;
					}
				} else {
					sender.sendMessage("§6§lInfo: §7Uzyj §e/heal <gracz>§7.");
					return true;
				}
			}
		}
		return false;
	}
}
