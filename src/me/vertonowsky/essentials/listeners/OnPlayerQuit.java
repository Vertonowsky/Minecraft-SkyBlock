package me.vertonowsky.essentials.listeners;

import me.vertonowsky.essentials.commands.CommandVanish;
import me.vertonowsky.mysql.MySQL;
import me.vertonowsky.mysql.Quests;
import me.vertonowsky.mysql.Users;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class OnPlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		User pUUID = User.get(p.getUniqueId());
		e.setQuitMessage("§8§l>> §c§lGracz §7§l" + p.getName() + " §c§lwyszedł z gry!");
		if (CommandVanish.vanish.contains(p.getUniqueId())) {
			CommandVanish.vanish.remove(p.getUniqueId());
		}
		
		for (UUID u : CommandVanish.vanish) {
			if (Bukkit.getPlayer(u) != null) {
				p.showPlayer(Bukkit.getPlayer(u));
			}
		}

		pUUID.setLastQuit(System.currentTimeMillis());

		MySQL.openConnection();
		Quests.saveQuestsPlayer(p, true);
		Users.saveDataUserSkyBlock(pUUID);
		MySQL.closeConnection();

		Users.saveDataUserGeneralOne(pUUID);

	}

}
