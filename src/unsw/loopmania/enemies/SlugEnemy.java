package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;

/**
 * Slug enemy type
 */
public class SlugEnemy extends Enemy {
    /**
     * Slug enemy simply uses basic enemy behaviour
     */
    public SlugEnemy(PathPosition position) {
        super(position, 25, 2, 2, 3, 0);
    }
}
