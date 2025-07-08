package com.mcstaralliance.hubutils.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingListener implements Listener {
    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        event.setCancelled(true);
    }
}
