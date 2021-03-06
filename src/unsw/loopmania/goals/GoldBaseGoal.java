package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

/**
 * Goal checking whether a character has acquired a certain amount of gold
 */
public class GoldBaseGoal implements ComplexGoalComponent {
    private int achievementThreshold;

    public GoldBaseGoal(int achievementThreshold) {
        this.achievementThreshold = achievementThreshold;
    }

    @Override
    public boolean achieved(LoopManiaWorld world) {
        if (world.getGold() >= this.achievementThreshold)
            return true;
        else
            return false;
    }

    @Override
    public String getGoalString() {
        return (String.valueOf(this.achievementThreshold) + " Gold");
    }
}
