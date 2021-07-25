package unsw.loopmania.enemies;

import unsw.loopmania.path.PathPosition;
import java.util.Random;
import unsw.loopmania.character.Character;

public class DoggieEnemy extends Enemy {
    /**
     * Doggie enemy simply uses basic enemy behaviour
     */
    public DoggieEnemy(PathPosition position) {
        super(position, 110, 2, 2, 25, 20);
    }

    /**
     * Uses parent class attack functionality while adding special 'stun attack'
     * @param mainChair = Main Character
     */
    @Override
    public boolean launchAttack(Character mainChar) {
        // Perform basic attack first
        super.launchAttack(mainChar);
        if (new Random().nextInt(100) <= 30) {
            mainChar.receiveStunAttack();
        }
        return false;
    }
}
