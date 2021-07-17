package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

public class GoldBaseGoal implements ComplexGoalComponent {
    private int achievementThreshold;

    public GoldBaseGoal(int achievementThreshold) {
        this.achievementThreshold = achievementThreshold;
    }

    public boolean achieved(LoopManiaWorld world) {
        if (world.getGold() >= this.achievementThreshold)
            return true;
        else
            return false;
    }
}
