package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.Character;

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
    public void launchAttack(Character mainChar) {
        if (this.doAction()) {
            super.launchAttack(mainChar);

            // TODO Critical bite stuff
        }
    }
}
