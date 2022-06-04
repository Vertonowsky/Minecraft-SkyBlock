package me.vertonowsky.essentials.listeners;

import me.vertonowsky.api.API;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import me.vertonowsky.mysql.Users;
import me.vertonowsky.permissions.PermissionVert;
import me.vertonowsky.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (User.get(p.getUniqueId()) == null) {
			new User(p.getUniqueId(), p.getName());
		}
		User pUUID = User.get(p.getUniqueId());
		MySQL.openConnection();
		Users.loadDataUserGeneralOne(pUUID);
		MySQL.closeConnection();

		MySQL.openConnection();
		Quests.saveQuestsPlayer(p, false);
		Quests.loadQuestsPlayer(p);
		Quests.repairPlayersQuests(p);
		MySQL.closeConnection();


		for (int x = 0; x < 100; x++) {
			p.sendMessage("");
		}

		p.sendMessage("§8>> §3Witaj na Prestigemc.pl :)");

		if (!p.hasPlayedBefore()) {
			e.setJoinMessage("§8§l>> §2Witamy nowego gracza §a" + p.getName() + "§2!");
		} else {
			e.setJoinMessage("§8§l>> §b§lGracz §7§l" + p.getName() + " §b§ldołączył do gry!");
		}

		p.setPlayerListName("§7" + p.getName() + " §8[" + API.getRankColor(pUUID.getRank()) + pUUID.getRank() + "§8]");



		/*HashMap<Integer, ItemStack> inventory = new HashMap<Integer, ItemStack>();
		for (int i = 0; i <= 40; i++) {
			if (p.getInventory().getItem(i) != null) {
				inventory.put(i, p.getInventory().getItem(i));
			}
		}
		p.getInventory().clear();

		/*
		Change skin

		Skin.changeSkin((CraftPlayer)p, p.getName());

		for (Map.Entry<Integer, ItemStack> entry : inventory.entrySet()) {
			p.getInventory().setItem(entry.getKey(), entry.getValue());
		}

		 */




		/*

		Check if players rank has already expired - if yes, then change it back to default.

		Then update permissions.

		 */

		if (pUUID.getRankExpireDate() > 0 && System.currentTimeMillis() > pUUID.getRankExpireDate()) {
			pUUID.setRank(PermissionVert.defaultRank);
			PermissionVert.setRank(pUUID, pUUID.getRank(), -2, false);
		} else {
			PermissionVert.setRank(pUUID, pUUID.getRank(), -1, true);
		}

		Scoreboard.setScoreboard(p);

	}
}
