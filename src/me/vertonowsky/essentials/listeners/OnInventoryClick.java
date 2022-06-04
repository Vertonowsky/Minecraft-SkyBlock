package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.Glow;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.enums.JobType;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.essentials.commands.CommandBan;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.inventory.*;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import me.vertonowsky.user.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OnInventoryClick implements Listener {

	private static final File marketFolder = new File(Main.getInst().getDataFolder(), "market");

	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory i = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		User pUUID = User.get(p.getName());
		if (i == null) return;

		if (i instanceof AnvilInventory && pUUID.getTotalLevel() >= 27) {
			AnvilInventory anvilInventory = (AnvilInventory) i;
			InventoryView view = e.getView();
			int rawSlot = e.getRawSlot();
			if (rawSlot == view.convertSlot(rawSlot) && rawSlot == 2) {
				ItemStack[] items = anvilInventory.getContents();
				ItemStack item1 = items[0];  //slot1
				ItemStack item2 = items[1];  //slot2

				if (item1 != null && item2 != null) {
					if (item1.getType().equals(Material.DIAMOND_PICKAXE) && item2.getType().equals(Material.DIAMOND) || item2.getType().equals(Material.DIAMOND_PICKAXE)) {
						ItemStack item3 = e.getCurrentItem();  //slot2
						if (item3 != null && item3.getType() == Material.DIAMOND_PICKAXE) {
							ItemMeta meta3 = item3.getItemMeta();
							if (meta3 instanceof Repairable) {
								Repairable repairable = (Repairable) meta3;
								int repairCost = repairable.getRepairCost();
								if (p.getLevel() >= repairCost && pUUID.getZadanie(12).equals(QuestState.W_TRAKCIE)) {
									Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, 12, QuestState.WYKONANE));
								}
							}
						}
					}
				}
			}
		}


		if (i.getName().equals("§8Zbanowani gracze")) {
			if (e.getRawSlot() > 53 || e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null ) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {
								i.clear();
								CommandBan.openInventoryBanned(i, p, getPageNumberFromText(itemName));
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
								return;
							}
						}

						else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§c")) {
							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							String itemName2 = ChatColor.stripColor(itemName);

							p.closeInventory();
							Inventory invBanned = Bukkit.createInventory(null, 27, "§8Odbanuj gracza");
							p.openInventory(invBanned);
							CommandBan.openInventoryUnBan(invBanned, p, itemName2);
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							return;
						}
					}
				}
			}
		}


		if (i.getName().equals("§8Odbanuj gracza")) {
			if (e.getRawSlot() > 26 || e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 2) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§c§l ANULUJ §8§m]----")) {
							p.closeInventory();
							p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
							return;
						}
					}
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 5) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§a§l ODBANUJ §8§m]----")) {

							String itemName = e.getCurrentItem().getItemMeta().getLore().get(1);
							String itemName2 = ChatColor.stripColor(itemName);
							itemName2 = itemName2.replaceAll("➢ Nick: ", "");

							if (UserUtils.getUsers().contains(User.get(itemName2))) {
								User tUUID = User.get(itemName2);
								if (tUUID.getBanStatus() == true) {
									tUUID.setBanReason("Brak");
									tUUID.setBanStartDate(0);
									tUUID.setBanExpireDate(0);
									tUUID.setBanStatus(false);
									tUUID.setBanBanner("Brak");
									Bukkit.broadcastMessage("§4Gracz §c" + tUUID.getName() + " §4został odblokowany!");
									p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
									p.closeInventory();
									return;
								} else {
									p.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7nie jest zbanowany.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							} else {
								p.sendMessage("§c§lBłąd: §7Niewłaściwy nick lub gracz nie jest online.");
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
								return;
							}

						}
					}
				}
			}
		}


		if (i.getName().equals("§8Skup")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {

								i.clear();
								CitizenSkup.openInventorySkup(i, p, getPageNumberFromText(itemName));
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
								return;

							}
						}
					}

					if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§8§m-----")) {
						p.closeInventory();

						Inventory inv = Bukkit.createInventory(null, 45, "§8Sprzedaj przedmiot");
						p.openInventory(inv);
						CitizenSkup.openInventoryFinalPurchase(inv, p, e.getCurrentItem(), true);
						p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
						return;
					}
				}
			}
		}

		if (i.getName().equals("§8Bloki") || i.getName().equals("§8Inne") || i.getName().equals("§8Spawnery")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§8§m-----")) {
						ItemStack item = e.getCurrentItem();
						/*
						if (item.getType().equals(Material.NETHER_STAR)) {
							if (!p.hasPermission("vert.spawn.shop")) {
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
								p.sendMessage("§8§l>> §cDostęp do tego przedmiotu ma tylko ranga §fVIP §club §fSuperVIP§c!");
								return;
							}
						}
						 */
						if (item.getType().equals(Material.NETHER_STAR)) {
							if (pUUID.getTotalLevel() < 65) {
								p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
								p.sendMessage("§8§l>> §cAby zakupić ten przedmiot musisz osiągnąć §f65 §cpoziom!");
								return;
							}
						}
						p.closeInventory();

						Inventory inv = Bukkit.createInventory(null, 45, "§8Kup przedmiot");
						p.openInventory(inv);
						CitizenSkup.openInventoryFinalPurchase(inv, p, e.getCurrentItem(), false);
						p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
						return;
					}
				}
			}
		}

		if (i.getName().equals("§8Sprzedaj przedmiot") || i.getName().equals("§8Kup przedmiot")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					boolean sell = true;
					if (i.getName().equals("§8Kup przedmiot")) sell = false;

					if (e.getCurrentItem().getType() == Material.STAINED_CLAY) {
						if (e.getCurrentItem().getDurability() == 5 || e.getCurrentItem().getDurability() == 14) {
							boolean adding = true;
							if (e.getCurrentItem().getDurability() == 14) adding = false;

							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							ItemStack itemToSell = i.getItem(4);
							ItemStack sellItem = i.getItem(22);

							int amount = 0;
							int staticAmount = 0;
							double staticPrice = 0;

							if (itemName.equals("§8§m--§a [+1] §8§m--") || itemName.equals("§8§m--§c [-1] §8§m--")) amount = 1;
							if (itemName.equals("§8§m--§a [+16] §8§m--") || itemName.equals("§8§m--§c [-16] §8§m--")) amount = 16;
							if (itemName.equals("§8§m--§a [+64] §8§m--") || itemName.equals("§8§m--§c [-64] §8§m--")) amount = 64;
							if (itemName.contains("§8§m--§a WSZYSTKO [+") || itemName.contains("§8§m--§c WSZYSTKO [-")) {
								String itemName2 = ChatColor.stripColor(itemName);
								itemName2 = itemName2.replaceAll("-- WSZYSTKO \\[", "");
								itemName2 = itemName2.replaceAll("-", "");
								itemName2 = itemName2.replaceAll("\\+", "");
								itemName2 = itemName2.replaceAll("\\]", "");
								itemName2 = itemName2.replaceAll(" ", "");
								amount = Integer.parseInt(itemName2);
							}


							String line2 = sellItem.getItemMeta().getLore().get(1);
							String line22 = ChatColor.stripColor(line2);
							line22 = line22.replaceAll("➢ Ilość: ", "");
							staticAmount = Integer.parseInt(line22);
							if (adding) staticAmount += amount;
							if (!adding) staticAmount -= amount;


							String line3 = itemToSell.getItemMeta().getLore().get(1);
							String line33 = ChatColor.stripColor(line3);
							line33 = line33.replaceAll("➢  1 szt.  - \\$", "");
							staticPrice = Double.parseDouble(line33);
							staticPrice = staticPrice*staticAmount;


							if (!adding) {
								if (staticAmount + amount < amount) {
									p.sendMessage("§c§lBłąd: §7Suma nie może być mniejsza od §f0§7. Wybrano: §8[§c-" + amount + "§8]§7.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							}

							if (sell) {
								if (adding) {
									int itemsInEq = InventoryAPI.getItemAmountInInventory(p, itemToSell, false);
									if (staticAmount > itemsInEq) {
										p.sendMessage("§c§lBłąd: §7Nie posiadasz przy sobie tylu przedmiotów! §8[§c" + staticAmount + "§8]§7.");
										p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
								}
							}

							if (!sell) {
								if (pUUID.getMoney() < staticPrice) {
									p.sendMessage("§c§lBłąd: §7Nie posiadasz tyle pieniędzy! §8[§c$" + staticPrice + "§8]§7.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}

								if (InventoryAPI.getSpaceForItemInInventory(p, itemToSell, false) < staticAmount) {
									p.sendMessage("§c§lBłąd: §7Brak wolnego miejsca w ekwipunku! §8[§c" + staticAmount + "§8]§7.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							}



							double priceRounded = new BigDecimal(staticPrice + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
							ItemStack item = sellItem;
							ItemMeta meta = item.getItemMeta();
							List<String> lore = meta.getLore();
							lore.set(1, "§8➢ §6Ilość: §7" + staticAmount);
							lore.set(2, "§8➢ §6Wartość: §7$" + priceRounded);
							meta.setLore(lore);
							item.setItemMeta(meta);
							Glow.addGlow(item);
							i.setItem(22, item);


							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
							return;
						}
					}

					if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 2) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§c§l ANULUJ §8§m]----")) {
							p.closeInventory();
							Inventory inv = Bukkit.createInventory(null, 45, i.getName());
							p.openInventory(inv);
							CitizenSklep.openInventorySklep(inv, pUUID, "§6§l" + ChatColor.stripColor(i.getName()));
							p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1, 1);
							return;
						}
					}

					if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 5) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§a§l SPRZEDAJ §8§m]----") || e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§a§l KUP §8§m]----")) {
							String line1 = e.getCurrentItem().getItemMeta().getLore().get(1);
							String line11 = ChatColor.stripColor(line1);
							line11 = line11.replaceAll("➢ Ilość: ", "");
							int staticAmount = Integer.parseInt(line11);


							String line2 = e.getCurrentItem().getItemMeta().getLore().get(2);
							String line22 = ChatColor.stripColor(line2);
							line22 = line22.replaceAll("➢ Wartość: \\$", "");
							double staticPrice = Double.parseDouble(line22);

							ItemStack itemSlot4 = i.getItem(4);
							short durability = itemSlot4.getDurability();

							if (itemSlot4.getType().equals(Material.MONSTER_EGG)) {
								if (itemSlot4.getItemMeta() != null && itemSlot4.getItemMeta().getDisplayName() != null) {
									String displayName = itemSlot4.getItemMeta().getDisplayName();
									if (displayName.contains("kura")) {
										durability = 93;
									}
									if (displayName.contains("owca")) {
										durability = 91;
									}
									if (displayName.contains("świnia")) {
										durability = 90;
									}
									if (displayName.contains("krowa")) {
										durability = 92;
										if (displayName.contains("grzybowa")) {
											durability = 96;
										}
									}
									if (displayName.contains("koń")) {
										durability = 100;
									}
									if (displayName.contains("wilk")) {
										durability = 95;
									}
								}
							}

							ItemStack itemToSell = new ItemStack(itemSlot4.getType(), 1, durability);

							if (sell) {
								if (staticAmount > 0) {
									if (InventoryAPI.getItemAmountInInventory(p, itemToSell, false) >= staticAmount) {
										pUUID.setMoney(pUUID.getMoney() + staticPrice);

										InventoryAPI.removeItemFromInventory(p, itemToSell, staticAmount);
										double priceRounded = new BigDecimal(staticPrice + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
										p.sendMessage("§a§lSukces: §7Sprzedano §f" + staticAmount + "x" + itemToSell.getType().toString() + "§7. [§a+$" + priceRounded + "§7].");


										p.closeInventory();
										Inventory inv = Bukkit.createInventory(null, 54, "§8Skup");
										p.openInventory(inv);
										CitizenSkup.openInventorySkup(inv, p, 0);
										p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
										Scoreboard.setScoreboard(p);
										return;
									} else {
										p.sendMessage("§c§lBłąd: §7Nie posiadasz przy sobie tylu przedmiotów! §8[§c" + staticAmount + "§8]§7.");
										p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
								} else {
									p.sendMessage("§c§lBłąd: §7Wybierz ilość przedmiotów jaką chcesz sprzedać.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							}

							if (!sell) {
								if (staticAmount > 0) {
									if (InventoryAPI.getSpaceForItemInInventory(p, itemToSell, false) >= staticAmount) {
										if (pUUID.getMoney() >= staticPrice) {
											pUUID.setMoney(pUUID.getMoney() - staticPrice);

											InventoryAPI.addItemToInventory(p, itemToSell, staticAmount);
											double priceRounded = new BigDecimal(staticPrice + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
											p.sendMessage("§a§lSukces: §7Kupiono §f" + staticAmount + "x" + itemToSell.getType().toString() + "§7. [§c-$" + priceRounded + "§7].");

											p.closeInventory();
											p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
											Scoreboard.setScoreboard(p);
											return;
										} else {
											p.sendMessage("§c§lBłąd: §7Nie posiadasz tyle pieniędzy! §8[§c$" + staticPrice + "§8]§7.");
											p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
											return;
										}
									} else {
										p.sendMessage("§c§lBłąd: §7Brak wolnego miejsca w ekwipunku! §8[§c" + staticAmount + "§8]§7.");
										p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
								} else {
									p.sendMessage("§c§lBłąd: §7Wybierz ilość przedmiotów jaką chcesz kupić.");
									p.getWorld().playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							}
						}
					}

				}
			}
		}

		if (i.getName().equals("§8TOP graczy")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {

								i.clear();
								TopPlayers.openInventoryTopPlayers(i, p, getPageNumberFromText(itemName));
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
								return;

							}
						}
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.contains("§e#")) {
							p.closeInventory();
							itemName = ChatColor.stripColor(itemName);
							String[] playerName = itemName.split(" ");

							Bukkit.getServer().dispatchCommand(p, "is warp " + playerName[1]);
							return;
						}
					}
				}
			}
		}

		if (i.getName().equals("§8Zadania")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
						if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
							String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
							if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {

								i.clear();
								Zadania.openInventoryZadania(i, p, getPageNumberFromText(itemName));
								p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
								return;

							}
						}
					}
					if (e.getCurrentItem().getType() == Material.BOOK) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.contains("W trakcie")) {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
							return;
						}
					}

					if (e.getCurrentItem().getType() == Material.BOOK_AND_QUILL) {
						String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
						Integer questId = Integer.parseInt(itemName.replaceAll("#", ""));

						Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(pUUID, questId, QuestState.ZAKONCZONE));

						i.clear();
						Zadania.openInventoryZadania(i, p, 0);
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
						return;
					}
				}
			}
		}

		if (i.getName().equals("§8Pomoc - Problemy")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.PAPER) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.equals("§8§l>> §cJak rozpoczac grę?")) {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							p.closeInventory();

							Inventory inv = Bukkit.createInventory(null, 54, "§8Jak zaczac?");
							p.openInventory(inv);
							Help.openInventoryHelp(true, inv, p, 0);
							return;
						}
					}

					if (e.getCurrentItem().getType() == Material.BARRIER) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.equals("§8§l>> §cMam problem..")) {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							p.closeInventory();

							Inventory inv = Bukkit.createInventory(null, 54, "§8Mam problem");
							p.openInventory(inv);
							Help.openInventoryHelp(false, inv, p, 0);
							return;
						}
					}
				}
			}
		}

		if (i.getName().equals("§8Jak zaczac?") || i.getName().equals("§8Mam problem")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					if (e.getCurrentItem().getType() == Material.BOOK) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.equals("§8[§bCofnij§8]")) {
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							p.closeInventory();
							Inventory inv = Bukkit.createInventory(null, 27, "§8Pomoc - Problemy");
							p.openInventory(inv);
							Help.openInventoryCategoryHelp(inv, p);
							return;
						}
					}
				}
			}
		}

		if (i.getName().contains("§8Zdolnosci ")) {
			if (e.getCurrentItem() == null) return;

			e.setCancelled(true);
			if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.MIDDLE || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
				if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
					Material type = e.getCurrentItem().getType();
					User u = User.get(ChatColor.stripColor(i.getName().replaceAll("§8Zdolnosci ", "")));
					if (type.equals(Material.CHEST)) {
						if (e.getCurrentItem().getItemMeta().getDisplayName() != null) {
							Roles.openInventoryRolesInventory(i, u);
							p.playSound(p.getLocation(), Sound.ENTITY_LLAMA_CHEST, 1, 1);
							return;
						}
					}
					if (type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.DIAMOND_AXE) || type.equals(Material.DIAMOND_SWORD) || type.equals(Material.FISHING_ROD)) {

						new BukkitRunnable() {
							int ticks = 0;

							public void run() {
								if (ticks == 10) {
									ItemStack item = new ItemStack(Material.CHEST, 1);
									ItemMeta meta = item.getItemMeta();
									List<String> lore = new ArrayList<>();
									lore.add("");
									lore.add("   §8§m-------------------------");
									lore.add("");

									if (type.equals(Material.DIAMOND_PICKAXE)) {
										meta.setDisplayName("§8§l>> §cDrop z kamienia");

										double bonusChance = (double) (u.getLevel(JobType.MINER)-1) / 100;
										for (Map.Entry<ItemStack, Double> entry : OnBlockBreak.getStoneDrops().entrySet()) {
											double valueRounded = new BigDecimal((entry.getValue() + bonusChance) + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
											lore.add("       §8➢ §7" + getItemName(entry.getKey()) + ": §a" + valueRounded + "%       ");
										}
									}

									if (type.equals(Material.DIAMOND_AXE)) {
										meta.setDisplayName("§8§l>> §cPodwójny drop");

										double bonusChance = (double) ((u.getLevel(JobType.LUMBERJACK) / 25) + 2.0);
										double valueRounded = new BigDecimal(bonusChance + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
										lore.add("       §8➢ §7Podwójny drop: §a" + valueRounded + "%             ");
									}


									if (type.equals(Material.DIAMOND_SWORD)) {
										HashMap<ItemStack, Double> drops = new HashMap<>();
										Random random = new Random();
										String name = "Błąd";
										int randomInt = random.nextInt(6);

										if (randomInt == 0) {
											drops.putAll(OnEntityDeath.getCreeperDrops());
											name = "z creeper'a";
										}
										else if (randomInt == 1) {
											drops.putAll(OnEntityDeath.getSkeletonDrops());
											name = "ze szkieleta";
										}
										else if (randomInt == 2) {
											drops.putAll(OnEntityDeath.getZombieDrops());
											name = "z zombie";
										}
										else if (randomInt == 3) {
											drops.putAll(OnEntityDeath.getSpiderDrops());
											name = "z pająka";
										}
										else if (randomInt == 4) {
											drops.putAll(OnEntityDeath.getPigmanDrops());
											name = "z pigmana";
										}
										else if (randomInt == 5) {
											drops.putAll(OnEntityDeath.getEndermanDrops());
											name = "z endermana";
										}

										meta.setDisplayName("§8§l>> §cDrop " + name);

										double bonusChance = (double) u.getLevel(JobType.WARRIOR) / 80;
										for (Map.Entry<ItemStack, Double> entry : drops.entrySet()) {
											double valueRounded = new BigDecimal((entry.getValue() + bonusChance) + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
											lore.add("       §8➢ §7" + getItemName(entry.getKey()) + ": §a" + valueRounded + "%       ");
										}
									}


									if (type.equals(Material.FISHING_ROD)) {
										meta.setDisplayName("§8§l>> §cPodwójny drop");

										double bonusChance = (double) ((u.getLevel(JobType.ANGLER) / 50) + 5.0);
										double valueRounded = new BigDecimal(bonusChance + "").setScale(2, RoundingMode.HALF_UP).doubleValue();
										lore.add("       §8➢ §7Podwójny drop: §a" + valueRounded + "%             ");
									}

									lore.add("");
									lore.add("   §8§m-------------------------");
									lore.add("   §8§8[Kliknij aby cofnąć]");
									meta.setLore(lore);
									item.setItemMeta(meta);

									i.setItem(e.getRawSlot(), item);

									p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 1);
									cancel();
									return;
								}
								ticks++;

								ItemStack item = InventoryAPI.getRandomGlassColor();
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§8§l>> " + ChatColor.getByChar(Integer.toHexString(item.getDurability())) + "Ladowanie §8§l<<");
								item.setItemMeta(meta);
								i.setItem(e.getRawSlot(), item);

								p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
							}
						}.runTaskTimer(Main.getInst(), 0, 2);
					}
				}
			}
		}


		if (i.getName().contains("§8[Market online]")) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getType() == null) return;

			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
				if (e.getRawSlot() < 0 || e.getRawSlot() > 44) return;

				if (e.getCurrentItem().getDurability() == 3 || e.getCurrentItem().getDurability() == 4) {
					if (e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.contains("§8§l>>")) {
							itemName = ChatColor.stripColor(itemName.replaceAll(">> ", ""));

							p.closeInventory();
							p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
							Inventory inv = Bukkit.createInventory(null, 54, "§8Market " + itemName);
							p.openInventory(inv);
							Market.openInventoryPlayerMarket(inv, User.get(itemName), 0, pUUID);
							return;
						}
					}
				}


				if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
					String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
					if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {

						i.clear();
						Market.openInventoryGlobalMarket(i, getPageNumberFromText(itemName));
						p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
						return;

					}
				}
			}

			if (e.getCurrentItem().getType() == Material.DIAMOND) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§b Twój sklep §8§m]----")) {
					User tUUID = User.get(p.getName());
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1 ,1);
					Inventory inv = Bukkit.createInventory(null, 54, "§8Market " + p.getName());
					p.openInventory(inv);
					Market.openInventoryPlayerMarket(inv, tUUID, 0, tUUID);
					return;
				}
			}
		}

		if (i.getName().contains("§8Market ")) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null || e.getCurrentItem().getType() == null) return;

			e.setCancelled(true);
			if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) return;

			if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT) || e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.MIDDLE)) {
				if (e.getRawSlot() >= 0 && e.getRawSlot() <= 44) {
					String invName = i.getName();
					invName = ChatColor.stripColor(invName.replaceAll("Market ", ""));

					User tUUID = User.get(invName);
					File file = new File(marketFolder, tUUID.getName() + "@" + tUUID.getUuid().toString() + ".yml");
					YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

					for (String s : yaml.getKeys(false)) {
						List<String> itemLore = e.getCurrentItem().getItemMeta().getLore();
						DateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
						String createDate = ChatColor.stripColor(itemLore.get(itemLore.size() - 6));
						createDate = createDate.replaceAll(" ", "");
						createDate = createDate.replaceAll("-", "");
						createDate = createDate.replaceAll(":", "");
						createDate = createDate.replaceAll(">>Wystawiono", "");

						String createDate2 = format.format(yaml.getLong(s + ".startDate"));
						createDate2 = createDate2.replaceAll(" ", "");
						createDate2 = createDate2.replaceAll("-", "");
						createDate2 = createDate2.replaceAll(":", "");


						if (Long.parseLong(createDate) == Long.parseLong(createDate2)) {
							if (invName.equals(p.getName())) {
								if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {

									if (e.getCurrentItem().getType() != Material.getMaterial(yaml.getString(s + ".type"))) return;
									if (e.getCurrentItem().getAmount() != Integer.parseInt(yaml.getString(s + ".amount"))) return;
									if (e.getCurrentItem().getDurability() != Short.parseShort(yaml.getString(s + ".data"))) return;

									int amount = Integer.parseInt(yaml.getString(s + ".amount"));
									int totalAmount = Integer.parseInt(yaml.getString(s + ".totalAmount"));
									if (totalAmount >= amount) {
										ItemStack is = Market.YamlToItem(s, yaml);
										if (InventoryAPI.getSpaceForItemInInventory(p, is, true) >= amount) {
											if (totalAmount == amount)
												yaml.set(s, null);
											else
												yaml.set(s + ".totalAmount", totalAmount - amount);

											pUUID.giveItem(is, true);
											try {
												yaml.save(file);
											} catch (IOException uihce) {
												uihce.printStackTrace();
											}
											i.clear();
											Market.openInventoryPlayerMarket(i, tUUID, 0, tUUID);
											p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
											p.sendMessage("§a§lSukces: §7Usunięto przedmiot ze sklepu!");
											return;
										} else {
											p.sendMessage("§c§lBład: §7Nie masz wolnego miejsca w ekwipunku!");
											p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
											return;
										}
									} else {
										p.sendMessage("§c§lBład: §7Wystąpił problem. Odśwież sklep!");
										p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
								}
								if (e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.MIDDLE)) {
									if (e.getCurrentItem().getType() != Material.getMaterial(yaml.getString(s + ".type"))) return;
									if (e.getCurrentItem().getAmount() != Integer.parseInt(yaml.getString(s + ".amount"))) return;
									if (e.getCurrentItem().getDurability() != Short.parseShort(yaml.getString(s + ".data"))) return;

									ItemStack is = Market.YamlToItem(s, yaml);
									int amount = Integer.parseInt(yaml.getString(s + ".amount"));
									int totalAmount = Integer.parseInt(yaml.getString(s + ".totalAmount"));
									if (totalAmount > 25000) {
										p.sendMessage("§c§lBłąd: §7Osiągnięto limit przedmiotów w tej aukcji!");
										p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
									if (InventoryAPI.getItemAmountInInventory(p, is, true) >= amount) {
										InventoryAPI.removeItemFromInventory(p, is, amount);
										yaml.set(s + ".totalAmount", Integer.parseInt(yaml.getString(s + ".totalAmount")) + amount);

										try {
											yaml.save(file);
										} catch (IOException uihce) {
											uihce.printStackTrace();
										}

										i.clear();
										Market.openInventoryPlayerMarket(i, tUUID, 0, tUUID);
										p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
										p.sendMessage("§a§lSukces: §7Dodano przedmiot do sklepu!");
										return;

									} else {
										p.sendMessage("§c§lBłąd: §7Nie posiadasz §f" + is.getAmount() + "x" + is.getType().toString() + "§7!");
										p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}
								}
							} else {
								Double cena = yaml.getDouble(s + ".price");
								if (pUUID.getMoney() >= cena) {
									if (e.getCurrentItem().getType() != Material.getMaterial(yaml.getString(s + ".type"))) return;
									if (e.getCurrentItem().getAmount() != Integer.parseInt(yaml.getString(s + ".amount"))) return;
									if (e.getCurrentItem().getDurability() != Short.parseShort(yaml.getString(s + ".data"))) return;

									int amount = Integer.parseInt(yaml.getString(s + ".amount"));
									int totalAmount = Integer.parseInt(yaml.getString(s + ".totalAmount"));
									if (totalAmount >= amount) {
										ItemStack is = Market.YamlToItem(s, yaml);

										if (InventoryAPI.getSpaceForItemInInventory(p, is, true) >= amount) {
											InventoryAPI.addItemToInventory(p, is, amount);
											if (totalAmount == amount) {
												yaml.set(s, null);
											} else {
												yaml.set(s + ".totalAmount", totalAmount - amount);
											}

											try {
												yaml.save(file);
											} catch (IOException uihce) {
												uihce.printStackTrace();
											}

											pUUID.setMoney(pUUID.getMoney() - cena);
											tUUID.setMoney(tUUID.getMoney() + cena);
											i.clear();
											Market.openInventoryPlayerMarket(i, tUUID, 0, pUUID);
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

											p.sendMessage("§a§lSukces: §7Zakupiono przedmiot u gracza §f" + tUUID.getName() + "§7. [§c-$" + cena + "§7].");
											Scoreboard.setScoreboard(p);

											if (tUUID.isOnline() == true) {
												Player t = Bukkit.getPlayer(invName);
												t.sendMessage("§a§lSukces: §7Sprzedano przedmiot w markecie!§7 [§a+$" + cena + "§7].");
												t.playSound(t.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
												Scoreboard.setScoreboard(t);
											}
											return;
										} else {
											p.sendMessage("§c§lBład: §7Nie masz wolnego miejsca w ekwipunku!");
											p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
											return;
										}
									} else {
										p.sendMessage("§c§lBład: §7Wystąpił problem. Odśwież sklep!");
										p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
										return;
									}

								} else {
									p.sendMessage("§c§lBłąd: §7Nie posiadasz tyle pieniędzy! §8[§c$" + cena + "§8]§7.");
									p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
									return;
								}
							}
						}
					}
					p.sendMessage("§c§lBłąd: §7Gracz §f" + tUUID.getName() + " §7nie posiada już tego przedmiotu!");
					p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
					return;

				}

				if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
					if (e.getCurrentItem().getDurability() == 0 || e.getCurrentItem().getDurability() == 1) {
						String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
						if (itemName.contains("§8§m---§b Poprzednia strona") || itemName.contains("§8§m---§b Następna strona")) {

							i.clear();
							if (User.get(itemName) != null) Market.openInventoryPlayerMarket(i, User.get(itemName), getPageNumberFromText(itemName), pUUID);
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_METAL_PRESSUREPLATE_CLICK_ON, 1, 1);
							return;

						}
					}
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§8§m----[§b Market online §8§m]----")) {
						p.closeInventory();
						p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
						Inventory inv = Bukkit.createInventory(null, 54, "§8[Market online]");
						p.openInventory(inv);
						Market.openInventoryGlobalMarket(inv, 0);
						return;
					}
				}
			}
		}
	}

	public static String getItemName(ItemStack item) {
		String itemName = item.getType().toString();
		if (item.getType().equals((Material.IRON_ORE)))
			itemName = "Ruda żelaza";
		if (item.getType().equals((Material.GOLD_ORE)))
			itemName = "Ruda złota";
		if (item.getType().equals((Material.COAL_ORE)))
			itemName = "Ruda węgla";
		if (item.getType().equals((Material.REDSTONE_ORE)))
			itemName = "Ruda redstone";
		if (item.getType().equals((Material.LAPIS_ORE)))
			itemName = "Ruda lazurytu";
		if (item.getType().equals((Material.QUARTZ_ORE)))
			itemName = "Ruda kwarcu";
		if (item.getType().equals((Material.DIAMOND_ORE)))
			itemName = "Ruda diamentu";
		if (item.getType().equals((Material.STONE)))
			itemName = "Kamień";
		if (item.getType().equals((Material.GRAVEL)))
			itemName = "Zwir";
		if (item.getType().equals((Material.CACTUS)))
			itemName = "Kaktus";
		if (item.getType().equals((Material.SUGAR_CANE)))
			itemName = "Trzcina cukrowa";
		if (item.getType().equals((Material.VINE)))
			itemName = "Pnącza";
		if (item.getType().equals((Material.INK_SACK)))
			if (item.getDurability() == 3) itemName = "Kakao";
		if (item.getType().equals((Material.PACKED_ICE)))
			itemName = "Zbity lód";
		if (item.getType().equals((Material.CLAY_BALL)))
			itemName = "Glina";
		if (item.getType().equals((Material.CLAY_BRICK)))
			itemName = "Cegła";
		if (item.getType().equals((Material.WEB)))
			itemName = "Pajęczyna";
		if (item.getType().equals((Material.MYCEL)))
			itemName = "Grzybnia";
		if (item.getType().equals((Material.SAND)))
			itemName = "Piasek";
		if (item.getType().equals((Material.MELON_SEEDS)))
			itemName = "Nasiona arbuza";
		if (item.getType().equals((Material.PUMPKIN_SEEDS)))
			itemName = "Nasiona dyni";
		if (item.getType().equals((Material.BEETROOT)))
			itemName = "Burak";
		if (item.getType().equals((Material.POTATO)))
			itemName = "Ziemniak";
		if (item.getType().equals((Material.CARROT)))
			itemName = "Marchewka";
		if (item.getType().equals((Material.IRON_INGOT)))
			itemName = "Sztabka żelaza";
		if (item.getType().equals((Material.GOLD_INGOT)))
			itemName = "Sztabka złota";
		if (item.getType().equals((Material.GOLD_NUGGET)))
			itemName = "Samorodek złota";
		if (item.getType().equals((Material.DIAMOND)))
			itemName = "Diament";
		if (item.getType().equals((Material.SOUL_SAND)))
			itemName = "Piasek dusz";
		if (item.getType().equals((Material.NETHERRACK)))
			itemName = "Netherrack";
		if (item.getType().equals((Material.NETHER_BRICK_ITEM)))
			itemName = "Netherowa cegła";
		if (item.getType().equals((Material.NETHER_BRICK)))
			itemName = "Netherowy blok";
		if (item.getType().equals((Material.RED_NETHER_BRICK)))
			itemName = "Czerwony netherowy blok";
		if (item.getType().equals((Material.NETHER_BRICK_STAIRS)))
			itemName = "Netherowe schody";
		if (item.getType().equals((Material.NETHER_STALK)))
			itemName = "Netherowa brodawka";
		if (item.getType().equals((Material.PRISMARINE_SHARD)))
			itemName = "Odłamek pryzmarynu";
		if (item.getType().equals((Material.PRISMARINE_CRYSTALS)))
			itemName = "Kryształy pryzmarynu";
		if (item.getType().equals((Material.MOSSY_COBBLESTONE)))
			itemName = "Zamszony bruk";
		if (item.getType().equals(Material.SMOOTH_BRICK)) {
			if (item.getDurability() == 0) itemName = "Kamienna cagła";
			if (item.getDurability() == 1) itemName = "Zamszona kamienna cagła";
			if (item.getDurability() == 2) itemName = "Popękana kamienna cagła";
			if (item.getDurability() == 3) itemName = "Rzeźbiona kamienna cagła";
		}
		if (item.getType().equals((Material.QUARTZ)))
			itemName = "Kwarc";
		if (item.getType().equals((Material.ENDER_CHEST)))
			itemName = "Skrzynia kresu";
		if (item.getType().equals((Material.BLAZE_ROD)))
			itemName = "Płomienna różdżka";
		if (item.getType().equals(Material.BROWN_MUSHROOM) || item.getType().equals(Material.BROWN_MUSHROOM))
			itemName = "Grzyb";
		return itemName;
	}


	private Integer getPageNumberFromText(String displayName) {
		String itemName2 = ChatColor.stripColor(displayName);
		itemName2 = itemName2.replaceAll("--- Następna strona \\[", "");
		itemName2 = itemName2.replaceAll("--- Poprzednia strona \\[", "");
		itemName2 = itemName2.replaceAll("\\] ---", "");
		int page = Integer.parseInt(itemName2);
		return page -1;
	}

}
