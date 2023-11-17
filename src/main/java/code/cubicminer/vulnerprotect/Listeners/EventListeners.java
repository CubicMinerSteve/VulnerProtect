package code.cubicminer.vulnerprotect.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import code.cubicminer.vulnerprotect.ConfigReader;
import code.cubicminer.vulnerprotect.MessageHandler;

public class EventListeners implements Listener {

	@EventHandler
	public void flowerPotInteractEvent(PlayerInteractEvent event) {
		if (event.getPlayer().hasPermission("vulnerprotect.admin")) {
			return;
		}
		if (event.getClickedBlock() == null) {
			return;
		}
		if (ConfigReader.whitelistedWorld.contains(event.getClickedBlock().getWorld().toString())) {
			return;
		}
		Material targetMaterial = event.getClickedBlock().getBlockData().getMaterial();
		if (targetMaterial.toString().contains("POTTED") || targetMaterial.toString().equals("FLOWER_POT")) {
			event.setCancelled(true);
			if (ConfigReader.isMessageHintEnabled) {
				event.getPlayer().sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
			}
		}
		return;
	}

	@EventHandler
	public void itemFrameInteractEvent(PlayerInteractEntityEvent event) {
		if (event.getPlayer().hasPermission("vulnerprotect.admin")) {
			return;
		}
		if (ConfigReader.whitelistedWorld.contains(event.getRightClicked().getWorld().toString())) {
			return;
		}
		if (event.getRightClicked() instanceof ItemFrame) {
			event.setCancelled(true);
			if (ConfigReader.isMessageHintEnabled) {
				event.getPlayer().sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
			}
		}
	}
	
	@EventHandler
	public void hangingEntityDamageEvent(HangingBreakByEntityEvent event) {
		Entity remover = event.getRemover();
		if (remover.hasPermission("vulnerprotect.admin")) {
			return;
		}
		if (ConfigReader.whitelistedWorld.contains(event.getEntity().getWorld().toString())) {
			return;
		}
		if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting) {
        	event.setCancelled(true);
			if (remover instanceof Player && ConfigReader.isMessageHintEnabled) {
				remover.sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
			}
    	}
	}

	@EventHandler
	public void armorStandDamageEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager().hasPermission("vulnerprotect.admin")) {
			return;
		}
		if (ConfigReader.whitelistedWorld.contains(event.getEntity().getWorld().toString())) {
			return;
		}
		if (event.getEntity() instanceof ArmorStand) {
			Entity damager = event.getDamager();
			event.setCancelled(true);
			if (damager instanceof Player && ConfigReader.isMessageHintEnabled) {
				damager.sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
			}
		}
	}

	@EventHandler 
	public void armorStandInteractEvent(PlayerArmorStandManipulateEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("vulnerprotect.admin")) {
			return;
		}
		if (ConfigReader.whitelistedWorld.contains(event.getRightClicked().getWorld().toString())) {
			return;
		}
		event.setCancelled(true);
		if (ConfigReader.isMessageHintEnabled)
		{
			player.sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
		}
	}

	@EventHandler
    public void entityFarmlandTrampleEvent(EntityInteractEvent event) {
        Block block = event.getBlock();
        if (block != null && block.getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerFarmlandTrampleEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("vulnerprotect.admin")) {
			return;
		}
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.FARMLAND) {
                event.setCancelled(true);
				if (ConfigReader.isMessageHintEnabled)
				{
					player.sendMessage((String)MessageHandler.loadedMsg.get("Event-Cancelled"));
				}	
            }
        }
    }

}