package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class SlugEnemy extends Enemy {
    
    // Slug enemy simply uses basic enemy behaviour
    public SlugEnemy(PathPosition position) {
        super(position, 25, 2, 2, 3, 0);
    }
}
