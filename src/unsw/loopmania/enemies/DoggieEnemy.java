package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;

public class DoggieEnemy extends Enemy {
     /**
     * Doggie enemy simply uses basic enemy behaviour
     */
    public DoggieEnemy(PathPosition position) {
        super(position, 110, 2, 2, 25, 20);
    }
}
