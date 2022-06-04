package me.vertonowsky.essentials.commands;

import me.vertonowsky.api.API;
import me.vertonowsky.inventory.InventoryAPI;
import me.vertonowsky.mysql.Users;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandBan implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("bany")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.ban")) {
					if (args.length == 0) {
						p.sendMessage("§7===================== §bBany §7=====================");
						API.sendJsonMessage(p, "§8- §b/ban <gracz> <powod> §8- §7permanentnie banuje gracza.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/ban ");
						API.sendJsonMessage(p, "§8- §b/tempban <gracz> <czas-h> <powod> §8- §7tymczasowo banuje gracza.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/tempban ");
						API.sendJsonMessage(p, "§8- §b/mute <gracz> <czas-h> §8- §7tymczasowo wycisza gracza.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/mute ");
						API.sendJsonMessage(p, "§8- §b/unmute <gracz> §8- §7pozwala graczowi znów pisać.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/unmute ");
						API.sendJsonMessage(p, "§8- §b/unban <gracz> §8- §7odbanowywuje gracza.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/unban ");
						API.sendJsonMessage(p, "§8- §b/checkban <gracz> §8- §7wyświetla informacje o banie.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/checkban ");
						API.sendJsonMessage(p, "§8- §b/banlist §8- §7pokazuję listę zbanowanych graczy.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/banlist");
						API.sendJsonMessage(p, "§8- §b/kick <gracz> <powod> §7- §fwyrzuca gracza z serwera.", "§8§l[§a§lKliknij§8§l]", Action.SUGGEST_COMMAND, "/kick ");
						p.sendMessage("§7=================================================");
						return true;
					}
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("unmute")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.ban")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length >= 2 && args[0].equalsIgnoreCase(p.getName())) {
					p.sendMessage("§c§lBłąd: §7Nie możesz odmutować siebie.");
					return true;
				}
			}
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]);
					User tUUID = User.get(args[0]);
					if (tUUID.getMuteExpireDate() > System.currentTimeMillis()) {
						tUUID.setMuteExpireDate(System.currentTimeMillis());
						sender.sendMessage("§a§lSuckes: §7Gracz §f" + t.getName() + " §7znów może mówić.");
						t.sendMessage("§6§lInfo: §7Znów możesz mówić.");
						return true;

					} else {
						sender.sendMessage("§c§lBłąd: §7Gracz §f" + t.getName() + " §7nie jest wyciszony!");
						return true;
					}
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			} else {
				sender.sendMessage("§6§lInfo: §7Użyj §e/unmute <gracz>§7.");
				return true;
			}

		}
		if (cmd.getName().equalsIgnoreCase("mute")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.ban")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length >= 2 && args[0].equalsIgnoreCase(p.getName())) {
					p.sendMessage("§c§lBłąd: §7Nie możesz wyciszyć siebie.");
					return true;
				}
			}

			if (args.length >= 2) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]);
					User tUUID = User.get(args[0]);
					if (!(tUUID.getRank().equals("Moderator") || tUUID.getRank().equals("Admin"))) {
						if (API.isInt(args[1])) {
							int muteLength = Integer.parseInt(args[1]);
							StringBuilder message = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								message.append(args[i] + " ");
							}

							tUUID.setMuteExpireDate(System.currentTimeMillis() + (1000*60*60*muteLength));

							sender.sendMessage("§a§lSuckes: §7Gracz §f" + t.getName() + " §7został wyciszony.");
							t.sendMessage("§6§lInfo: §7Zostałeś wyciszony na §f" + muteLength + "§7h.");
							return true;
						} else {
							sender.sendMessage("§6§lInfo: §7Użyj §e/mute <gracz> <czas-h>§7.");
							return true;
						}
					} else {
						sender.sendMessage("§c§lBłąd: §7Nie możesz wyciszyć innego administratora.");
						return true;
					}
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			} else {
				sender.sendMessage("§6§lInfo: §7Użyj §e/mute <gracz> <czas-h>§7.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("tempban")) {
			String banner = "Administrator";
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.ban")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length >= 3 && args[0].equalsIgnoreCase(p.getName())) {
					p.sendMessage("§c§lBłąd: §7Nie możesz zbanować siebie.");
					return true;
				}
				banner = p.getName();
			}

			if (args.length >= 3) {
				if (UserUtils.getUsers().contains(User.get(args[0]))) {
					User tUUID = User.get(args[0]);
					if (!(tUUID.getRank().equals("Moderator") || tUUID.getRank().equals("Admin"))) {
						if (!tUUID.getBanStatus()) {
							if (API.isInt(args[1])) {
								int banLength = Integer.parseInt(args[1]);
								StringBuilder message = new StringBuilder();
								for (int i = 2; i < args.length; i++) {
									message.append(args[i] + " ");
								}

								API.sendBroadcastMessage("§8§m------------------------------------");
								API.sendBroadcastMessage("");
								API.sendBroadcastMessage("  §4Gracz §c" + tUUID.getName() + "§4 został zbanowany!");
								API.sendBroadcastMessage("");
								API.sendBroadcastMessage("§8➢ §cZbanował: §e" + banner);
								API.sendBroadcastMessage("§8➢ §cPowód: §e" + message);
								API.sendBroadcastMessage("§8➢ §cDługość bana: §e" + banLength + "§ch");
								API.sendBroadcastMessage("");
								API.sendBroadcastMessage("§8§m------------------------------------");


								tUUID.setBanReason(message.toString());
								tUUID.setBanStartDate(System.currentTimeMillis());
								tUUID.setBanStatus(true);
								tUUID.setBanBanner(banner);
								tUUID.setBanExpireDate(System.currentTimeMillis() + (1000*60*60*banLength));


								if (User.get(args[0]).isOnline()) {
									Player t = Bukkit.getPlayer(args[0]);

									World world = t.getWorld();
									Location loc = t.getLocation();
									world.strikeLightningEffect(loc);
									t.kickPlayer("§4Zostałeś zbanowany na tym serwerze! \n\n §8➢ §cZbanował: §e" + banner + "\n§8➢  §cPowód: §e" + message.toString() + "\n§8➢  §cDługość bana: §e" + banLength + "§ch");
								}
								Users.saveDataUserGeneralOne(tUUID);
								return true;
							} else {
								sender.sendMessage("§6§lInfo: §7Użyj §e/tempban <gracz> <czas-h> <powód>§7.");
								return true;
							}
						} else {
							sender.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7jest już zbanowany.");
							return true;
						}
					} else {
						sender.sendMessage("§c§lBłąd: §7Nie możesz zbanować innego administratora.");
						return true;
					}
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			} else {
				sender.sendMessage("§6§lInfo: §7Użyj §e/tempban <gracz> <czas-h> <powód>§7.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("ban")) {
			String banner = "Administrator";
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.ban")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length >= 2 && args[0].equalsIgnoreCase(p.getName())) {
					p.sendMessage("§c§lBłąd: §7Nie możesz zbanować siebie.");
					return true;
				}
				banner = p.getName();
			}

			if (args.length >= 2) {
				if (UserUtils.getUsers().contains(User.get(args[0]))) {
					User tUUID = User.get(args[0]);
					if (!(tUUID.getRank().equals("Moderator") || tUUID.getRank().equals("Admin"))) {
						if (!tUUID.getBanStatus()) {
							StringBuilder message = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								message.append(args[i] + " ");
							}

							API.sendBroadcastMessage("§8§m------------------------------------");
							API.sendBroadcastMessage("");
							API.sendBroadcastMessage("  §4Gracz §c" + tUUID.getName() + "§4 został zbanowany!");
							API.sendBroadcastMessage("");
							API.sendBroadcastMessage("§8➢ §cZbanował: §e" + banner);
							API.sendBroadcastMessage("§8➢ §cPowód: §e" + message);
							API.sendBroadcastMessage("§8➢ §cDługość bana: §eNa zawsze");
							API.sendBroadcastMessage("");
							API.sendBroadcastMessage("§8§m------------------------------------");



							tUUID.setBanReason(message.toString());
							tUUID.setBanStartDate(System.currentTimeMillis());
							tUUID.setBanStatus(true);
							tUUID.setBanBanner(banner);
							tUUID.setBanExpireDate(-1);


							if (User.get(args[0]).isOnline()) {
								Player t = Bukkit.getPlayer(args[0]);

								World world = t.getWorld();
								Location loc = t.getLocation();
								world.strikeLightningEffect(loc);
								t.kickPlayer("§4Zostałeś zbanowany na tym serwerze! \n\n §8➢ §cZbanował: §e" + banner + "\n§8➢  §cPowód: §e" + message.toString() + "\n§8➢  §cDługość bana: §eNa zawsze");
							}
							Users.saveDataUserGeneralOne(tUUID);
							return true;
						} else {
							sender.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7jest już zbanowany.");
							return true;
						}
					} else {
						sender.sendMessage("§c§lBłąd: §7Nie możesz zbanować innego administratora.");
						return true;
					}
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			} else {
				sender.sendMessage("§6§lInfo: §7Użyj §e/ban <gracz> <powód>§7.");
				return true;
			}
		}
		
		
		if (cmd.getName().equalsIgnoreCase("unban")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!p.hasPermission("vert.ban")) {
					p.sendMessage("§c§lBłąd: §7Nieznana komenda lub nie masz do niej uprawnień!");
					return true;
				}
				if (args.length == 1 && args[0].equalsIgnoreCase(p.getName())) {
					p.sendMessage("§c§lBłąd: §7Nie możesz siebie odbanować.");
					return true;
				}
			}

			if (args.length == 1) {
				if (UserUtils.getUsers().contains(User.get(args[0]))) {
					User tUUID = User.get(args[0]);
					if (tUUID.getBanStatus() == true) {
						Bukkit.broadcastMessage("§4Gracz §c" + tUUID.getName() + " §4został odblokowany!");
						tUUID.setBanReason("Brak");
						tUUID.setBanStartDate(0);
						tUUID.setBanExpireDate(0);
						tUUID.setBanStatus(false);
						tUUID.setBanBanner("Brak");
						Users.saveDataUserGeneralOne(tUUID);
						return true;

					} else {
						sender.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7nie jest zbanowany.");
						return true;
					}
				} else {
					sender.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
					return true;
				}
			} else {
				sender.sendMessage("§6§lInfo: §7Użyj §e/unban <gracz>§7.");
				return true;
			}
		}
		
		
		
		if (cmd.getName().equalsIgnoreCase("banlist")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.ban")) {
					Inventory invBanned = Bukkit.createInventory(null, 54, "§8Zbanowani gracze");
					p.openInventory(invBanned);
					openInventoryBanned(invBanned, p, 0);
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
		
		
		if (cmd.getName().equalsIgnoreCase("checkban")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("vert.ban")) {
					if (args.length == 1) {
						if (UserUtils.getUsers().contains(User.get(args[0]))) {
							User tUUID = User.get(args[0]);
							if (tUUID.getBanStatus() == true) {
								for (String s : getBanMessage(tUUID)) {
									p.sendMessage(s);
								}
								return true;
							} else {
								p.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7nie jest zbanowany!");
								return true;
							}
						} else {
							p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
							return true;
						}
					} else {
						p.sendMessage("§6§lInfo: §7Użyj §e/checkban <gracz>§7.");
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









	private static List<String> getBanMessage(User u) {
		String status = "";
		String status2 = "";
		List<String> message = new ArrayList<>();

		ArrayList<User> allBannedPlayers = new ArrayList<User>();
		for (User user : UserUtils.getUsers()) {
			if (user.getBanStatus() == true) {
				allBannedPlayers.add(user);

			}
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		status = (format.format(u.getBanStartDate()));

		if (u.getBanExpireDate() == -1) status2 = "Na zawsze";
		if (u.getBanExpireDate() >= 1) status2 = (format.format(u.getBanExpireDate()));

		message.add("§8§m------------------------------------");
		message.add("");
		message.add("§8➢ §cIndywidualny numer: §e" + allBannedPlayers.indexOf(u));
		message.add("§8➢ §cData zbanowania: §e" + status);
		message.add("§8➢ §cZbanował: §e" + u.getBanBanner());
		message.add("§8➢ §cPowód: §e" + u.getBanReason());
		message.add("§8➢ §cDługość bana: §e" + status2);
		message.add("");
		message.add("§8§m------------------------------------");

		return message;
	}











	public static void openInventoryBanned(Inventory inv, Player p, Integer strona) {
		String status = new String();
		String status2 = new String();
		List<ItemStack> items = new ArrayList<>();
		int count = 0;

		for (User u : UserUtils.getUsers()) {
			if (u.getBanStatus() == true) {

				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setDisplayName("§c" + u.getName());
				meta.setLore(getBanMessage(u));
				meta.setOwner(u.getName());
				item.setItemMeta(meta);

				items.add(item);


				count ++;
			}
		}

		int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

		Integer pageCurrent = strona + 1;
		InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);


	}








	public static void openInventoryUnBan(Inventory inv, Player p, String name) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 5);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§8§m----[§a§l ODBANUJ §8§m]----");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§8➢ §6Nick: §7" + name);
		lore.add("");
		lore.add("§8[Kliknij aby odbanować]");
		meta.setLore(lore);
		item.setItemMeta(meta);

		inv.setItem(12, item);

		ItemStack item2 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 2);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.setDisplayName("§8§m----[§c§l ANULUJ §8§m]----");
		item2.setItemMeta(meta2);

		inv.setItem(14, item2);

	}


}