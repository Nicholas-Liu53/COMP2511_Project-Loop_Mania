package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

public interface ComplexGoalComponent {

    /**
     * Takes in world state and returns whether goals have been met
     * 
     * @param world
     * @return true if the goal has been achieved
     */
    public boolean achieved(LoopManiaWorld world);

    /**
     * Generates user readable string describing goals
     * 
     * @return string describing goals
     */
    public String getGoalString();
}
