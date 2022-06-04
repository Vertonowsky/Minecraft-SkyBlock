package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommandMoney implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("money")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (p.hasPermission("vert.*")) {
					if (args.length == 0) {
						double priceRounded = new BigDecimal(pUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
						p.sendMessage("§6§lInfo: §7Saldo: §a$" + priceRounded);
						return true;
					}

					if (args.length == 1) {
						if (User.get(args[0]) != null) {
							double priceRounded = new BigDecimal(User.get(args[0]).getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
							p.sendMessage("§6§lInfo: §7Saldo §f" + args[0] + "§7: §a$" + priceRounded);
							return true;
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					}
					p.sendMessage("§6§lInfo: §7Użyj §e/money <gracz>§7.");
					return true;
				}
				double priceRounded = new BigDecimal(pUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
				p.sendMessage("§6§lInfo: §7Saldo: §a$" + priceRounded);
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("pay")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (args.length == 2) {
					if (Bukkit.getPlayer(args[0]) != null) {
						Player t = Bukkit.getPlayer(args[0]);
						User tUUID = User.get(args[0]);
						if (p == t) {
							p.sendMessage("§c§lBłąd: §7Nie możesz przelać pieniędzy sobie!");
							return true;
						}
						if (User.get(args[0]) != null) {
							if (API.isDouble(args[1])) {
								if (Double.parseDouble(args[1]) > 0) {
									String money1 = String.format("%.2f", Double.parseDouble(args[1])).replaceAll(",", ".");
									Double money = Double.parseDouble(money1);
									if (pUUID.getMoney() < money) {
										p.sendMessage("§c§lBłąd: §7Nie posiadasz tyle pieniędzy! §8[§c$" + money + "§8]§7.");
										return true;
									}

									pUUID.setMoney(pUUID.getMoney() - money);
									tUUID.setMoney(tUUID.getMoney() + money);
									double priceRounded = new BigDecimal(pUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
									double priceRounded2 = new BigDecimal(tUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();

									p.sendMessage("§a§lSukces: §7Przelano §a$" + money + "§7 do gracza: §f" + t.getName() + "§7. Saldo: §a$" + priceRounded);
									p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
									Scoreboard.setScoreboard(p);

									t.sendMessage("§6§lInfo: §7Gracz §f" + p.getName() + " §7przelał Ci §a$" + money + "§7. Saldo: §a$" + priceRounded2);
									t.getWorld().playSound(t.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
									Scoreboard.setScoreboard(t);
									return true;
								} else {
									p.sendMessage("§c§lBłąd: §7Nieprawidłowa kwota.");
									return true;
								}
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
						return true;
					}
					p.sendMessage("§6§lInfo: §7Użyj §e/pay <gracz> <kwota>§7.");
					return true;
				}
				p.sendMessage("§6§lInfo: §7Użyj §e/pay <gracz> <kwota>§7.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("addmoney")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.*")) {
					if (args.length == 2) {
						if (User.get(args[0]) != null) {
							if (API.isDouble(args[1])) {
								User tUUID = User.get(args[0]);
								double amount = Double.parseDouble(args[1]);
								double moneyRounded = 0;

								if (tUUID.getMoney() + amount < 0) {
									moneyRounded = new BigDecimal(tUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
									p.sendMessage("§c§lBłąd: §7Saldo §f" + args[0] + "§7: §c$" + moneyRounded + "§7. Saldo po zmianie nie może być §c< 0§7.");
									return true;
								}
								tUUID.setMoney(tUUID.getMoney() + amount);
								moneyRounded = new BigDecimal(tUUID.getMoney() + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
								double amountRounded = new BigDecimal(amount + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
								if (amount > 0) p.sendMessage("§6§lInfo: §7Saldo §f" + args[0] + "§7: §a$" + moneyRounded + "§7. §8[§a+$" + amountRounded + "§8].");
								if (amount < 0) p.sendMessage("§6§lInfo: §7Saldo §f" + args[0] + "§7: §a$" + moneyRounded + "§7. §8[§c$" + amountRounded + "§8].");
								if (amount == 0) {
									p.sendMessage("§c§lBłąd: §7Ty tak na serio?");
									return true;
								}

								if (Bukkit.getPlayer(args[0]) != null) {
									Player t = Bukkit.getPlayer(args[0]);

									if (amount > 0) t.sendMessage("§6§lInfo: §7Otrzymano §a+$" + amountRounded + " §7od Administracji. Saldo: §a$" + moneyRounded );
									if (amount < 0) t.sendMessage("§6§lInfo: §7Otrzymano §c$" + amountRounded + " §7od Administracji. Saldo: §a$" + moneyRounded );
									t.getWorld().playSound(t.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
									Scoreboard.setScoreboard(t);
								}
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					}
					p.sendMessage("§6§lInfo: §7Użyj §e/addmoney <gracz> <ilość>§7.");
					return true;
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			}
		}
		return false;
	}
}