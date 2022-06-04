package me.vertonowsky.essentials.commands;


import me.vertonowsky.api.API;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CommandGive implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("daj")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				User pUUID = User.get(p.getUniqueId());
				if (p.hasPermission("vert.*")) {
					if (args.length == 2) {
						if (API.isInt(args[0])) {
							if (args[1].contains(":")) {
								String[] argument = args[1].split(":");
								if (argument.length != 2) {
									p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
									return true;
								}
								if (API.isInt(argument[0])) {
									if (API.isInt(argument[1])) {
										Integer amount = Integer.parseInt(args[0]);
										Integer id = Integer.parseInt(argument[0]);
										ItemStack item = new ItemStack(Material.getMaterial(id), 0);
										if (Integer.parseInt(argument[1]) > 0) {
											item.setAmount(amount);
											item.setDurability((short) Integer.parseInt(argument[1]));
											p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7.");
											pUUID.giveItem(item, false);
											return true;
										} else {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
									} else {
										p.sendMessage("§6§lInfo: §7Użyj §e/daj <ilość> <id>§7.");
										return true;
									}
								} else if (!(API.isInt(argument[0]))) {
									if (argument.length != 2) {
										p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
										return true;
									}
									if (argument.length == 2) {
										if (argument[0].equals("") || argument[1].equals("")) {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
									}
									
									if (API.isInt(argument[1])) {
										Integer amount = Integer.parseInt(args[0]);
										if (Material.valueOf(argument[0].toUpperCase()) != null) {
											Material id = Material.valueOf(argument[0].toUpperCase());
											ItemStack item = new ItemStack(id, 0);
											if (Integer.parseInt(argument[1]) > 0) {
												item.setAmount(amount);
												item.setDurability((short) Integer.parseInt(argument[1]));
												p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + id.toString() + "§7.");
												pUUID.giveItem(item, false);
												return true;
											} else {
												p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
												return true;
											}
										} else {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
									} else {
										p.sendMessage("§6§lInfo: §7Użyj §e/daj <ilość> <id>§7.");
										return true;
									}
								}
							}

							else if (!(args[1].contains(":"))) {
								if (API.isInt(args[1])) {
									Integer amount = Integer.parseInt(args[0]);
									Integer id = Integer.parseInt(args[1]);
									ItemStack item = new ItemStack(Material.getMaterial(id), 0);
									
									item.setAmount(amount);
									p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7.");
									pUUID.giveItem(item, false);
									return true;
								} else {
									Integer amount = Integer.parseInt(args[0]);
									if (Material.valueOf(args[1].toUpperCase()) != null) {
										Material id = Material.valueOf(args[1].toUpperCase());
										ItemStack item = new ItemStack(id, 0);
										
										item.setAmount(amount);
										p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + id.toString() + "§7.");
										pUUID.giveItem(item, false);
										return true;
									} else {
										p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
										return true;
									}
								}
							}
						} else {
							p.sendMessage("§6§lInfo: §7Użyj §e/daj <ilość> <id>§7.");
							return true;
						}
					}
					
					if (args.length == 3) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player t = Bukkit.getPlayer(args[0]);
							User tUUID = User.get(t.getName());
							if (API.isInt(args[1])) {
								if (args[2].contains(":")) {
									String[] argument = args[2].split(":");
									if (argument.length != 2) {
										p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
										return true;
									}
									if (argument.length == 2) {
										if (argument[0].equals("") || argument[1].equals("")) {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
									}
									if (API.isInt(argument[0])) {
										if (API.isInt(argument[1])) {
											Integer amount = Integer.parseInt(args[1]);
											Integer id = Integer.parseInt(argument[0]);
											ItemStack item = new ItemStack(Material.getMaterial(id), 0);
											if (Integer.parseInt(argument[1]) > 0) {
												item.setAmount(amount);
												item.setDurability((short) Integer.parseInt(argument[1]));
												p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7 do §f" + t.getName() + "§7.");
												t.sendMessage("§6§lInfo: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7 przez §f" + t.getName() + "§7.");
												tUUID.giveItem(item, false);
												return true;
											} else {
												p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
												return true;
											}
										} else {
											p.sendMessage("§6§lInfo: §7Użyj §e/daj <gracz> <ilość> <id>§7.");
											return true;
										}
									} else if (!(API.isInt(argument[0]))) {
										if (argument.length != 2) {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
										if (argument.length == 2) {
											if (argument[0].equals("") || argument[1].equals("")) {
												p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
												return true;
											}
										}
										if (API.isInt(argument[1])) {
											Integer amount = Integer.parseInt(args[1]);
											if (Material.valueOf(argument[0].toUpperCase()) != null) {
												Material id = Material.valueOf(argument[0].toUpperCase());
												ItemStack item = new ItemStack(id, 0);
												if (Integer.parseInt(argument[1]) > 0) {
													item.setAmount(amount);
													item.setDurability((short) Integer.parseInt(argument[1]));
													p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + id.toString() + "§7 do §f" + t.getName() + "§7.");
													t.sendMessage("§6§lInfo: §7Dodano " + amount + " " + id.toString() + "§7 przez §f" + t.getName() + "§7.");
													pUUID.giveItem(item, false);
													return true;
												} else {
													p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
													return true;
												}
											} else {
												p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
												return true;
											}
										} else {
											p.sendMessage("§6§lInfo: §7Użyj §e/daj <ilość> <id>§7.");
											return true;
										}
									}
								}

								else if (!(args[2].contains(":"))) {
									if (API.isInt(args[2])) {
										Integer amount = Integer.parseInt(args[1]);
										Integer id = Integer.parseInt(args[2]);
										ItemStack item = new ItemStack(Material.getMaterial(id), 0);
										
										item.setAmount(amount);
										p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7 do §f" + t.getName() + "§7.");
										t.sendMessage("§6§lInfo: §7Dodano " + amount + " " + Material.getMaterial(id).toString() + "§7 przez §f" + t.getName() + "§7.");
										tUUID.giveItem(item, false);
										return true;
									} else {
										Integer amount = Integer.parseInt(args[1]);
										if (Material.valueOf(args[2].toUpperCase()) != null) {
											Material id = Material.valueOf(args[2].toUpperCase());
											ItemStack item = new ItemStack(id, 0);
											
											item.setAmount(amount);
											p.sendMessage("§a§lSukces: §7Dodano " + amount + " " + id.toString() + "§7.");
											pUUID.giveItem(item, false);
											return true;
										} else {
											p.sendMessage("§c§lBłąd: §7Nie ma takiego przedmiotu.");
											return true;
										}
									}
								}
							} else {
								p.sendMessage("§6§lInfo: §7Użyj §e/daj <gracz> <ilość> <id>§7.");
								return true;
							}
						} else {
							p.sendMessage("§6§lInfo: §7Użyj §e/daj <gracz> <ilość> <id>§7.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/daj <gracz> <ilość> <id>§7.");
						return true;
					}
				} else {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
			} 
		}
		return false;
	}

}
