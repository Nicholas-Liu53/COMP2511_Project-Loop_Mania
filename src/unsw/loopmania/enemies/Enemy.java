package unsw.loopmania.enemies;

import java.util.Random;

import unsw.loopmania.MovingEntity;
import unsw.loopmania.path.PathPosition;

public abstract class Enemy extends MovingEntity {
    // TODO = modify this, and add additional forms of enemy
    public Enemy(PathPosition position) {
        super(position);
    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }
}