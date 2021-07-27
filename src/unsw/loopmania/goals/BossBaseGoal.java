package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.enemies.DoggieEnemy;
import unsw.loopmania.enemies.ElanMuskeEnemy;
import unsw.loopmania.enemies.Enemy;

public class BossBaseGoal implements ComplexGoalComponent {
    
    @Override
    public boolean achieved(LoopManiaWorld world) {
        // If more than 40 cycles and 10000 XP and no bosses exist
        // on the map then all bosses must have been defeated once
        if (world.getCharacterXp() > 10000 && world.getCurrCycle() > 40) {
            for (Enemy e : world.getEnemiesList()) {
                // Boss is on map
                if (e instanceof ElanMuskeEnemy || e instanceof DoggieEnemy) return false;
            }
            // Bosses have been defeated
            return true;
        } 

        return false;
    }

    @Override
    public String getGoalString() {
        return "Defeat Bosses";
    }
}
