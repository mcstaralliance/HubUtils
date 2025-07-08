package com.mcstaralliance.hubutils.listener;

import com.mcstaralliance.hubutils.HubUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            String msg = event.getMessage();
            if (msg.contains("/minecraft:"))
                event.setCancelled(true);
            if (msg.contains("/bukkit:"))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack stack = event.getItem();
            if (stack != null && stack.getType() == Material.CLOCK) {
                event.setCancelled(true);
                event.getPlayer().chat("/menu");
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(Bukkit.getWorld("world"), 139.0D, 13.0D, 635.5D, 0.0F, 0.0F));
        HubUtils.ghostTeam.addEntry(player.getName());
        if (!player.hasPermission("hubutils.invisibles"))
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0, false, false));
        giveServerSelector(player);
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HubUtils.ghostTeam.removeEntry(player.getName());
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        giveServerSelector(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    private void giveServerSelector(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        ItemStack stack = new ItemStack(Material.CLOCK);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§b§l星域世界 §a§l彩虹桥§7(右键打开)");
        meta.setLore(Arrays.asList("§f§l听说这个菜单是通往异世界大门的钥匙哦"));
        stack.setItemMeta(meta);
        player.getInventory().setItem(4, stack);
        player.getInventory().setHeldItemSlot(4);
  }
}
