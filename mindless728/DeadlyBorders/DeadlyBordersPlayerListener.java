package mindless728.DeadlyBorders;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;

public class DeadlyBordersPlayerListener extends PlayerListener {
	private DeadlyBorders plugin;

	public DeadlyBordersPlayerListener(DeadlyBorders p) {
		plugin = p;
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location to = event.getTo();
		WorldConfig wc = plugin.borders.get(to.getWorld());
		if(wc == null)
			wc = plugin.defaultBorder;

		//test to see if the player moves out of the border, if so kill them
		if((to.getBlockX() > wc.maxX) ||
		   (to.getBlockX() < wc.minX) ||
		   (to.getBlockZ() > wc.maxZ) ||
		   (to.getBlockZ() < wc.minZ)) {
			player.getInventory().clear(); //clear inventory
			player.setHealth(0); //kill the player
		}
	}
}
