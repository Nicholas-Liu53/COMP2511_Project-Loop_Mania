package unsw.loopmania.goals;

import java.util.ArrayList;

import unsw.loopmania.LoopManiaWorld;

public class ComplexGoalComposite implements ComplexGoalComponent {
    
    private ArrayList<ComplexGoalComponent> children;

    public ComplexGoalComposite() {
        this.children = new ArrayList<>();
    }

    /**
     * Adds a component to the compex goal
     * 
     * @param component
     */
    public void add(ComplexGoalComponent component) {
        this.children.add(component);
    }

    /**
     * Removes a component from the complex goal
     * 
     * @param component
     */
    public void remove(ComplexGoalComponent component) {
        this.children.remove(component);
    }

    /**
     * @return list of children goals
     */
    public ArrayList<ComplexGoalComponent> getChildren() {
        return this.children;
    }

    /**
     * @return true is goals have been met, else false
     */
    public boolean achieved(LoopManiaWorld world) {
        return false;
    }
}
