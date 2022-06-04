package me.vertonowsky.essentials.commands;

import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandMsg implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("msg")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (p.hasPermission("vert.msg")) {
					if (args.length < 2) {
						p.sendMessage("§6§lInfo: §7Użyj §e/msg <gracz> <wiadomość>§7.");
						return true;
					}
					if (pUUID.getMuteExpireDate() > System.currentTimeMillis()) {
						p.sendMessage("§c§lBłąd: §7Zostałeś wyciszony!");
						return true;
					}
					if (args.length > 1) {
						if (Bukkit.getPlayer(args[0]) != null) {
							sendPrivateMessage(p, Bukkit.getPlayer(args[0]), args, 1);
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/msg <gracz> <wiadomość>§7.");
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
		if (cmd.getName().equalsIgnoreCase("r")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (p.hasPermission("vert.msg")) {
					if (args.length < 1) {
						p.sendMessage("§6§lInfo: §7Użyj §e/r <wiadomość>§7.");
						return true;
					}
					if (pUUID.getMuteExpireDate() > System.currentTimeMillis()) {
						p.sendMessage("§c§lBłąd: §7Zostałeś wyciszony!");
						return true;
					}
					if (args.length > 0) {
						if (pUUID.getLastMsg() != null) {
							if (User.get(pUUID.getLastMsg().getUniqueId()) != null && User.get(pUUID.getLastMsg().getUniqueId()).isOnline()) {
								sendPrivateMessage(p, Bukkit.getPlayer(User.get(p.getUniqueId()).getLastMsg().getName()), args, 0);
								return true;
							} else {
								p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Nie masz komu odpisać!");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/r <wiadomość>§7.");
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


	private void sendPrivateMessage(Player from, Player to, String[] mes, int startArg) {
		User pUUID = User.get(from.getUniqueId());
		User tUUID = User.get(to.getUniqueId());
		StringBuilder message = new StringBuilder();
		for (int i = startArg; i < mes.length; i++) {
			String s = mes[i];
			if (i < mes.length - 1) s = s + " ";
			message.append(s);
		}

		from.sendMessage("§7[§cJa §8-> §c" + to.getName() + "§7] §f" + message.toString());
		to.sendMessage("§7[§c" + from.getName() + " §8-> §cJa§7] §f" + message.toString());

		pUUID.setLastMsg(to);
		tUUID.setLastMsg(from);
	}
}