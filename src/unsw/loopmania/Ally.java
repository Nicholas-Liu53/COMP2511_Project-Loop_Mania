package unsw.loopmania;

import org.javatuples.Pair;

import unsw.loopmania.enemies.Enemy;

public class Ally extends StaticEntity {
    private int health;
    private int damage;

    public Ally(Pair<Integer, Integer> position) {
        super(position);
        this.damage = 5;
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * Allows ally to launch attack against an enemy
     * 
     * @param enemy
     */
    public void launchAttack(Enemy enemy) {
        enemy.receiveAttack(this.damage);
    }

    /**
     * Allows ally to receive attack from enemy
     * 
     * @param damage
     */
    public void receiveAttack(int damage) {
        this.health = this.health - this.damage;
        
        if (this.health < 0) {
            this.health = 0;
        }
    }
}
