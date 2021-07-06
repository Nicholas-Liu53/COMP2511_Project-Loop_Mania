package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.path.PathPosition;

/**
 * vampire enemy type
 */
public class VampireEnemy extends Enemy {
    // Attributes
    boolean criticalAttack;
    int criticalAttackDmg;
    int criticalAttackUses;
    int criticalAttackUsesMax;
    
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
     * Uses parent class attack functionality while adding special
     * 'critical bite' behaviour that vampires must use
     */
    @Override
    public void launchAttack(Character mainChar) {
        // Perform basic attack first
        super.launchAttack(mainChar);

        // Use critical attack logic
        if (this.criticalAttack) {
            // Attacks character a second time with bonus damage
            mainChar.receiveAttack(this.criticalAttackDmg);
            this.criticalAttackUses++;
            
            // Critical attack has been used max number of times
            if (this.criticalAttackUses >= this.criticalAttackUsesMax) {
                this.criticalAttack = false;
                this.criticalAttackUses = 0;
            }
        } else {
            // Checks if vampire is making a critical attack in the next attack
            int criticalCheck = (new Random()).nextInt(10);
            if (criticalCheck == 5) {
                // Sets critical attack variables in preparation
                this.criticalAttack = true;
                this.criticalAttackUsesMax = (new Random()).nextInt(3) + 1;
                this.criticalAttackDmg = (new Random()).nextInt(6) + 5;
            }
        }
    }
}