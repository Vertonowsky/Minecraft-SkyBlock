package me.vertonowsky.inventory;

import me.vertonowsky.essentials.commands.CommandMarket;
import me.vertonowsky.main.Main;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Market {

	private static final File marketFolder = new File(Main.getInst().getDataFolder(), "market");



	public static void openInventoryPlayerMarket(Inventory inv, User whosInventoryUser, Integer strona, User whoIsOpening) {
		File fi = new File(marketFolder, whosInventoryUser.getName() + "@" + whosInventoryUser.getUuid().toString() + ".yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(fi);
		List<ItemStack> items = new ArrayList<ItemStack>();

		for (String s : yaml.getKeys(false)) {
			ArrayList<String> lore = new ArrayList<String>();
			SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
			lore.add("");
			lore.add("   §8§m---------------------------------  §7  §7  §7");
			lore.add("");
			lore.add("       §8§l>> §bCena: §a$" + yaml.getString(s + ".price"));
			lore.add("       §8§l>> §bIlość: §a" + yaml.getString(s + ".amount"));
			lore.add("");
			lore.add("       §8§l>> §bWystawiono: §7" + format.format(yaml.getLong(s + ".startDate")));
			lore.add("       §8§l>> §bPozostało: §7" + yaml.getString(s + ".totalAmount"));
			lore.add("");
			lore.add("   §8§m---------------------------------  §7  §7  §7");
			if (whoIsOpening.equals(whosInventoryUser)) {
				lore.add(" §8§l>> §8Kliknij lewy aby dodać więcej przedmiotów!");
				lore.add(" §8§l>> §8Kliknij shift+prawy aby usunąć przedmiot ze sklepu!");
			} else {
				lore.add(" §8§l>> §8Kliknij lewy aby kupić!");
				lore.add("");
			}


			ItemStack is = YamlToItem(s, yaml);
			ItemMeta meta = is.getItemMeta();
			List<String> loreOriginal = new ArrayList<>();
			if (meta.getLore() != null) loreOriginal.addAll(meta.getLore());
			loreOriginal.addAll(lore);

			meta.setLore(loreOriginal);
			is.setItemMeta(meta);
			items.add(is);
		}

		int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

		Integer pageCurrent = strona + 1;
		InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);


		ItemStack item3 = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.setDisplayName("§8§m----[§b Market online §8§m]----");
		item3.setItemMeta(meta3);

		ItemStack item4 = new ItemStack(Material.GHAST_TEAR, 1);
		ItemMeta meta4 = item4.getItemMeta();
		meta4.setDisplayName("§8§m--------[§6 Poradnik §8§m]--------");
		ArrayList<String> lore4 = new ArrayList<String>();
		lore4.add("§8➢ §7Aby wystawić przedmiot w swoim");
		lore4.add("§7sklepie wpisz §e/wystaw <cena>§7.");
		lore4.add("");
		lore4.add("§8➢ §7Aby sprawdzić sklep konkretnego");
		lore4.add("§7gracza wpisz §e/market <gracz>§7.");
		meta4.setLore(lore4);
		item4.setItemMeta(meta4);

		inv.setItem(inv.getSize() -7, item3);
		inv.setItem(inv.getSize() -3, item4);


	}

	public static void openInventoryGlobalMarket(Inventory inv, Integer strona) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		List<ItemStack> itemsVip = new ArrayList<ItemStack>();
		List<ItemStack> itemsGracz = new ArrayList<ItemStack>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			User u = User.get(player.getUniqueId());
			int shopSize = 0;

			File file = new File(marketFolder, player.getName() + "@" + player.getUniqueId().toString() + ".yml");
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			for (String s : yaml.getKeys(false)) {
				shopSize++;
			}


			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setDisplayName("§8§l>> §b" + u.getName());
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("   §8§m---------------------------------  §7  §7  §7");
			lore.add("");
			lore.add("       §8§l>> §bPoziom: §7" + u.getTotalLevel());
			lore.add("");
			lore.add("       §8§l>> §bPrzedmioty: §7" + shopSize + "§8/§7" + CommandMarket.maxItemsInMarket);
			lore.add("");
			lore.add("   §8§m---------------------------------  §7  §7  §7");



			if (u.getRank().equals("Gracz")) {
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				meta.setOwner(u.getName());
				itemsGracz.add(item);
			}

			meta.setLore(lore);
			item.setItemMeta(meta);
			if (!u.getRank().equals("Gracz")) itemsVip.add(item);
		}

		items.addAll(itemsVip);
		items.addAll(itemsGracz);

		int totalPages = InventoryAPI.addInventoryContent(inv, items, strona);

		Integer pageCurrent = strona + 1;
		InventoryAPI.addInventoryNavigation(inv, pageCurrent, totalPages);


		ItemStack item3 = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.setDisplayName("§8§m----[§b Twój sklep §8§m]----");
		item3.setItemMeta(meta3);

		ItemStack item4 = new ItemStack(Material.GHAST_TEAR, 1);
		ItemMeta meta4 = item4.getItemMeta();
		meta4.setDisplayName("§8§m--------[§6 Poradnik §8§m]--------");
		ArrayList<String> lore4 = new ArrayList<String>();
		lore4.add("§8➢ §7Aby wystawić przedmiot w swoim");
		lore4.add("§7sklepie wpisz §e/wystaw <cena>§7.");
		lore4.add("");
		lore4.add("§8➢ §7Aby sprawdzić sklep konkretnego");
		lore4.add("§7gracza wpisz §e/market <gracz>§7.");
		meta4.setLore(lore4);
		item4.setItemMeta(meta4);

		inv.setItem(inv.getSize() -7, item3);
		inv.setItem(inv.getSize() -3, item4);

	}

	public static List<String> enchantsToString(Map<Enchantment, Integer> enchs) {
		List<String> toReturn = new ArrayList<String>();
		if (enchs == null || enchs.isEmpty())
			return toReturn;
		for (Enchantment e : enchs.keySet()) {
			toReturn.add(e.getName() + "@" + enchs.get(e));
		}
		return toReturn;
	}

	public static Map<Enchantment, Integer> stringsToEnchants(List<String> str) {
		Map<Enchantment, Integer> toReturn = new HashMap<Enchantment, Integer>();
		if (str == null || str.isEmpty())
			return toReturn;
		for (String s : str) {
			String[] ss = s.split("@");
			toReturn.put(Enchantment.getByName(ss[0]), Integer.parseInt(ss[1]));
		}
		return toReturn;
	}

	public static List<String> effectsToString(List<PotionEffect> effs) {
		List<String> toReturn = new ArrayList<String>();
		if (effs == null || effs.isEmpty())
			return toReturn;
		for (PotionEffect e : effs) {
			toReturn.add(e.getType().toString() + "@" + e.getDuration() + "@" + e.getAmplifier() + "@" + e.isAmbient() + "@" + e.hasParticles());
		}
		return toReturn;
	}

	public static List<PotionEffect> stringsToEffects(List<String> str) {
		List<PotionEffect> toReturn = new ArrayList<PotionEffect>();
		if (str == null || str.isEmpty())
			return toReturn;
		for (String s : str) {
			String[] ss = s.split("@");
			PotionEffect e = new PotionEffect(PotionEffectType.getByName(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), Boolean.parseBoolean(ss[3]), Boolean.parseBoolean(ss[4]));
			toReturn.add(e);
		}
		return toReturn;
	}

	public static List<Integer> colorsToString(List<Color> cols) {
		List<Integer> toReturn = new ArrayList<Integer>();
		if (cols == null || cols.isEmpty())
			return toReturn;
		for (Color c : cols) {
			toReturn.add(c.asRGB());
		}
		return toReturn;
	}

	public static List<Color> stringsToColors(List<String> cols) {
		List<Color> toReturn = new ArrayList<Color>();
		if (cols == null || cols.isEmpty())
			return toReturn;
		for (String c : cols) {
			toReturn.add(Color.fromRGB(Integer.parseInt(c)));
		}
		return toReturn;
	}



	public static ItemStack YamlToItem(String s, YamlConfiguration yaml) {
		ItemStack is = new ItemStack(Material.getMaterial(yaml.getString(s + ".type")), Integer.parseInt(yaml.getString(s + ".amount")), Short.parseShort(yaml.getString(s + ".data")));
		is.addUnsafeEnchantments(stringsToEnchants(yaml.getStringList(s + ".enchants")));
		ItemMeta im = is.getItemMeta();
		if (yaml.get(s + ".name") != null)
			im.setDisplayName(yaml.getString(s + ".name"));
		if (yaml.get(s + ".lore") != null)
			im.setLore(yaml.getStringList(s + ".lore"));
		if (yaml.get(s + ".armorMeta") != null)
			((LeatherArmorMeta) im).setColor(Color.fromRGB(yaml.getInt(s + ".armorMeta.color")));
		if (yaml.get(s + ".enchantmentStorageMeta") != null) {
			Map<Enchantment, Integer> map = stringsToEnchants(yaml.getStringList(s + ".enchantmentStorageMeta.storedEnchants"));
			for (Enchantment e : map.keySet())
				((EnchantmentStorageMeta) im).addStoredEnchant(e, map.get(e), true);
		}
		if (yaml.get(s + ".potionMeta") != null) {
			for (PotionEffect pe : stringsToEffects(yaml.getStringList(s + ".potionMeta.effects"))) {
				((PotionMeta) im).addCustomEffect(pe, true);
			}
		}
		if (yaml.get(s + ".skullMeta") != null)
			((SkullMeta) im).setOwner(yaml.getString(s + ".skullMeta.owner"));
		if (yaml.get(s + ".fireworkMeta") != null) {
			FireworkMeta fm = (FireworkMeta) im;
			ConfigurationSection cs = yaml.getConfigurationSection(s + ".fireworkMeta");
			fm.setPower(yaml.getInt(s + ".fireworkMeta.power"));
			for (String fe : cs.getKeys(false)) {
				if (fe.equalsIgnoreCase("power"))
					continue;
				FireworkEffect f = FireworkEffect.builder().with(FireworkEffect.Type.valueOf(cs.getString(fe + ".type"))).withColor(stringsToColors(cs.getStringList(fe + ".colors"))).withFade(stringsToColors(cs.getStringList(fe + ".fadeColors"))).trail(cs.getBoolean(fe + ".trail")).flicker(cs.getBoolean(fe + ".trail")).build();
				fm.addEffect(f);
			}
		}
		if (yaml.get(s + ".bookMeta") != null) {
			BookMeta bm = (BookMeta) im;
			bm.setAuthor(yaml.getString(s + ".bookMeta.author"));
			bm.setTitle(yaml.getString(s + ".bookMeta.title"));
			bm.setPages(yaml.getStringList(s + ".bookMeta.pages"));
		}
		is.setItemMeta(im);

		return is;

	}



	public static void ItemToYaml(long id, YamlConfiguration yaml, ItemStack item, Double price) {
		yaml.set(id + ".type", item.getType().toString());
		yaml.set(id + ".data", item.getDurability());
		yaml.set(id + ".amount", item.getAmount());
		yaml.set(id + ".totalAmount", item.getAmount());
		yaml.set(id + ".price", price);
		yaml.set(id + ".startDate", System.currentTimeMillis());
		yaml.set(id + ".enchants", Market.enchantsToString(item.getEnchantments()));
		if (item.hasItemMeta()) {
			ItemMeta im = item.getItemMeta();
			if (im.hasDisplayName())
				yaml.set(id + ".name", im.getDisplayName());
			if (im.hasLore())
				yaml.set(id + ".lore", im.getLore());
			if (im instanceof LeatherArmorMeta) {
				LeatherArmorMeta lam = (LeatherArmorMeta) im;
				yaml.set(id + ".armorMeta.color", lam.getColor().asRGB());
			}
			if (im instanceof PotionMeta) {
				PotionMeta pm = (PotionMeta) im;
				yaml.set(id + ".potionMeta.effects", Market.effectsToString(pm.getCustomEffects()));
			}
			if (im instanceof SkullMeta) {
				SkullMeta sm = (SkullMeta) im;
				if (sm.hasOwner())
					yaml.set(id + ".skullMeta.owner", sm.getOwner());
			}
			if (im instanceof EnchantmentStorageMeta) {
				EnchantmentStorageMeta esm = (EnchantmentStorageMeta) im;
				if (esm.hasStoredEnchants())
					yaml.set(id + ".enchantmentStorageMeta.storedEnchants", Market.enchantsToString(esm.getStoredEnchants()));
			}
			if (im instanceof FireworkMeta) {
				FireworkMeta fm = (FireworkMeta) im;
				yaml.set(id + ".fireworkMeta.power", fm.getPower());
				if (fm.hasEffects()) {
					for (int f = 0; f < fm.getEffects().size(); f++) {
						FireworkEffect fe = fm.getEffects().get(f);
						yaml.set(id + ".fireworkMeta." + f + ".type", fe.getType().toString());
						yaml.set(id + ".fireworkMeta." + f + ".colors", Market.colorsToString(fe.getColors()));
						yaml.set(id + ".fireworkMeta." + f + ".fadeColors", Market.colorsToString(fe.getFadeColors()));
						yaml.set(id + ".fireworkMeta." + f + ".flicker", fe.hasFlicker());
						yaml.set(id + ".fireworkMeta." + f + ".trail", fe.hasTrail());
					}
				}
			}
			if (im instanceof BookMeta) {
				BookMeta bm = (BookMeta) im;
				if (bm.hasAuthor())
					yaml.set(id + ".bookMeta.author", bm.getAuthor());
				if (bm.hasTitle())
					yaml.set(id + ".bookMeta.title", bm.getTitle());
				if (bm.hasPages())
					yaml.set(id + ".bookMeta.pages", bm.getPages());
			}
		}
	}

}
