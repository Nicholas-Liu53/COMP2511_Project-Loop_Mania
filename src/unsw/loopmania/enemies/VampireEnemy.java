package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.path.PathPosition;
import unsw.loopmania.character.Character;
import unsw.loopmania.items.Shield;

/**
 * Vampire enemy type
 */
public class VampireEnemy extends Enemy {
    // Attributes
    private boolean criticalAttack;
    private int criticalAttackDmg;
    private int criticalAttackUses;
    private int criticalAttackUsesMax;

    // Basic constructor
    public VampireEnemy(PathPosition position) {
        super(position, 75, 4, 3, 15, 20);
        // Sets up special critical attack values
        this.criticalAttack = false;
        this.criticalAttackDmg = 0;
        this.criticalAttackUses = 0;
        this.criticalAttackUsesMax = 0;
    }

    /**
     * Uses parent class attack functionality while adding special 'critical bite'
     * behaviour that vampires must use
     */
    @Override
    public boolean launchAttack(Character mainChar) {
        // Perform basic attack first
        super.launchAttack(mainChar);

        // Use critical attack logic
        if (this.criticalAttack) {
            // Attacks character a second time with bonus damage
            mainChar.receiveAttack(this, this.criticalAttackDmg);
            this.criticalAttackUses++;

            // Critical attack has been used max number of times
            if (this.criticalAttackUses >= this.criticalAttackUsesMax) {
                this.criticalAttack = false;
                this.criticalAttackUses = 0;
            }
        } else {
            // Checks if vampire is making a critical attack in the next attack
            // 10% chance without shield, 4% with
            int criticalCheck;

            if (mainChar.getShield() instanceof Shield)
                criticalCheck = (new Random()).nextInt(25);
            else
                criticalCheck = (new Random()).nextInt(10);

            if (criticalCheck == 5) {
                // Sets critical attack variables in preparation
                this.criticalAttack = true;
                this.criticalAttackUsesMax = (new Random()).nextInt(3) + 1;
                this.criticalAttackDmg = (new Random()).nextInt(6) + 5;
            }
        }

        return false;
    }
}