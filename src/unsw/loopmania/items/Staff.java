package unsw.loopmania.items;

import java.util.Random;
import org.javatuples.Pair;

import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Item implements WeaponStrategy {
    public Staff(Pair<Integer, Integer> position) {
        super(position);
    }

    public Staff() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        enemy.receiveAttack(baseDamage + 3);

        // Staff performs critical attack, putting enemy in trance
        int criticalCheck = (new Random()).nextInt(4);
        if (criticalCheck == 1) {
            mainChar.addTrancedEnemy(enemy);
        }
    }

    public static int getPurchasePrice() {
        return 300;
    }

    public static int getSellPrice() {
        return 240;
    }
}