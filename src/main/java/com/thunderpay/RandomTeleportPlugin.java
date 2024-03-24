package com.thunderpay;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import java.util.Random;

public class RandomTeleportPlugin extends JavaPlugin implements CommandExecutor {

    private int minX = -100; // Configura los límites del área de teletransporte
    private int maxX = 100;
    private int minZ = -100;
    private int maxZ = 100;
    private int minY = 0;
    private int maxY = 256;

    @Override
    public void onEnable() {
        // Registrar el comando en el plugin
        this.getCommand("tpa").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            // Teletransportar al jugador a una ubicación aleatoria dentro del área especificada
            Location randomLocation = getRandomLocation();
            if (randomLocation != null) {
                player.teleport(randomLocation);
                player.sendMessage("¡Te has teletransportado a una ubicación aleatoria!");
            } else {
                player.sendMessage("No se pudo encontrar una ubicación segura para teletransportarte.");
            }
            return true;
        }
        return false;
    }

    private Location getRandomLocation() {
        Random random = new Random();
        int x = random.nextInt(maxX - minX + 1) + minX;
        int z = random.nextInt(maxZ - minZ + 1) + minZ;
        int y = random.nextInt(maxY - minY + 1) + minY;

        Location randomLocation = new Location(getServer().getWorlds().get(0), x, y, z);
        // Verificar si la ubicación es segura (no en bloques sólidos)
        if (randomLocation.getBlock().getType().isSolid()) {
            return null;
        }
        return randomLocation;
    }
}

