package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.character.Character;
import unsw.loopmania.enemies.Enemy;

/**
 * represents an equipped or unequipped anduril in the backend world
 */
public class Anduril extends Item implements WeaponStrategy {
    public Anduril(Pair<Integer, Integer> position) {
        super(position);
    }

    public Anduril() {
        super(new Pair<Integer, Integer>(1, 2));
    }

    public void launchAttack(Enemy enemy, int baseDamage, Character mainChar) {
        // Causes triple damage against bosses
        enemy.receiveAttack(baseDamage + 15);
    }
}
