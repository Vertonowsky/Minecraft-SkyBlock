package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandTeleport implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("teleport")) {
			if (sender instanceof BlockCommandSender) {
				if (args.length == 4) {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player t = Bukkit.getPlayer(args[0]);
						Location l = t.getLocation();
						if (API.isInt(args[1])) {
							if (API.isInt(args[2])) {
								if (API.isInt(args[3])) {
									l.setX((double) Integer.parseInt(args[1]));
									l.setY((double) Integer.parseInt(args[2]));
									l.setZ((double) Integer.parseInt(args[3]));
									t.teleport(l);
									//t.sendMessage("§a§lSukces: §7Przeteleportowano na wskazane koordynaty.");
									return true;
								}
							}
						}
					}
				}
			}
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.teleport")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length == 3) {
					if (API.isInt(args[0]) && API.isInt(args[1]) && API.isInt(args[2])) {
						Double x = Double.parseDouble(args[0]);
						Double y = Double.parseDouble(args[1]);
						Double z = Double.parseDouble(args[2]);
						tpCord(p, x, y, z);
						sender.sendMessage("§a§lSukces: §7Przeteleportowano do §aX: §f" + x + "§7, §aY: §f" + y + "§7, §aZ: §f" + z + "§7.");
						return true;
					}
				}
			}


			if (args.length == 2) {
				if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[1]) != null) {
					Player pl = Bukkit.getPlayer(args[0]);
					Player t = Bukkit.getPlayer(args[1]);

					Location loc = t.getLocation();
					pl.teleport(loc);
					pl.sendMessage("§a§lSukces: §7Przeteleportowano do gracza §f" + t.getName() + "§7.");
					if (sender instanceof Player) {
						if (pl != (Player) sender) {
							sender.sendMessage("§a§lSukces: §7Przeteleportowano do gracza §f" + t.getName() + "§7.");
						}

					}
					return true;
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			}

			if (args.length == 4) {
				Player t = Bukkit.getPlayer(args[0]);
				if (API.isInt(args[1]) && API.isInt(args[2]) && API.isInt(args[3])) {
					Double x = Double.parseDouble(args[1]);
					Double y = Double.parseDouble(args[2]);
					Double z = Double.parseDouble(args[3]);
					tpCord(t, x, y, z);
					sender.sendMessage("§a§lSukces: §7Przeteleportowano gracza §f" + t.getName() + " §7do §aX: §f" + x + "§7, §aY: §f" + y + "§7, §aZ: §f" + z + "§7.");
					if (t != sender) t.sendMessage("§6§lInfo: §7Przeteleportowano Cię do §aX: §f" + x + "§7, §aY: §f" + y + "§7, §aZ: §f" + z + "§7.");
					return true;
				}
			}
			sender.sendMessage("§6§lInfo: §7Użyj §e/teleport <gracz-kto> <gracz-do-kogo> [x,y,z]§7.");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("tpall")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.teleport.all")) {
					Location loc = p.getLocation();
					for (Player t : Bukkit.getServer().getOnlinePlayers()) {
						t.teleport(loc);
						t.sendMessage("§6§lInfo: §7Wszyscy gracze zostali przeteleportowani do gracza §f" + p.getName() + "§7.");
					}
					return true;
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





	private void tpCord(Player p, double x, double y, double z) {
		Location loc = p.getLocation();
		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		p.teleport(loc);
	}
}
