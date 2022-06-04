package me.vertonowsky.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gamemode")) {
			if (sender instanceof BlockCommandSender) {
				if (args.length == 2) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player t = Bukkit.getPlayer(args[1]);
						if (args[0].equalsIgnoreCase("0")) {
							t.setGameMode(GameMode.SURVIVAL);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fprzetrwanie§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("1")) {
							t.setGameMode(GameMode.CREATIVE);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fkreatywny§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("2")) {
							t.setGameMode(GameMode.ADVENTURE);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fprzygodowy§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("3")) {
							t.setGameMode(GameMode.SPECTATOR);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fobserwator§7.");
							return true;
						}
					}
				}
			} 
			
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("0")) {
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage("§a§lSukces: §7Zmieniono gamemode na §fprzetrwanie§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("1")) {
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage("§a§lSukces: §7Zmieniono gamemode na §fkreatywny§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("2")) {
							p.setGameMode(GameMode.ADVENTURE);
							p.sendMessage("§a§lSukces: §7Zmieniono gamemode na §fprzygodowy§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("3")) {
							p.setGameMode(GameMode.SPECTATOR);
							p.sendMessage("§a§lSukces: §7Zmieniono gamemode na §fobserwator§7.");
							return true;
						}
						else {
							p.sendMessage("§6§lInfo: §7Użyj §e/gamemode <0/1/2/3> <gracz>§7.");
							return false;
						}
					}
					if (args.length == 2) {
						if (Bukkit.getPlayer(args[1]) != null) {
							Player t = Bukkit.getPlayer(args[1]);
							if (args[0].equalsIgnoreCase("0")) {
								t.setGameMode(GameMode.SURVIVAL);
								t.sendMessage("§6§lInfo: §7Gracz §f" + p.getName() + " §7zmienił Twój gamemode na §fprzetrwanie§7.");
								p.sendMessage("§a§lSukces: §7Zmieniono gamemode gracza §f" + t.getName() + " §7na §fprzetrwanie§7.");
								return true;
							}
							if (args[0].equalsIgnoreCase("1")) {
								t.setGameMode(GameMode.CREATIVE);
								t.sendMessage("§6§lInfo: §7Gracz §f" + p.getName() + " §7zmienił Twój gamemode na §fkreatywny§7.");
								p.sendMessage("§a§lSukces: §7Zmieniono gamemode gracza §f" + t.getName() + " §7na §fkreatywny§7.");
								return true;
							}
							if (args[0].equalsIgnoreCase("2")) {
								;
								t.setGameMode(GameMode.ADVENTURE);
								t.sendMessage("§6§lInfo: §7Gracz §f" + p.getName() + " §7zmienił Twój gamemode na §fprzygodowy§7.");
								p.sendMessage("§a§lSukces: §7Zmieniono gamemode gracza §f" + t.getName() + " §7na §fprzygodowy§7.");
								return true;
							}
							if (args[0].equalsIgnoreCase("3")) {
								;
								t.setGameMode(GameMode.SPECTATOR);
								t.sendMessage("§6§lInfo: §7Gracz §f" + p.getName() + " §7zmienił Twój gamemode na §fobserwator§7.");
								p.sendMessage("§a§lSukces: §7Zmieniono gamemode gracza §f" + t.getName() + " §7na §fobserwator§7.");
								return true;
							}
							else {
								p.sendMessage("§6§lInfo: §7Użyj §e/gamemode <0/1/2/3> <gracz>§7.");
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}

					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/gamemode <0/1/2/3> <gracz>§7.");
						return true;
					}

				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				
				
			} 
			
			if (sender instanceof ConsoleCommandSender) {
				if (args.length == 2) {
					if (Bukkit.getPlayer(args[1]) != null) {
						Player t = Bukkit.getPlayer(args[1]);
						if (args[0].equalsIgnoreCase("0")) {
							t.setGameMode(GameMode.SURVIVAL);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fprzetrwanie§7.");
							sender.sendMessage("§7Pomyslnie zmieniles gamemode gracza §b" + t.getName() + " §7na §bprzetrwanie§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("1")) {
							t.setGameMode(GameMode.CREATIVE);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fkreatywny§7.");
							sender.sendMessage("§7Pomyslnie zmieniles gamemode gracza §b" + t.getName() + " §7na §bkreatywny§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("2")) {
							t.setGameMode(GameMode.ADVENTURE);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fprzygodowy§7.");
							sender.sendMessage("§7Pomyslnie zmieniles gamemode gracza §b" + t.getName() + " §7na §bprzygodowy§7.");
							return true;
						}
						if (args[0].equalsIgnoreCase("3")) {
							t.setGameMode(GameMode.SPECTATOR);
							t.sendMessage("§6§lInfo: §7Administrator §7zmienił Twój gamemode na §fobserwator§7.");
							sender.sendMessage("§7Pomyslnie zmieniles gamemode gracza §b" + t.getName() + " §7na §fobserwator§7.");
							return true;
						}else {
							sender.sendMessage("§6§lInfo: §7Uzyj §e/gamemode <0/1/2/3> <gracz>§7.");
							return true;
						}
					} else {
						sender.sendMessage("§c§lBlad: §7Niewlasciwy nick lub gracz nie jest online.");
						return true;
					}

				} else {
					sender.sendMessage("§6§lInfo: §7Uzyj §e/gamemode <0/1/2/3> <gracz>§7.");
					return true;
				}
			} 
		}
		return false;
	}
}
