package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

/**
 * Goal checking whether a character has completed a certain number
 * of cycles
 */
public class CyclesBaseGoal implements ComplexGoalComponent {
    private int achievementThreshold;

    public CyclesBaseGoal(int achievementThreshold) {
        this.achievementThreshold = achievementThreshold;
    }

    public boolean achieved(LoopManiaWorld world) {
        if (world.getCurrCycle() >= this.achievementThreshold)
            return true;
        else
            return false;
    }
}
