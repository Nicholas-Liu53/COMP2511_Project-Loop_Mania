package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

/**
 * Goal checking whether a character has met a certain level of experience
 */
public class XpBaseGoal implements ComplexGoalComponent {
    private int achievementThreshold;

    public XpBaseGoal(int achievementThreshold) {
        this.achievementThreshold = achievementThreshold;
    }

    @Override
    public boolean achieved(LoopManiaWorld world) {
        if (world.getCharacterXp() >= this.achievementThreshold)
            return true;
        else
            return false;
    }

    @Override
    public int getAchievementThreshold() {
        return this.achievementThreshold;
    }
}
