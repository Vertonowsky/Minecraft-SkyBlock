package me.vertonowsky.essentials.listeners;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.vertonowsky.api.Scoreboard;
import me.vertonowsky.enums.QuestState;
import me.vertonowsky.essentials.commands.CommandPvp;
import me.vertonowsky.events.PlayerQuestCompleteEvent;
import me.vertonowsky.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		User pUUID = User.get(p.getName());
		pUUID.setDeaths(pUUID.getDeaths() + 1);

		if (pUUID.getSouls() >= 1) {
			pUUID.setSouls(pUUID.getSouls() -1);
			e.setKeepInventory(true);
			e.setKeepLevel(true);
			e.setDroppedExp(0);
			p.sendMessage("§8§l>> §cZginąłeś, ale nie straciłeś żadnych przedmiotów. §8[§7-1 dusza§8]");
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
			Scoreboard.setScoreboard(p);
		}

		if (pUUID.getSouls() == 0) {
			pUUID.setSouls(-1);
		}



		if (e.getEntity().getKiller() instanceof Player) {
			Player t = e.getEntity().getKiller();
			User tUUID = User.get(t.getName());
			tUUID.setKills(tUUID.getKills() + 1);
			if (CommandPvp.bar.getPlayers().contains(p)) {
				CommandPvp.bar.removePlayer(p);
				pUUID.setPvp(false);
				p.setGlowing(false);
			}


			if (ASkyBlockAPI.getInstance().hasIsland(p.getUniqueId())) {
				if (ASkyBlockAPI.getInstance().isOnIsland(p, t)) {
					if (tUUID.getTotalLevel() >= 45) {
						if (tUUID.getZadanie(6).equals(QuestState.W_TRAKCIE))
							Bukkit.getServer().getPluginManager().callEvent(new PlayerQuestCompleteEvent(tUUID, 6, QuestState.WYKONANE));
					}
				}
			}
		}

		e.setDeathMessage("");

	}

}
