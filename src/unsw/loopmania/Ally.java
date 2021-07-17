package unsw.loopmania;

import unsw.loopmania.enemies.Enemy;

public interface Ally {
    
    public void launchAttack(Enemy enemy);

    public void receiveAttack(int damage);

    public int getHealth();
}
