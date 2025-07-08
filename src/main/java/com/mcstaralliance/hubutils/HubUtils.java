package com.mcstaralliance.hubutils;

import com.mcstaralliance.hubutils.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class HubUtils extends JavaPlugin implements Listener {
    public static Plugin plugin;

    public static Team ghostTeam;
    @Override
    public void onEnable() {
        plugin = (Plugin)this;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        ghostTeam = scoreboard.getTeam("Ghosts");
        if (ghostTeam == null) {
            ghostTeam = scoreboard.registerNewTeam("Ghosts");
            ghostTeam.setAllowFriendlyFire(false);
            ghostTeam.setCanSeeFriendlyInvisibles(true);
            ghostTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            ghostTeam.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.NEVER);
        }
        Bukkit.getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new EntityListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new HangingListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new WeatherListener(), (Plugin)this);
    }

    @Override
    public void onDisable() {
        ghostTeam.unregister();
    }
}
