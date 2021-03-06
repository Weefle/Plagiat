package net.samagames.plagiat.modules.sheepwars.sheeps;

import net.samagames.plagiat.Plagiat;
import net.samagames.plagiat.modules.sheepwars.WoolType;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/*
 * This file is part of Plagiat.
 *
 * Plagiat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Plagiat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plagiat.  If not, see <http://www.gnu.org/licenses/>.
 */
public class BlindnessSheep extends WoolType
{
    private Map<Integer, BukkitTask> blindnessTask;

    /**
     * Blindness Sheep constructor
     *
     * @param plagiat Plagiat plugin's instance
     */
    public BlindnessSheep(Plagiat plagiat)
    {
        super(plagiat, DyeColor.BLACK, ChatColor.BLACK, "ténébreux");
        this.blindnessTask = new HashMap<>();
    }

    /**
     * Event method on sheep land
     * Overridden to start blindness task
     *
     * @param sheep The sheep entity
     */
    @Override
    protected void onLand(Sheep sheep)
    {
        this.blindnessTask.put(sheep.getEntityId(), this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> sheep.getWorld().getNearbyEntities(sheep.getLocation(), 6D, 6D, 6D).forEach(entity ->
        {
            if (entity instanceof Player)
                ((Player)entity).addPotionEffect(PotionEffectType.BLINDNESS.createEffect(80, 1));
        }), 20L, 20L));
    }

    /**
     * Event method on sheep death
     *
     * @param sheep The sheep entity
     * @param killer The sheep killer, or null
     */
    @Override
    protected void onDeath(Sheep sheep, @Nullable Player killer)
    {
        BukkitTask bukkitTask = this.blindnessTask.remove(sheep.getEntityId());
        if (bukkitTask != null)
            bukkitTask.cancel();
    }
}
