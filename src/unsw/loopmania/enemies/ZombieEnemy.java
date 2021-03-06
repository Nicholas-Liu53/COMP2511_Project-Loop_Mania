package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.Soldier;

/**
 * Zombie enemy type
 */
public class ZombieEnemy extends Enemy {
    // Attributes
    private int speedDelay;

    /**
     * Creates a new zombie enemey in the game world at specified path position
     * 
     * @param position
     */
    public ZombieEnemy(PathPosition position) {
        super(position, 50, 3, 3, 10, 10);
        // Setting up speedDelay
        this.speedDelay = 2;
    }

    /**
     * Used to simulate slower speed of the zombie, will only perform an action once
     * out of every three times it's attack and movement methods are called slowing
     * down movement and attacks
     * 
     * @return true if the zombie is ready to do an action, otherwise false
     */
    private boolean doAction() {
        if (this.speedDelay == 0) {
            this.speedDelay = 2;
            return true;
        } else {
            this.speedDelay -= 1;
            return false;
        }
    }

    @Override
    public void move() {
        if (this.doAction()) {
            super.move();
        }
    }

    @Override
    public boolean launchAttack(Character mainChar) {
        if (this.doAction()) {
            super.launchAttack(mainChar);

            // Citical bite implementation
            int criticalCheck = (new Random()).nextInt(10);
            if (criticalCheck == 5 && (mainChar.getNumAllies() > 0)) {
                // Remove soldier and spawn new zombie, only works on soldiers
                if (mainChar.getAllies().get(0) instanceof Soldier) {
                    mainChar.removeAlly();
                    // Notifying caller that zombie must be spawned
                    return true;
                }
            }
        }

        return false;
    }
}
